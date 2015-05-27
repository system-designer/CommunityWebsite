/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 职务分类(zwfl)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Zwfl {
/**    */
private Integer zwid;//
/**  职务名称  */
private String zwmc;//职务名称

/**
 * zwfl不带参数的构造方法
 */
public Zwfl() {
}
/**
 * zwfl带参数的构造方法
 * @param zwid 
 * @param zwmc 职务名称
 */
public Zwfl(Integer zwid,String zwmc) {
    this.zwid=zwid;
    this.zwmc=zwmc;
}
/**
 *获得
 *@return 
 */
public Integer getZwid() {
    return zwid;
}

/**
 *设置
 *@param zwid 
 */
public void setZwid(Integer zwid) {
    this.zwid = zwid;
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

