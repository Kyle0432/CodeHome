package com.kyle.codehome.provider;

import com.alibaba.fastjson.JSON;
import com.kyle.codehome.dto.AccessTokenDTO;
import com.kyle.codehome.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
/**
 * @author: Kyle
 * @date: 2019/10/23 -  11:19
 */

/**
 * @Component,把普通pojo实例化到spring容器中，相当于配置文件中的
 * <bean id="" class=""/>  也就是加了此注解不需要  GithubProvider g = new GithubProvider()；
 * 获取对象了,此时该对象在spring容器里面,可以通过自动注入方式获取spring容器对象
 */
@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
           // log.error("getAccessToken error,{}", accessTokenDTO, e);
        }
        return null;
    }
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (Exception e) {
           //log.error("getUser error,{}", accessToken, e);
        }
        return null;
    }
}
