package com.chao.sell.service.impl;

import com.chao.sell.dto.OrderDTO;
import com.chao.sell.enums.OrderStatusEnum;
import com.chao.sell.exception.SellException;
import com.chao.sell.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private OrderServiceImpl orderService;
    @Override
    public OrderDTO findOneOrder(String openid, String orderId) {
        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = findOneOrder(openid,orderId);
        if(orderDTO==null){
            log.error("【查找订单】查找不到该订单");
            throw new SellException(OrderStatusEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }
    public OrderDTO checkOrderOwner(String openid,String orderId){
        OrderDTO orderDTO = orderService.findOne(orderId);
        //equalsIgnoreCase忽略大小写
        if(!orderDTO.getCustomerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】该订单的openid不一致,openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(OrderStatusEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
