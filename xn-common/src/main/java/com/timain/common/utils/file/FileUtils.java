package com.timain.common.utils.file;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件处理工具类
 * @author yyf
 * @version 1.0
 * @date 2020/7/28 13:53
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
     * 输出指定文件的byte数组
     * @param filePath 文件路径
     * @param ops 输出流
     * @throws IOException
     */
    public static void writeBytes(String filePath, OutputStream ops) throws IOException {
        FileInputStream inputStream = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            inputStream = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                ops.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (null!=ops) {
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null!=inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 文件名称验证
     * @param fileName 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFileName(String fileName) {
        return fileName.matches(FILENAME_PATTERN);
    }

    /**
     * 下载文件名重新编码
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     * @throws UnsupportedEncodingException
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
        final String agent = request.getHeader("USER-AGENT");
        String name = "";
        if (agent.contains("MSIE")) {
            //IE浏览器
            name = URLEncoder.encode(fileName, "utf-8");
            name = name.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            //火狐浏览器
            name = new String(fileName.getBytes(), "ISO8859-1");
        } else if (agent.contains("Chrome")) {
            //google浏览器
            name = URLEncoder.encode(fileName, "utf-8");
        } else {
            //其他浏览器
            name = URLEncoder.encode(fileName, "utf-8");
        }
        return name;
    }
}
