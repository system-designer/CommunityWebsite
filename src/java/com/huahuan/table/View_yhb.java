/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * VIEW(view_yhb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class View_yhb {
/**  权限名称  */
private String qxmc;//权限名称
/**    */
private Integer id;//
/**  用户名  */
private String yhm;//用户名
/**  密码  */
private String mm;//密码
/**  真实姓名  */
private String zsxm;//真实姓名
/**  性别  */
private String xb;//性别
/**  邮箱  */
private String yx;//邮箱
/**  用户图像  */
private String yhtx;//用户图像
/**  是否激活  */
private Boolean sfjh;//是否激活
/**  注册时间  */
private java.sql.Timestamp zcsj;//注册时间
/**  最后登录时间  */
private java.sql.Timestamp zhdlsj;//最后登录时间
/**  权限  */
private Integer qx;//权限
/**  积分  */
private Integer jf;//积分
/**  个性签名  */
private String gxqm;//个性签名
/**  临时积分  */
private Integer lsjf;//临时积分
/**  是否在线  */
private Boolean sfzx;//是否在线
/**  qqid  */
private String qqid;//qqid

/**
 * view_yhb不带参数的构造方法
 */
public View_yhb() {
}
/**
 * view_yhb带参数的构造方法
 * @param qxmc 权限名称
 * @param id 
 * @param yhm 用户名
 * @param mm 密码
 * @param zsxm 真实姓名
 * @param xb 性别
 * @param yx 邮箱
 * @param yhtx 用户图像
 * @param sfjh 是否激活
 * @param zcsj 注册时间
 * @param zhdlsj 最后登录时间
 * @param qx 权限
 * @param jf 积分
 * @param gxqm 个性签名
 * @param lsjf 临时积分
 * @param sfzx 是否在线
 * @param qqid qqid
 */
public View_yhb(String qxmc,Integer id,String yhm,String mm,String zsxm,String xb,String yx,String yhtx,Boolean sfjh,java.sql.Timestamp zcsj,java.sql.Timestamp zhdlsj,Integer qx,Integer jf,String gxqm,Integer lsjf,Boolean sfzx,String qqid) {
    this.qxmc=qxmc;
    this.id=id;
    this.yhm=yhm;
    this.mm=mm;
    this.zsxm=zsxm;
    this.xb=xb;
    this.yx=yx;
    this.yhtx=yhtx;
    this.sfjh=sfjh;
    this.zcsj=zcsj;
    this.zhdlsj=zhdlsj;
    this.qx=qx;
    this.jf=jf;
    this.gxqm=gxqm;
    this.lsjf=lsjf;
    this.sfzx=sfzx;
    this.qqid=qqid;
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

/**
 *获得
 *@return 
 */
public Integer getId() {
    return id;
}

/**
 *设置
 *@param id 
 */
public void setId(Integer id) {
    this.id = id;
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
 *获得密码
 *@return 密码
 */
public String getMm() {
    return mm;
}

/**
 *设置密码
 *@param mm 密码
 */
public void setMm(String mm) {
    this.mm = mm;
}

/**
 *获得真实姓名
 *@return 真实姓名
 */
public String getZsxm() {
    return zsxm;
}

/**
 *设置真实姓名
 *@param zsxm 真实姓名
 */
public void setZsxm(String zsxm) {
    this.zsxm = zsxm;
}

/**
 *获得性别
 *@return 性别
 */
public String getXb() {
    return xb;
}

/**
 *设置性别
 *@param xb 性别
 */
public void setXb(String xb) {
    this.xb = xb;
}

/**
 *获得邮箱
 *@return 邮箱
 */
public String getYx() {
    return yx;
}

/**
 *设置邮箱
 *@param yx 邮箱
 */
public void setYx(String yx) {
    this.yx = yx;
}

/**
 *获得用户图像
 *@return 用户图像
 */
public String getYhtx() {
    return yhtx;
}

/**
 *设置用户图像
 *@param yhtx 用户图像
 */
public void setYhtx(String yhtx) {
    this.yhtx = yhtx;
}

/**
 *获得是否激活
 *@return 是否激活
 */
public Boolean getSfjh() {
    return sfjh;
}

/**
 *设置是否激活
 *@param sfjh 是否激活
 */
public void setSfjh(Boolean sfjh) {
    this.sfjh = sfjh;
}

/**
 *获得注册时间
 *@return 注册时间
 */
public java.sql.Timestamp getZcsj() {
    return zcsj;
}

/**
 *设置注册时间
 *@param zcsj 注册时间
 */
public void setZcsj(java.sql.Timestamp zcsj) {
    this.zcsj = zcsj;
}

/**
 *获得最后登录时间
 *@return 最后登录时间
 */
public java.sql.Timestamp getZhdlsj() {
    return zhdlsj;
}

/**
 *设置最后登录时间
 *@param zhdlsj 最后登录时间
 */
public void setZhdlsj(java.sql.Timestamp zhdlsj) {
    this.zhdlsj = zhdlsj;
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

/**
 *获得积分
 *@return 积分
 */
public Integer getJf() {
    return jf;
}

/**
 *设置积分
 *@param jf 积分
 */
public void setJf(Integer jf) {
    this.jf = jf;
}

/**
 *获得个性签名
 *@return 个性签名
 */
public String getGxqm() {
    return gxqm;
}

/**
 *设置个性签名
 *@param gxqm 个性签名
 */
public void setGxqm(String gxqm) {
    this.gxqm = gxqm;
}

/**
 *获得临时积分
 *@return 临时积分
 */
public Integer getLsjf() {
    return lsjf;
}

/**
 *设置临时积分
 *@param lsjf 临时积分
 */
public void setLsjf(Integer lsjf) {
    this.lsjf = lsjf;
}

/**
 *获得是否在线
 *@return 是否在线
 */
public Boolean getSfzx() {
    return sfzx;
}

/**
 *设置是否在线
 *@param sfzx 是否在线
 */
public void setSfzx(Boolean sfzx) {
    this.sfzx = sfzx;
}

/**
 *获得qqid
 *@return qqid
 */
public String getQqid() {
    return qqid;
}

/**
 *设置qqid
 *@param qqid qqid
 */
public void setQqid(String qqid) {
    this.qqid = qqid;
}


}

