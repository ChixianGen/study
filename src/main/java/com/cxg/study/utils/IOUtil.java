package com.cxg.study.utils;   // Administrator 于 2019/8/2 创建;

import java.io.*;

public class IOUtil {

    /**
     * 图片转字节数组；
     *
     * @return
     */
    public static byte[] file2ByteArray(String filePath) {
        File file = new File(filePath);
        try (InputStream is = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] section = new byte[1024 * 10];
            int len;
            while ((len = is.read(section)) != -1) {
                baos.write(section, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * 字节数组转图片；
     *
     * @param content
     * @return
     */
    public static void byteArray2File(byte[] content, String filePath) {
        try (
            InputStream is = new ByteArrayInputStream(content);
            OutputStream os = new FileOutputStream(new File(filePath))) {
            byte[] flush = new byte[1024];
            int len;
            while ((len = is.read(flush)) != -1) {
                os.write(flush, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
