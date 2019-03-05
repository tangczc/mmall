package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerRespons;
import com.mmall.pojo.User;
import com.mmall.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description: 购物车前端控制器
 * @author: LiChen
 * @create: 2019-03-04 09:30
 */
@Controller
@RequestMapping("/cart/")
public class CarController {

    @Autowired
    ICarService iCarService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServerRespons add(HttpSession httpSession, Integer count, Integer productId) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCarService.add(user.getId(), productId, count);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerRespons update(HttpSession httpSession, Integer count, Integer productId) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCarService.update(user.getId(), productId, count);
    }

    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerRespons deleteProduct(HttpSession httpSession, String productIds) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCarService.delete(user.getId(), productIds);
    }
}