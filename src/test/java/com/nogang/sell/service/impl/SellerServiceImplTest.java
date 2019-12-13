package com.nogang.sell.service.impl;

import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.entity.SellerInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SellerServiceImplTest extends SellApplicationTests {
    private static final String OPENID = "cyj";
    @Autowired
    private SellerServiceImpl sellerService;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo result = sellerService.findSellerInfoByOpenid(OPENID);
        assertEquals("cyj",result.getOpenid());
    }
}