/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 论坛版块(ltbk)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Ltbk {
/**    */
private Integer bkid;//
/**  版块名称  */
private String bkmc;//版块名称
/**  版块图标  */
private String bktb;//版块图标
/**  版主  */
private Integer bz;//版主
/**  版块描述  */
private String bkms;//版块描述
/**  帖子总数  */
private Integer tzzs;//帖子总数

/**
 * ltbk不带参数的构造方法
 */
public Ltbk() {
}
/**
 * ltbk带参数的构造方法
 * @param bkid 
 * @param bkmc 版块名称
 * @param bktb 版块图标
 * @param bz 版主
 * @param bkms 版块描述
 * @param tzzs 帖子总数
 */
public Ltbk(Integer bkid,String bkmc,String bktb,Integer bz,String bkms,Integer tzzs) {
    this.bkid=bkid;
    this.bkmc=bkmc;
    this.bktb=bktb;
    this.bz=bz;
    this.bkms=bkms;
    this.tzzs=tzzs;
}
/**
 *获得
 *@return 
 */
public Integer getBkid() {
    return bkid;
}

/**
 *设置
 *@param bkid 
 */
public void setBkid(Integer bkid) {
    this.bkid = bkid;
}

/**
 *获得版块名称
 *@return 版块名称
 */
public String getBkmc() {
    return bkmc;
}

/**
 *设置版块名称
 *@param bkmc 版块名称
 */
public void setBkmc(String bkmc) {
    this.bkmc = bkmc;
}

/**
 *获得版块图标
 *@return 版块图标
 */
public String getBktb() {
    return bktb;
}

/**
 *设置版块图标
 *@param bktb 版块图标
 */
public void setBktb(String bktb) {
    this.bktb = bktb;
}

/**
 *获得版主
 *@return 版主
 */
public Integer getBz() {
    return bz;
}

/**
 *设置版主
 *@param bz 版主
 */
public void setBz(Integer bz) {
    this.bz = bz;
}

/**
 *获得版块描述
 *@return 版块描述
 */
public String getBkms() {
    return bkms;
}

/**
 *设置版块描述
 *@param bkms 版块描述
 */
public void setBkms(String bkms) {
    this.bkms = bkms;
}

/**
 *获得帖子总数
 *@return 帖子总数
 */
public Integer getTzzs() {
    return tzzs;
}

/**
 *设置帖子总数
 *@param tzzs 帖子总数
 */
public void setTzzs(Integer tzzs) {
    this.tzzs = tzzs;
}


}

