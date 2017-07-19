package com.seckill.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件描述：系统拦截器（只对处理器映射器拦截）
 * 创建作者：陈苗
 * 创建时间：2016年6月4日 15:15
 */
public class SystemInterceptor implements HandlerInterceptor {
    /**
     * 进入Handler方法之前执行（用于身份认证和身份授权）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return 如果为false则表示拦截，不向下执行；若为true，则放行；
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        return true;
    }

    /**
     * 进入Handler方法之后在返回ModelAndView之前执行（可以添加公用的模型数据添加到视图上，也可以在这里统一的指定视图，比如菜单的导航）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("这是系统拦截器的postHandle方法");
    }

    /**
     * 执行Handler完成之后执行此方法（统一的异常处理，和统一的日志处理）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @param e
     * @throws Exception
     */
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        System.out.println("这是系统拦截器的afterCompletion方法");
    }
}
