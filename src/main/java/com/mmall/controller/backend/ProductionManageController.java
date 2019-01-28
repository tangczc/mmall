package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerRespons;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description: 产品管理
 * @author: LiChen
 * @create: 2019-01-25 11:02
 */
@Controller
@RequestMapping("/manage/product")
public class ProductionManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerRespons productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //填充增加产品的业务逻辑
            return iProductService.saveOrUPdateProduct(product);

        } else {
            return ServerRespons.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerRespons setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSaleStatus(productId, status);

        } else {
            return ServerRespons.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("get_detail.do")
    @ResponseBody
    public ServerRespons getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.manageProductDetail(productId);
        }else {
            return ServerRespons.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("get_list.do")
    @ResponseBody
    public ServerRespons getList(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSizse",defaultValue = "10") int pageSizse){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.getProductList(pageNum,pageSizse);
        }else {
            return ServerRespons.createByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerRespons getList(HttpSession session,String productName,Integer productId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSizse",defaultValue = "10") int pageSizse){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "先登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()){
            //业务逻辑
            return  iProductService.searchProduct(productName,productId,pageNum,pageSizse);
        }else {
            return ServerRespons.createByErrorMessage("无权限操作");
        }
    }
}