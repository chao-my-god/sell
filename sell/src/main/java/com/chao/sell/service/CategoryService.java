package com.chao.sell.service;

import com.chao.sell.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
    //卖家后台管理
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    //买家端使用
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
    ProductCategory save(ProductCategory productCategory);

}
