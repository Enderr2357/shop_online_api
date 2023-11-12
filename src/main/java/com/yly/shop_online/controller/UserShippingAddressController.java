package com.yly.shop_online.controller;

import com.yly.shop_online.common.exception.ServerException;
import com.yly.shop_online.common.result.Result;
import com.yly.shop_online.query.AddressQuery;
import com.yly.shop_online.service.UserShippingAddressService;
import com.yly.shop_online.vo.AddressVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.yly.shop_online.common.utils.ObtainUserIdUtils.getUserId;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
@Tag(name = "地址管理")
@RestController
@RequestMapping("member")
@Slf4j
@AllArgsConstructor
public class UserShippingAddressController {
    private final UserShippingAddressService userShippingAddressService;

    @Operation(summary = "添加收货地址")
    @PostMapping("address")
    public Result<Integer> saveAddress(@RequestBody @Validated AddressVO addressVO, HttpServletRequest request) {
        Integer userId = getUserId(request);
        addressVO.setUserId(userId);
        Integer addressId = userShippingAddressService.saveShippingAddress(addressVO);
        return Result.ok(addressId);
    }

    @Operation(summary = "修改收货地址")
    @PutMapping("address")
    public Result<Integer> editAddress(@RequestBody @Validated AddressVO addressVO, HttpServletRequest request) {
        if (addressVO.getId() == null) {
            throw new ServerException("请求参数不能为空");
        }
        addressVO.setUserId(getUserId(request));
        Integer addressId = userShippingAddressService.editShippingAddress(addressVO);
        return Result.ok(addressId);
    }

    @Operation(summary = "获取所有收货地址")
    @GetMapping("address")
    public Result<List<AddressVO>> getAddressList(HttpServletRequest request){
        Integer userId = getUserId(request);
        List<AddressVO> list=userShippingAddressService.getList(userId);
        return Result.ok(list);
    }

    @Operation(summary = "获取指定地址详情")
    @GetMapping("address/detail")
    public Result<AddressVO> getAddressDetail(AddressQuery query){
        Integer addressId=query.getAddressId();
        AddressVO addressVO=userShippingAddressService.getAddressDetail(addressId);
        return Result.ok(addressVO);
    }

    @Operation(summary = "删除指定地址")
    @DeleteMapping("address")
    public Result<String> deleteById(AddressQuery query){
        Integer addressId=query.getAddressId();
        String result= userShippingAddressService.DeleteAddressById(addressId);
        return Result.ok(result);
    }
}
