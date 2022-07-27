package com.xiaowen.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaowen.common.Result;
import com.xiaowen.entity.Employee;
import com.xiaowen.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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

        // 2, 根据用户提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //        employeeService.getOne(new LambdaQueryWrapper<Employee>()
//                .eq(Employee::getUsername, employee.getUsername()));

        // 3. 没有查询到数据
        if (Objects.isNull(emp)) {
            return Result.error("登陆失败");
        }

        // 4. 密码比对
        if (!emp.getPassword().equals(password)) {
            return Result.error("密码错误");
        }

        // 5. 查看员工状态
        if (emp.getStatus() == 0) {
            return Result.error("账号已禁用");
        }

        // 6. 登陆成功, id放入 Session
        request.getSession().setAttribute("employee", emp.getId());

        return Result.success(emp);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        // 清理 Session 中保存的员工id
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    @PostMapping("/save")
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();

        if(Objects.isNull(password)) {
            employee.setPassword(DigestUtils.md5DigestAsHex("12345".getBytes()));
        } else {
            employee.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        }

//        try {
//            employeeService.save(employee);
//        } catch(Exception e) {
//
//        }

        employeeService.save(employee);

        return Result.success("添加成功");
    }

    @GetMapping("/query")
    public Result<Page> query(int page, int pageSize, String name) {
        Page pageInfo = new Page(page,pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();

        // 模糊查询
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);

        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, queryWrapper);

        return Result.success(pageInfo);
    }

    @PutMapping("/update")
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        employeeService.updateById(employee);

        return Result.success("员工信息修改成功");
    }


    @DeleteMapping("/delete/{id}")
    public Result<String> del(@PathVariable("id") Long id) {
//        log.info(id.toString());

        employeeService.removeById(id);

        return Result.success("删除成功");
    }
}
