package com.yly.shop_online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yly.shop_online.entity.Category;
import com.yly.shop_online.enums.CategoryRecommendEnum;
import com.yly.shop_online.mapper.CategoryMapper;
import com.yly.shop_online.mapper.GoodsMapper;
import com.yly.shop_online.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Override
    public List<Category> getIndexCategoryList() {
        LambdaQueryWrapper<Category> queryWrapper= new LambdaQueryWrapper<>();
        //查询首页和分类页都推荐的分类以及在首页推荐的分类
        queryWrapper.eq(Category::getIsRecommend, CategoryRecommendEnum.ALL_RECOMMEND.getValue()).or().eq(Category::getIsRecommend,CategoryRecommendEnum.INDEX_RECOMMEND.getValue());
        queryWrapper.orderByDesc(Category::getCreateTime);
        List<Category> list = baseMapper.selectList(queryWrapper);
        return list;
    }
}
