package com.nogang.sell.controller;

import com.nogang.sell.VO.ProductInfoVO;
import com.nogang.sell.VO.ProductVO;
import com.nogang.sell.VO.ResultVO;
import com.nogang.sell.entity.ProductCategory;
import com.nogang.sell.entity.ProductInfo;
import com.nogang.sell.service.impl.CategoryServiceImpl;
import com.nogang.sell.service.impl.ProductServiceImpl;
import com.nogang.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductServiceImpl productInfoService;
    @Autowired
    private CategoryServiceImpl categoryService;

    /**
     * 查询所有上架商品列表
     * @return
     */
    @GetMapping("/list")
//    @Cacheable(cacheNames = "product",key = "#sellerId",condition = "#sellerId.length() > 3",unless = "#result.getCode() != 0")
    public ResultVO list(@RequestParam(value = "sellerId",required = false) String sellerId){
        //1.查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //2.查询类目（一次性查询）
        //传统方法
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for (ProductInfo p : productInfoList){
//            categoryTypeList.add(p.getCategoryType());
//        }
        //精简方法（java8，lamba表达式）
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory:productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo:productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
