/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 活动类别(hdlb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Hdlb {
/**    */
private Integer lbid;//
/**  类别名称  */
private String lbmc;//类别名称

/**
 * hdlb不带参数的构造方法
 */
public Hdlb() {
}
/**
 * hdlb带参数的构造方法
 * @param lbid 
 * @param lbmc 类别名称
 */
public Hdlb(Integer lbid,String lbmc) {
    this.lbid=lbid;
    this.lbmc=lbmc;
}
/**
 *获得
 *@return 
 */
public Integer getLbid() {
    return lbid;
}

/**
 *设置
 *@param lbid 
 */
public void setLbid(Integer lbid) {
    this.lbid = lbid;
}

/**
 *获得类别名称
 *@return 类别名称
 */
public String getLbmc() {
    return lbmc;
}

/**
 *设置类别名称
 *@param lbmc 类别名称
 */
public void setLbmc(String lbmc) {
    this.lbmc = lbmc;
}


}

