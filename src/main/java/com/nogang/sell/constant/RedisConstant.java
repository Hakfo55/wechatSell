package com.nogang.sell.constant;

public interface RedisConstant {
    Integer EXPIRE = 7200; //过期时间2小时,秒为单位
    String TOKEN_PREFIX = "token_%s"; //名字前缀
}
