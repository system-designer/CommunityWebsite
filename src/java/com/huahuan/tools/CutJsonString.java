/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.huahuan.tools;

/**
 * 剪裁字符串为easyui标准格式的工具类
 *
 * @author Administrator
 */
public class CutJsonString {

    public static String cutObjectJson(String str) {
        return str.substring(2, str.length() - 2);
    }

    public static String cutComboJson(String str) {
        return str.substring(1, str.length() - 1);
    }
}
