package com.yly.shop_online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yly.shop_online.common.result.PageResult;
import com.yly.shop_online.entity.Goods;
import com.yly.shop_online.query.Query;
import com.yly.shop_online.query.RecommendByTabGoodsQuery;
import com.yly.shop_online.vo.GoodsVO;
import com.yly.shop_online.vo.IndexTabRecommendVO;
import com.yly.shop_online.vo.RecommendGoodsVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 首页热门推荐,根据tab-Id获取该推荐下的商品列表
     * @param query
     * @return
     */
    IndexTabRecommendVO getTabRecommendGoodsByTabId(RecommendByTabGoodsQuery query);

    /**
     * 猜你喜欢 分页查询推荐商品
     * @param query
     * @return
     */
    PageResult<RecommendGoodsVO> getRecommendGoodsByPage(Query query);

    /**
     * 根据id获取商品详情
     * @param id
     * @return
     */
    GoodsVO getGoodsDetail(Integer id);

}
