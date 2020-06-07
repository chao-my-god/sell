package com.chao.sell.service.impl;

import com.chao.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;
    @Test
    void findOne() throws Exception{
        ProductCategory productCategory = categoryService.findOne(2);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> list = categoryService.findAll();
        Assert.assertEquals(4,list.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<Integer> listIn = Arrays.asList(1,3,10);
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(listIn);
        Assert.assertEquals(4,list.size());
    }

    @Test
    @Transactional
    void save() {
        ProductCategory productCategory = new ProductCategory("电脑",5);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}