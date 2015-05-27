/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 留言回复表(lyhfb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Lyhfb {
/**    */
private Integer hfid;//
/**  回复内容  */
private String hfnr;//回复内容
/**  回复人  */
private Integer hfr;//回复人
/**  回复时间  */
private java.sql.Timestamp hfsj;//回复时间
/**  所回留言  */
private Integer shly;//所回留言

/**
 * lyhfb不带参数的构造方法
 */
public Lyhfb() {
}
/**
 * lyhfb带参数的构造方法
 * @param hfid 
 * @param hfnr 回复内容
 * @param hfr 回复人
 * @param hfsj 回复时间
 * @param shly 所回留言
 */
public Lyhfb(Integer hfid,String hfnr,Integer hfr,java.sql.Timestamp hfsj,Integer shly) {
    this.hfid=hfid;
    this.hfnr=hfnr;
    this.hfr=hfr;
    this.hfsj=hfsj;
    this.shly=shly;
}
/**
 *获得
 *@return 
 */
public Integer getHfid() {
    return hfid;
}

/**
 *设置
 *@param hfid 
 */
public void setHfid(Integer hfid) {
    this.hfid = hfid;
}

/**
 *获得回复内容
 *@return 回复内容
 */
public String getHfnr() {
    return hfnr;
}

/**
 *设置回复内容
 *@param hfnr 回复内容
 */
public void setHfnr(String hfnr) {
    this.hfnr = hfnr;
}

/**
 *获得回复人
 *@return 回复人
 */
public Integer getHfr() {
    return hfr;
}

/**
 *设置回复人
 *@param hfr 回复人
 */
public void setHfr(Integer hfr) {
    this.hfr = hfr;
}

/**
 *获得回复时间
 *@return 回复时间
 */
public java.sql.Timestamp getHfsj() {
    return hfsj;
}

/**
 *设置回复时间
 *@param hfsj 回复时间
 */
public void setHfsj(java.sql.Timestamp hfsj) {
    this.hfsj = hfsj;
}

/**
 *获得所回留言
 *@return 所回留言
 */
public Integer getShly() {
    return shly;
}

/**
 *设置所回留言
 *@param shly 所回留言
 */
public void setShly(Integer shly) {
    this.shly = shly;
}


}

