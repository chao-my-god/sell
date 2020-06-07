package com.chao.sell.repository;

import com.chao.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;
    @Test
    void findByProductStatus() throws Exception{
        List<ProductInfo> result = repository.findByProductStatus(0);
        Assert.assertEquals(1,result.size());
    }
}