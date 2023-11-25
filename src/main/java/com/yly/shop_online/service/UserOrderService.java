package com.yly.shop_online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yly.shop_online.common.result.PageResult;
import com.yly.shop_online.entity.UserOrder;
import com.yly.shop_online.query.CancelGoodsQuery;
import com.yly.shop_online.query.OrderQuery;
import com.yly.shop_online.vo.OrderDetailVO;
import com.yly.shop_online.vo.OrderPreQuery;
import com.yly.shop_online.vo.SubmitOrderVO;
import com.yly.shop_online.vo.UserOrderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface UserOrderService extends IService<UserOrder> {
    /**
     * 提交订单
     *
     * @return
     */
    Integer addGoodsOrder(UserOrderVO orderVO);
    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    OrderDetailVO getOrderDetail(Integer id);
    /**
     * 填写订单 - 获取预付订单
     *
     * @param userId
     * @return
     */
    SubmitOrderVO getPreOrderDetail(Integer userId);
    /**
     * 填写订单 - 获取立即购买订单
     *
     * @param query
     * @return
     */
    SubmitOrderVO getPreNowOrderDetail(OrderPreQuery query);
    /**
     * 填写订单 - 获取再次购买订单
     *
     * @param id
     * @return
     */
    SubmitOrderVO getRepurchaseOrderDetail(Integer id);
    /**
     * 订单列表
     *
     * @param query
     * @return
     */
    PageResult<OrderDetailVO> getOrderList(OrderQuery query);
    /**
     * 取消订单
     *
     * @param query
     * @return
     */
    OrderDetailVO cancelOrder(CancelGoodsQuery query);
    /**
     * 删除订单
     *
     * @param ids
     */
    void deleteOrder(List<Integer> ids, Integer userId);
}
