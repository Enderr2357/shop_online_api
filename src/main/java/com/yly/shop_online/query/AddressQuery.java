package com.yly.shop_online.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddressQuery {
    @Schema(description = "地址id addressId")
    private Integer addressId;
}
