package com.chao.sell.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum {
    //枚举变量
    UP(0,"在架"),
    DOWN(1,"下架");
    Integer code;
    String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
