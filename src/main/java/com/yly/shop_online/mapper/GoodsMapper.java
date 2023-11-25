package com.yly.shop_online.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yly.shop_online.entity.Goods;
import com.yly.shop_online.vo.UserOrderGoodsVO;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * 根据订单id 查询商品信息列表
     *
     * @param id
     * @return
     */
    List<UserOrderGoodsVO> getGoodsListByOrderId(@Param("id") Integer id);

}
