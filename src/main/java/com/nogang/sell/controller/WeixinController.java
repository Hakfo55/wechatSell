package com.nogang.sell.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeixinController {
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("进入auth方法");
        log.info("code={}",code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx5ae48c862385c7e6&secret=a0d1a718f380d0cc8456c2ff2031637b&code=" + code + "&grant_type=authorization_code";
        RestTemplate restTemplate =  new RestTemplate();
        String result = restTemplate.getForObject(url,String.class);
        log.info("result={}",result);
    }
}
