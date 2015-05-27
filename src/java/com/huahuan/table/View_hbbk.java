/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * VIEW(view_hbbk)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class View_hbbk {
/**  类别名称  */
private String lbmc;//类别名称
/**  百科id  */
private Integer bkid;//百科id
/**  百科标题  */
private String bkbt;//百科标题
/**  百科内容  */
private String bknr;//百科内容
/**  百科时间  */
private java.sql.Date bksj;//百科时间
/**  百科类别  */
private Integer bklb;//百科类别

/**
 * view_hbbk不带参数的构造方法
 */
public View_hbbk() {
}
/**
 * view_hbbk带参数的构造方法
 * @param lbmc 类别名称
 * @param bkid 百科id
 * @param bkbt 百科标题
 * @param bknr 百科内容
 * @param bksj 百科时间
 * @param bklb 百科类别
 */
public View_hbbk(String lbmc,Integer bkid,String bkbt,String bknr,java.sql.Date bksj,Integer bklb) {
    this.lbmc=lbmc;
    this.bkid=bkid;
    this.bkbt=bkbt;
    this.bknr=bknr;
    this.bksj=bksj;
    this.bklb=bklb;
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

/**
 *获得百科id
 *@return 百科id
 */
public Integer getBkid() {
    return bkid;
}

/**
 *设置百科id
 *@param bkid 百科id
 */
public void setBkid(Integer bkid) {
    this.bkid = bkid;
}

/**
 *获得百科标题
 *@return 百科标题
 */
public String getBkbt() {
    return bkbt;
}

/**
 *设置百科标题
 *@param bkbt 百科标题
 */
public void setBkbt(String bkbt) {
    this.bkbt = bkbt;
}

/**
 *获得百科内容
 *@return 百科内容
 */
public String getBknr() {
    return bknr;
}

/**
 *设置百科内容
 *@param bknr 百科内容
 */
public void setBknr(String bknr) {
    this.bknr = bknr;
}

/**
 *获得百科时间
 *@return 百科时间
 */
public java.sql.Date getBksj() {
    return bksj;
}

/**
 *设置百科时间
 *@param bksj 百科时间
 */
public void setBksj(java.sql.Date bksj) {
    this.bksj = bksj;
}

/**
 *获得百科类别
 *@return 百科类别
 */
public Integer getBklb() {
    return bklb;
}

/**
 *设置百科类别
 *@param bklb 百科类别
 */
public void setBklb(Integer bklb) {
    this.bklb = bklb;
}


}

