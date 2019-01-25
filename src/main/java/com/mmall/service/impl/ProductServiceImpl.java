package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerRespons;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
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

    @Autowired
    private CategoryMapper categoryMapper;

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

    public ServerRespons<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerRespons.createBySuccessMessage("修改产品成功");
        }
        return ServerRespons.createByErrorMessage("修改产品失败");
    }

    public ServerRespons<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            ServerRespons.createByErrorMessage("该产品已经下架或不存在");
        }
        ProductDetailVo productDetailVo = assembleProductDetaiVo(product);
        return ServerRespons.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetaiVo(Product product) {
        ProductDetailVo productDetailVo = ProductDetailVo.builder().build();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubitile(product.getSubtitle());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setSubImage(product.getMainImage());
        productDetailVo.setMainImage(product.getMainImage());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        Category category = categoryMapper.selectByPrimaryKey(product.getId());
        if (category == null) {
            productDetailVo.setParentCategoryId(0);//默认根节点
        } else {
            productDetailVo.setParentCategoryId(category.getId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;

    }
}






























