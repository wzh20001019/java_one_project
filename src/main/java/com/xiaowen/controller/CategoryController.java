package com.xiaowen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaowen.common.Result;
import com.xiaowen.entity.Category;
import com.xiaowen.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/query")
    public Result<Page> query(int page, int pageSize) {
        Page<Category> pageInfo = new Page(page, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }

    @PostMapping("/save")
    public Result<String> add(@RequestBody Category category) {
        log.info(category.toString());
        return categoryService.save(category) ? Result.success("添加成功") : Result.error("失败");
    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return Result.success("更新成功");
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable("id") Long id) {
        categoryService.removeById(id);

        return Result.success("删除成功");
    }
}
