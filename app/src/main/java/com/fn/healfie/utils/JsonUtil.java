package com.fn.healfie.utils;

import com.fn.healfie.model.BaseBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


/**
 * Created by thinkpad on 2016/6/12.
 * Json解析工具类，封装Gson框架
 */
public class JsonUtil {

    public static String outJson = "{\"rettoken\":\"1\"," +
            "\"retcode\":1,\"retmsg\":\"1\",\"retdata\":replace}";

    /**
     * 获取json解析后的javabean
     *
     * @param json  json字符串
     * @param clazz 对应beanclass
     * @param <T>   泛型
     * @return 解析后的bean
     */
    public static <T> T getBean(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    /**
     * 对象转json
     *
     * @param clazz 对应beanclass
     * @return 解析后的bean
     */
    public static String getJson(BaseBean clazz) {
        Gson gson = new Gson();
        return gson.toJson(clazz);
    }

}
