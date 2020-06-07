package com.chao.sell.service.impl;

import com.chao.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;
    @Test
    void findOne() {
        ProductInfo productInfo = productService.findOne("1");
        Assert.assertNotNull(productInfo);
    }

    @Test
    void findOnAll() {
        List<ProductInfo> list = productService.findOnAll();
        Assert.assertNotNull(list);
    }

    @Test
    void findAll() {
        PageRequest request = PageRequest.of(0,2);
        Page<ProductInfo> list = productService.findAll(request);
        System.out.println(list.getTotalElements());
    }

    @Test
    void save() {
        ProductInfo productInfo = new ProductInfo("华为Mate30",new BigDecimal(2000),10,"http://","好用",1);
        productInfo.setProductId("123");
        ProductInfo result = productService.save(productInfo);
    }
}