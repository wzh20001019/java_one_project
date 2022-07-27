package com.xiaowen.controller;


import com.xiaowen.entity.SetMeal;
import com.xiaowen.service.SetMealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/SetMeal")
@RequiredArgsConstructor
public class SetMealController {

    private final SetMealService setMealService;

}
