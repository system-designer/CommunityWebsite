/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 用户权限表(yhqxb)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Yhqxb {
/**    */
private Integer id;//
/**  用户id  */
private Integer yhid;//用户id
/**  权限id  */
private Integer qxid;//权限id

/**
 * yhqxb不带参数的构造方法
 */
public Yhqxb() {
}
/**
 * yhqxb带参数的构造方法
 * @param id 
 * @param yhid 用户id
 * @param qxid 权限id
 */
public Yhqxb(Integer id,Integer yhid,Integer qxid) {
    this.id=id;
    this.yhid=yhid;
    this.qxid=qxid;
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
 *获得用户id
 *@return 用户id
 */
public Integer getYhid() {
    return yhid;
}

/**
 *设置用户id
 *@param yhid 用户id
 */
public void setYhid(Integer yhid) {
    this.yhid = yhid;
}

/**
 *获得权限id
 *@return 权限id
 */
public Integer getQxid() {
    return qxid;
}

/**
 *设置权限id
 *@param qxid 权限id
 */
public void setQxid(Integer qxid) {
    this.qxid = qxid;
}


}

