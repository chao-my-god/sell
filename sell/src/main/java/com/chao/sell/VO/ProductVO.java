package com.chao.sell.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品包含类目
 */
@Data
public class ProductVO {

    /**类目名字*/
    @JsonProperty("name")
    private String categoryName;
    /**类目编号*/
    @JsonProperty("type")
    private Integer categoryType;
    /**商品*/
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
