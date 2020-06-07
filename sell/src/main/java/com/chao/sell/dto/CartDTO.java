package com.chao.sell.dto;

import lombok.Data;

/**
 * 购物车data transform object
 * 中间实体类
 */
@Data
public class CartDTO {
    private String productId;
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
