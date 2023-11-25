package com.yly.shop_online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yly.shop_online.common.exception.ServerException;
import com.yly.shop_online.entity.Goods;
import com.yly.shop_online.entity.UserShoppingCart;
import com.yly.shop_online.mapper.GoodsMapper;
import com.yly.shop_online.mapper.UserShoppingCartMapper;
import com.yly.shop_online.query.CartQuery;
import com.yly.shop_online.query.EditCartQuery;
import com.yly.shop_online.service.UserShoppingCartService;
import com.yly.shop_online.vo.CartGoodsVO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

import static com.yly.shop_online.common.utils.ObtainUserIdUtils.getUserId;

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

    @Override
    public CartGoodsVO editCart(EditCartQuery query) {
        UserShoppingCart userShoppingCart = baseMapper.selectById(query.getId());
        if(userShoppingCart==null){
            throw new ServerException("购物车信息不存在");
        }

        userShoppingCart.setCount(query.getCount());
        userShoppingCart.setSelected(query.getSelected());
        baseMapper.updateById(userShoppingCart);
        //查询购物车信息
        Goods goods= goodsMapper.selectById(userShoppingCart.getGoodsId());
        if(query.getCount()>goods.getInventory()){
            throw new ServerException(goods.getName()+"库存数量不足");
        }
        CartGoodsVO goodsVO = new CartGoodsVO();
        goodsVO.setId(userShoppingCart.getId());
        goodsVO.setName(goods.getName());
        goodsVO.setAttrsText(userShoppingCart.getAttrsText());
        goodsVO.setPrice(userShoppingCart.getPrice());
        goodsVO.setNowPrice(goods.getPrice());
        goodsVO.setSelected(userShoppingCart.getSelected());
        goodsVO.setStock(goods.getInventory());
        goodsVO.setCount(query.getCount());
        goodsVO.setPicture(goods.getCover());
        goodsVO.setDiscount(goods.getDiscount());
        return goodsVO;
    }

    @Override
    public void removeCartGoods(Integer userId, List<Integer> ids) {
        //查询用户的购物车李彪
        List<UserShoppingCart> cartList=baseMapper.selectList(new LambdaQueryWrapper<UserShoppingCart>()
                                                                .eq(UserShoppingCart::getUserId,userId));
        if (cartList.size()==0){
            return;
        }
        //与要删除的购物车取交集
        List<UserShoppingCart> deleteCartList=cartList.stream().filter(item->ids.contains(item.getId()))
                                                                        .collect(Collectors.toList());
        removeBatchByIds(deleteCartList);
    }

    @Override
    public void editCartSelected(Boolean selected) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Integer userId=getUserId(request);
        // 在这里就 可以使用request对象来获取HttpRequest的信息了
        //查询用户的购物车李彪
        List<UserShoppingCart> cartList=baseMapper.selectList(new LambdaQueryWrapper<UserShoppingCart>()
                .eq(UserShoppingCart::getUserId,userId));
        if (cartList.size()==0){
            return;
        }
        //批量修改购物车中选中状态
        cartList.stream().forEach(item->item.setSelected(selected));
        saveOrUpdateBatch(cartList);
    }

}
