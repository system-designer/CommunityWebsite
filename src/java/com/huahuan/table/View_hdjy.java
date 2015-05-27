/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * VIEW(view_hdjy)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class View_hdjy {
/**    */
private Integer hdid;//
/**  活动标题  */
private String hdbt;//活动标题
/**    */
private Integer jyid;//
/**  剪影标题  */
private String jybt;//剪影标题
/**  剪影图片  */
private String jytp;//剪影图片
/**  剪影描述  */
private String jyms;//剪影描述
/**  剪影时间  */
private java.sql.Date jysj;//剪影时间
/**  活动时间  */
private java.sql.Date hdsj;//活动时间

/**
 * view_hdjy不带参数的构造方法
 */
public View_hdjy() {
}
/**
 * view_hdjy带参数的构造方法
 * @param hdid 
 * @param hdbt 活动标题
 * @param jyid 
 * @param jybt 剪影标题
 * @param jytp 剪影图片
 * @param jyms 剪影描述
 * @param jysj 剪影时间
 * @param hdsj 活动时间
 */
public View_hdjy(Integer hdid,String hdbt,Integer jyid,String jybt,String jytp,String jyms,java.sql.Date jysj,java.sql.Date hdsj) {
    this.hdid=hdid;
    this.hdbt=hdbt;
    this.jyid=jyid;
    this.jybt=jybt;
    this.jytp=jytp;
    this.jyms=jyms;
    this.jysj=jysj;
    this.hdsj=hdsj;
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
 *获得
 *@return 
 */
public Integer getJyid() {
    return jyid;
}

/**
 *设置
 *@param jyid 
 */
public void setJyid(Integer jyid) {
    this.jyid = jyid;
}

/**
 *获得剪影标题
 *@return 剪影标题
 */
public String getJybt() {
    return jybt;
}

/**
 *设置剪影标题
 *@param jybt 剪影标题
 */
public void setJybt(String jybt) {
    this.jybt = jybt;
}

/**
 *获得剪影图片
 *@return 剪影图片
 */
public String getJytp() {
    return jytp;
}

/**
 *设置剪影图片
 *@param jytp 剪影图片
 */
public void setJytp(String jytp) {
    this.jytp = jytp;
}

/**
 *获得剪影描述
 *@return 剪影描述
 */
public String getJyms() {
    return jyms;
}

/**
 *设置剪影描述
 *@param jyms 剪影描述
 */
public void setJyms(String jyms) {
    this.jyms = jyms;
}

/**
 *获得剪影时间
 *@return 剪影时间
 */
public java.sql.Date getJysj() {
    return jysj;
}

/**
 *设置剪影时间
 *@param jysj 剪影时间
 */
public void setJysj(java.sql.Date jysj) {
    this.jysj = jysj;
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


}

