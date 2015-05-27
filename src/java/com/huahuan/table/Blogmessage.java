/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 用户日志表(blogmessage)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Blogmessage {
/**    */
private Integer mid;//
/**  主人  */
private Integer host;//主人
/**  发送者  */
private Integer from;//发送者
/**  发送时间  */
private java.sql.Timestamp sendtime;//发送时间
/**  是否查看  */
private Boolean check;//是否查看
/**  发送内容  */
private String content;//发送内容
/**  消息类型  */
private Integer type;//消息类型

/**
 * blogmessage不带参数的构造方法
 */
public Blogmessage() {
}
/**
 * blogmessage带参数的构造方法
 * @param mid 
 * @param host 主人
 * @param from 发送者
 * @param sendtime 发送时间
 * @param check 是否查看
 * @param content 发送内容
 * @param type 消息类型
 */
public Blogmessage(Integer mid,Integer host,Integer from,java.sql.Timestamp sendtime,Boolean check,String content,Integer type) {
    this.mid=mid;
    this.host=host;
    this.from=from;
    this.sendtime=sendtime;
    this.check=check;
    this.content=content;
    this.type=type;
}
/**
 *获得
 *@return 
 */
public Integer getMid() {
    return mid;
}

/**
 *设置
 *@param mid 
 */
public void setMid(Integer mid) {
    this.mid = mid;
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
 *获得发送者
 *@return 发送者
 */
public Integer getFrom() {
    return from;
}

/**
 *设置发送者
 *@param from 发送者
 */
public void setFrom(Integer from) {
    this.from = from;
}

/**
 *获得发送时间
 *@return 发送时间
 */
public java.sql.Timestamp getSendtime() {
    return sendtime;
}

/**
 *设置发送时间
 *@param sendtime 发送时间
 */
public void setSendtime(java.sql.Timestamp sendtime) {
    this.sendtime = sendtime;
}

/**
 *获得是否查看
 *@return 是否查看
 */
public Boolean getCheck() {
    return check;
}

/**
 *设置是否查看
 *@param check 是否查看
 */
public void setCheck(Boolean check) {
    this.check = check;
}

/**
 *获得发送内容
 *@return 发送内容
 */
public String getContent() {
    return content;
}

/**
 *设置发送内容
 *@param content 发送内容
 */
public void setContent(String content) {
    this.content = content;
}

/**
 *获得消息类型
 *@return 消息类型
 */
public Integer getType() {
    return type;
}

/**
 *设置消息类型
 *@param type 消息类型
 */
public void setType(Integer type) {
    this.type = type;
}


}

