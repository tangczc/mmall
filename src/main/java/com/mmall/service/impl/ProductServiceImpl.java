package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerRespons;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description: IProduct实现类
 * @author: LiChen
 * @create: 2019-01-25 11:09
 */
@Service("iProductService")
public class ProductServiceImpl implements IProductService {


    @Autowired
    private ProductMapper productMapper;

    public ServerRespons saveOrUPdateProduct(Product product) {
        if (product != null) {
            if (StringUtils.isBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerRespons.createBySuccessMessage("更新产品成功");
                }
                return ServerRespons.createBySuccessMessage("更新产品失败");
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerRespons.createBySuccessMessage("添加产品成功");
                }
                return ServerRespons.createBySuccessMessage("添加产品失败");
            }

        }
        return ServerRespons.createByErrorMessage("更新或添加产品参数不正确");
    }

    public ServerRespons<String> setSaleStatus(Integer productId,Integer status){
        if (productId == null || status == null){
            return ServerRespons.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerRespons.createBySuccessMessage("修改产品成功");
        }
        return ServerRespons.createByErrorMessage("修改产品失败");
    }

}