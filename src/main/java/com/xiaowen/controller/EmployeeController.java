package com.xiaowen.controller;

import com.xiaowen.common.Result;
import com.xiaowen.entity.Employee;
import com.xiaowen.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.digester.Digester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Slf4j
@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1. 密码 md5 加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());


        return null;
    }
}
