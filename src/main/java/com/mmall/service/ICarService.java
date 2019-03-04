package com.mmall.service;

import com.mmall.common.ServerRespons;
import com.mmall.vo.CartVo;
import org.springframework.stereotype.Service;

/**
 * @description: 购物车接口
 * @author: LiChen
 * @create: 2019-03-04 09:38
 */
@Service
public interface ICarService {

    /**
     * 添加商品到购物车
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerRespons<CartVo> add(Integer userId, Integer productId, Integer count);
}
