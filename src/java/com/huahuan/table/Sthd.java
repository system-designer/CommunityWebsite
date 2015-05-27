/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 社团活动(sthd)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Sthd {
/**    */
private Integer hdid;//
/**  活动标题  */
private String hdbt;//活动标题
/**  活动内容  */
private String hdnr;//活动内容
/**  活动时间  */
private java.sql.Date hdsj;//活动时间
/**  活动类别  */
private Integer hdlb;//活动类别
/**  活动图片  */
private String hdtp;//活动图片
/**  活动积极分子  */
private Integer hdjjfz;//活动积极分子

/**
 * sthd不带参数的构造方法
 */
public Sthd() {
}
/**
 * sthd带参数的构造方法
 * @param hdid 
 * @param hdbt 活动标题
 * @param hdnr 活动内容
 * @param hdsj 活动时间
 * @param hdlb 活动类别
 * @param hdtp 活动图片
 * @param hdjjfz 活动积极分子
 */
public Sthd(Integer hdid,String hdbt,String hdnr,java.sql.Date hdsj,Integer hdlb,String hdtp,Integer hdjjfz) {
    this.hdid=hdid;
    this.hdbt=hdbt;
    this.hdnr=hdnr;
    this.hdsj=hdsj;
    this.hdlb=hdlb;
    this.hdtp=hdtp;
    this.hdjjfz=hdjjfz;
}
/**
 *获得
 *@return 
 */
public Integer getHdid() {
    return hdid;
}

/**
 *设置
 *@param hdid 
 */
public void setHdid(Integer hdid) {
    this.hdid = hdid;
}

/**
 *获得活动标题
 *@return 活动标题
 */
public String getHdbt() {
    return hdbt;
}

/**
 *设置活动标题
 *@param hdbt 活动标题
 */
public void setHdbt(String hdbt) {
    this.hdbt = hdbt;
}

/**
 *获得活动内容
 *@return 活动内容
 */
public String getHdnr() {
    return hdnr;
}

/**
 *设置活动内容
 *@param hdnr 活动内容
 */
public void setHdnr(String hdnr) {
    this.hdnr = hdnr;
}

/**
 *获得活动时间
 *@return 活动时间
 */
public java.sql.Date getHdsj() {
    return hdsj;
}

/**
 *设置活动时间
 *@param hdsj 活动时间
 */
public void setHdsj(java.sql.Date hdsj) {
    this.hdsj = hdsj;
}

/**
 *获得活动类别
 *@return 活动类别
 */
public Integer getHdlb() {
    return hdlb;
}

/**
 *设置活动类别
 *@param hdlb 活动类别
 */
public void setHdlb(Integer hdlb) {
    this.hdlb = hdlb;
}

/**
 *获得活动图片
 *@return 活动图片
 */
public String getHdtp() {
    return hdtp;
}

/**
 *设置活动图片
 *@param hdtp 活动图片
 */
public void setHdtp(String hdtp) {
    this.hdtp = hdtp;
}

/**
 *获得活动积极分子
 *@return 活动积极分子
 */
public Integer getHdjjfz() {
    return hdjjfz;
}

/**
 *设置活动积极分子
 *@param hdjjfz 活动积极分子
 */
public void setHdjjfz(Integer hdjjfz) {
    this.hdjjfz = hdjjfz;
}


}

