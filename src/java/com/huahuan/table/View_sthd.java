/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * VIEW(view_sthd)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class View_sthd {
/**  姓名  */
private String xm;//姓名
/**  类别名称  */
private String lbmc;//类别名称
/**  活动图片  */
private String hdtp;//活动图片
/**  活动类别  */
private Integer hdlb;//活动类别
/**  活动时间  */
private java.sql.Date hdsj;//活动时间
/**  活动内容  */
private String hdnr;//活动内容
/**  活动标题  */
private String hdbt;//活动标题
/**    */
private Integer hdid;//
/**  活动积极分子  */
private Integer hdjjfz;//活动积极分子

/**
 * view_sthd不带参数的构造方法
 */
public View_sthd() {
}
/**
 * view_sthd带参数的构造方法
 * @param xm 姓名
 * @param lbmc 类别名称
 * @param hdtp 活动图片
 * @param hdlb 活动类别
 * @param hdsj 活动时间
 * @param hdnr 活动内容
 * @param hdbt 活动标题
 * @param hdid 
 * @param hdjjfz 活动积极分子
 */
public View_sthd(String xm,String lbmc,String hdtp,Integer hdlb,java.sql.Date hdsj,String hdnr,String hdbt,Integer hdid,Integer hdjjfz) {
    this.xm=xm;
    this.lbmc=lbmc;
    this.hdtp=hdtp;
    this.hdlb=hdlb;
    this.hdsj=hdsj;
    this.hdnr=hdnr;
    this.hdbt=hdbt;
    this.hdid=hdid;
    this.hdjjfz=hdjjfz;
}
/**
 *获得姓名
 *@return 姓名
 */
public String getXm() {
    return xm;
}

/**
 *设置姓名
 *@param xm 姓名
 */
public void setXm(String xm) {
    this.xm = xm;
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

