package com.xiaowen.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaowen.entity.SetMeal;
import com.xiaowen.mapper.SetMealMapper;
import com.xiaowen.service.SetMealService;
import org.springframework.stereotype.Service;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal> implements SetMealService {
}
