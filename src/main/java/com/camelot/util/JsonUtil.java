package com.camelot.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Lord Camelot
 * @date 2024/2/8
 * @description
 */
public class JsonUtil {

    public static String convertObjToJson(Object obj) {
        if (null == obj) {
            return null;
        }
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }
}
