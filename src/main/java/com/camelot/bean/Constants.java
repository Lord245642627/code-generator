package com.camelot.bean;

import com.camelot.util.PropertiesUtil;

/**
 * @author Lord Camelot
 * @date 2024/2/8
 * @description
 */
public class Constants {
    public static String AUTHOR_NAME;

    public static Boolean IGNORE_TABLE_PREFIX;

    public static String SUFFIX_BEAN_QUERY;
    public static String SUFFIX_BEAN_QUERY_FUZZY;
    public static String SUFFIX_BEAN_QUERY_TIME_START;
    public static String SUFFIX_BEAN_QUERY_TIME_END;
    public static String SUFFIX_MAPPER;

    // 需要忽略的属性
    public static String IGNORE_BEAN_TOJSON_FIELD;
    public static String IGNORE_BEAN_TOJSON_EXPRESSION;
    public static String IGNORE_BEAN_TOJSON_CLASS;

    // 日期序列化、反序列化
    public static String BEAN_DATE_FORMAT_EXPRESSION;
    public static String BEAN_DATE_FORMAT_CLASS;
    public static String BEAN_DATE_UNFORMAT_EXPRESSION;
    public static String BEAN_DATE_UNFORMAT_CLASS;

    public static String PACKAGE_BASE;
    public static String PACKAGE_PO;
    public static String PACKAGE_VO;
    public static String PACKAGE_UTIL;
    public static String PACKAGE_ENUMS;
    public static String PACKAGE_EXCEPTION;
    public static String PACKAGE_QUERY;
    public static String PACKAGE_MAPPER;
    public static String PACKAGE_SERVICE;
    public static String PACKAGE_SERVICE_IMPL;
    public static String PACKAGE_CONTROLLER;

    private static String PATH_JAVA = "java";
    private static String PATH_RESOURCE = "resources";
    public static String PATH_BASE;
    public static String PATH_PO;
    public static String PATH_VO;
    public static String PATH_UTIL;
    public static String PATH_ENUMS;
    public static String PATH_EXCEPTION;
    public static String PATH_QUERY;
    public static String PATH_MAPPER;
    public static String PATH_MAPPER_XML;
    public static String PATH_SERVICE;
    public static String PATH_SERVICE_IMPL;
    public static String PATH_CONTROLLER;



    static {
        AUTHOR_NAME = PropertiesUtil.getString("author.name");

        IGNORE_BEAN_TOJSON_FIELD = PropertiesUtil.getString("ignore.bean.tojson.field");
        IGNORE_BEAN_TOJSON_EXPRESSION = PropertiesUtil.getString("ignore.bean.tojson.expression");
        IGNORE_BEAN_TOJSON_CLASS = PropertiesUtil.getString("ignore.bean.tojson.class");
        BEAN_DATE_FORMAT_EXPRESSION = PropertiesUtil.getString("bean.date.format.expression");
        BEAN_DATE_FORMAT_CLASS = PropertiesUtil.getString("bean.date.format.class");
        BEAN_DATE_UNFORMAT_EXPRESSION = PropertiesUtil.getString("bean.date.unformat.expression");
        BEAN_DATE_UNFORMAT_CLASS = PropertiesUtil.getString("bean.date.unformat.class");

        IGNORE_TABLE_PREFIX = Boolean.valueOf(PropertiesUtil.getString("ignore.table.prefix"));
        SUFFIX_BEAN_QUERY = PropertiesUtil.getString("suffix.bean.query");
        SUFFIX_BEAN_QUERY_FUZZY = PropertiesUtil.getString("suffix.bean.query.fuzzy");
        SUFFIX_BEAN_QUERY_TIME_START = PropertiesUtil.getString("suffix.bean.query.time.start");
        SUFFIX_BEAN_QUERY_TIME_END = PropertiesUtil.getString("suffix.bean.query.time.end");
        SUFFIX_MAPPER = PropertiesUtil.getString("suffix.mapper");

        PACKAGE_BASE = PropertiesUtil.getString("package.base");
        PACKAGE_PO = PACKAGE_BASE + "." + PropertiesUtil.getString("package.po");
        PACKAGE_VO = PACKAGE_BASE + "." + PropertiesUtil.getString("package.vo");
        PACKAGE_UTIL = PACKAGE_BASE + "." + PropertiesUtil.getString("package.util");
        PACKAGE_ENUMS = PACKAGE_BASE + "." + PropertiesUtil.getString("package.enums");
        PACKAGE_EXCEPTION = PACKAGE_BASE + "." + PropertiesUtil.getString("package.exception");
        PACKAGE_QUERY = PACKAGE_BASE + "." + PropertiesUtil.getString("package.query");
        PACKAGE_MAPPER = PACKAGE_BASE + "." + PropertiesUtil.getString("package.mapper");
        PACKAGE_SERVICE = PACKAGE_BASE + "." + PropertiesUtil.getString("package.service");
        PACKAGE_SERVICE_IMPL = PACKAGE_SERVICE + "." + PropertiesUtil.getString("package.service.impl");
        PACKAGE_CONTROLLER = PACKAGE_BASE + "." + PropertiesUtil.getString("package.controller");

        PATH_BASE = PropertiesUtil.getString("path.base") + "/" + PATH_JAVA;
        PATH_BASE = PATH_BASE.replace(".", "/");
        PATH_PO = PATH_BASE + "/" + PACKAGE_PO.replace(".", "/");
        PATH_VO = PATH_BASE + "/" + PACKAGE_VO.replace(".", "/");
        PATH_UTIL = PATH_BASE + "/" + PACKAGE_UTIL.replace(".", "/");
        PATH_ENUMS = PATH_BASE + "/" + PACKAGE_ENUMS.replace(".", "/");
        PATH_EXCEPTION = PATH_BASE + "/" + PACKAGE_EXCEPTION.replace(".", "/");
        PATH_QUERY = PATH_BASE + "/" + PACKAGE_QUERY.replace(".", "/");
        PATH_MAPPER = PATH_BASE + "/" + PACKAGE_MAPPER.replace(".", "/");
        PATH_MAPPER_XML = PropertiesUtil.getString("path.base") + "/" + PATH_RESOURCE + "/" + PACKAGE_MAPPER.replace(".", "/");
        PATH_SERVICE = PATH_BASE + "/" + PACKAGE_SERVICE.replace(".", "/");
        PATH_SERVICE_IMPL = PATH_BASE + "/" + PACKAGE_SERVICE_IMPL.replace(".", "/");
        PATH_CONTROLLER = PATH_BASE + "/" + PACKAGE_CONTROLLER.replace(".", "/");
    }

    public static final String[] SQL_DATE_TIME_TYPE = new String[]{"datetime", "timestamp"};
    public static final String[] SQL_DATE_TYPE = new String[]{"date"};
    public static final String[] SQL_DECIMAL_TYPE = new String[]{"decimal", "double", "float"};
    public static final String[] SQL_STRING_TYPE = new String[]{"char", "varchar", "text", "mediumtext", "longtext"};
    public static final String[] SQL_INTEGER_TYPE = new String[]{"int", "tinyint"};
    public static final String[] SQL_LONG_TYPE = new String[]{"bigint"};

    public static void main(String[] args) {
        System.out.println(PATH_MAPPER_XML);
    }

}
