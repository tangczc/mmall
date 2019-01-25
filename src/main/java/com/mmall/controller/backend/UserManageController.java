package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerRespons;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author lichen
 */
@Controller
@RequestMapping("/mange/user")
public class UserManageController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerRespons<User> login(String userName, String password, HttpSession session){
        ServerRespons<User> respons = iUserService.login(userName,password);
        if (respons.isSuccess()){
            User user = respons.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
                return respons;
            }else {
                return ServerRespons.createByErrorMessage("不是管理员");
            }
        }
       return respons;
    }
}
