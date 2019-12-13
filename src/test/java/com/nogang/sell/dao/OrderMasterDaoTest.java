package com.nogang.sell.dao;

import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.entity.OrderMaster;
import org.junit.Test;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class OrderMasterDaoTest extends SellApplicationTests {
    @Autowired
    private OrderMasterDao orderMasterDao;

    private final String OPENID = "cyj";

    @Test
    public void save() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerName("陈宇健");
        orderMaster.setBuyerPhone("13413403089");
        orderMaster.setBuyerAddress("广州");
        orderMaster.setOrderAmount(new BigDecimal(52));

        OrderMaster result = orderMasterDao.save(orderMaster);
        assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = PageRequest.of(0,3);
        Page<OrderMaster> result =orderMasterDao.findByBuyerOpenid(OPENID,pageRequest);
        assertNotEquals(0,result.getTotalElements());
        System.out.println(result.getTotalElements());
    }
}