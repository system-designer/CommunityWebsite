/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 用户留言表(blogleave)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Blogleave {
/**    */
private Integer lid;//
/**  主人  */
private Integer host;//主人
/**  留言人  */
private Integer from;//留言人
/**  留言内容  */
private String content;//留言内容
/**  留言时间  */
private java.sql.Timestamp leavetime;//留言时间

/**
 * blogleave不带参数的构造方法
 */
public Blogleave() {
}
/**
 * blogleave带参数的构造方法
 * @param lid 
 * @param host 主人
 * @param from 留言人
 * @param content 留言内容
 * @param leavetime 留言时间
 */
public Blogleave(Integer lid,Integer host,Integer from,String content,java.sql.Timestamp leavetime) {
    this.lid=lid;
    this.host=host;
    this.from=from;
    this.content=content;
    this.leavetime=leavetime;
}
/**
 *获得
 *@return 
 */
public Integer getLid() {
    return lid;
}

/**
 *设置
 *@param lid 
 */
public void setLid(Integer lid) {
    this.lid = lid;
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
 *获得留言人
 *@return 留言人
 */
public Integer getFrom() {
    return from;
}

/**
 *设置留言人
 *@param from 留言人
 */
public void setFrom(Integer from) {
    this.from = from;
}

/**
 *获得留言内容
 *@return 留言内容
 */
public String getContent() {
    return content;
}

/**
 *设置留言内容
 *@param content 留言内容
 */
public void setContent(String content) {
    this.content = content;
}

/**
 *获得留言时间
 *@return 留言时间
 */
public java.sql.Timestamp getLeavetime() {
    return leavetime;
}

/**
 *设置留言时间
 *@param leavetime 留言时间
 */
public void setLeavetime(java.sql.Timestamp leavetime) {
    this.leavetime = leavetime;
}


}

