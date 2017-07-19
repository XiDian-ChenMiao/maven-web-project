package com.seckill.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件描述：全局异常解析器
 * 创建作者：陈苗
 * 创建时间：2016年6月3日 22:28
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver{
    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler 处理器适配器要执行的Handler对象
     * @param e
     * @return
     */
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) {
        CustomException exception;
        if (e instanceof CustomException) {
            exception = (CustomException) e;
        } else {
            exception = new CustomException("未知错误");
        }
        String errorMesg = exception.getMessage();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message",errorMesg);
        modelAndView.setViewName("error/global-error");
        return modelAndView;
    }
}
