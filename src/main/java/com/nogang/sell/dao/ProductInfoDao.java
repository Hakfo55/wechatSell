package com.nogang.sell.dao;

import com.nogang.sell.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {
    /**
     * 通过商品状态查找商品
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * 通过类目查找商品
     * @param category
     * @return
     */
    List<ProductInfo> findByCategoryType(Integer category);
}
