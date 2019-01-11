package com.mmall.service;

import com.mmall.common.ServerRespons;
import com.mmall.pojo.User;

/**
 * @author lichen
 */
public interface IUserService {
    ServerRespons<User> login(String userName, String password);
    ServerRespons<String> register(User user);
    ServerRespons<String> checkValid(String str,String type);
    ServerRespons selectQuestion(String userName);
    ServerRespons<String> checkAnswer(String userName,String question,String answer);
    ServerRespons<String> forgetResetPassword(String userName,String passwordNew,String forgetToken);
    ServerRespons<String> resetPassword(String passwordOld,String passwordNew,User user);
    ServerRespons<User> updateInformation(User user);
    ServerRespons<User> getInformation(Integer userId);
}
