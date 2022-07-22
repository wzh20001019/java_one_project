package com.xiaowen.common;

import com.xiaowen.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 全局异常处理
 * annotations = {捕获加了这些注解的类}
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 指定异常 处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.info(e.getMessage());

        if(e.getMessage().contains("Duplicate entry")) {
            String[] errTexts = e.getMessage().split(" ");
            return Result.error(errTexts[2] + "已存在");
        }

        return Result.error("失败了");
    }
}
