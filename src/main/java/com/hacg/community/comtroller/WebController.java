package com.hacg.community.comtroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 项目关于web操作相关控制器
 * 后端路径：
 *
 * 前端路径：
 *  1)../为上一目录
 *  2)/为当前目录
 *  3)//为前缀只加协议
 *  1. dirname/ 这种路径，浏览器会讲地址栏的url和该路径拼接。
 * 2.  /dirname 这种路径，浏览器会将协议、主机名和该路径拼接。
 * 3. ./dirname 这种路径，浏览器会将地址栏的url和该路径拼接。
 * 4. //dirname 这种路径，浏览器会将协议头和该api拼接。
 */

@Controller
public class WebController {

    //默认访问首页controller
    @GetMapping("/")
    public String index(){
        return "index";
    }


}
