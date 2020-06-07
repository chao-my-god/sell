package com.chao.sell.controller;

import com.chao.sell.VO.ProductInfoVO;
import com.chao.sell.VO.ProductVO;
import com.chao.sell.VO.ResultVO;
import com.chao.sell.dataobject.ProductCategory;
import com.chao.sell.dataobject.ProductInfo;
import com.chao.sell.service.impl.CategoryServiceImpl;
import com.chao.sell.service.impl.ProductServiceImpl;
import com.chao.sell.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */
//处理http请求，返回json格式
@RestController
@RequestMapping("/buyer/product")
public class CustomerProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1、查询所有在架商品
        List<ProductInfo> productInfoList = productService.findOnAll();

        //2、根据查询到的在架商品列表得到其所有类目编号
        //Java8 lambda
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        //3、根据类目编号查询类目表
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        //4、数据拼装
        //集合
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            //foods
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
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
