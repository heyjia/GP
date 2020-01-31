package com.heihei.bookrecommendsystem.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    public static <T> T  stringToBean(String str,Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }
        if (clazz == long.class || clazz == Long.class) {
            return (T)Long.valueOf(str);
        }
        if (clazz == String.class) {
            return (T)str;
        }
        return JSONObject.toJavaObject(JSONObject.parseObject(str),clazz);
    }

    public static <T>String beanToString(T t) {
        if (t == null) {
            return null;
        }
        Class clazz = t.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return t+"";
        }
        if (clazz == long.class || clazz == Long.class) {
            return t+"";
        }
        if (clazz == String.class) {
            return (String)t;
        }
        return JSONObject.toJSONString(t);
    }

}
