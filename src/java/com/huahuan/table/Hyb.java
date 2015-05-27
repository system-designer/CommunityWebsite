/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 会员表(hyb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Hyb {
/**    */
private Integer id;//
/**  姓名  */
private String xm;//姓名
/**  性别  */
private String xb;//性别
/**  系级  */
private String xj;//系级
/**  联系电话  */
private String lxdh;//联系电话
/**  校园短号  */
private String dh;//校园短号
/**  会员照片  */
private String hyzp;//会员照片
/**  备注  */
private String bz;//备注
/**  职务  */
private Integer zw;//职务

/**
 * hyb不带参数的构造方法
 */
public Hyb() {
}
/**
 * hyb带参数的构造方法
 * @param id 
 * @param xm 姓名
 * @param xb 性别
 * @param xj 系级
 * @param lxdh 联系电话
 * @param dh 校园短号
 * @param hyzp 会员照片
 * @param bz 备注
 * @param zw 职务
 */
public Hyb(Integer id,String xm,String xb,String xj,String lxdh,String dh,String hyzp,String bz,Integer zw) {
    this.id=id;
    this.xm=xm;
    this.xb=xb;
    this.xj=xj;
    this.lxdh=lxdh;
    this.dh=dh;
    this.hyzp=hyzp;
    this.bz=bz;
    this.zw=zw;
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
 *获得系级
 *@return 系级
 */
public String getXj() {
    return xj;
}

/**
 *设置系级
 *@param xj 系级
 */
public void setXj(String xj) {
    this.xj = xj;
}

/**
 *获得联系电话
 *@return 联系电话
 */
public String getLxdh() {
    return lxdh;
}

/**
 *设置联系电话
 *@param lxdh 联系电话
 */
public void setLxdh(String lxdh) {
    this.lxdh = lxdh;
}

/**
 *获得校园短号
 *@return 校园短号
 */
public String getDh() {
    return dh;
}

/**
 *设置校园短号
 *@param dh 校园短号
 */
public void setDh(String dh) {
    this.dh = dh;
}

/**
 *获得会员照片
 *@return 会员照片
 */
public String getHyzp() {
    return hyzp;
}

/**
 *设置会员照片
 *@param hyzp 会员照片
 */
public void setHyzp(String hyzp) {
    this.hyzp = hyzp;
}

/**
 *获得备注
 *@return 备注
 */
public String getBz() {
    return bz;
}

/**
 *设置备注
 *@param bz 备注
 */
public void setBz(String bz) {
    this.bz = bz;
}

/**
 *获得职务
 *@return 职务
 */
public Integer getZw() {
    return zw;
}

/**
 *设置职务
 *@param zw 职务
 */
public void setZw(Integer zw) {
    this.zw = zw;
}


}

