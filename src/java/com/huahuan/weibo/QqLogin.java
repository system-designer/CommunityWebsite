package com.huahuan.weibo;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Yhb;
import com.huahuan.tools.MD5;
import com.huahuan.tools.UserUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jplus.hyb.database.Hyberbin;

/**
 *
 * @author Evance
 */
@WebServlet(name = "QqLogin", urlPatterns = {"/QqLogin.jsp", "/QqLoginHSDDZ/*"})
public class QqLogin extends HttpServlet {

    public final static int OTHER = 0;//其它
    public final static int LOGIN = 1;

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

    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            String url = new Oauth().getAuthorizeURL(request);
//            String user = hsojLogin(request, request.getSession());
//            if (user != null) {
//                url = url.replace("QqLogin.jsp", "/QqLoginHSDDZ/" + user);
//            }
//            url = url.replace("QqLogin.jsp", "/QqLoginHSDDZ/");
            response.sendRedirect(url);
        } catch (Exception e) {
        }
    }

    public void qq(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("QQ跳转回来了");
        String notice;
        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
            String accessToken, openID;
            if (accessTokenObj.getAccessToken().equals("")) {
                notice = "没有获取到响应参数！";
            } else {
                accessToken = accessTokenObj.getAccessToken();
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                // 利用获取到的accessToken 去获取当前用户的openid --------- end
                System.out.println("openid：" + openID.toString());
                UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
                Yhb qqYh = UserUtil.getQqYh(openID);
                if (qqYh.getId() == null) {
                    boolean registerBySina = UserUtil.registerByQq(userInfoBean.getNickname(), openID);
                    if (registerBySina) {
                        notice = "欢迎使用QQ登录HSDDZ！";
                        qqYh = UserUtil.getQqYh(openID);
                        login(request, request.getSession(), qqYh);
//                        HsojLogger.log(request, "用QQ注册并登录");
                    } else {
                        notice = "用您QQ在HSDDZ上注册账号注册失败！";
//                        HsojLogger.log(request, "用QQ号注册失败");
                    }
                } else {
                    login(request, request.getSession(), qqYh);
                }
                System.out.println("QQ登录成功");
            }
        } catch (QQConnectException e) {
            notice = "没有成功登录QQ！";
//            HsojLogger.log(request, "用QQ登录失败");
        }
        System.out.println("跳转到首页成功");
        try {
            response.sendRedirect(request.getContextPath() + "/tz.jsp");
        } catch (IOException ex) {
            Logger.getLogger(QqLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void execute(int event, HttpServletRequest request, HttpServletResponse response) {
        /**
         * 下面是相关模式下所做的动作*
         */
        switch (event) {
            case OTHER:
                qq(request, response);
                break;
            case LOGIN:
                login(request, response);
                break;
        }
    }
    //以下方法为第三方登录通用的方法

    public void login(HttpServletRequest request, HttpSession session, Yhb yhb) {
        session.setAttribute("yhb", yhb);
        Hyberbin hyberbin = new Hyberbin(yhb);
        //修改最后登录时间
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        yhb.setZhdlsj(sj);
        hyberbin.updateByKey("id");
        //关于权限的部分
//        if (PowerManager.getPower(request, PowerManager.HTGL)) {
//            session.setAttribute("power", PowerManager.HTGL);
//        }
//        if (PowerManager.getPower(request, PowerManager.CKDM)) {
//            session.setAttribute("ckdm", PowerManager.CKDM);
//        }
        session.setAttribute("weibo", yhb);
//        String notice = null;
        /**
         * *******绑定功能暂时取消**********
         */
//        Yhb logined = isHsojLogin(request);
//        if (logined != null) {
        //微博（QQ或者新浪）绑定
//            boolean qqToYhb = UserUtil.weiboToyhb(logined, yhb);
//            notice = "登录并绑定" + (qqToYhb ? "成功！" : "失败！");
//            HsojLogger.log(request, "登录并绑定微博");
//        } else {
//            HsojLogger.log(request, "用微博账号登录");
//        }
        /**
         * *******跳转取消*******
         */
//        try {
//        setNotice(request, notice);
//            response.sendRedirect(request.getContextPath() + "/tz.jsp");
//        } catch (IOException ex) {
//            Logger.getLogger(QqLogin.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public String hsojLogin(HttpServletRequest request, HttpSession session) {
        //if (GetAdm.getCode(request, "code")) {
        Yhb test = (Yhb) session.getAttribute("yhb");
        if (test == null || test.getId() == null) {
            return null;
        } else {
            Yhb yhb = new Yhb();
            String yhm = request.getParameter("username");
            String pass = request.getParameter("password");
            Hyberbin hyberbin = new Hyberbin(yhb);
            pass = MD5.md5(pass);//这里pass是null
            hyberbin.addParmeter(yhm).addParmeter(yhm).addParmeter(yhm).addParmeter(pass);
            hyberbin.showOne("select * from yhb where (yhm=? or yx=? ) and mm=?");
            if (yhb.getId() != null) {
                return yhb.getId() + "_" + yhb.getMm();
            }
        }
        return null;
    }

    private Yhb isHsojLogin(HttpServletRequest request) {
        String l = null, p = null;
        String url = request.getRequestURL().toString();
        if (url != null && url.contains("huahuan/")) {
            url = url.substring(url.lastIndexOf("huahuan/") + "huahuan/".length());
            String[] split = url.split("_");
            l = split[0];
            p = split[1];
        }
        if (l != null && p != null) {
            Yhb yhb = new Yhb();
            String yhid_str = l;
            Integer yhid_int = 0;
            try {
                yhid_int = Integer.parseInt(yhid_str);
            } catch (NumberFormatException numberFormatException) {
            }
            String pass = p;
            if (yhid_int == null || yhid_int == 0 || pass == null || pass.trim().equals("")) {
                return null;
            } else {
                yhb.setId(yhid_int);
                Hyberbin hyberbin = new Hyberbin(yhb);
                hyberbin.addParmeter(yhid_int).addParmeter(pass);
                hyberbin.showOne("select * from yhb where id=? and mm=?");
                if (yhb.getYhm() != null) {
                    return yhb;
                }
            }
        }
        return null;
    }
}
