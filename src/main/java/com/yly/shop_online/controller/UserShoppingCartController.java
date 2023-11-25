package com.yly.shop_online.controller;

import com.yly.shop_online.common.result.Result;
import com.yly.shop_online.query.CartQuery;
import com.yly.shop_online.query.EditCartQuery;
import com.yly.shop_online.service.UserShoppingCartService;
import com.yly.shop_online.vo.CartGoodsVO;
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
@Tag(name = "购物车管理")
@RestController
@RequestMapping("/cart")
@AllArgsConstructor
@Slf4j
public class UserShoppingCartController {


    private final UserShoppingCartService userShoppingCartService;

    @Operation(summary = "加入购物车")
    @PostMapping("add")
    public Result<CartGoodsVO> addShopCart(@RequestBody @Validated CartQuery query, HttpServletRequest request) {
        query.setUserId(getUserId(request));
        CartGoodsVO goodsVO = userShoppingCartService.addShopCart(query);
        return Result.ok(goodsVO);
    }
    @Operation(summary = "获取购物车列表")
    @GetMapping("list")
    public Result<List<CartGoodsVO>> getShopCartList(HttpServletRequest request){
        Integer userId=getUserId(request);
        List<CartGoodsVO> list=userShoppingCartService.shopCartList(userId);

        return Result.ok(list);
    }

    @Operation(summary = "修改购物车单品")
    @PutMapping("edit")
    public Result<CartGoodsVO> editShopCart(@RequestBody @Validated EditCartQuery query) {
        log.info(query.toString());
        CartGoodsVO goodsVO = userShoppingCartService.editCart(query);
        return Result.ok(goodsVO);
    }

    @Operation(summary = "删除购物车单品")
    @DeleteMapping("remove")
    public Result removeShopCart(@RequestParam String id) {

        userShoppingCartService.removeById(Integer.parseInt(id));
        return Result.ok();
    }

    @Operation(summary = "购物车全选/取消全选")
    @PutMapping("selected")
    public Result editCartSelected(@RequestParam Boolean selected) {
        userShoppingCartService.editCartSelected(selected);
        return Result.ok();
    }
}