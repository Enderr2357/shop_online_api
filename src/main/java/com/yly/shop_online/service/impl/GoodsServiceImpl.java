package com.yly.shop_online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yly.shop_online.common.exception.ServerException;
import com.yly.shop_online.common.result.PageResult;
import com.yly.shop_online.convert.GoodsConvert;
import com.yly.shop_online.entity.*;
import com.yly.shop_online.mapper.*;
import com.yly.shop_online.query.Query;
import com.yly.shop_online.query.RecommendByTabGoodsQuery;
import com.yly.shop_online.service.GoodsService;
import com.yly.shop_online.vo.GoodsVO;
import com.yly.shop_online.vo.IndexTabGoodsVO;
import com.yly.shop_online.vo.IndexTabRecommendVO;
import com.yly.shop_online.vo.RecommendGoodsVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
@Slf4j
@AllArgsConstructor
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {


    private IndexRecommendMapper indexRecommendMapper;
    private IndexRecommendTabMapper indexRecommendTabMapper;
    private GoodsDetailMapper goodsDetailMapper;
    private GoodsSpecificationMapper goodsSpecificationMapper;
    private GoodsSpecificationDetailMapper goodsSpecificationDetailMapper;

    @Override
    public IndexTabRecommendVO getTabRecommendGoodsByTabId(RecommendByTabGoodsQuery query) {
        //根据推荐的recommendId查询实体
        IndexRecommend indexRecommend = indexRecommendMapper.selectById(query.getSubType());
        if (indexRecommend == null) {
            throw new ServerException("推荐分类不存在");
        }
        //查询该分类下的tab列表
        LambdaQueryWrapper<IndexRecommendTab> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(IndexRecommendTab::getRecommendId, indexRecommend.getId());
        List<IndexRecommendTab> tabList = indexRecommendTabMapper.selectList(queryWrapper);
        if (tabList.size() == 0) {
            throw new ServerException("该分类下不存在tab分类");
        }
        //tab分类下的商品列表
        List<IndexTabGoodsVO> list = new ArrayList<>();
        for (IndexRecommendTab item : tabList) {
            IndexTabGoodsVO tabGoods = new IndexTabGoodsVO();
            tabGoods.setId(item.getId());
            tabGoods.setName(item.getName());
            Page<Goods> page = new Page<>(query.getPage(), query.getPageSize());
            Page<Goods> goodsPage = baseMapper.selectPage(page, new LambdaQueryWrapper<Goods>().eq(Goods::getTabId, item.getRecommendId()));
            List<RecommendGoodsVO> goodsList = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsPage.getRecords());
            PageResult<RecommendGoodsVO> result = new PageResult<>(page.getTotal(), query.getPageSize(), query.getPage(), page.getPages(), goodsList);
            tabGoods.setGoodsItems(result);
            list.add(tabGoods);
        }

        IndexTabRecommendVO recommendVO = new IndexTabRecommendVO();
        recommendVO.setId(indexRecommend.getId());
        recommendVO.setName(indexRecommend.getName());
        recommendVO.setCover(indexRecommend.getCover());
        recommendVO.setSubTypes(list);
        return recommendVO;
    }

    @Override
    public PageResult<RecommendGoodsVO> getRecommendGoodsByPage(Query query) {
        //构建分页查询条件
        Page<Goods> page = new Page<>(query.getPage(), query.getPageSize());
        Page<Goods> goodsPage = baseMapper.selectPage(page, null);
        List<RecommendGoodsVO> result = GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsPage.getRecords());
        return new PageResult<>(page.getTotal(), query.getPageSize(), query.getPage(), page.getPages(), result);
    }

    @Override
    public GoodsVO getGoodsDetail(Integer id) {
        //根据id获取商品详情
        Goods goods = baseMapper.selectById(id);
        if (goods == null) {
            throw new ServerException("商品不存在");
        }
        GoodsVO goodsVO=GoodsConvert.INSTANCE.convertToGoodsVO(goods);
        //商品规格
        List<GoodsDetail> goodsDetails = goodsDetailMapper.selectList(new LambdaQueryWrapper<GoodsDetail>().eq(GoodsDetail::getGoodsId,goods.getId()));
        goodsVO.setProperties(goodsDetails);
        //商品可选规格集合
        List<GoodsSpecification> specificationsList=goodsSpecificationMapper.selectList(new LambdaQueryWrapper<GoodsSpecification>().eq(GoodsSpecification::getGoodsId,goods.getId()));
        goodsVO.setSpecs(specificationsList);
        //商品规格详情
        List<GoodsSpecificationDetail> goodsSpecificationDetailList=goodsSpecificationDetailMapper.selectList(new LambdaQueryWrapper<GoodsSpecificationDetail>()
                .eq(GoodsSpecificationDetail::getGoodsId,goods.getId()));
        goodsVO.setSkus(goodsSpecificationDetailList);
        log.info("Skus列表里的信息为 :{}",goodsSpecificationDetailList);
        //查找同类商品,去除自身
        List<Goods> goodsList = baseMapper.selectList(new LambdaQueryWrapper<Goods>().eq(Goods::getCategoryId,goods.getCategoryId()).ne(Goods::getId,goods.getId()));
        List<RecommendGoodsVO> recommendGoodsVOList=GoodsConvert.INSTANCE.convertToRecommendGoodsVOList(goodsList);
        goodsVO.setSimilarProducts(recommendGoodsVOList);
        return goodsVO;
    }
}

