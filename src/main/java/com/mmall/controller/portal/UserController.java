package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
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
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登陆
     * @param userName
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerRespons<User> login(String userName, String password, HttpSession session){
        ServerRespons<User> response = iUserService.login(userName,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @RequestMapping(value = "logout.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerRespons.createBySuccess();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping(value = "register.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<String> register(User user){
        return iUserService.register(user);
    }
    @RequestMapping(value = "check_valid.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<String> checkValid(String str, String type){
        return iUserService.checkValid(str,type);
    }

    /**
     * 获取用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<User> getUserInfo(HttpSession session){
       User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerRespons.createBySuccess(user);
        }
        return ServerRespons.createByErrorMessage("用户未登录");
    }

    /**
     * 获取密码提示问题
     * @param userName
     * @return
     */
    @RequestMapping(value = "forget_get_question.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<String> forgetGetQuestion(String userName){

        return  iUserService.selectQuestion(userName);
    }

    /**
     * 检查问题答案
     * @param userName
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<String> forgetCheckAnswer(String userName,String question,String answer){
        return iUserService.checkAnswer(userName,question,answer);
    }

    /**
     * 忘记密码下的重置密码
     * @param userName
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<String> forgetResetPassword(String userName,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(userName,passwordNew,forgetToken);
    }

    /**
     * 登陆状态下重置密码
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerRespons.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 更新个人信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<User> updateInformation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerRespons.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerRespons<User> respons = iUserService.updateInformation(user);
        if(respons.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,respons.getData());
        }
        return respons;
    }

    /**
     * 个人信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_information.do",method =RequestMethod.POST)
    @ResponseBody
    public ServerRespons<User> getInformation(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录status=10");
        }
        return  iUserService.getInformation(currentUser.getId());
    }



}
