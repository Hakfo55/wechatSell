package com.nogang.sell.service.impl;

import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.entity.ProductInfo;
import com.nogang.sell.enums.ProductStatusEnum;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceImplTest extends SellApplicationTests {
    @Autowired
    private ProductServiceImpl productInfoService;

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("57");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(5.5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的虾");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result = productInfoService.save(productInfo);
        assertNotNull(result);
    }

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("55");
        assertEquals("55",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> result = productInfoService.findUpAll();
        assertNotEquals(0,result.size());
    }

    @Test
    public void findAll() {
        PageRequest request = PageRequest.of(0,2);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void onSale() {
        ProductInfo result = productInfoService.onSale("55");
        assertEquals(ProductStatusEnum.UP,result.getProductStatusEnum());
    }

    @Test
    public void offSale() {
        ProductInfo result = productInfoService.offSale("55");
        assertEquals(ProductStatusEnum.DOWN,result.getProductStatusEnum());
    }
}