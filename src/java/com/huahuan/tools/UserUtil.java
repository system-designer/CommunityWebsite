package com.huahuan.tools;

import com.huahuan.table.Yhb;
import java.sql.Connection;
import org.jplus.hyb.database.DatabaseINI;
import org.jplus.hyb.database.Hyberbin;
import weibo4j.model.User;

/**
 * 用户模块的一些公共方法
 *
 * @author hyberbin
 */
public class UserUtil {

    public static String[] yxbm = new String[]{
        "",
        "文学院",
        "政法学院",
        "国际学院",
        "历史文化学院",
        "教育科学学院",
        "经济与管理学院",
        "美术学院",
        "音乐学院",
        "",
        "",
        "数学与统计学院",
        "物理与电子科学学院",
        "化学与环境工程学院",
        "生命科学学院",
        "计算机科学与技术学院",
        "教育信息与技术学院",
        "体育学院",
        "机电与控制工程学院",
        "地理科学系"
    };

    /**
     * 获得院系名称
     *
     * @param xh 学号
     * @return
     */
    public static String getYx(String xh) {
        if (xh == null || xh.length() != 13) {
            return "";//20093150101016
        } else {
            int index = 0;
            try {
                index = Integer.parseInt(xh.substring(5, 7));
            } catch (NumberFormatException numberFormatException) {
            }
            if (index > 0 && index < yxbm.length) {
                return yxbm[index];
            }
        }
        return "";
    }

    public static String getBj(String xh) {
        if (xh == null || xh.length() != 13) {
            return "";//2009315010106
        } else {
            String bj = xh.substring(2, 4);
            bj += xh.substring(9, 11);
            return bj;
        }
    }

    /**
     * 获得用户的等级
     *
     * @param jf 积分
     * @return
     */
    public static String getDj(Integer jf) {
        if (jf == null || jf < 30) {
            return "目不识丁";
        }
        if (jf < 60) {
            return "初出茅庐";
        }
        if (jf < 80) {
            return "初入江湖";
        }
        if (jf < 200) {
            return "武林高手";
        }
        if (jf < 500) {
            return "江湖剑客";
        }
        if (jf < 1000) {
            return "武林至尊";
        }
        if (jf >= 1000) {
            return "一代宗师";
        }
        return null;
    }

    public static boolean registerBySina(User user) {
        Yhb yhb = new Yhb();
        //设置注册时间
//        yhb.setZcsj(Status.getNowTime());
        yhb.setYhm(user.getScreenName());
        yhb.setSinaid(user.getId());
        yhb.setGxqm(user.getDescription());
        Hyberbin hyberbin = new Hyberbin(yhb);
        return hyberbin.insert("id");
    }

    public static boolean registerByQq(String name, String openid) {
        Yhb yhb = new Yhb();
        yhb.setYhm(name);
        //设置注册时间
//        yhb.setZcsj(Status.getNowTime());
        yhb.setQqid(openid);
        Hyberbin hyberbin = new Hyberbin(yhb);
        return hyberbin.insert("id");
    }

    public static boolean weiboToyhb(Yhb yhb, Yhb weibo) {
        if (yhb.getId().equals(weibo.getId())) {
            return true;
        }
        boolean b = true;
        Connection connection = DatabaseINI.getConnection();//修改过
        Hyberbin hyberbin = new Hyberbin(yhb, connection);
        hyberbin.transactionBegan();
        b = b && hyberbin.updateByKey("id");
        //修改发帖表
        b = b && hyberbin.addParmeter(yhb.getId()).addParmeter(weibo.getId()).update("update ftb set ftr=? where ftr=?");
        //修改回帖表
        b = b && hyberbin.addParmeter(yhb.getId()).addParmeter(weibo.getId()).update("update htb set ftr=? where ftr=?");
        //如果是版主修改版主
        b = b && hyberbin.addParmeter(yhb.getId()).addParmeter(weibo.getId()).update("update ltbk set bz=? where bz=?");
        //修改公告人
        b = b && hyberbin.addParmeter(yhb.getId()).addParmeter(weibo.getId()).update("update stgg set ggr=? where ggr=?");
        b = b && hyberbin.addParmeter(weibo.getId()).update("delete from yhb where id=?");
        hyberbin.transactionEnd(b);
        hyberbin.reallyClose();
        return b;
    }

    public static boolean sinaToYhb(Yhb yhb, Yhb sina) {
        yhb.setSinaid(sina.getSinaid());
        yhb.setGxqm(sina.getGxqm());
        return weiboToyhb(yhb, sina);
    }

    public static Yhb getSinaYh(String id) {
        Yhb yhb = new Yhb();
        yhb.setSinaid(id);
        Hyberbin hyberbin = new Hyberbin(yhb);
        return hyberbin.showOnebyKey("sinaid");
    }

    public static boolean qqToYhb(Yhb yhb, Yhb qq) {
        yhb.setQqid(qq.getQqid());
        return weiboToyhb(yhb, qq);
    }

    public static Yhb getQqYh(String openid) {
        Yhb yhb = new Yhb();
        yhb.setQqid(openid);
        Hyberbin hyberbin = new Hyberbin(yhb);
        return hyberbin.showOnebyKey("qqid");
    }
}
