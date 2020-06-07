package com.chao.sell.enums;

import lombok.Getter;

@Getter
//enum类默认继承java.lang.Enum，无法再继承其他类
public enum OrderStatusEnum{
    //默认是public static final
    NEW(0,"新下单"),
    FINISHED(1,"订单完成"),
    CANCEL(2,"订单取消"),
    ORDER_NOT_EXIST(3,"订单不存在"),
    ORDERDETAIL_NOT_EXIST(4,"订单详情不存在"),
    ORDER_STATUS_ERROR(5,"订单状态错误"),
    ORDER_UPDATE_FAIL(6,"订单更新失败"),
    PRODUCT__QUANTITY_ERROR(7,"商品数量错误"),
    ORDER_PAY_STATUS_ERROR(8,"订单支付状态错误"),
    ORDER_OWNER_ERROR(9,"订单拥有者错误")
    ;
    Integer code;
    String msg;
    //构造方法为private，无法在外部创建对象实例
    OrderStatusEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }
}
