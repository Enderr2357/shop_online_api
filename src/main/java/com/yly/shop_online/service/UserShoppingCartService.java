package com.yly.shop_online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yly.shop_online.entity.UserShoppingCart;
import com.yly.shop_online.query.CartQuery;
import com.yly.shop_online.query.EditCartQuery;
import com.yly.shop_online.vo.CartGoodsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface UserShoppingCartService extends IService<UserShoppingCart> {

    /**
     * 添加购物车
     * @param query
     * @return
     */
    CartGoodsVO addShopCart(CartQuery query);

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    List<CartGoodsVO> shopCartList(Integer userId);

    /**
     * 修改购物车
     * @param query
     * @return
     */
    CartGoodsVO editCart(EditCartQuery query);

    /**
     * 删除/清空购物车单品
     * @param userId
     * @param ids
     */
    void removeCartGoods(Integer userId,List<Integer> ids);

    /**
     * 购物车全选/取消全选
     * @param selected
     * @param userId
     */
    void editCartSelected(Boolean selected);
}
