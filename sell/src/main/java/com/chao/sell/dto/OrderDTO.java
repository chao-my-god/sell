package com.chao.sell.dto;

import com.chao.sell.dataobject.OrderDetail;
import com.chao.sell.enums.OrderStatusEnum;
import com.chao.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单主表+订单详情列表
 * 中间实体类
 */
//数据转化
@Data
public class OrderDTO {
    /**id*/
    private String orderId;
    /**买家名字*/
    private String customerName;
    /**买家电话*/
    private String customerPhone;
    /**买家地址*/
    private String customerAddress;
    /**买家微信openid*/
    private String customerOpenid;
    /**订单总金额*/
    private BigDecimal orderTotal;
    /**订单状态，默认0新下单*/
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /**支付状态，默认0未支付*/
    private Integer payStatus = PayStatusEnum.UNPAID.getCode();
    private Date createTime;
    private Date updateTime;
    //订单对应多个订单商品详情orderId-orderId
    List<OrderDetail> orderDetailList;

    public OrderDTO() {
    }

    public OrderDTO(String customerName, String customerPhone, String customerAddress, String customerOpenid) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.customerOpenid = customerOpenid;
    }
}
