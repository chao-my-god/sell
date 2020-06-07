package com.chao.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@DynamicUpdate
@Data
public class ProductInfo {
    @Id
    private String productId;
    /** 名字 */
    private String productName;
    /** 价格 */
    private BigDecimal productPrice;
    /** 库存 */
    private Integer productStock;
    /** 小图 */
    private String productIcon;
    /** 描述 */
    private String productDescription;
    /** 状态 */
    private Integer productStatus;
    /** 类型 */
    private Integer categoryType;
    //public Date createTime;
    //public Date updateTime;

    public ProductInfo() {
    }

    public ProductInfo(String productName, BigDecimal productPrice, Integer productStock, String productIcon, String productDescription, Integer categoryType) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productIcon = productIcon;
        this.productDescription = productDescription;
        this.categoryType = categoryType;
    }
}
