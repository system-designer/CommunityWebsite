/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 社团部门(stbm)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Stbm {
/**    */
private Integer bmid;//
/**  部门名称  */
private String bmmc;//部门名称
/**  部门简介  */
private String bmjj;//部门简介

/**
 * stbm不带参数的构造方法
 */
public Stbm() {
}
/**
 * stbm带参数的构造方法
 * @param bmid 
 * @param bmmc 部门名称
 * @param bmjj 部门简介
 */
public Stbm(Integer bmid,String bmmc,String bmjj) {
    this.bmid=bmid;
    this.bmmc=bmmc;
    this.bmjj=bmjj;
}
/**
 *获得
 *@return 
 */
public Integer getBmid() {
    return bmid;
}

/**
 *设置
 *@param bmid 
 */
public void setBmid(Integer bmid) {
    this.bmid = bmid;
}

/**
 *获得部门名称
 *@return 部门名称
 */
public String getBmmc() {
    return bmmc;
}

/**
 *设置部门名称
 *@param bmmc 部门名称
 */
public void setBmmc(String bmmc) {
    this.bmmc = bmmc;
}

/**
 *获得部门简介
 *@return 部门简介
 */
public String getBmjj() {
    return bmjj;
}

/**
 *设置部门简介
 *@param bmjj 部门简介
 */
public void setBmjj(String bmjj) {
    this.bmjj = bmjj;
}


}

