package com.chao.sell.form;

import com.chao.sell.dto.CartDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderForm {
    @JsonProperty("name")
    @NotEmpty(message = "姓名必填")
    private String orderName;
    @NotEmpty(message = "电话必填")
    private String phone;
    @NotEmpty(message = "地址必填")
    private String address;
    @NotEmpty(message = "微信id必填")
    private String openid;
    @NotEmpty(message = "购物车必填")
    private List<CartDTO> cartDTOList;
}
