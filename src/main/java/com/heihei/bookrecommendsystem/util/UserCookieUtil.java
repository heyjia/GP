package com.heihei.bookrecommendsystem.util;

import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.redis.RedisService;
import com.heihei.bookrecommendsystem.redis.UserKeyPerfix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
public class UserCookieUtil {
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    RedisService redisService;

    public void addCookie(HttpServletResponse response, UserDO user, String token) {
        if (token == null || token.length() == 0) {
            token = UUID.randomUUID().toString().replace("-","");
        }
        redisService.set(UserKeyPerfix.userKeyPerfix,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,token);
        if (UserKeyPerfix.userKeyPerfix.getExpireSeconds() != 0) {
            cookie.setMaxAge(UserKeyPerfix.userKeyPerfix.getExpireSeconds());
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public UserDO getCookieValue (HttpServletResponse response, String key) {
        UserDO user = redisService.get(UserKeyPerfix.userKeyPerfix,key,UserDO.class);
        if (user != null) {
            //刷新过时时间
            addCookie(response,user,key);
        }
        return user;
    }

    public void removeCookie(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String cookievalue = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(COOKIE_NAME_TOKEN)) {
                cookievalue = cookie.getValue();
            }
        }
        if (cookievalue == null) {
            return;
        }
        //删除redis
        redisService.del(UserKeyPerfix.userKeyPerfix,cookievalue);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN,cookievalue);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
