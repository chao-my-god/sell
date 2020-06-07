package com.chao.sell.exception;

import com.chao.sell.enums.OrderStatusEnum;
import com.chao.sell.enums.ResultEnum;

/**
 * 商品不存在的异常类
 */
public class SellException extends RuntimeException {
    private Integer code;
    public SellException(ResultEnum resultEnum){
        //调用父类的含msg参数的构造方法
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
    public SellException(OrderStatusEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
    public SellException(Integer code,String msg){
        super(msg);
        this.code = code;
    }

}
