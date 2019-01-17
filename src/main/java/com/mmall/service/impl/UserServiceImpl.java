package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerRespons;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public ServerRespons<User> login(String userName, String password) {
        int resultCount = userMapper.checkUserName(userName);
        if(resultCount == 0){
            return ServerRespons.createByErrorMessage("用户名不存在");
        }

        //todo 密码登陆md5

        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(userName,md5Password);
        if(user == null){
            return ServerRespons.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerRespons.createBySuccess(user,"登陆成功");
    }

    public ServerRespons<String> register(User user){
        ServerRespons validRespons = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validRespons.isSuccess()){
            return validRespons;
        }
        validRespons = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validRespons.isSuccess()){
            return validRespons;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if (resultCount == 0){
            return ServerRespons.createByErrorMessage("注册失败");
        }else{
            return ServerRespons.createBySuccess("注册成功");
        }
    }

    public ServerRespons<String> checkValid(String str,String type){
        if(StringUtils.isNotBlank(type)){
            //校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUserName(str);
                if(resultCount > 0){
                    return ServerRespons.createByErrorMessage("用户名已经存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount > 0){
                    return ServerRespons.createByErrorMessage("邮箱已经存在");
                }
            }
        }else {
            return ServerRespons.createByErrorMessage("参数错误");
        }
        return ServerRespons.createBySuccessMessage("校验成功");
    }

    public ServerRespons selectQuestion(String userName){
        ServerRespons validRespons = this.checkValid(userName,Const.USERNAME);
        if(validRespons.isSuccess()){
            return ServerRespons.createByErrorMessage("用户不存在");
        }
        String question = userMapper.selectQuestionByUserName(userName);
        if(StringUtils.isNotBlank(question)){
            return ServerRespons.createBySuccess(question);
        }
        return ServerRespons.createByErrorMessage("找回密码的问题是空的");
    }


    public ServerRespons<String> checkAnswer(String userName,String question,String answer){
        int resultCount = userMapper.checkAnswer(userName,question,answer);
        if(resultCount > 0){
            //问题和答案都是对的
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+userName,forgetToken);
            return ServerRespons.createBySuccessMessage(forgetToken);
        }
        return ServerRespons.createByErrorMessage("问题回答错误");
    }

    public ServerRespons<String> forgetResetPassword(String userName,String passwordNew,String forgetToken){
        if(StringUtils.isNotBlank(forgetToken)){
            return ServerRespons.createByErrorMessage("参数错误token需要传递");
        }
        ServerRespons validRespons = this.checkValid(userName,Const.USERNAME);
        if(validRespons.isSuccess()){
            return ServerRespons.createByErrorMessage("用户不存在");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+userName);
        if(StringUtils.isBlank(token)){
            return ServerRespons.createByErrorMessage("token无效");
        }
        if(StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUserName(userName,md5Password);
            if(rowCount > 0){
                return ServerRespons.createBySuccessMessage("更新密码成功");
            }
        }else {
            return ServerRespons.createByErrorMessage("token错误，请重新获取重置密码token");
        }
        return ServerRespons.createByErrorMessage("修改密码失败");
    }

    public ServerRespons<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权
        int resultCount = userMapper.checkPasswrod(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerRespons.createByErrorMessage("密码输入错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerRespons.createBySuccessMessage("密码更新成功");
        }
        return ServerRespons.createByErrorMessage("更新失败");
    }

    public  ServerRespons<User> updateInformation(User user){
        //用户名不能更新
        //新的邮箱是不是在数据库里已经存在，如果存在并且相同的话不能是当前用户的
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerRespons.createByErrorMessage("邮箱已经存在");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setQuestion(user.getQuestion());

        int updateUserCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateUserCount > 0){
            return ServerRespons.createBySuccessMessage("更新个人信息成功");
        }
        return ServerRespons.createByErrorMessage("更新失败");
    }

    public ServerRespons<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
           return ServerRespons.createByErrorMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerRespons.createBySuccess(user);
    }

    /**
     * 校验是否为管理员
     * @param user
     * @return
     */
    public ServerRespons checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerRespons.createBySuccess();
        }
        return ServerRespons.createByError();
    }
}
