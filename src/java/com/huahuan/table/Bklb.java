/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 百科类别(bklb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Bklb {
/**  类别id  */
private Integer lbid;//类别id
/**  类别名称  */
private String lbmc;//类别名称

/**
 * bklb不带参数的构造方法
 */
public Bklb() {
}
/**
 * bklb带参数的构造方法
 * @param lbid 类别id
 * @param lbmc 类别名称
 */
public Bklb(Integer lbid,String lbmc) {
    this.lbid=lbid;
    this.lbmc=lbmc;
}
/**
 *获得类别id
 *@return 类别id
 */
public Integer getLbid() {
    return lbid;
}

/**
 *设置类别id
 *@param lbid 类别id
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

