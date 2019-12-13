package com.nogang.sell.controller;

import com.nogang.sell.entity.ProductCategory;
import com.nogang.sell.entity.ProductInfo;
import com.nogang.sell.exception.SellException;
import com.nogang.sell.form.CategoryForm;
import com.nogang.sell.service.CategoryService;
import com.nogang.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    /**
     * 查询所有类目
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("categoryList",productCategoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 新增或修改页面
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                              Map<String,Object> map){
        if (categoryId != null){
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("category",productCategory);
        }
        return new ModelAndView("category/index",map);
    }

    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm, BindingResult bindingResult,
                             Map<String,Object> map){
        if (bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory = new ProductCategory();
        //自行添加代码，修改类目编号同时修改商品的对应的类目编号
        List<ProductInfo> productInfoList = new ArrayList<>();
        try {
            //如果传过来的表单参数，id有值，代表修改，所以要先去查询一遍数据再做修改保存
            if (categoryForm.getCategoryId() != null) {
                productCategory = categoryService.findOne(categoryForm.getCategoryId());
                //自行添加代码，修改类目编号同时修改商品的对应的类目编号
                productInfoList = productService.findByCategoryType(productCategory.getCategoryType());
            }
            BeanUtils.copyProperties(categoryForm, productCategory);
            categoryService.save(productCategory);
            //自行添加代码，修改类目编号同时修改商品的对应的类目编号
            if(productInfoList.size()>0) {
                for (ProductInfo productInfo : productInfoList) {
                    productInfo.setCategoryType(categoryForm.getCategoryType());
                    productService.save(productInfo);
                }
            }
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
