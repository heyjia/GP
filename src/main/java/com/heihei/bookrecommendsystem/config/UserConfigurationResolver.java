package com.heihei.bookrecommendsystem.config;

import com.heihei.bookrecommendsystem.entity.UserDO;
import com.heihei.bookrecommendsystem.util.UserCookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class UserConfigurationResolver implements HandlerMethodArgumentResolver {

    @Autowired
    UserCookieUtil userCookieUtil;
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == UserDO.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response =  nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String paramvalue = request.getParameter(userCookieUtil.COOKIE_NAME_TOKEN);
        Cookie[] cookies = request.getCookies();
        String cookievalue = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(userCookieUtil.COOKIE_NAME_TOKEN)) {
                cookievalue = cookie.getValue();
            }
        }
        if (cookievalue == null && paramvalue == null) {
            return "login";
        }
        String key = paramvalue == null ? cookievalue : paramvalue;
        UserDO seckillUser = userCookieUtil.getCookieValue(response,key);
        return seckillUser;
    }
}
