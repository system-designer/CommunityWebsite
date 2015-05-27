package com.huahuan.web.index;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.mailutil.SendMail;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Qxlb;
import com.huahuan.table.Yhb;
import com.huahuan.tools.MD5;
import com.huahuan.tools.Util;
import com.jplus.json.JSONException;
import com.jplus.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jplus.hyb.database.Hyberbin;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "IndexAction", urlPatterns = "/IndexAction.jsp")
public class IndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLIST = 2;//显示列表
    public final static int SHOWJCSJLIST = 3;
    public final static int LOGIN = 7;
    public final static int LOGINOUT = 8;
    public final static int REGISTER = 9;
    public final static int CHECKYHM = 10;
    public final static int ACTIVATE = 11;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        execute(ServletUtil.setModel(request.getParameter("mode"), this), request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * 首页显示的各种列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        //公告列表
        ArrayList<HashMap> gglist = emm.executeQuery("select ggsj,ggbt,ggid,gglb from stgg limit 0,6");
        request.setAttribute("gglist", gglist);
        //活动列表
        ArrayList<HashMap> hdlist = emm.executeQuery("select  hdsj,hdbt,hdid,hdlb from sthd limit 0,6");
        request.setAttribute("hdlist", hdlist);
        //环保新闻列表
        ArrayList<HashMap> hbxwlist = emm.executeQuery("select bkid,bklb,bkbt,bksj from hbbk where bklb=1 limit 0,6");
        request.setAttribute("hbxwlist", hbxwlist);
        //活动图片列表
        ArrayList<HashMap> hdtplist = emm.executeQuery("select hdid,hdtp,hdbt,hdlb from sthd  order by hdid desc limit 0,8");
        request.setAttribute("hdtplist", hdtplist);
        //环保小知识列表
        ArrayList<HashMap> hbxzs = emm.executeQuery("select * from hbxzs");
        request.setAttribute("hbcy", hbxzs.get(0));
        request.setAttribute("hbzn", hbxzs.get(1));
        dao.close();
        //得到首页的环保漫画
        String basepath = request.getServletContext().getRealPath("/userfiles/image/网站/主页/环保漫画");
        File file = new File(basepath);
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            String hbmh = "/" + files[0].getPath().substring(basepath.indexOf("userfiles") + "userfiles".length() + 1).replace("\\", "/");
            request.setAttribute("hbmh", hbmh);
        }
        try {
            request.getRequestDispatcher("/web/public/index.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(IndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 得到首页精彩瞬间图片的路径
     */
    private void showJcsjList(HttpServletRequest request, HttpServletResponse response) {
        String basepath = request.getServletContext().getRealPath("/userfiles/image/网站/主页/精彩瞬间");
        File file = new File(basepath);
        File[] files = file.listFiles();
        int size = files.length;
        LinkedList<String> list = new LinkedList<String>();
        if (files != null) {
            for (int i = 0; i < size; i++) {
                String temp = "/" + files[i].getPath().substring(basepath.indexOf("userfiles") + "userfiles".length() + 1).replace("\\", "/");
                list.add(temp);
            }
        }
        request.setAttribute("jcsjlist", list);
        JSONObject json = new JSONObject();
        try {
            json.put("jcsjlist", list);
        } catch (JSONException ex) {
            Logger.getLogger(IndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServletUtil.ajaxData(json.toString(), response);
    }

    /**
     * 用户的登录操作
     */
    private void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            //普通登录
            String message = "";
            Yhb yhb = new Yhb();
            Hyberbin hyb = new Hyberbin(yhb, true);
            String yhm = request.getParameter("yhm");
            String mm = request.getParameter("mm");
            String code = request.getParameter("code");
            HttpSession session = request.getSession(true);
            if (!code.equals(session.getAttribute("code").toString())) {
                message = "验证码错误";
                hyb.reallyClose();
                request.setAttribute("yhm", yhm);
                request.setAttribute("notice", message);
                request.getRequestDispatcher("/web/login/login.jsp").forward(request, response);
                return;
            }
            hyb.addParmeter(yhm);
            yhb = hyb.showOne("select * from yhb where yhm=?");
            if (yhb.getId() == null) {
                message = "该用户不存在";
                hyb.reallyClose();
                request.setAttribute("yhm", yhm);
                request.setAttribute("notice", message);
                request.getRequestDispatcher("/web/login/login.jsp").forward(request, response);
                return;
            } else {
                hyb.clearParmeter();
                hyb.addParmeter(yhm);
                hyb.addParmeter(MD5.jplusMd5(mm));
                yhb = hyb.showOne("select * from yhb where yhm=? and mm=?");
                if (yhb.getId() == null) {
                    message = "密码错误，请重试";
                    hyb.reallyClose();
                    request.setAttribute("yhm", yhm);
                    request.setAttribute("notice", message);
                    request.getRequestDispatcher("/web/login/login.jsp").forward(request, response);
                    return;
                } else {
                    String autologin = request.getParameter("autologin");
                    System.out.println("autologin:" + autologin);
                    if (!Util.isEmpty(autologin)) {
                        //设置新的cookie，发给浏览器，异步情况下不能使用cookie机制 
                        Cookie cookie_yhm = new Cookie("username", yhm);
                        cookie_yhm.setMaxAge(3600 * 24 * 30);
                        cookie_yhm.setComment("huahuan");
                        response.addCookie(cookie_yhm);
                        //将加密的密码发给浏览器
                        Cookie cookie_mm = new Cookie("password", MD5.jplusMd5(mm));
                        cookie_mm.setComment("huahuan");
                        cookie_mm.setMaxAge(3600 * 24 * 30);
                        response.addCookie(cookie_mm);
                    }
                    message = "登录成功";
                    SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String composetime = ss.format(new java.util.Date());
                    Timestamp sj = Timestamp.valueOf(composetime);
                    hyb.clearParmeter();
                    hyb.addParmeter(sj);
                    hyb.addParmeter(yhb.getId());
                    hyb.update("update yhb set zhdlsj=? where id=?");
                    session.setAttribute("yhb", yhb);
                    Qxlb qxlb = new Qxlb();
                    hyb.changeTable(qxlb);
                    qxlb.setQxid(yhb.getQx());
                    hyb.showOnebyKey("qxid");
                    hyb.reallyClose();
                    session.setAttribute("yhqxmc", qxlb.getQxmc());
                    session.setMaxInactiveInterval(3600);//表示session生命期1小时
                    String forward = request.getParameter("forward");
                    if (Util.isEmpty(forward)) {
                        response.sendRedirect(request.getContextPath());
                    } else {
                        response.sendRedirect(request.getContextPath() + "/" + forward.replaceAll("AND", "&"));
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(IndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
    }

    /**
     * 用户注销登录
     */
    private void loginOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Yhb yhb = (Yhb) session.getAttribute("yhb");
        Hyberbin hyb = new Hyberbin(yhb);
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        yhb.setZhdlsj(sj);
        hyb.updateByKey("id");
        session.removeAttribute("yhb");
    }

    /**
     * 用户注册
     */
    private void register(HttpServletRequest request, HttpServletResponse response) {
        String message = "";
        String code = request.getParameter("code");
        HttpSession session = request.getSession(true);
        if (!code.equals(session.getAttribute("code").toString())) {
            message = "验证码错误";
            ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
            return;
        } else {
            Yhb yh = new Yhb();
            Hyberbin hy = new Hyberbin(yh);
            hy.addParmeter(request.getParameter("yhm"));
            yh = hy.showOne("select id,yhm from yhb where yhm=?");
            if (yh.getId() != null) {
                message = "该账号已注册，请勿重复提交";
                ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
                return;
            } else {
                Yhb yhb = new Yhb();
                Hyberbin hyb = new Hyberbin(yhb);
                ServletUtil.loadByBean(request, yhb, true);
                yhb.setMm(MD5.jplusMd5(yhb.getMm()));
                SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String composetime = ss.format(new java.util.Date());
                Timestamp sj = Timestamp.valueOf(composetime);
                yhb.setZcsj(sj);
                boolean b = hyb.insert("id");
                message = b ? "恭喜你，注册成功" : "对不起，注册失败";
                if (b) {
                    String path = request.getContextPath();
                    String basePath = request.getScheme() + "://"
                            + request.getServerName() + ":" + request.getServerPort()
                            + path + "/";
                    String email = "<p>" + yhb.getYhm() + " 您好！欢迎您注册湖北师范学院担当者志愿社（HSDDZ）。</p>\n"
                            + "	\n"
                            + "	<p>点击下面链接激活帐号</p>\n"
                            + "	<p><a href=\""
                            + basePath + "IndexAction.jsp?mode=ACTIVATE&mm=" + yhb.getMm() + "&yx=" + yhb.getYx() + "\">直接点击进入激活！</a></p>\n";
                    SendMail.sendEmail(request, yhb.getYx(), "用邮箱激活账号", email, 1);
                    message = "注册成功！积分+5！请登录后三天之内用邮箱激活！（激活后积分再+5!）";
                }
                ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
            }
        }
    }

    /**
     * 检测用户名是否重复
     */
    private void checkYhm(HttpServletRequest request, HttpServletResponse response) {
        String message = "";
        Yhb yhb = new Yhb();
        Hyberbin hyb = new Hyberbin(yhb);
        String yhm = request.getParameter("yhm");
        hyb.addParmeter(yhm);
        yhb = hyb.showOne("select * from yhb where yhm=?");
        if (yhb.getId() != null) {
            message = "该用户名已被注册";
        } else {
            message = "该用户名可以使用";
        }
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
    }

    /**
     * 注册用户
     */
    public String add(HttpServletRequest request, HttpServletResponse response) {
        Yhb yhb = new Yhb();
        String notice;
        String redirect;
        ServletUtil.loadByParams(request, yhb, true);
        HttpSession session = request.getSession();
        String scode = (String) session.getAttribute("code");
        session.removeAttribute("code");
        String code = request.getParameter("code");
        String pass = request.getParameter("repassword");
        if (scode == null || code == null || !code.equals(scode)) {
            notice = "验证码不正确！";
//            setNotice(request, notice);
            return "redirect:user/register.jsp";
        } else if (pass == null || yhb.getMm() == null || !pass.equals(yhb.getMm())) {
            notice = "两次密码不一致！";
//            setNotice(request, notice);
            return "redirect:user/register.jsp";
        }
        yhb.setMm(MD5.jplusMd5(pass));
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        yhb.setZcsj(sj);
        yhb.setJf(10);
        Hyberbin hyberbin = new Hyberbin(yhb);
        boolean b = hyberbin.insert("yhid");
        if (b) {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String email = "<p>" + yhb.getYhm() + " 您好！欢迎您注册湖北师范学院在线程序设计系统（HSOJ）。</p>\n"
                    + "	\n"
                    + "	<p>点击下面链接激活帐号</p>\n"
                    + "	<p><a href=\""
                    + basePath + "/UserServlet.jsp?mode=jh&yhmm=" + yhb.getMm() + "&yx=" + yhb.getYx() + "\">直接点击进入激活！</a></p>\n";
            SendMail.sendEmail(request, yhb.getYx(), "用邮箱激活账号", email, 1);
            notice = "注册成功！积分+5！请登录后三天之内用邮箱激活！（激活后积分再+5!）";
            redirect = "user/login.jsp";
        } else {
            notice = "注册失败！";
            redirect = "user/register.jsp";
        }
        return "redirect:" + redirect;
    }

    /**
     * 申请邮箱认证
     */
    public void sqyxrz(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Yhb yhb = (Yhb) session.getAttribute("user");
        String notice;
        String redirect;
        if (yhb != null) {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
            String email = "<p>" + yhb.getYhm() + " 您好！欢迎您使用湖北师范学院担当者志愿社（HSDDZ）。</p>\n"
                    + "	\n"
                    + "	<p>点击下面链接激活帐号</p>\n"
                    + "	<p><a href=\""
                    + basePath + "/IndexAction.jsp?mode=ACTIVATE&yhmm=" + yhb.getMm() + "&yx=" + yhb.getYx() + "\">直接点击进入激活！</a></p>\n";
            SendMail.sendEmail(request, yhb.getYx(), "用邮箱激活账号", email, 1);
            notice = "激活信息已经发送到您的邮箱，你查收！";
            redirect = "user/user_yanzheng.jsp";
        } else {
            notice = "当前用户已经退出！请重新登录！";
            redirect = "index.jsp";
        }
    }

    /**
     * 激活邮箱
     */
    public void activate(HttpServletRequest request, HttpServletResponse response) {
        String pass = request.getParameter("mm");
        String notice = null;
        String send;
        String yx = request.getParameter("yx");
        Yhb yhb = new Yhb();
        yhb.setYx(yx);
        Hyberbin hyberbin = new Hyberbin(yhb, true);
        hyberbin.showOnebyKey("yx");
        if (yhb.getId() != null) {
            if (yhb.getMm() != null && pass != null && yhb.getMm().equals(pass)) {
                if (yhb.getSfjh() != null && !yhb.getSfjh()) {
                    boolean b = hyberbin.addParmeter(yx).addParmeter(pass).update("update yhb set sfjh=1 where yx=? and mm=?");
                    notice = b ? "激活成功！积分+5" : "激活失败！";
                    yhb.setJf(yhb.getJf() + 5);
                    hyberbin.clearParmeter();
                    hyberbin.setField("jf").updateByKey("yx");
                } else {
                    notice = "已经激活过！";
                }
            }
        } else {
            notice = "用户不存在！";
        }
        hyberbin.reallyClose();
    }

    /**
     * 下面的模式和方法可以自行增删
     */
    public void execute(int event, HttpServletRequest request, HttpServletResponse response) {
        /**
         * 下面是相关模式下所做的动作*
         */
        switch (event) {
            case OTHER:
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (ServletException ex) {
                    Logger.getLogger(IndexAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(IndexAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            case SHOWLIST:
                showlist(request, response);
                break;
            case SHOWJCSJLIST:
                showJcsjList(request, response);
                break;
            case LOGIN:
                login(request, response);
                break;
            case LOGINOUT:
                loginOut(request, response);
                showlist(request, response);
                break;
            case REGISTER:
                register(request, response);
                break;
            case CHECKYHM:
                checkYhm(request, response);
                break;
            case ACTIVATE:
                activate(request, response);
                break;
        }
    }
}
