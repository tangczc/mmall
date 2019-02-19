package com.mmall.controller.portal;

import com.mmall.common.ServerRespons;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 前台商品管理
 * @author: LiChen
 * @create: 2019-02-08 17:50
 */
@Controller
@RequestMapping("/product/")
public class ProductionController {
    @Autowired
    private IProductService iProductService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerRespons<ProductDetailVo> detail(Integer id) {
        return iProductService.getProductDetial(id);
    }

}