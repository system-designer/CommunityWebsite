/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 用户站内通信表(bloglog)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Bloglog {
/**    */
private Integer lid;//
/**  主人  */
private Integer host;//主人
/**  日志标题  */
private String title;//日志标题
/**  日志内容  */
private String content;//日志内容
/**  发布时间  */
private java.sql.Timestamp pubtime;//发布时间
/**  是否公开  */
private Boolean open;//是否公开
/**  浏览次数  */
private Integer scantimes;//浏览次数
/**  回复次数  */
private Integer replytimes;//回复次数
/**  点赞次数  */
private Integer praisetimes;//点赞次数
/**  置顶  */
private Boolean top;//置顶

/**
 * bloglog不带参数的构造方法
 */
public Bloglog() {
}
/**
 * bloglog带参数的构造方法
 * @param lid 
 * @param host 主人
 * @param title 日志标题
 * @param content 日志内容
 * @param pubtime 发布时间
 * @param open 是否公开
 * @param scantimes 浏览次数
 * @param replytimes 回复次数
 * @param praisetimes 点赞次数
 * @param top 置顶
 */
public Bloglog(Integer lid,Integer host,String title,String content,java.sql.Timestamp pubtime,Boolean open,Integer scantimes,Integer replytimes,Integer praisetimes,Boolean top) {
    this.lid=lid;
    this.host=host;
    this.title=title;
    this.content=content;
    this.pubtime=pubtime;
    this.open=open;
    this.scantimes=scantimes;
    this.replytimes=replytimes;
    this.praisetimes=praisetimes;
    this.top=top;
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
 *获得日志标题
 *@return 日志标题
 */
public String getTitle() {
    return title;
}

/**
 *设置日志标题
 *@param title 日志标题
 */
public void setTitle(String title) {
    this.title = title;
}

/**
 *获得日志内容
 *@return 日志内容
 */
public String getContent() {
    return content;
}

/**
 *设置日志内容
 *@param content 日志内容
 */
public void setContent(String content) {
    this.content = content;
}

/**
 *获得发布时间
 *@return 发布时间
 */
public java.sql.Timestamp getPubtime() {
    return pubtime;
}

/**
 *设置发布时间
 *@param pubtime 发布时间
 */
public void setPubtime(java.sql.Timestamp pubtime) {
    this.pubtime = pubtime;
}

/**
 *获得是否公开
 *@return 是否公开
 */
public Boolean getOpen() {
    return open;
}

/**
 *设置是否公开
 *@param open 是否公开
 */
public void setOpen(Boolean open) {
    this.open = open;
}

/**
 *获得浏览次数
 *@return 浏览次数
 */
public Integer getScantimes() {
    return scantimes;
}

/**
 *设置浏览次数
 *@param scantimes 浏览次数
 */
public void setScantimes(Integer scantimes) {
    this.scantimes = scantimes;
}

/**
 *获得回复次数
 *@return 回复次数
 */
public Integer getReplytimes() {
    return replytimes;
}

/**
 *设置回复次数
 *@param replytimes 回复次数
 */
public void setReplytimes(Integer replytimes) {
    this.replytimes = replytimes;
}

/**
 *获得点赞次数
 *@return 点赞次数
 */
public Integer getPraisetimes() {
    return praisetimes;
}

/**
 *设置点赞次数
 *@param praisetimes 点赞次数
 */
public void setPraisetimes(Integer praisetimes) {
    this.praisetimes = praisetimes;
}

/**
 *获得置顶
 *@return 置顶
 */
public Boolean getTop() {
    return top;
}

/**
 *设置置顶
 *@param top 置顶
 */
public void setTop(Boolean top) {
    this.top = top;
}


}

