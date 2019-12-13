package com.nogang.sell.service.impl;

import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.dto.OrderDTO;
import com.nogang.sell.entity.OrderDetail;
import com.nogang.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class PushMessageServiceImplTest extends SellApplicationTests {
    private static final String BUYER_OPENID = "oJ2o_1kq2GZKqeH3m41ZwRR4gqWY";
    @Autowired
    private PushMessageServiceImpl pushMessageService;
    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderService.findOne("1572592570588106229");
        pushMessageService.orderStatus(orderDTO);
    }

    @Test
    public void newOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("广州");
        orderDTO.setBuyerName("陈宇健");
        orderDTO.setBuyerPhone("13413403089");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail one = new OrderDetail();
        one.setProductId("1572840903754213222");
        one.setProductQuantity(1);
        orderDetailList.add(one);

        OrderDetail two = new OrderDetail();
        two.setProductId("1572840932200282045");
        two.setProductQuantity(1);
        orderDetailList.add(two);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}",result);
        assertNotNull(result);


        OrderDTO orderDTO1 = orderService.findOne(result.getOrderId());
        pushMessageService.newOrder(orderDTO1);
    }
}