package com.yly.shop_online.convert;

import com.yly.shop_online.entity.User;
import com.yly.shop_online.vo.LoginResultVO;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);
    

    LoginResultVO convertToLoginResultVO(User user);
}