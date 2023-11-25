package com.yly.shop_online.convert;

import com.yly.shop_online.entity.UserOrder;
import com.yly.shop_online.vo.OrderDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserOrderDetailConvert {


    UserOrderDetailConvert INSTANCE = Mappers.getMapper(UserOrderDetailConvert.class);


    OrderDetailVO convertToOrderDetailVo(UserOrder userOrder);
}