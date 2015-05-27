/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huahuan.tools;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class JstlExtend {

    public static String Encode(String value, String enc) {
        try {
            return java.net.URLEncoder.encode(value, enc);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JstlExtend.class.getName()).log(Level.SEVERE, null, ex);
            return value;
        }
    }

    public static String Decode(String value, String enc) {
        try {
            return java.net.URLDecoder.decode(value, enc);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(JstlExtend.class.getName()).log(Level.SEVERE, null, ex);
            return value;
        }
    }

    public static String ShowLen(String str, Integer len) {
        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len) + "...";
        }
    }
}
