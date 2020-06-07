package com.chao.sell.converter;

import com.chao.sell.dataobject.OrderMaster;
import com.chao.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * master转换到DTO
 */
public class OrderMaster2OrderDTO {
    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        //java8 lambda
        //e代表的是orderMasterList中的一个对象元素，相当于是遍历
        return orderMasterList.stream().map(e->convert(e))
                .collect(Collectors.toList());
    }
}
