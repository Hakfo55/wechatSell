package com.nogang.sell.mapper;

import com.nogang.sell.SellApplicationTests;
import com.nogang.sell.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class CategoryMapperTest extends SellApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void insertByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName","最不受欢迎");
        map.put("categoryType",103);
        int result = categoryMapper.insertByMap(map);
        assertEquals(1,result);
    }

    @Test
    public void insertByObject() {
        ProductCategory productCategory = new ProductCategory("最不受欢迎",104);
        int result = categoryMapper.insertByObject(productCategory);
        assertEquals(1,result);
    }

    @Test
    public void findByCategoryType() {
        ProductCategory result = categoryMapper.findByCategoryType(101);
        assertNotNull(result);
    }

    @Test
    public void findByCategoryName() {
        List<ProductCategory> result = categoryMapper.findByCategoryName("最不受欢迎");
        assertNotEquals(0,result.size());
    }

    @Test
    public void updateByCategoryType() {
        int result = categoryMapper.updateByCategoryType("修改",102);
        assertEquals(1,result);
    }

    @Test
    public void updateByObject() {
        ProductCategory productCategory = new ProductCategory("再次修改",102);
        int result = categoryMapper.updateByObject(productCategory);
        assertEquals(1,result);
    }

    @Test
    public void deleteByCategoryType() {
        int result = categoryMapper.deleteByCategoryType(102);
        assertEquals(1,result);
    }

    @Test
    public void selectByCategoryType() {
        ProductCategory result = categoryMapper.selectByCategoryType(101);
        assertNotNull(result);
    }
}