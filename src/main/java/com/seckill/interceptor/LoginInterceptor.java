package com.seckill.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 文件描述：系统登录拦截器
 * 创建作者：陈苗
 * 创建时间：2016年6月4日 15:36
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 如果请求的是公开地址则无需拦截
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();//获取请求的URL
        //判断请求的URL是否是公开的URL，如果是则不拦截；如果不是，则需要拦截
        if (url.indexOf("login") >= 0)
            return true;//如果进行登录提交，则不拦截
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("username") != null)
            return true;//身份信息存在，则不拦截
        //如果既不是公开的URL，并且用户的身份信息也没有存在，则直接请求转发到系统登录页面
        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(httpServletRequest,httpServletResponse);
        return false;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
