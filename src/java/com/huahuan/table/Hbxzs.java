/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 环保小知识(hbxzs)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Hbxzs {
/**    */
private Integer id;//
/**  标题  */
private String bt;//标题
/**  内容  */
private String nr;//内容

/**
 * hbxzs不带参数的构造方法
 */
public Hbxzs() {
}
/**
 * hbxzs带参数的构造方法
 * @param id 
 * @param bt 标题
 * @param nr 内容
 */
public Hbxzs(Integer id,String bt,String nr) {
    this.id=id;
    this.bt=bt;
    this.nr=nr;
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
 *获得标题
 *@return 标题
 */
public String getBt() {
    return bt;
}

/**
 *设置标题
 *@param bt 标题
 */
public void setBt(String bt) {
    this.bt = bt;
}

/**
 *获得内容
 *@return 内容
 */
public String getNr() {
    return nr;
}

/**
 *设置内容
 *@param nr 内容
 */
public void setNr(String nr) {
    this.nr = nr;
}


}

