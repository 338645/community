package com.hacg.community.service;

import com.hacg.community.dto.AccessTokenDto;
import com.hacg.community.dto.GithubUser;
import com.hacg.community.mapper.UserMapper;
import com.hacg.community.model.User;
import com.hacg.community.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    //关于github登录的工具类
    @Autowired
    private GithubUtil githubUtil;

    @Value("${github.client_id}")
    private String client_id;

    @Value("${github.client_secret}")
    private String client_secret;

    @Value("${github.redirect_uri}")
    private String redirect_uri;

    public User insertUser(String code, String state) {
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirect_uri);
        accessTokenDto.setState(state);

        //向github获取access_token
        String accessToken = githubUtil.getAccess_token(accessTokenDto);
        //使用获得的access_token向github获取用户信息
        GithubUser githubUtilUser = githubUtil.getUser(accessToken);
        //将用户信息存入数据库
        String token = UUID.randomUUID().toString();
        User user = null;

        if (githubUtilUser != null && githubUtilUser.getId() != null) {
            //登录成功操作
            //将用户信息存入数据库
            user = new User();
            user.setAccount_id(String.valueOf(githubUtilUser.getId()));
            user.setName(githubUtilUser.getName());
            user.setToken(token);
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insertUser(user);
        }

        return user;
    }
}
