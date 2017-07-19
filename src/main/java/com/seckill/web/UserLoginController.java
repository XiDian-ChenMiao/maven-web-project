package com.seckill.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * 文件描述：系统登录控制器
 * 创建作者：陈苗
 * 创建时间：2016年6月4日 15:54
 */
@Controller
@RequestMapping("/user")
public class UserLoginController {
    /**
     * 登录方法
     * @param session
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    public String login(HttpSession session,String username, String password) throws Exception{
        //添加对用户名和密码的校验

        //将用户信息存储到会话状态中
        session.setAttribute("username",username);
        session.setAttribute("password",password);
        return "redirect:/seckill/list";//转到商品展示页面
    }

    /**
     * 登出方法
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session) throws Exception {
        session.setAttribute("username",null);
        session.setAttribute("password",null);
        session = null;
        return "redirect:/user/home";
    }

    /**
     * 返回登录首页
     * @return
     */
    @RequestMapping("/home")
    public String home() {
        return "login/login";
    }
}
