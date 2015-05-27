/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 回帖表(htb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Htb {
/**    */
private Integer htid;//
/**  主贴id  */
private Integer ftid;//主贴id
/**  回帖内容  */
private String htnr;//回帖内容
/**  回帖时间  */
private java.sql.Timestamp htsj;//回帖时间
/**  回帖人  */
private Integer htr;//回帖人
/**  是否审核  */
private Boolean sfsh;//是否审核

/**
 * htb不带参数的构造方法
 */
public Htb() {
}
/**
 * htb带参数的构造方法
 * @param htid 
 * @param ftid 主贴id
 * @param htnr 回帖内容
 * @param htsj 回帖时间
 * @param htr 回帖人
 * @param sfsh 是否审核
 */
public Htb(Integer htid,Integer ftid,String htnr,java.sql.Timestamp htsj,Integer htr,Boolean sfsh) {
    this.htid=htid;
    this.ftid=ftid;
    this.htnr=htnr;
    this.htsj=htsj;
    this.htr=htr;
    this.sfsh=sfsh;
}
/**
 *获得
 *@return 
 */
public Integer getHtid() {
    return htid;
}

/**
 *设置
 *@param htid 
 */
public void setHtid(Integer htid) {
    this.htid = htid;
}

/**
 *获得主贴id
 *@return 主贴id
 */
public Integer getFtid() {
    return ftid;
}

/**
 *设置主贴id
 *@param ftid 主贴id
 */
public void setFtid(Integer ftid) {
    this.ftid = ftid;
}

/**
 *获得回帖内容
 *@return 回帖内容
 */
public String getHtnr() {
    return htnr;
}

/**
 *设置回帖内容
 *@param htnr 回帖内容
 */
public void setHtnr(String htnr) {
    this.htnr = htnr;
}

/**
 *获得回帖时间
 *@return 回帖时间
 */
public java.sql.Timestamp getHtsj() {
    return htsj;
}

/**
 *设置回帖时间
 *@param htsj 回帖时间
 */
public void setHtsj(java.sql.Timestamp htsj) {
    this.htsj = htsj;
}

/**
 *获得回帖人
 *@return 回帖人
 */
public Integer getHtr() {
    return htr;
}

/**
 *设置回帖人
 *@param htr 回帖人
 */
public void setHtr(Integer htr) {
    this.htr = htr;
}

/**
 *获得是否审核
 *@return 是否审核
 */
public Boolean getSfsh() {
    return sfsh;
}

/**
 *设置是否审核
 *@param sfsh 是否审核
 */
public void setSfsh(Boolean sfsh) {
    this.sfsh = sfsh;
}


}

