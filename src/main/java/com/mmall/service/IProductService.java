package com.mmall.service;

import com.mmall.common.ServerRespons;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

public interface IProductService {
    ServerRespons saveOrUPdateProduct(Product product);

    ServerRespons<String> setSaleStatus(Integer productId, Integer status);

    ServerRespons<ProductDetailVo> manageProductDetail(Integer productId);
}
