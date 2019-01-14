package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerRespons;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加分类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerRespons addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请先登录");

        }
        //校验是否为管理员
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //是管理员
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerRespons.createByErrorMessage("无权操作,不是管理员");
        }
    }

    /**
     * 更新分类
     * @param session
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category.do")
    @ResponseBody
    public ServerRespons setCategory(HttpSession session,Integer categoryId,String categoryName){

        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerRespons.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录请先登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //更新category
            return iCategoryService.updateCategory(categoryId,categoryName);
        }else {
            return ServerRespons.createByErrorMessage("无权操作,不是管理员");
        }
    }



}
