package com.xiaowen.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaowen.entity.Employee;
import com.xiaowen.mapper.EmployeeMapper;
import com.xiaowen.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
