/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 用户好友表(blogfriends)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Blogfriends {
/**    */
private Integer fid;//
/**  主人  */
private Integer host;//主人
/**  主人的好友  */
private Integer friend;//主人的好友
/**  开始时间  */
private java.sql.Date starttime;//开始时间
/**  主人是否同意  */
private Boolean agree;//主人是否同意
/**  亲密程度  */
private Integer degree;//亲密程度

/**
 * blogfriends不带参数的构造方法
 */
public Blogfriends() {
}
/**
 * blogfriends带参数的构造方法
 * @param fid 
 * @param host 主人
 * @param friend 主人的好友
 * @param starttime 开始时间
 * @param agree 主人是否同意
 * @param degree 亲密程度
 */
public Blogfriends(Integer fid,Integer host,Integer friend,java.sql.Date starttime,Boolean agree,Integer degree) {
    this.fid=fid;
    this.host=host;
    this.friend=friend;
    this.starttime=starttime;
    this.agree=agree;
    this.degree=degree;
}
/**
 *获得
 *@return 
 */
public Integer getFid() {
    return fid;
}

/**
 *设置
 *@param fid 
 */
public void setFid(Integer fid) {
    this.fid = fid;
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
 *获得主人的好友
 *@return 主人的好友
 */
public Integer getFriend() {
    return friend;
}

/**
 *设置主人的好友
 *@param friend 主人的好友
 */
public void setFriend(Integer friend) {
    this.friend = friend;
}

/**
 *获得开始时间
 *@return 开始时间
 */
public java.sql.Date getStarttime() {
    return starttime;
}

/**
 *设置开始时间
 *@param starttime 开始时间
 */
public void setStarttime(java.sql.Date starttime) {
    this.starttime = starttime;
}

/**
 *获得主人是否同意
 *@return 主人是否同意
 */
public Boolean getAgree() {
    return agree;
}

/**
 *设置主人是否同意
 *@param agree 主人是否同意
 */
public void setAgree(Boolean agree) {
    this.agree = agree;
}

/**
 *获得亲密程度
 *@return 亲密程度
 */
public Integer getDegree() {
    return degree;
}

/**
 *设置亲密程度
 *@param degree 亲密程度
 */
public void setDegree(Integer degree) {
    this.degree = degree;
}


}

