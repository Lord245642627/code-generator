package com.camelot.builder;

import com.camelot.bean.Constants;
import com.camelot.bean.FieldInfo;
import com.camelot.bean.TableInfo;
import com.camelot.util.DateUtil;
import com.camelot.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author Lord Camelot
 * @date 2024/2/8
 * @description
 */
public class BuildPo {

    private static final Logger logger = LoggerFactory.getLogger(BuildPo.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_PO);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File poFile = new File(folder, tableInfo.getBeanName() + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, "utf8");
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_PO + ";");
            bw.newLine();
            bw.newLine();

            if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
                bw.write("import " + Constants.PACKAGE_ENUMS + ".DateTimePatternEnum" + ";");
                bw.newLine();
                bw.write("import " + Constants.PACKAGE_UTIL + ".DateUtil" + ";");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_FORMAT_CLASS + ";");
                bw.newLine();
                bw.write(Constants.BEAN_DATE_UNFORMAT_CLASS + ";");
                bw.newLine();
            }

            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                if (ArrayUtils.contains(Constants.IGNORE_BEAN_TOJSON_FIELD.split(","), fieldInfo.getPropertyName())) {
                    bw.write(Constants.IGNORE_BEAN_TOJSON_CLASS + ";");
                    bw.newLine();
                    break;
                }
            }

            bw.newLine();

            bw.write("import java.io.Serializable;");
            bw.newLine();

            if (tableInfo.getHaveBigDecimal()) {
                bw.write("import java.math.BigDecimal;");
                bw.newLine();
            }

            if (tableInfo.getHaveDate() || tableInfo.getHaveDateTime()) {
                bw.write("import java.util.Date;");
                bw.newLine();
            }

            bw.newLine();

            // 构建类注释
            BuildComment.createClassComment(bw, tableInfo.getComment());

            bw.write("public class " + tableInfo.getBeanName() + " implements Serializable {");
            bw.newLine();
            bw.newLine();

            // 构建字段
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                // 构建字段注释
                BuildComment.createFieldComment(bw, fieldInfo.getComment());

                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, fieldInfo.getSqlType())) {
                    bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtil.YYYY_MM_DD_HH_MM_SS));
                    bw.newLine();
                    bw.write("\t" + String.format(Constants.BEAN_DATE_UNFORMAT_EXPRESSION, DateUtil.YYYY_MM_DD_HH_MM_SS));
                    bw.newLine();
                }
                if (ArrayUtils.contains(Constants.SQL_DATE_TYPE, fieldInfo.getSqlType())) {
                    bw.write("\t" + String.format(Constants.BEAN_DATE_FORMAT_EXPRESSION, DateUtil.YYYY_MM_DD_1));
                    bw.newLine();
                    bw.write("\t" + String.format(Constants.BEAN_DATE_UNFORMAT_EXPRESSION, DateUtil.YYYY_MM_DD_1));
                    bw.newLine();
                }

                if (ArrayUtils.contains(Constants.IGNORE_BEAN_TOJSON_FIELD.split(","), fieldInfo.getPropertyName())) {
                    bw.write("\t" + Constants.IGNORE_BEAN_TOJSON_EXPRESSION);
                    bw.newLine();
                }

                bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.newLine();
            }

            // 构建 getter 和 setter
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                String tempField = StringUtil.upperCaseFirstLetter(fieldInfo.getPropertyName());
                bw.write("\tpublic " + fieldInfo.getJavaType() + " get" + tempField + "() {");
                bw.newLine();
                bw.write("\t\treturn " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();

                bw.write("\tpublic void set" + tempField + "(" + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ") {");
                bw.newLine();
                bw.write("\t\tthis." + fieldInfo.getPropertyName() + " = " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.write("\t}");
                bw.newLine();
                bw.newLine();
            }

            StringBuffer toString = new StringBuffer();
            toString.append("\"" + tableInfo.getBeanName() + "{\" + ");
            Integer index = 0;
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                index++;
                String propertyName = fieldInfo.getPropertyName();
                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, fieldInfo.getSqlType())) {
                    propertyName = "DateUtil.format(" + propertyName + ", DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern())";
                } else if (ArrayUtils.contains(Constants.SQL_DATE_TYPE, fieldInfo.getSqlType())) {
                    propertyName = "DateUtil.format(" + propertyName + ", DateTimePatternEnum.YYYY_MM_DD.getPattern())";
                }
                toString.append("\"" + fieldInfo.getComment() + ": \" + (" + fieldInfo.getPropertyName() + " == null ? \"空\" : " + propertyName + ")");
                if (index < tableInfo.getFieldList().size()) {
                    toString.append(" + \", \" + ");
                }
            }
            toString.append(" + \"}\"");

            bw.write("\t@Override");
            bw.newLine();
            bw.write("\tpublic String toString() {");
            bw.newLine();
            bw.write("\t\treturn " + toString + ";");
            bw.newLine();
            bw.write("\t}");
            bw.newLine();

            bw.write("}");
            bw.flush();
        } catch (Exception e) {
            logger.error("创建 po 失败", e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outw != null) {
                try {
                    outw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
