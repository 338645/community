package com.hacg.community.comtroller;

import com.hacg.community.dto.AccessTokenDto;
import com.hacg.community.dto.GithubUser;
import com.hacg.community.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
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

    @Value("${github.client_id}")
    private String client_id;

    @Value("${github.client_secret}")
    private String client_secret;

    @Value("${github.redirect_uri}")
    private String redirect_uri;



    /**
     * @param code:从github的authorize传入的code，用于post给github的access_token
     * @param state：防止跨站请求伪造攻击的随机数
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirect_uri);
        accessTokenDto.setState(state);

        //向github获取access_token
        String accessToken = githubUtil.getAccess_token(accessTokenDto);
        //使用获得的access_token向github获取用户信息
        GithubUser user = githubUtil.getUser(accessToken);
        System.out.println(user);
        return "index";
    }


}
