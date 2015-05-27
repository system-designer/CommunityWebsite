package com.huahuan.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 *
 * @author 黄迎斌
 */
public class MD5 {

    /**
     *
     * @param plainText
     * @return
     */
    public static String md5(String plainText) {
        try {
            //确定使用md5加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            //加密
            md.update(plainText.getBytes());
            //加密后得到byte,在转化为字符串。yy
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }
                if (i < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            buf.toString();
            return buf.toString();

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * jplus实验室的加密方法，加入其它字符更安全
     *
     * @param str 明文
     * @return 密文
     */
    public static String jplusMd5(String str) {
        return md5("!@#$%^&*()" + str + ")(*&^%$#@!`");
    }
}
