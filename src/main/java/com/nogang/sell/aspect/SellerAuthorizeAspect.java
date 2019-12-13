package com.nogang.sell.aspect;

import com.nogang.sell.constant.CookieConstant;
import com.nogang.sell.constant.RedisConstant;
import com.nogang.sell.exception.SellException;
import com.nogang.sell.exception.SellerAuthorizeException;
import com.nogang.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Pointcut("execution(public * com.nogang.sell.controller.Seller*.*(..))" +
    "&& !execution(public * com.nogang.sell.controller.SellerUserController.*(..))")
    public void verify(){}

//    @Before("verify()")
    public void doverify(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("【登录检验】Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        //去redis查询
        String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)){
            log.warn("【登录检验】Redis中查不到token");
            throw new SellerAuthorizeException();
        }

    }
}
