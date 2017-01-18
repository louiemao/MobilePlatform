package com.sws.platform.mobile.common.util;

import com.sws.platform.mobile.common.exception.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;

/**
 * Created by MaoLiang on 2016/9/8.
 */
public class FileUtil {
    private static Logger LOGGER = LogManager.getLogger(FileUtil.class.getName());

    public static void download(File file, String fileName, HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        if (file == null) {
            throw new BusinessException("文件不能为空");
        }
        if (!file.exists()) {
            throw new BusinessException("文件不存在");
        }
        try {
            //中文文件名支持
            String encodeFileName = null;
            String userAgent = request.getHeader("User-Agent");
            if (null != userAgent && -1 != userAgent.indexOf("MSIE")) {//IE
                encodeFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            } else if (null != userAgent && -1 != userAgent.indexOf("Mozilla")) {
                encodeFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            } else {
                encodeFileName = java.net.URLEncoder.encode(fileName, "UTF-8");
            }
//            byte[] bytes = userAgent.contains("MSIE") ? fileName.getBytes() : fileName.getBytes("UTF-8");
//            fileName = new String(bytes, "ISO-8859-1");
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", encodeFileName));

            //下载文件
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            OutputStream outputStream = response.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            int byteRead = 0;
            byte[] buffer = new byte[8192];
            while ((byteRead = bufferedInputStream.read(buffer, 0, 8192)) != -1) {
                bufferedOutputStream.write(buffer, 0, byteRead);
            }
            bufferedOutputStream.flush();
            fileInputStream.close();
            bufferedInputStream.close();
            outputStream.close();
            bufferedOutputStream.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

    /**
     * 删除文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) {
            return false;
        }
        if (dir.isDirectory()) {
            File[] childrens = dir.listFiles();
            for (File children : childrens) {
                if (!deleteDir(children)) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static String read(String path) {
        StringBuffer buf = new StringBuffer();
        try {
            FileInputStream in = new FileInputStream(path);
            // 指定读取文件时以UTF-8的格式读取
            BufferedReader br = new BufferedReader(new UnicodeReader(in, "UTF-8"));
            String line = br.readLine();
            while (line != null) {
                buf.append(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static void write(String path, String content) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(
                    new FileOutputStream(path), "UTF-8");
            //out.write("\n"+content);
            out.write(content);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(String fileName) {
        String[] ss = fileName.split(".");
        fileName = ss[0];
        String[] ss2 = fileName.split("/");
        fileName = ss[ss.length - 1];
        return fileName;
    }
}
