package com.mmall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description:
 * @author: LiChen
 * @create: 2019-03-04 14:14
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CartVo {
    private List<CartPorductVo> cartPorductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;
    private String  imageHost;


}