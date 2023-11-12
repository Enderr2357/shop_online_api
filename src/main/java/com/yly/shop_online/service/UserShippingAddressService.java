package com.yly.shop_online.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yly.shop_online.entity.UserShippingAddress;
import com.yly.shop_online.vo.AddressVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface UserShippingAddressService extends IService<UserShippingAddress> {

    /**
     * 添加收货地址
     * @param addressVO
     * @return
     */
     Integer saveShippingAddress(AddressVO addressVO);

    /**
     * 修改收货地址
     * @param addressVO
     * @return
     */
     Integer editShippingAddress(AddressVO addressVO);

    /**
     * 获取收货地址列表
     * @param userId
     * @return
     */
     List<AddressVO> getList(Integer userId);

    /**
     * 根据id查找地址详情
     * @param addressId
     * @return
     */
     AddressVO getAddressDetail(Integer addressId);

    /**
     * 根据id删除地址
     * @param addressId
     * @return
     */
     String DeleteAddressById(Integer addressId);

}
