package com.nogang.sell.service.impl;

import com.google.gson.Gson;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.nogang.sell.dto.OrderDTO;
import com.nogang.sell.enums.ResultEnum;
import com.nogang.sell.exception.SellException;
import com.nogang.sell.service.PayService;
import com.nogang.sell.utils.JsonUtil;
import com.nogang.sell.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】发起支付，request={}", JsonUtil.toJosn(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，response={}",JsonUtil.toJosn(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名    sdk已经帮我们验证了，无须担心
        //2.支付的状态  sdk已经帮我们验证了，无须担心
        //3.支付的金额  自己需要判断
        //4.支付人（下单人 == 支付人）看业务要求

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，response={}",JsonUtil.toJosn(payResponse));

        //查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if(orderDTO == null){
            log.error("【微信支付】异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断金额是否一致
        if (!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】异步通知，订单金额不一致，orderId={}，微信通知的金额={}，系统金额={}",
                    payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //检验都没问题之后修改订单的支付状态
        orderService.pay(orderDTO);
        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】request={}",JsonUtil.toJosn(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}",JsonUtil.toJosn(refundResponse));
        return refundResponse;
    }
}
