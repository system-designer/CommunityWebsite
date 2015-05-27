/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * VIEW(view_lyb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class View_lyb {
/**  回复人  */
private String hfr;//回复人
/**  是否审核  */
private Boolean sfsh;//是否审核
/**  留言时间  */
private java.sql.Timestamp lysj;//留言时间
/**  留言人  */
private String lyr;//留言人
/**  留言内容  */
private String lynr;//留言内容
/**    */
private Integer lyid;//
/**  回复内容  */
private String hfnr;//回复内容
/**  回复时间  */
private java.sql.Timestamp hfsj;//回复时间
/**  用户名  */
private String yhm;//用户名
/**  权限  */
private Integer qx;//权限

/**
 * view_lyb不带参数的构造方法
 */
public View_lyb() {
}
/**
 * view_lyb带参数的构造方法
 * @param hfr 回复人
 * @param sfsh 是否审核
 * @param lysj 留言时间
 * @param lyr 留言人
 * @param lynr 留言内容
 * @param lyid 
 * @param hfnr 回复内容
 * @param hfsj 回复时间
 * @param yhm 用户名
 * @param qx 权限
 */
public View_lyb(String hfr,Boolean sfsh,java.sql.Timestamp lysj,String lyr,String lynr,Integer lyid,String hfnr,java.sql.Timestamp hfsj,String yhm,Integer qx) {
    this.hfr=hfr;
    this.sfsh=sfsh;
    this.lysj=lysj;
    this.lyr=lyr;
    this.lynr=lynr;
    this.lyid=lyid;
    this.hfnr=hfnr;
    this.hfsj=hfsj;
    this.yhm=yhm;
    this.qx=qx;
}
/**
 *获得回复人
 *@return 回复人
 */
public String getHfr() {
    return hfr;
}

/**
 *设置回复人
 *@param hfr 回复人
 */
public void setHfr(String hfr) {
    this.hfr = hfr;
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
 *获得留言时间
 *@return 留言时间
 */
public java.sql.Timestamp getLysj() {
    return lysj;
}

/**
 *设置留言时间
 *@param lysj 留言时间
 */
public void setLysj(java.sql.Timestamp lysj) {
    this.lysj = lysj;
}

/**
 *获得留言人
 *@return 留言人
 */
public String getLyr() {
    return lyr;
}

/**
 *设置留言人
 *@param lyr 留言人
 */
public void setLyr(String lyr) {
    this.lyr = lyr;
}

/**
 *获得留言内容
 *@return 留言内容
 */
public String getLynr() {
    return lynr;
}

/**
 *设置留言内容
 *@param lynr 留言内容
 */
public void setLynr(String lynr) {
    this.lynr = lynr;
}

/**
 *获得
 *@return 
 */
public Integer getLyid() {
    return lyid;
}

/**
 *设置
 *@param lyid 
 */
public void setLyid(Integer lyid) {
    this.lyid = lyid;
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
 *获得用户名
 *@return 用户名
 */
public String getYhm() {
    return yhm;
}

/**
 *设置用户名
 *@param yhm 用户名
 */
public void setYhm(String yhm) {
    this.yhm = yhm;
}

/**
 *获得权限
 *@return 权限
 */
public Integer getQx() {
    return qx;
}

/**
 *设置权限
 *@param qx 权限
 */
public void setQx(Integer qx) {
    this.qx = qx;
}


}

