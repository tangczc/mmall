package com.mmall.service;

import com.mmall.common.ServerRespons;

/**
 * @description: 支付接口
 * @author: LiChen
 * @create: 2019-02-19 13:42
 */
public interface IOrderService {
    ServerRespons pay(Long orderNo, Integer userId, String path);
}
