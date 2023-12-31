package com.yly.shop_online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yly.shop_online.common.exception.ServerException;
import com.yly.shop_online.convert.AddressConvert;
import com.yly.shop_online.entity.UserShippingAddress;
import com.yly.shop_online.enums.AddressDefaultEnum;
import com.yly.shop_online.mapper.UserShippingAddressMapper;
import com.yly.shop_online.service.UserShippingAddressService;
import com.yly.shop_online.vo.AddressVO;
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
public class UserShippingAddressServiceImpl extends ServiceImpl<UserShippingAddressMapper, UserShippingAddress> implements UserShippingAddressService {

    @Autowired
    private  UserShippingAddressMapper userShippingAddressMapper;
    @Override
    public Integer saveShippingAddress(AddressVO addressVO) {

        UserShippingAddress convert= AddressConvert.INSTANCE.convert(addressVO);
        if(addressVO.getIsDefault()== AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            List<UserShippingAddress> list=baseMapper.selectList(new LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getIsDefault,AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            if(list.size()>0){
                throw new ServerException("已经存在默认地址,请勿重复操作");
            }
        }
        save(convert);
        return convert.getId();
    }

    @Override
    public Integer editShippingAddress(AddressVO addressVO) {
        UserShippingAddress userShippingAddress=baseMapper.selectById(addressVO.getId());
        if(userShippingAddress==null){
            throw new ServerException("地址不存在");
        }
        if(addressVO.getIsDefault()==AddressDefaultEnum.DEFAULT_ADDRESS.getValue()){
            //查询用户是否存在默认地址
            UserShippingAddress address=baseMapper.selectOne(new LambdaQueryWrapper<UserShippingAddress>().eq(UserShippingAddress::getIsDefault,AddressDefaultEnum.DEFAULT_ADDRESS.getValue()));
            //如果存在之前的默认地址,则修改
            if(address!=null){
                address.setIsDefault(AddressDefaultEnum.DEFAULT_ADDRESS.getValue());
                updateById(address);
            }
        }
        UserShippingAddress address=AddressConvert.INSTANCE.convert(addressVO);
        updateById(address);
        return address.getId();
    }

    @Override
    public List<AddressVO> getList(Integer userId) {
        LambdaQueryWrapper<UserShippingAddress> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserShippingAddress::getUserId,userId);
        queryWrapper.eq(UserShippingAddress::getDeleteFlag,0);
        queryWrapper.orderByDesc(UserShippingAddress::getIsDefault);
        List<UserShippingAddress> list=baseMapper.selectList(queryWrapper);
        List<AddressVO> addressVOList=AddressConvert.INSTANCE.convertToAddressVOList(list);
        return addressVOList;
    }

    @Override
    public AddressVO getAddressDetail(Integer addressId) {
        UserShippingAddress address=baseMapper.selectById(addressId);
        if (address==null){
            throw new ServerException("该地址已被删除,无法获取");
        }
        AddressVO addressVO=AddressConvert.INSTANCE.convertToAddressVO(address);
        return addressVO;
    }

    @Override
    public String DeleteAddressById(Integer addressId) {
        removeById(addressId);
        return "删除成功";
    }


}
