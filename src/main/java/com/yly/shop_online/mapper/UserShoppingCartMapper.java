package com.yly.shop_online.mapper;

import com.yly.shop_online.entity.UserShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yly.shop_online.vo.CartGoodsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface UserShoppingCartMapper extends BaseMapper<UserShoppingCart> {
    /**
     * 查询购物车信息
     * @param id
     * @return
     */
    List<CartGoodsVO> getCartGoodsInfo(@Param("id") Integer id);
}
