package com.hacg.community.comtroller;

import com.hacg.community.model.User;
import com.hacg.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目关于web操作相关控制器
 * 后端路径：
 * <p>
 * 前端路径：
 * 1)../为上一目录
 * 2)/为当前目录
 * 3)//为前缀只加协议
 * 1. dirname/ 这种路径，浏览器会讲地址栏的url和该路径拼接。
 * 2.  /dirname 这种路径，浏览器会将协议、主机名和该路径拼接。
 * 3. ./dirname 这种路径，浏览器会将地址栏的url和该路径拼接。
 * 4. //dirname 这种路径，浏览器会将协议头和该api拼接。
 */

@CrossOrigin(allowCredentials = "true", originPatterns = "*")
@RestController
public class AuthorizeController {

    /**
     * vue项目github登录步骤，
     * vue页面访问login接口
     * login接口发起authorize请求
     * github重定向到callback
     * 完成登录返回用户数据
     * 返回成功后，前端把resp的data里的数据填入页面
     */

    @Autowired
    private UserService userService;

    /**
     * @param code:从github的authorize传入的code，用于post给github的access_token
     * @param state：防止跨站请求伪造攻击的随机数
     * @return
     */
    @GetMapping("/callback")
    public User callback(@RequestParam(name = "code") String code,
                         @RequestParam(name = "state") String state,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        //插入用户
        User user = userService.insertUser(code, state);


        if (user != null) {
            //发送给浏览器一个cookie，默认expire时间为session
            response.addCookie(new Cookie("token", user.getToken()));
            request.getSession().setAttribute("user", user);
            //登录成功返回用户
            return user;
            //return null;
        } else {
            //登录失败操作
            return null;
        }
    }

    @PostMapping("/getUserByToken")
    public User getUserByToken(@RequestBody User user1) {
        User user = userService.selectByToken(user1.getToken());
        return user;
    }
}
