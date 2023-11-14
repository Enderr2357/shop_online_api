package com.yly.shop_online.service.impl;

import com.yly.shop_online.common.exception.ServerException;
import com.yly.shop_online.entity.Goods;
import com.yly.shop_online.entity.UserShoppingCart;
import com.yly.shop_online.mapper.GoodsMapper;
import com.yly.shop_online.mapper.UserShoppingCartMapper;
import com.yly.shop_online.query.CartQuery;
import com.yly.shop_online.service.UserShoppingCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yly.shop_online.vo.CartGoodsVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
@Service
@AllArgsConstructor
public class UserShoppingCartServiceImpl extends ServiceImpl<UserShoppingCartMapper, UserShoppingCart> implements UserShoppingCartService {

    private GoodsMapper goodsMapper;
    @Override
    public CartGoodsVO addShopCart(CartQuery query) {
        //查询商品是否存在,以及库存是否充足
        Goods goods=goodsMapper.selectById(query.getId());
        if (goods==null){
            throw new ServerException("商品信息不存在");
        }
        if(query.getCount()>goods.getInventory()){
            throw new ServerException("商品库存不足");
        }
        //插入购物车信息
        UserShoppingCart userShoppingCart=new UserShoppingCart();
        userShoppingCart.setUserId(query.getUserId());
        userShoppingCart.setGoodsId(goods.getId());
        userShoppingCart.setPrice(goods.getPrice());
        userShoppingCart.setCount(query.getCount());
        userShoppingCart.setAttrsText(query.getAttrsText());
        userShoppingCart.setSelected(false);
        baseMapper.insert(userShoppingCart);

        //返回用户添加的购物车信息
        CartGoodsVO goodsVO = new CartGoodsVO();
        goodsVO.setId(userShoppingCart.getId());
        goodsVO.setName(goods.getName());
        goodsVO.setAttrsText(query.getAttrsText());
        goods.setPrice(userShoppingCart.getPrice());
        goodsVO.setNowPrice(goods.getPrice());
        goodsVO.setSelected(userShoppingCart.getSelected());
        goodsVO.setStock(goods.getInventory());
        goodsVO.setCount(query.getCount());
        goodsVO.setPicture(goods.getCover());
        goodsVO.setDiscount(goods.getDiscount());
        return goodsVO;
    }

    @Override
    public List<CartGoodsVO> shopCartList(Integer userId) {
        List<CartGoodsVO> list=baseMapper.getCartGoodsInfo(userId);
        return list;
    }

}
