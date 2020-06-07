package com.chao.sell.dataobject;

import com.chao.sell.enums.OrderStatusEnum;
import com.chao.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class OrderMaster {
    /**id*/
    @Id
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

    public OrderMaster() {
    }
}
