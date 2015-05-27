/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 日志回复表(bloglogreply)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Bloglogreply {
/**    */
private Integer rid;//
/**  所回复的日志  */
private Integer log;//所回复的日志
/**  回复人  */
private Integer from;//回复人
/**  回复时间  */
private java.sql.Timestamp replytime;//回复时间
/**  回复内容  */
private String content;//回复内容

/**
 * bloglogreply不带参数的构造方法
 */
public Bloglogreply() {
}
/**
 * bloglogreply带参数的构造方法
 * @param rid 
 * @param log 所回复的日志
 * @param from 回复人
 * @param replytime 回复时间
 * @param content 回复内容
 */
public Bloglogreply(Integer rid,Integer log,Integer from,java.sql.Timestamp replytime,String content) {
    this.rid=rid;
    this.log=log;
    this.from=from;
    this.replytime=replytime;
    this.content=content;
}
/**
 *获得
 *@return 
 */
public Integer getRid() {
    return rid;
}

/**
 *设置
 *@param rid 
 */
public void setRid(Integer rid) {
    this.rid = rid;
}

/**
 *获得所回复的日志
 *@return 所回复的日志
 */
public Integer getLog() {
    return log;
}

/**
 *设置所回复的日志
 *@param log 所回复的日志
 */
public void setLog(Integer log) {
    this.log = log;
}

/**
 *获得回复人
 *@return 回复人
 */
public Integer getFrom() {
    return from;
}

/**
 *设置回复人
 *@param from 回复人
 */
public void setFrom(Integer from) {
    this.from = from;
}

/**
 *获得回复时间
 *@return 回复时间
 */
public java.sql.Timestamp getReplytime() {
    return replytime;
}

/**
 *设置回复时间
 *@param replytime 回复时间
 */
public void setReplytime(java.sql.Timestamp replytime) {
    this.replytime = replytime;
}

/**
 *获得回复内容
 *@return 回复内容
 */
public String getContent() {
    return content;
}

/**
 *设置回复内容
 *@param content 回复内容
 */
public void setContent(String content) {
    this.content = content;
}


}

