package com.yly.shop_online.service;

import com.yly.shop_online.entity.IndexCarousel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface IndexCarouselService extends IService<IndexCarousel> {

    /**
     * 首页轮播图查询
     * @param distributionSite
     * @return
     */
    List<IndexCarousel> getList(Integer distributionSite);
}
