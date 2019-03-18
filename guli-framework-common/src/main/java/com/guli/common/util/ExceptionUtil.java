package com.guli.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author ：mei
 * @date ：Created in 2019/2/24 0024 下午 18:20
 * @description：异常工具类
 * @modified By：
 * @version: $
 */
public class ExceptionUtil {

    public static String getMessage(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } finally {

            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
}
