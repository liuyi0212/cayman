package com.medishare.cayman.config;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 允许跨域.
 *
 * @author lxg
 * @version 1.0, 2016-04-22
 * @since 1.0
 */
@Component
public class SimpleFilter implements Filter {
	
    /**
     * 初始化
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        
    }

    /**
     * 过滤
     *
     * @param req
     * @param res
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletResponse response = (HttpServletResponse) res;
        final HttpServletRequest request = (HttpServletRequest) req;
        //如果是h5资源和后端接口在同一台服务器就不需要下面这些代码  如果前端H5资源和后端代码分离不在同一个服务器内或者在同一服务器内但是端口不一致则需要使用下面的代码
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,accept,Content-Type,SESSION,Cookie,Set-Cookie,Cookie-Alt,Msid,X-Medishare-city,X-Medishare-Token");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        filterChain.doFilter(request, response);
    }
    @Override
    public void destroy() {
        
    }
}