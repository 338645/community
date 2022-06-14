package com.hacg.community.comtroller;

import com.hacg.community.dto.AccessTokenDto;
import com.hacg.community.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

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

@Controller
public class WebController {

    //关于github登录的工具类
    @Autowired
    private GithubUtil githubUtil;

    //默认访问首页controller
    @GetMapping("/")
    public String index() {
        return "index";
    }


    /**
     * @param code:从github的authorize传入的code，用于post给github的access_token
     * @param state：防止跨站请求伪造攻击的随机数
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id("ae6d4adb632935201bcb");
        accessTokenDto.setClient_secret("ce3064a20497fb4c8cb1a61c84190c2622228d08");
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDto.setState(state);
        githubUtil.getAccess_token(accessTokenDto);

        return "index";
    }


}
