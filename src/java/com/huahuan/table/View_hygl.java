/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * VIEW(view_hygl)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class View_hygl {
/**    */
private Integer id;//
/**  姓名  */
private String xm;//姓名
/**  系级  */
private String xj;//系级
/**  性别  */
private String xb;//性别
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
/**  部门id  */
private Integer bmid;//部门id
/**  职务分类  */
private Integer zwfl;//职务分类
/**  职务描述  */
private String zwms;//职务描述
/**  部门名称  */
private String bmmc;//部门名称
/**  部门简介  */
private String bmjj;//部门简介
/**  职务名称  */
private String zwmc;//职务名称

/**
 * view_hygl不带参数的构造方法
 */
public View_hygl() {
}
/**
 * view_hygl带参数的构造方法
 * @param id 
 * @param xm 姓名
 * @param xj 系级
 * @param xb 性别
 * @param lxdh 联系电话
 * @param dh 校园短号
 * @param hyzp 会员照片
 * @param bz 备注
 * @param zw 职务
 * @param bmid 部门id
 * @param zwfl 职务分类
 * @param zwms 职务描述
 * @param bmmc 部门名称
 * @param bmjj 部门简介
 * @param zwmc 职务名称
 */
public View_hygl(Integer id,String xm,String xj,String xb,String lxdh,String dh,String hyzp,String bz,Integer zw,Integer bmid,Integer zwfl,String zwms,String bmmc,String bmjj,String zwmc) {
    this.id=id;
    this.xm=xm;
    this.xj=xj;
    this.xb=xb;
    this.lxdh=lxdh;
    this.dh=dh;
    this.hyzp=hyzp;
    this.bz=bz;
    this.zw=zw;
    this.bmid=bmid;
    this.zwfl=zwfl;
    this.zwms=zwms;
    this.bmmc=bmmc;
    this.bmjj=bmjj;
    this.zwmc=zwmc;
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

/**
 *获得部门id
 *@return 部门id
 */
public Integer getBmid() {
    return bmid;
}

/**
 *设置部门id
 *@param bmid 部门id
 */
public void setBmid(Integer bmid) {
    this.bmid = bmid;
}

/**
 *获得职务分类
 *@return 职务分类
 */
public Integer getZwfl() {
    return zwfl;
}

/**
 *设置职务分类
 *@param zwfl 职务分类
 */
public void setZwfl(Integer zwfl) {
    this.zwfl = zwfl;
}

/**
 *获得职务描述
 *@return 职务描述
 */
public String getZwms() {
    return zwms;
}

/**
 *设置职务描述
 *@param zwms 职务描述
 */
public void setZwms(String zwms) {
    this.zwms = zwms;
}

/**
 *获得部门名称
 *@return 部门名称
 */
public String getBmmc() {
    return bmmc;
}

/**
 *设置部门名称
 *@param bmmc 部门名称
 */
public void setBmmc(String bmmc) {
    this.bmmc = bmmc;
}

/**
 *获得部门简介
 *@return 部门简介
 */
public String getBmjj() {
    return bmjj;
}

/**
 *设置部门简介
 *@param bmjj 部门简介
 */
public void setBmjj(String bmjj) {
    this.bmjj = bmjj;
}

/**
 *获得职务名称
 *@return 职务名称
 */
public String getZwmc() {
    return zwmc;
}

/**
 *设置职务名称
 *@param zwmc 职务名称
 */
public void setZwmc(String zwmc) {
    this.zwmc = zwmc;
}


}

