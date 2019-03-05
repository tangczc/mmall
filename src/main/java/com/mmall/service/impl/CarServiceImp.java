package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerRespons;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICarService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartPorductVo;
import com.mmall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 购物车接口实现
 * @author: LiChen
 * @create: 2019-03-04 09:38
 */
public class CarServiceImp implements ICarService {

    @Autowired
    CartMapper cartMapper;

    @Autowired
    ProductMapper productMapper;

    public ServerRespons<CartVo> add(Integer userId, Integer productId, Integer count) {

        if (productId == null && count == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Cart cart = cartMapper.serlectCartByUserIdProducId(userId, productId);
        if (cart == null) {
            //不在购物车里
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartMapper.insert(cartItem);
        } else {
            //产品已经在购物车里
            //产品已经存在数量相加
            count = cart.getQuantity() + count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        CartVo cartVo = this.getCartVo(userId);

        return ServerRespons.createBySuccess(cartVo);
    }


    public ServerRespons<CartVo> update(Integer userId, Integer productId, Integer count){
        if (productId == null && count == null) {
            return ServerRespons.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.serlectCartByUserIdProducId(userId, productId);
        if (cart != null){
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKey(cart);
        CartVo cartVo = this.getCartVo(userId);
        return ServerRespons.createBySuccess(cartVo);

    }

    public ServerRespons<CartVo> delete(Integer userId, String productIds){
        List<String> productLits = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productLits)){
            return ServerRespons.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByuserIdProductIds(userId,productLits);
        CartVo cartVo = this.getCartVo(userId);
        return ServerRespons.createBySuccess(cartVo);
    }


    private CartVo getCartVo(Integer userId) {
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartPorductVo> cartPorductVoList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        if (!CollectionUtils.isEmpty(cartList)) {
            for (Cart cartItem : cartList) {
                CartPorductVo cartPorductVo = new CartPorductVo();
                cartPorductVo.setId(cartItem.getId());
                cartPorductVo.setUserId(cartItem.getUserId());
                cartPorductVo.setProductId(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null) {
                    cartPorductVo.setProductMainImage(product.getMainImage());
                    cartPorductVo.setProductName(product.getName());
                    cartPorductVo.setProductSubtitle(product.getSubtitle());
                    cartPorductVo.setProductPrice(product.getPrice());
                    cartPorductVo.setProductStatus(product.getStatus());
                    cartPorductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cartItem.getQuantity()) {
                        cartPorductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartPorductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FILE);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartPorductVo.setQuantity(buyLimitCount);
                    //计算总价
                    cartPorductVo.setProductTotalPrice(new BigDecimal((BigDecimalUtil.multiply(product.getPrice().doubleValue(), cartPorductVo.getQuantity())).toString()));
                    cartPorductVo.setProductChecked(cartItem.getChecked());
                }
                if (cartItem.getChecked() == Const.Cart.CHECKED) {
                    //如果勾选，增加到总价当中
                    cartTotalPrice = new BigDecimal((BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartPorductVo.getProductTotalPrice().doubleValue())).toString());
                }
                cartPorductVoList.add(cartPorductVo);
            }
        }

        CartVo cartVo = CartVo.builder()
                .cartTotalPrice(cartTotalPrice)
                .cartPorductVoList(cartPorductVoList)
                .allChecked(this.getAllCheckedStatus(userId))
                .imageHost(PropertiesUtil.getProperty("http://img.happymmall.com/"))
                .build();
        return cartVo;
    }

    private Boolean getAllCheckedStatus(Integer userId) {
        if (userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }




}