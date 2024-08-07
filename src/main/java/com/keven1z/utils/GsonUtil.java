package com.keven1z.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static java.lang.reflect.Modifier.TRANSIENT;


/**
 * @author keven1z
 * @date 2021/12/22
 */
public class GsonUtil {
    private static Gson gson = new GsonBuilder()
            .excludeFieldsWithModifiers(TRANSIENT).create();


    private GsonUtil() {
    }


    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }


    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T toBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }


}
