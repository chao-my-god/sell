package com.chao.sell.service;

import com.chao.sell.dto.OrderDTO;
import org.hibernate.criterion.Order;

public interface CustomerService {
    //查询一个订单
    OrderDTO findOneOrder(String openid,String orderId);
    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);
}
