package com.huahuan.system;

/**
 * ReplaceAll说明.
 * @author Hyberbin
 * @date 2013-6-19 19:56:27
 */
public class ReplaceAll {

    public static String replaceAll(String str, String oldchr, String newchr) {
        if (oldchr == null || "".equals(oldchr) || newchr == null || "".equals(newchr)) {
            return str;
        } else {
            while (str.contains(oldchr)) {
                str = str.replaceAll(oldchr, newchr);
            }
        }
        return str;
    }
}
