package com.chao.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
public class OrderDetail {
    /**详情ID*/
    @Id
    private String detailId;
    /**订单ID*/
    private String orderId;
    /**商品ID*/
    private String productId;
    /**商品名字*/
    private String productName;
    /**商品价格*/
    private BigDecimal productPrice;
    /**商品数量*/
    private Integer productQuantity;
    /**商品小图*/
    private String productIcon;
    private Date createTime;
    private Date updateTime;

    public OrderDetail() {
    }
}
