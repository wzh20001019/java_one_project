package com.xiaowen.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaowen.common.CustomException;
import com.xiaowen.entity.Category;
import com.xiaowen.entity.Dish;
import com.xiaowen.entity.SetMeal;
import com.xiaowen.mapper.CategoryMapper;
import com.xiaowen.service.CategoryService;
import com.xiaowen.service.DishService;
import com.xiaowen.service.SetMealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    private final DishService dishService;
    private SetMealService setMealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);

        int count1 = dishService.count(dishLambdaQueryWrapper);

        // 关联了 菜品
        if (count1 > 0) {
            throw new CustomException("当前分类关联了菜品, 不能删除");
        }

        LambdaQueryWrapper<SetMeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealLambdaQueryWrapper.eq(SetMeal::getCategoryId, id);

        int count2 = setMealService.count(setMealLambdaQueryWrapper);

        // 关联了套餐
        if (count2 > 0) {
            throw new CustomException("当前分类关联了套餐, 不能删除");
        }

        super.removeById(id);
    }
}
