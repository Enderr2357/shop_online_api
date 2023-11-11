package com.yly.shop_online.service;

import com.yly.shop_online.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yly.shop_online.vo.CategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface CategoryService extends IService<Category> {

    /**
     * 首页 分类列表
     * @return
     */
    List<Category> getIndexCategoryList();

    List<CategoryVO> getCategoryList();
}
