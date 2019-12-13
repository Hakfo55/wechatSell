package com.nogang.sell.service;

import com.nogang.sell.dto.CartDTO;
import com.nogang.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo save(ProductInfo productInfo);

    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架商品
    ProductInfo onSale(String productId);

    //下架商品
    ProductInfo offSale(String productId);

    List<ProductInfo> findByCategoryType(Integer categoryType);
}
