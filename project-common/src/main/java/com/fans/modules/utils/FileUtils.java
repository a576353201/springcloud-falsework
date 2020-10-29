package com.fans.modules.utils;


import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * className: FileUtils
 *
 * @author k
 * @version 1.0
 * @description 文件工具类
 * @date 2018-09-14 11:23
 **/
@Slf4j
public class FileUtils {

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * description: 将文件压缩至zip
     *
     * @param srcDir           要压缩的文件路径
     * @param out              zip的输出流
     * @param keepDirStructure 是否保留目录结构
     * @author k
     * @date 2018/09/14 11:30
     **/
    public static void toZip(String srcDir, OutputStream out, boolean keepDirStructure)
            throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), keepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Description:
     * @Param: [sourceFile（）, zos（）, name（）, KeepDirStructure]
     * @return: void
     * @Author: fan
     * @Date: 2018/09/14 11:34
     **/
    /**
     * description: 递归压缩
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param keepDirStructure 是否保持目录结构
     * @return void
     * @author k
     * @date 2020/05/27 20:45
     **/
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
                                 boolean keepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 要保留原来的文件结构时,需要对空文件夹进行处理
                if (keepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (keepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), keepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), false);
                    }
                }
            }
        }
    }

    /**
     * description: 件流下载，在浏览器展示
     *
     * @param response 输出
     * @param file     文件
     * @author k
     * @date 2020/10/05 16:22
     **/
    public static void downloadFileByStream(HttpServletRequest request, HttpServletResponse response, File file, Boolean isView) {
        String filePath = file.getPath();
        log.info("--> filePath = {}", filePath);
        // 对encode过的filePath处理
        if (filePath.contains("%")) {
            try {
                filePath = URLDecoder.decode(filePath, Charsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        log.info("--> 文件大小：{}KB", file.length() / 1024);
        try (FileInputStream in = new FileInputStream(file); ServletOutputStream out = response.getOutputStream();) {
            String[] dir = filePath.split("/");
            String fileName = dir[dir.length - 1];
            String[] array = fileName.split("[.]");
            String fileType = array[array.length - 1].toLowerCase();
            // 设置文件ContentType类型
            if ("jpg,jepg,gif,png".contains(fileType) && isView) {
                // 判断图片类型
                response.setContentType("image/" + fileType);
            } else if ("pdf".contains(fileType) && isView) {
                // 判断pdf类型
                response.setContentType("application/pdf");
            } else {
                // 设置multipart
                response.setContentType("multipart/form-data");
                //设置文件类型
                String downFileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
                if (StringUtils.isNotBlank(downFileName)) {
                    String headerName = "User-Agent";
                    if (request.getHeader(headerName).toLowerCase().indexOf("firefox") > 0) {
                        // firefox浏览器
                        downFileName = new String(downFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                    } else if (request.getHeader(headerName).toUpperCase().indexOf("MSIE") > 0) {
                        // IE浏览器
                        downFileName = URLEncoder.encode(downFileName, StandardCharsets.UTF_8.name());
                    } else if (request.getHeader(headerName).toUpperCase().indexOf("CHROME") > 0) {
                        // 谷歌
                        downFileName = new String(downFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                    }
                }
                response.setHeader("Content-disposition", "attachment;filename=" + downFileName);
            }
            // 读取文件流
            int len;
            byte[] buffer = new byte[1024 * 10];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadFileByUrl(HttpServletRequest request, HttpServletResponse response, String url) {
        HttpURLConnection conn = null;
        try {
            URL httpUrl = new URL(url);
            conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            throw new RuntimeException("url 不合法！！！");
        }
        try (InputStream in = conn.getInputStream()) {
            MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
            MimeType mimeType = allTypes.forName(conn.getContentType());
            String suffix = mimeType.getExtension();
            File file = inputStream2File(in, "." + File.separator, Thread.currentThread().getId() + "-" + UUID.randomUUID().toString() + suffix);
            downloadFileByStream(request, response, file, false);
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        } catch (IOException | MimeTypeException e) {
            e.printStackTrace();
        }
    }

    /**
     * description: 文件转换为base64
     *
     * @param file 文件
     * @return java.lang.String
     * @author k
     * @date 2020/10/05 16:22
     **/
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String fileToBase64(File file) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] fileData = null;
        // 读取文件字节数组
        try (InputStream in = new FileInputStream(file)) {
            fileData = new byte[in.available()];
            in.read(fileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码并且返回
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(fileData);
    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code base码
     * @param pathName   目录名
     * @param fileName   文件名
     */
    public static File decoderBase64File(String base64Code, String pathName, String fileName) {
        File file = new File(pathName);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        try {
            byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
            FileOutputStream out = new FileOutputStream(pathName + File.separator + fileName);
            out.write(buffer);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(pathName + File.separator + fileName);
    }

    /**
     * 将inputStream转化为file
     *
     * @param inputStream 文件输入流
     * @param pathName    目录名
     * @param fileName    文件名
     */
    public static File inputStream2File(InputStream inputStream, String pathName, String fileName) throws IOException {
        OutputStream outputStream = null;
        File file = new File(pathName);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        try {
            outputStream = new FileOutputStream(pathName + File.separator + fileName);
            int len;
            byte[] buffer = new byte[BUFFER_SIZE];

            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            inputStream.close();
        }
        return new File(pathName + fileName);
    }

}
