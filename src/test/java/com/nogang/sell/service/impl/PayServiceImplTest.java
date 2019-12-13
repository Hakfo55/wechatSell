package com.nogang.sell.service.impl;

import com.lly835.bestpay.model.PayRequest;
import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.dto.OrderDTO;
import com.nogang.sell.service.OrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class PayServiceImplTest extends SellApplicationTests {
    @Autowired
    private PayServiceImpl payService;
    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1571934431092627980");
        payService.create(orderDTO);
    }
}