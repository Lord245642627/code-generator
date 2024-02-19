package com.camelot.builder;

import com.camelot.bean.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lord Camelot
 * @date 2024/2/17
 * @description
 */
public class BuildBase {
    private static Logger logger = LoggerFactory.getLogger(BuildBase.class);

    public static void execute() {
        List<String> headerInfoList = new ArrayList<>();

        // 生成 DateTimePatternEnum
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS + ";");
        build(headerInfoList, "DateTimePatternEnum", Constants.PATH_ENUMS);
        headerInfoList.clear();

        headerInfoList.add("package " + Constants.PACKAGE_UTIL + ";");
        build(headerInfoList, "DateUtil", Constants.PATH_UTIL);
        headerInfoList.clear();

        // 生成 BaseMapper
        headerInfoList.add("package " + Constants.PACKAGE_MAPPER + ";");
        build(headerInfoList, "BaseMapper", Constants.PATH_MAPPER);
        headerInfoList.clear();

        // 生成 PageSize 枚举类
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS + ";");
        build(headerInfoList, "PageSize", Constants.PATH_ENUMS);
        headerInfoList.clear();

        // 生成 SimplePage
        headerInfoList.add("package " + Constants.PACKAGE_QUERY + ";");
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".PageSize;");
        build(headerInfoList, "SimplePage", Constants.PATH_QUERY);
        headerInfoList.clear();

        // 生成 BaseQuery
        headerInfoList.add("package " + Constants.PACKAGE_QUERY + ";");
        build(headerInfoList, "BaseQuery", Constants.PATH_QUERY);
        headerInfoList.clear();

        // 生成 PaginationResultVO
        headerInfoList.add("package " + Constants.PACKAGE_VO + ";");
        headerInfoList.add("import java.util.ArrayList;");
        headerInfoList.add("import java.util.List;");
        build(headerInfoList, "PaginationResultVO", Constants.PATH_VO);
        headerInfoList.clear();

        // 生成 BusinessException
        headerInfoList.add("package " + Constants.PACKAGE_EXCEPTION + ";");
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;");
        build(headerInfoList, "BusinessException", Constants.PATH_EXCEPTION);
        headerInfoList.clear();

        // 生成 ResponseCodeEnum
        headerInfoList.add("package " + Constants.PACKAGE_ENUMS + ";");
        build(headerInfoList, "ResponseCodeEnum", Constants.PATH_ENUMS);
        headerInfoList.clear();

        // 生成 ABaseController
        headerInfoList.add("package " + Constants.PACKAGE_CONTROLLER + ";");
        headerInfoList.add("import " + Constants.PACKAGE_VO + ".ResponseVO;");
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;");
        build(headerInfoList, "ABaseController", Constants.PATH_CONTROLLER);
        headerInfoList.clear();

        // 生成 ResponseVO
        headerInfoList.add("package " + Constants.PACKAGE_VO + ";");
        build(headerInfoList, "ResponseVO", Constants.PATH_VO);
        headerInfoList.clear();

        // 生成 GlobalExceptionHandlerController
        headerInfoList.add("package " + Constants.PACKAGE_CONTROLLER + ";");
        headerInfoList.add("import " + Constants.PACKAGE_VO + ".ResponseVO;");
        headerInfoList.add("import " + Constants.PACKAGE_ENUMS + ".ResponseCodeEnum;");
        headerInfoList.add("import " + Constants.PACKAGE_EXCEPTION + ".BusinessException;");
        headerInfoList.add("import org.slf4j.Logger;");
        headerInfoList.add("import org.slf4j.LoggerFactory;");
        headerInfoList.add("import org.springframework.dao.DuplicateKeyException;");
        headerInfoList.add("import org.springframework.web.bind.annotation.ExceptionHandler;");
        headerInfoList.add("import org.springframework.web.servlet.NoHandlerFoundException;");
        headerInfoList.add("");
        headerInfoList.add("import javax.servlet.http.HttpServletRequest;");
        headerInfoList.add("import java.net.BindException;");
        build(headerInfoList, "AGlobalExceptionHandlerController", Constants.PATH_CONTROLLER);
        headerInfoList.clear();
    }

    private static void build(List<String> headerInfoList, String fileName, String outputPath) {
        File folder = new File(outputPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File javaFile = new File(outputPath, fileName + ".java");

        OutputStream out = null;
        OutputStreamWriter outw = null;
        BufferedWriter bw = null;

        InputStream in = null;
        InputStreamReader inr = null;
        BufferedReader br = null;

        try {
            out = new FileOutputStream(javaFile);
            outw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            bw = new BufferedWriter(outw);

            String templatePath = BuildBase.class.getClassLoader().getResource("template/" + fileName + ".txt").getPath();
            in = new FileInputStream(templatePath);
            inr = new InputStreamReader(in, "utf-8");
            br = new BufferedReader(inr);

            int index = 0;
            for (String header : headerInfoList) {
                index++;
                bw.write(header);
                bw.newLine();
                if (header.contains("package") || index == headerInfoList.size()) {
                    bw.newLine();
                }
            }

            String lineInfo = null;
            while ((lineInfo = br.readLine()) != null) {
                bw.write(lineInfo);
                bw.newLine();
            }
            bw.flush();
        } catch (Exception e) {
            logger.error("生成基础类 {} 失败", fileName, e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inr != null) {
                try {
                    inr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
