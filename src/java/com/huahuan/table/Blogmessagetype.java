/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 站内消息内型(blogmessagetype)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Blogmessagetype {
/**    */
private Integer tid;//
/**  消息类型名称  */
private String typename;//消息类型名称

/**
 * blogmessagetype不带参数的构造方法
 */
public Blogmessagetype() {
}
/**
 * blogmessagetype带参数的构造方法
 * @param tid 
 * @param typename 消息类型名称
 */
public Blogmessagetype(Integer tid,String typename) {
    this.tid=tid;
    this.typename=typename;
}
/**
 *获得
 *@return 
 */
public Integer getTid() {
    return tid;
}

/**
 *设置
 *@param tid 
 */
public void setTid(Integer tid) {
    this.tid = tid;
}

/**
 *获得消息类型名称
 *@return 消息类型名称
 */
public String getTypename() {
    return typename;
}

/**
 *设置消息类型名称
 *@param typename 消息类型名称
 */
public void setTypename(String typename) {
    this.typename = typename;
}


}

