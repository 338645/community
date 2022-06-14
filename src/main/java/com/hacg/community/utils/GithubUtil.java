package com.hacg.community.utils;

import com.alibaba.fastjson.JSON;
import com.hacg.community.dto.AccessTokenDto;
import com.hacg.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubUtil {

    /**
     * 获得github用户的授权令牌
     * @param accessTokenDto:授权数据传递对象
     * @return
     */
    public String getAccess_token(AccessTokenDto accessTokenDto){
        final MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDto));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //返回access_token
            String ret = response.body().string();
            System.out.println(ret);
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取github用户的信息
     * @param access_token：用户的授权令牌
     * @return
     */
    public GithubUser getUser(String access_token){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://github.com/user?access_token ="+access_token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser user = JSON.parseObject(string, GithubUser.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
