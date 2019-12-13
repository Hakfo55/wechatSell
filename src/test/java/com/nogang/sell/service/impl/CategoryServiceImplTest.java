package com.nogang.sell.service.impl;

import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.entity.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryServiceImplTest extends SellApplicationTests {
    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory p = categoryService.findOne(1);
        assertEquals(new Integer(1),p.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> p = categoryService.findAll();
        assertNotEquals(0,p.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> p = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3));
        assertNotEquals(0,p.size());
    }

    @Test
    public void save() {
        ProductCategory p = new ProductCategory("男生专享",10);
        ProductCategory result = categoryService.save(p);
        assertNotNull(result);
    }
}