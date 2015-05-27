/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 用户空间访问信息(blogvisit)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Blogvisit {
/**    */
private Integer vid;//
/**  主人  */
private Integer host;//主人
/**  访客  */
private Integer visitor;//访客
/**  访问时间  */
private java.sql.Date visittime;//访问时间
/**  访客操作  */
private String action;//访客操作

/**
 * blogvisit不带参数的构造方法
 */
public Blogvisit() {
}
/**
 * blogvisit带参数的构造方法
 * @param vid 
 * @param host 主人
 * @param visitor 访客
 * @param visittime 访问时间
 * @param action 访客操作
 */
public Blogvisit(Integer vid,Integer host,Integer visitor,java.sql.Date visittime,String action) {
    this.vid=vid;
    this.host=host;
    this.visitor=visitor;
    this.visittime=visittime;
    this.action=action;
}
/**
 *获得
 *@return 
 */
public Integer getVid() {
    return vid;
}

/**
 *设置
 *@param vid 
 */
public void setVid(Integer vid) {
    this.vid = vid;
}

/**
 *获得主人
 *@return 主人
 */
public Integer getHost() {
    return host;
}

/**
 *设置主人
 *@param host 主人
 */
public void setHost(Integer host) {
    this.host = host;
}

/**
 *获得访客
 *@return 访客
 */
public Integer getVisitor() {
    return visitor;
}

/**
 *设置访客
 *@param visitor 访客
 */
public void setVisitor(Integer visitor) {
    this.visitor = visitor;
}

/**
 *获得访问时间
 *@return 访问时间
 */
public java.sql.Date getVisittime() {
    return visittime;
}

/**
 *设置访问时间
 *@param visittime 访问时间
 */
public void setVisittime(java.sql.Date visittime) {
    this.visittime = visittime;
}

/**
 *获得访客操作
 *@return 访客操作
 */
public String getAction() {
    return action;
}

/**
 *设置访客操作
 *@param action 访客操作
 */
public void setAction(String action) {
    this.action = action;
}


}

