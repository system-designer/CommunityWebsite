/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 用户权限类别(qxlb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Qxlb {
/**    */
private Integer qxid;//
/**  权限名称  */
private String qxmc;//权限名称

/**
 * qxlb不带参数的构造方法
 */
public Qxlb() {
}
/**
 * qxlb带参数的构造方法
 * @param qxid 
 * @param qxmc 权限名称
 */
public Qxlb(Integer qxid,String qxmc) {
    this.qxid=qxid;
    this.qxmc=qxmc;
}
/**
 *获得
 *@return 
 */
public Integer getQxid() {
    return qxid;
}

/**
 *设置
 *@param qxid 
 */
public void setQxid(Integer qxid) {
    this.qxid = qxid;
}

/**
 *获得权限名称
 *@return 权限名称
 */
public String getQxmc() {
    return qxmc;
}

/**
 *设置权限名称
 *@param qxmc 权限名称
 */
public void setQxmc(String qxmc) {
    this.qxmc = qxmc;
}


}

