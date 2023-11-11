package com.yly.shop_online.service;

import com.yly.shop_online.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yly.shop_online.query.UserLoginQuery;
import com.yly.shop_online.vo.LoginResultVO;
import com.yly.shop_online.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
public interface UserService extends IService<User> {

    //登录
    LoginResultVO login(UserLoginQuery query);


    /**
     * 根据UserId查找用户
     * @param userId
     * @return
     */
    User getUserInfo(Integer userId);

    /**
     * 修改用户信息
     * @param userVO
     * @return
     */
    UserVO editUserInfo(UserVO userVO);

    String editUserAvatar(Integer userId, MultipartFile  file);
}
