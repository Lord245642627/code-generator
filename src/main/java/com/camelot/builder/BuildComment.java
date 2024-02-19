package com.camelot.builder;

import com.camelot.bean.Constants;
import com.camelot.util.DateUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

/**
 * @author Lord Camelot
 * @date 2024/2/9
 * @description
 */
public class BuildComment {
    public static void createClassComment(BufferedWriter bw, String classComment) throws IOException {
        bw.write("/**");
        bw.newLine();
        bw.write(" * @author " + Constants.AUTHOR_NAME);
        bw.newLine();
        bw.write(" * @date " + DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_2));
        bw.newLine();
        bw.write(" * @description " + classComment);
        bw.newLine();
        bw.write(" */");
        bw.newLine();
    }

    public static void createFieldComment(BufferedWriter bw, String fieldComment) throws IOException {
        if (fieldComment != null) {
            bw.write("\t/**");
            bw.newLine();
            bw.write("\t * " + fieldComment);
            bw.newLine();
            bw.write("\t */");
            bw.newLine();
        }
    }

    public static void createMethodComment(BufferedWriter bw, String methodComment) throws IOException {
        if (methodComment != null) {
            bw.write("\t/**");
            bw.newLine();
            bw.write("\t * " + methodComment);
            bw.newLine();
            bw.write("\t */");
            bw.newLine();
        }
    }
}
