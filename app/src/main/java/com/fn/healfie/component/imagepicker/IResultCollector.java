package com.fn.healfie.component.imagepicker;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * author: liu sai
 * date :  2018/7/26
 * desc :  IResultCollector
 */

public interface IResultCollector {
    void resolve(JSONArray array);
    void resolve(JSONObject object);
    void reject(String code, String message);
}
