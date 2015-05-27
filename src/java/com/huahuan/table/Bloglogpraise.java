/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 日志点赞表(bloglogpraise)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Bloglogpraise {
/**    */
private Integer pid;//
/**  所赞日志  */
private Integer log;//所赞日志
/**  点赞人  */
private Integer from;//点赞人
/**  点赞时间  */
private Integer praisetime;//点赞时间

/**
 * bloglogpraise不带参数的构造方法
 */
public Bloglogpraise() {
}
/**
 * bloglogpraise带参数的构造方法
 * @param pid 
 * @param log 所赞日志
 * @param from 点赞人
 * @param praisetime 点赞时间
 */
public Bloglogpraise(Integer pid,Integer log,Integer from,Integer praisetime) {
    this.pid=pid;
    this.log=log;
    this.from=from;
    this.praisetime=praisetime;
}
/**
 *获得
 *@return 
 */
public Integer getPid() {
    return pid;
}

/**
 *设置
 *@param pid 
 */
public void setPid(Integer pid) {
    this.pid = pid;
}

/**
 *获得所赞日志
 *@return 所赞日志
 */
public Integer getLog() {
    return log;
}

/**
 *设置所赞日志
 *@param log 所赞日志
 */
public void setLog(Integer log) {
    this.log = log;
}

/**
 *获得点赞人
 *@return 点赞人
 */
public Integer getFrom() {
    return from;
}

/**
 *设置点赞人
 *@param from 点赞人
 */
public void setFrom(Integer from) {
    this.from = from;
}

/**
 *获得点赞时间
 *@return 点赞时间
 */
public Integer getPraisetime() {
    return praisetime;
}

/**
 *设置点赞时间
 *@param praisetime 点赞时间
 */
public void setPraisetime(Integer praisetime) {
    this.praisetime = praisetime;
}


}

