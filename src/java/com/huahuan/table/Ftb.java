/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 发帖表(ftb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Ftb {
/**    */
private Integer ftid;//
/**  版块id  */
private Integer bkid;//版块id
/**  帖子标题  */
private String tzbt;//帖子标题
/**  帖子内容  */
private String tznr;//帖子内容
/**  发帖时间  */
private java.sql.Timestamp ftsj;//发帖时间
/**  发帖人  */
private Integer ftr;//发帖人
/**  是否审核  */
private Boolean sfsh;//是否审核
/**  是否加精  */
private Boolean sfjj;//是否加精
/**  是否置顶  */
private Boolean sfzd;//是否置顶
/**  浏览次数  */
private Integer llcs;//浏览次数
/**  回帖总数  */
private Integer htzs;//回帖总数

/**
 * ftb不带参数的构造方法
 */
public Ftb() {
}
/**
 * ftb带参数的构造方法
 * @param ftid 
 * @param bkid 版块id
 * @param tzbt 帖子标题
 * @param tznr 帖子内容
 * @param ftsj 发帖时间
 * @param ftr 发帖人
 * @param sfsh 是否审核
 * @param sfjj 是否加精
 * @param sfzd 是否置顶
 * @param llcs 浏览次数
 * @param htzs 回帖总数
 */
public Ftb(Integer ftid,Integer bkid,String tzbt,String tznr,java.sql.Timestamp ftsj,Integer ftr,Boolean sfsh,Boolean sfjj,Boolean sfzd,Integer llcs,Integer htzs) {
    this.ftid=ftid;
    this.bkid=bkid;
    this.tzbt=tzbt;
    this.tznr=tznr;
    this.ftsj=ftsj;
    this.ftr=ftr;
    this.sfsh=sfsh;
    this.sfjj=sfjj;
    this.sfzd=sfzd;
    this.llcs=llcs;
    this.htzs=htzs;
}
/**
 *获得
 *@return 
 */
public Integer getFtid() {
    return ftid;
}

/**
 *设置
 *@param ftid 
 */
public void setFtid(Integer ftid) {
    this.ftid = ftid;
}

/**
 *获得版块id
 *@return 版块id
 */
public Integer getBkid() {
    return bkid;
}

/**
 *设置版块id
 *@param bkid 版块id
 */
public void setBkid(Integer bkid) {
    this.bkid = bkid;
}

/**
 *获得帖子标题
 *@return 帖子标题
 */
public String getTzbt() {
    return tzbt;
}

/**
 *设置帖子标题
 *@param tzbt 帖子标题
 */
public void setTzbt(String tzbt) {
    this.tzbt = tzbt;
}

/**
 *获得帖子内容
 *@return 帖子内容
 */
public String getTznr() {
    return tznr;
}

/**
 *设置帖子内容
 *@param tznr 帖子内容
 */
public void setTznr(String tznr) {
    this.tznr = tznr;
}

/**
 *获得发帖时间
 *@return 发帖时间
 */
public java.sql.Timestamp getFtsj() {
    return ftsj;
}

/**
 *设置发帖时间
 *@param ftsj 发帖时间
 */
public void setFtsj(java.sql.Timestamp ftsj) {
    this.ftsj = ftsj;
}

/**
 *获得发帖人
 *@return 发帖人
 */
public Integer getFtr() {
    return ftr;
}

/**
 *设置发帖人
 *@param ftr 发帖人
 */
public void setFtr(Integer ftr) {
    this.ftr = ftr;
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

/**
 *获得是否加精
 *@return 是否加精
 */
public Boolean getSfjj() {
    return sfjj;
}

/**
 *设置是否加精
 *@param sfjj 是否加精
 */
public void setSfjj(Boolean sfjj) {
    this.sfjj = sfjj;
}

/**
 *获得是否置顶
 *@return 是否置顶
 */
public Boolean getSfzd() {
    return sfzd;
}

/**
 *设置是否置顶
 *@param sfzd 是否置顶
 */
public void setSfzd(Boolean sfzd) {
    this.sfzd = sfzd;
}

/**
 *获得浏览次数
 *@return 浏览次数
 */
public Integer getLlcs() {
    return llcs;
}

/**
 *设置浏览次数
 *@param llcs 浏览次数
 */
public void setLlcs(Integer llcs) {
    this.llcs = llcs;
}

/**
 *获得回帖总数
 *@return 回帖总数
 */
public Integer getHtzs() {
    return htzs;
}

/**
 *设置回帖总数
 *@param htzs 回帖总数
 */
public void setHtzs(Integer htzs) {
    this.htzs = htzs;
}


}

