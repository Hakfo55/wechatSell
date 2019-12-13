package com.nogang.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "project-url")
@Component
public class ProjectUrlConfig {
    /**
     * 微信公众平台授权url
     */
    private String wechatMpAuthorize;

    /**
     * 微信开发平台授权url
     */
    private String wechatOpenAuthorize;

    /**
     * 项目url
     */
    private String sell;

}
