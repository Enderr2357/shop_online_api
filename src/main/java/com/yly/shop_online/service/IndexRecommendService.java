package com.yly.shop_online.service;

import com.yly.shop_online.entity.IndexRecommend;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yly.shop_online.vo.IndexRecommendVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface IndexRecommendService extends IService<IndexRecommend> {


    /**
     * 首页--热门推荐
     * @return
     */
    List<IndexRecommendVO> getList();
}
