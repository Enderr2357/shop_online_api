package com.yly.shop_online.controller;

import com.yly.shop_online.common.result.Result;
import com.yly.shop_online.entity.Category;
import com.yly.shop_online.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ender2357
 * @since 2023-11-09
 */
@Tag(name = "分类管理")
@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("index")
    @Operation(summary = "首页-商品分类")
    public Result<List<Category>> getIndexCategoryList() {
        List<Category> list = categoryService.getIndexCategoryList();
        return Result.ok(list);
    }
}
