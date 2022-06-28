package com.hacg.community.comtroller;

import com.hacg.community.mapper.UserMapper;
import com.hacg.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    //默认访问首页controller
    @GetMapping("/")
    public String index(HttpServletRequest request) {
        System.out.println("有人访问");
        //从cookie中获得token
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();

                //使用token获得user对象
                User user = userMapper.findByToken(token);

                if (user != null) request.getSession().setAttribute("user", user);
                return "index";
            }
        }
        //如果没有token从session中删除用户信息
        request.getSession().removeAttribute("user");
        return "index";
    }
}
