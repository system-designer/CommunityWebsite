/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 活动剪影(hdjy)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Hdjy {
/**    */
private Integer jyid;//
/**  剪影标题  */
private String jybt;//剪影标题
/**  剪影图片  */
private String jytp;//剪影图片
/**  活动id  */
private Integer hdid;//活动id
/**  剪影描述  */
private String jyms;//剪影描述
/**  剪影时间  */
private java.sql.Date jysj;//剪影时间

/**
 * hdjy不带参数的构造方法
 */
public Hdjy() {
}
/**
 * hdjy带参数的构造方法
 * @param jyid 
 * @param jybt 剪影标题
 * @param jytp 剪影图片
 * @param hdid 活动id
 * @param jyms 剪影描述
 * @param jysj 剪影时间
 */
public Hdjy(Integer jyid,String jybt,String jytp,Integer hdid,String jyms,java.sql.Date jysj) {
    this.jyid=jyid;
    this.jybt=jybt;
    this.jytp=jytp;
    this.hdid=hdid;
    this.jyms=jyms;
    this.jysj=jysj;
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
 *获得活动id
 *@return 活动id
 */
public Integer getHdid() {
    return hdid;
}

/**
 *设置活动id
 *@param hdid 活动id
 */
public void setHdid(Integer hdid) {
    this.hdid = hdid;
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


}

