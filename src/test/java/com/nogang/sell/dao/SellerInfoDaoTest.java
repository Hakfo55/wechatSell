package com.nogang.sell.dao;

import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.entity.SellerInfo;
import com.nogang.sell.utils.KeyUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class SellerInfoDaoTest extends SellApplicationTests {
    @Autowired
    private SellerInfoDao sellerInfoDao;

    @Test
    public void findByOpenid() {
        SellerInfo result = sellerInfoDao.findByOpenid("cyj");
        assertEquals("cyj",result.getOpenid());
    }

    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("cyj");
        SellerInfo result = sellerInfoDao.save(sellerInfo);
        assertNotNull(result);
    }
}