package com.camelot.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lord Camelot
 * @date 2024/2/7
 * @description
 */
public class PropertiesUtil {
    private static Properties props = new Properties();
    private static Map<String, String> PROP_MAP = new ConcurrentHashMap<>();

    static {
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(new InputStreamReader(is, StandardCharsets.UTF_8));

            Iterator<Object> iterator = props.keySet().iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                PROP_MAP.put(key, props.getProperty(key));
            }
        } catch (Exception e) {

        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getString(String key) {
        return PROP_MAP.get(key);
    }

}
