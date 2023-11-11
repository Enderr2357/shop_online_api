package com.yly.shop_online.convert;

import com.yly.shop_online.entity.User;
import com.yly.shop_online.vo.LoginResultVO;
import com.yly.shop_online.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author ycshang
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mapping(expression = "java(MapStruct.transferTimeStamp(entity.getBirthday()))", target = "birthday")
    User convert(UserVO entity);


    @Mapping(expression = "java(MapStruct.transferTime(user.getBirthday()))", target = "birthday")
    UserVO convertToUserVO(User user);

    @Mapping(expression = "java(MapStruct.transferTime(user.getBirthday()))", target = "birthday")
    LoginResultVO convertToLoginResultVO(User user);


    class MapStruct {
        public static Timestamp transferTime(LocalDateTime value) {
            return Timestamp.valueOf(value);
        }


        public static LocalDateTime transferTimeStamp(Timestamp time){
            return  time.toLocalDateTime();
        }
    }
}