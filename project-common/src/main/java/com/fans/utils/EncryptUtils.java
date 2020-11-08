package com.fans.utils;

import com.google.common.base.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * className: EncryptUtils
 *
 * @author k
 * @version 1.0
 * @description 文本安全加密工具（MD5,AES）
 * @date 2018-12-20 14:14
 **/
public class EncryptUtils {

    /**
     * description: spring md5加密工具
     *
     * @param content 加密体
     * @return java.lang.String
     * @author k
     * @date 2018/09/10 15:35
     **/
    public static String md5Encrypt(String content) {
        return DigestUtils.md5DigestAsHex(content.getBytes());
    }

    /**
     * description: spring md5加密工具 增加编码格式
     *
     * @param content 加密体
     * @param charset 编码
     * @return java.lang.String
     * @author k
     * @date 2018/09/10 15:35
     **/
    public static String md5Encrypt(String content, String charset) {
        String md5EncryptStr;
        try {
            md5EncryptStr = DigestUtils.md5DigestAsHex(content.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return md5EncryptStr;
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     */
    private static byte[] aesEncode(String content, String encryptKey) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(encryptKey.getBytes()));
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES"));
            return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base 64 加密
     *
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    private static String base64Encode(byte[] bytes) {
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     */
    public static String getAesEncrypt(String content, String encryptKey) {
        return base64Encode(aesEncode(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     */
    private static String aesDecode(byte[] encryptBytes, String decryptKey) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(decryptKey.getBytes()));

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES"));
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * base 64 解密
     *
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     */
    private static byte[] base64Decode(String base64Code) throws Exception {
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     */
    public static String getAesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecode(base64Decode(encryptStr), decryptKey);
    }

    /**
     * description: URL编码
     *
     * @param str url
     * @return java.lang.String
     * @author k
     * @date 2019/03/22 17:58
     **/
    public static String getUrlEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, Charsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * description: URL解码
     *
     * @param str url
     * @return java.lang.String
     * @author k
     * @date 2019/03/22 17:58
     **/
    public static String urlDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, Charsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * description: 将 s 进行 BASE64 编码
     *
     * @param s 加密体
     * @return java.lang.String
     * @author k
     * @date 2019/03/22 17:58
     **/
    public static String base64Enc(String s) {
        if (s == null) {
            return null;
        }
        String res = "";
        try {
            res = new BASE64Encoder().encode(s.getBytes("GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * description: 将 BASE64 编码的字符串 s 进行解码
     *
     * @param s 解密体
     * @return java.lang.String
     * @author k
     * @date 2019/03/22 17:58
     **/
    public static String base64Dec(String s) {
        if (s == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b, "GBK");
        } catch (Exception e) {
            return null;
        }
    }
}
