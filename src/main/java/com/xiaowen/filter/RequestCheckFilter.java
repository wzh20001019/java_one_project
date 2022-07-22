package com.xiaowen.filter;

import com.alibaba.fastjson.JSON;
import com.xiaowen.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否登陆
 * filterName: 过滤器名称
 * urlPatterns: 拦截请求路径    /* : (拦截所有请求路径)
 */
@WebFilter(filterName = "RequestCheckFilter")
@Slf4j
public class RequestCheckFilter implements Filter {
    // 路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("请求路径" + request.getRequestURI());

        // 1. 获取本地请求的路径
        String requestURL = request.getRequestURI();

        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",

                // 静态资源
                "/front/**",
                "/backend/**"
        };

        // 2. 判断本次请求是否需要处理
        boolean isNeedHandler = checkRequestUrl(urls, requestURL);

        // 3. 不需要处理, 直接放行
        if (isNeedHandler) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 判断登陆状态, 已登录, 直接放行
        if(request.getSession().getAttribute("employee") != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. 未登录, 通过输出流方式将数据返回
        response.getWriter().write(JSON.toJSONString(Result.error("not login")));
        return;
    }

    public boolean checkRequestUrl(String[] urls, String requestURL) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);

            if (match) {
                return true;
            }
        }

        return false;
    }

    ;
}
