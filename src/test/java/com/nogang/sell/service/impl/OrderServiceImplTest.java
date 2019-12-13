package com.nogang.sell.service.impl;

import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.dto.CartDTO;
import com.nogang.sell.dto.OrderDTO;
import com.nogang.sell.entity.OrderDetail;
import com.nogang.sell.enums.OrderStatusEnum;
import com.nogang.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class OrderServiceImplTest extends SellApplicationTests {
    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "oJ2o_1kq2GZKqeH3m41ZwRR4gqWY";
    private final String ORDER_ID = "1571887991638908586";

    @Test
    public void create() {
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

        OrderDetail three = new OrderDetail();
        three.setProductId("1572840969598166415");
        three.setProductQuantity(1);
        orderDetailList.add(three);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        log.info("【创建订单】result={}",result);
        assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("【查询单个订单】result={}",result);
        assertEquals(ORDER_ID,result.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = PageRequest.of(0,4);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,pageRequest);
        assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.pay(orderDTO);
        assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void testFindList() {
        PageRequest pageRequest = PageRequest.of(0,4);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);
//        assertNotEquals(0,orderDTOPage.getTotalElements());
        assertTrue("查询所有订单列表",orderDTOPage.getTotalElements()<0);
    }
}