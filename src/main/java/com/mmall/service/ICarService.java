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

    /**
     * 更新购物车
     *
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerRespons<CartVo> update(Integer userId, Integer productId, Integer count);

    /**
     * 删除产品
     *
     * @param userId
     * @param productIds
     * @return
     */
    ServerRespons<CartVo> delete(Integer userId, String productIds);

    /**
     * 查询产品
     *
     * @param userId
     * @return
     */
    ServerRespons<CartVo> list(Integer userId);

    /**
     * 全选 全反选
     *
     * @param userId
     * @param checked
     * @return
     */
    ServerRespons<CartVo> selectOrUnselectAll(Integer userId, Integer productId, Integer checked);


    /**
     * 查询购物车商品数量
     *
     * @param userId
     * @return
     */
    ServerRespons<Integer> getCartProductCount(Integer userId);
}
