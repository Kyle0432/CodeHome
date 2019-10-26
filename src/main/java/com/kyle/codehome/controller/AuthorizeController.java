package com.kyle.codehome.controller;

import com.kyle.codehome.dto.AccessTokenDTO;
import com.kyle.codehome.dto.GithubUser;
import com.kyle.codehome.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: Kyle
 * @date: 2019/10/23 -  10:58
 */
@Controller
public class AuthorizeController {
   //由于GithubProvider类上有@Compoent注解,所以这里可以扫描该组件进行自动装配
    @Autowired
    private GithubProvider githubProvider;

    //@Value会去配置文件里面读取以github.client.id为Key的Value,其他一样

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        return "index";
    }
}
