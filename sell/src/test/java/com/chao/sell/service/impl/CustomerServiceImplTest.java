package com.chao.sell.service.impl;

import com.chao.sell.dto.OrderDTO;
import com.chao.sell.service.CustomerService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerServiceImplTest {
    @Autowired
    private CustomerServiceImpl customerService;
    String openid = "1354545454321456783";
    String orderId = "1591253582839907650";
    @Test
    void findOneOrder() {
        OrderDTO orderDTO = customerService.findOneOrder(openid,orderId);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    @Transactional
    void cancelOrder() {
        OrderDTO orderDTO = customerService.cancelOrder(openid,orderId);
        Assert.assertNotNull(orderDTO);
    }

}