package com.mmall.service;

import com.mmall.common.ServerRespons;
import com.mmall.pojo.Product;

public interface IProductService {
    ServerRespons saveOrUPdateProduct(Product product);
    ServerRespons<String> setSaleStatus(Integer productId,Integer status);
}
