/*
 * 当前使用数据库为mysql
 * 此文件由Hyberbin自动生成仅供参考
 * QQ：464863696
 */
package com.huahuan.table;

/**
 * 社团公告(stgg)表POJO类
 * QQ：464863696
 * @author hyberbin
 */
public class Stgg {
/**    */
private Integer ggid;//
/**  公告标题  */
private String ggbt;//公告标题
/**  公告内容  */
private String ggnr;//公告内容
/**  公告时间  */
private java.sql.Date ggsj;//公告时间
/**  公告人  */
private Integer ggr;//公告人
/**  公告类别  */
private Integer gglb;//公告类别

/**
 * stgg不带参数的构造方法
 */
public Stgg() {
}
/**
 * stgg带参数的构造方法
 * @param ggid 
 * @param ggbt 公告标题
 * @param ggnr 公告内容
 * @param ggsj 公告时间
 * @param ggr 公告人
 * @param gglb 公告类别
 */
public Stgg(Integer ggid,String ggbt,String ggnr,java.sql.Date ggsj,Integer ggr,Integer gglb) {
    this.ggid=ggid;
    this.ggbt=ggbt;
    this.ggnr=ggnr;
    this.ggsj=ggsj;
    this.ggr=ggr;
    this.gglb=gglb;
}
/**
 *获得
 *@return 
 */
public Integer getGgid() {
    return ggid;
}

/**
 *设置
 *@param ggid 
 */
public void setGgid(Integer ggid) {
    this.ggid = ggid;
}

/**
 *获得公告标题
 *@return 公告标题
 */
public String getGgbt() {
    return ggbt;
}

/**
 *设置公告标题
 *@param ggbt 公告标题
 */
public void setGgbt(String ggbt) {
    this.ggbt = ggbt;
}

/**
 *获得公告内容
 *@return 公告内容
 */
public String getGgnr() {
    return ggnr;
}

/**
 *设置公告内容
 *@param ggnr 公告内容
 */
public void setGgnr(String ggnr) {
    this.ggnr = ggnr;
}

/**
 *获得公告时间
 *@return 公告时间
 */
public java.sql.Date getGgsj() {
    return ggsj;
}

/**
 *设置公告时间
 *@param ggsj 公告时间
 */
public void setGgsj(java.sql.Date ggsj) {
    this.ggsj = ggsj;
}

/**
 *获得公告人
 *@return 公告人
 */
public Integer getGgr() {
    return ggr;
}

/**
 *设置公告人
 *@param ggr 公告人
 */
public void setGgr(Integer ggr) {
    this.ggr = ggr;
}

/**
 *获得公告类别
 *@return 公告类别
 */
public Integer getGglb() {
    return gglb;
}

/**
 *设置公告类别
 *@param gglb 公告类别
 */
public void setGglb(Integer gglb) {
    this.gglb = gglb;
}


}

