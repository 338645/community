package com.hacg.community.service;

import com.hacg.community.dto.AccessTokenDto;
import com.hacg.community.dto.GithubUser;
import com.hacg.community.mapper.UserMapper;
import com.hacg.community.model.User;
import com.hacg.community.utils.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
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
        AccessTokenDto accessTokenDto = AccessTokenDto.builder()
                .client_id(client_id).code(code)
                .client_secret(client_secret).state(state)
                .redirect_uri(redirect_uri).build();

        //向github获取access_token
        String accessToken = githubUtil.getAccess_token(accessTokenDto);
        //使用获得的access_token向github获取用户信息
        GithubUser githubUtilUser = githubUtil.getUser(accessToken);

        //将用户信息存入数据库
        String token = UUID.randomUUID().toString();
        User user = null;

        if (githubUtilUser != null && githubUtilUser.getId() != null) {
            //从数据库查询access_token，如果有相同的用户则更新后返回
            user = userMapper.selectByGithubAccount(String.valueOf(githubUtilUser.getId()));
            if (user != null) {
                user.setName(githubUtilUser.getName());
                user.setAvatarUrl(githubUtilUser.getAvatarUrl());
                user.setBio(githubUtilUser.getBio());
                user.setGmt_modified(System.currentTimeMillis());
                userMapper.updateUserByUser(user);
                return user;
            }
            //登录成功操作
            //将用户信息存入数据库
            long gmt_create = System.currentTimeMillis();
            user = User.builder().account_id(String.valueOf(githubUtilUser.getId()))
                    .name(githubUtilUser.getName())
                    .token(token).gmt_create(gmt_create)
                    .gmt_modified(gmt_create)
                    .avatarUrl(githubUtilUser.getAvatarUrl())
                    .bio(githubUtilUser.getBio())
                    .build();
            userMapper.insertUser(user);
        }

        return user;
    }

    public User selectByToken(String token) {
        User user = userMapper.findByToken(token);
        return user;
    }

    public List<Integer> selectByAccountId(String userId) {
        return userMapper.selectByAccountId(userId);
    }

    public User selectById(Integer userId) {
        return userMapper.selectById(userId);
    }
}
