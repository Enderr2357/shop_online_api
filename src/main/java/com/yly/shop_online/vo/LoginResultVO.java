package com.yly.shop_online.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class LoginResultVO extends UserVO {
    @Schema(description = "用户id")
    private Integer id;
    @Schema(description = "手机号")
    private String mobile;
    @Schema(description = "登录token")
    private String token;
    @Schema(description = "用户名")
    private String nickname;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "账号")
    private String account;
    @Schema(description = "生日")
    private Timestamp birthday;
}