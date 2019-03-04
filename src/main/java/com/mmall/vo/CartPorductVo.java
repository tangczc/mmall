package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description:结合产品和购物车的抽象对象
 * @author: LiChen
 * @create: 2019-03-04 14:07
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartPorductVo {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;//购物车中此产品的数量
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productStock;
    private Integer productChecked;//此商品是否勾选
    private String limitQuantity;//限制数量
}