package com.nogang.sell.dao;

import com.nogang.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {
    /**
     * 通过类目编号查找类目
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 通过单个类目编号查找类目
     * @param categoryType
     * @return
     */
    ProductCategory findByCategoryType(Integer categoryType);
}