package com.chao.sell.service.impl;

import com.chao.sell.dataobject.OrderDetail;
import com.chao.sell.dto.OrderDTO;
import com.chao.sell.enums.OrderStatusEnum;
import com.chao.sell.enums.PayStatusEnum;
import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceImplTest {
    private String customerOpenid = "1354545454321456783";
    private String orderId = "1591253582839907650";
    @Autowired
    private OrderServiceImpl orderService;
    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerName("李彦宏");
        orderDTO.setCustomerAddress("北京");
        orderDTO.setCustomerPhone("12345");
        orderDTO.setCustomerOpenid(customerOpenid);
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductQuantity(2);
        orderDetail1.setProductId("1");

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductQuantity(2);
        orderDetail2.setProductId("126");

        orderDetailList.add(orderDetail1);
        orderDetailList.add(orderDetail2);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    void findOne() {
        OrderDTO result = orderService.findOne("1");
        Assert.assertNotNull(result);
    }

    @Test
    void findList() {
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> result = orderService.findList(customerOpenid,request);
        Assert.assertNotNull(request);
    }

    @Test
    void cancel() {
        OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    void paid() {
        OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.PAID.getCode(),result.getPayStatus());
    }
}