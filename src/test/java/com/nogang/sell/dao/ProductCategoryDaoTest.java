package com.nogang.sell.dao;

import com.nogang.sell.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryDaoTest  {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = productCategoryDao.findById(1).orElse(null);
//        System.out.println(productCategory.toString());
        Assert.assertEquals("热销榜",productCategory.getCategoryName());
//        Assert.assertNotNull(productCategory);
    }

    @Test
    @Transactional
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("男生最爱",5);
        ProductCategory result = productCategoryDao.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> result = productCategoryDao.findByCategoryTypeIn(list);
//        System.out.println(result.get(0).getCategoryName());
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void findByCategoryTypeTest(){
        ProductCategory result = productCategoryDao.findByCategoryType(2);
        Assert.assertEquals("女生最爱",result.getCategoryName());
    }

}