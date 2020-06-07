package com.chao.sell.repository;

import com.chao.sell.dataobject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;
    @Test
    public void findByCustomerOpenid() {
        PageRequest request = PageRequest.of(0,1);
        Page<OrderMaster> orderMasterList = repository.findByCustomerOpenid("wx12345",request);
        System.out.println(orderMasterList.getTotalElements());
    }
}