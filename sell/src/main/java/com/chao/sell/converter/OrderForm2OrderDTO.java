package com.chao.sell.converter;

import com.chao.sell.dto.OrderDTO;
import com.chao.sell.form.OrderForm;
import org.springframework.beans.BeanUtils;

public class OrderForm2OrderDTO {
    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderForm,orderDTO);
        return orderDTO;
    }
}
