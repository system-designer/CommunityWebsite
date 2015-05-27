/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 会员职务(hyzw)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Hyzw {
/**    */
private Integer id;//
/**  部门id  */
private Integer bmid;//部门id
/**  职务分类  */
private Integer zwfl;//职务分类
/**  职务描述  */
private String zwms;//职务描述

/**
 * hyzw不带参数的构造方法
 */
public Hyzw() {
}
/**
 * hyzw带参数的构造方法
 * @param id 
 * @param bmid 部门id
 * @param zwfl 职务分类
 * @param zwms 职务描述
 */
public Hyzw(Integer id,Integer bmid,Integer zwfl,String zwms) {
    this.id=id;
    this.bmid=bmid;
    this.zwfl=zwfl;
    this.zwms=zwms;
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


}

