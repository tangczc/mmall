package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerRespons;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

public interface IProductService {
    ServerRespons saveOrUPdateProduct(Product product);

    ServerRespons<String> setSaleStatus(Integer productId, Integer status);

    ServerRespons<ProductDetailVo> manageProductDetail(Integer productId);

    ServerRespons getProductList(int pageNum, int pageSize);

    ServerRespons<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);

    ServerRespons<ProductDetailVo> getProductDetial(Integer productId);

    ServerRespons<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);
}
