package com.camelot.builder;

import com.camelot.bean.Constants;
import com.camelot.bean.FieldInfo;
import com.camelot.bean.TableInfo;
import com.camelot.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Lord Camelot
 * @date 2024/2/8
 * @description
 */
public class BuildQuery {

    private static final Logger logger = LoggerFactory.getLogger(BuildQuery.class);

    public static void execute(TableInfo tableInfo) {
        File folder = new File(Constants.PATH_QUERY);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File poFile = new File(folder, tableInfo.getBeanParamName() + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        try {
            out = new FileOutputStream(poFile);
            outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            bw = new BufferedWriter(outw);

            bw.write("package " + Constants.PACKAGE_QUERY + ";");
            bw.newLine();
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
            BuildComment.createClassComment(bw, tableInfo.getComment() + "查询对象");

            bw.write("public class " + tableInfo.getBeanParamName() + " extends BaseQuery {");
            bw.newLine();
            bw.newLine();

            // 构建字段
            for (FieldInfo fieldInfo : tableInfo.getFieldList()) {
                // 构建字段注释
                BuildComment.createFieldComment(bw, fieldInfo.getComment());

                bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + ";");
                bw.newLine();
                bw.newLine();

                // 模糊查询
                if (ArrayUtils.contains(Constants.SQL_STRING_TYPE, fieldInfo.getSqlType())) {
                    BuildComment.createFieldComment(bw, fieldInfo.getComment() + "模糊查询");
                    bw.write("\tprivate " + fieldInfo.getJavaType() + " " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_FUZZY + ";");
                    bw.newLine();
                    bw.newLine();
                }

                if (ArrayUtils.contains(Constants.SQL_DATE_TIME_TYPE, fieldInfo.getSqlType()) || ArrayUtils.contains(Constants.SQL_DATE_TYPE, fieldInfo.getSqlType())) {
                    BuildComment.createFieldComment(bw, fieldInfo.getComment() + "查询开始时间");
                    bw.write("\tprivate String " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_START + ";");
                    bw.newLine();
                    bw.newLine();
                    BuildComment.createFieldComment(bw, fieldInfo.getComment() + "查询结束时间");
                    bw.write("\tprivate String " + fieldInfo.getPropertyName() + Constants.SUFFIX_BEAN_QUERY_TIME_END + ";");
                    bw.newLine();
                    bw.newLine();
                }
            }

            // 构建 getter 和 setter
            buildGetAndSet(bw, tableInfo.getFieldList());
            buildGetAndSet(bw, tableInfo.getFieldExtendList());

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

    private static void buildGetAndSet(BufferedWriter bw, List<FieldInfo> fieldInfoList) throws IOException {
        for (FieldInfo fieldInfo : fieldInfoList) {
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
    }
}
