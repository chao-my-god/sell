package com.chao.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(-1,"商品不存在"),
    STOCK_NOT_ENOUGH(5,"商品库存不足"),
    PARAM_ERROR(2,"参数错误")
    ;
    private Integer code;
    private String msg;
    ResultEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
