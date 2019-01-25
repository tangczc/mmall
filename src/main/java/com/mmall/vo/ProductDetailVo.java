package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @description:
 * @author: LiChen
 * @create: 2019-01-25 13:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailVo {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subitile;
    private String mainImage;
    private String subImage;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;

    private String imageHost;
    private Integer parentCategoryId;
}