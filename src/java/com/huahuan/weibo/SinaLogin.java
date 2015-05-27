package com.huahuan.weibo;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Yhb;
import com.huahuan.tools.MD5;
import com.huahuan.tools.UserUtil;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jplus.hyb.database.Hyberbin;
import weibo4j.Account;
import weibo4j.Oauth;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.model.User;
import weibo4j.org.json.JSONObject;

/**
 *
 * @author Hyberbin
 */
@WebServlet(name = "SinaLogin", urlPatterns = {"/SinaLogin.jsp", "/SinaLoginHSOJ/*"})
public class SinaLogin extends HttpServlet {

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

    public String login(HttpServletRequest request, HttpServletResponse response) {
        String url = "https://api.weibo.com/oauth2/authorize?client_id=4192697754&response_type=code&redirect_uri=http://opdps.hbnu.edu.cn/SinaLogin.jsp";
        String user = hsojLogin(request, request.getSession());
        if (user != null) {
            url = url.replace("SinaLogin.jsp", "/SinaLoginHSOJ/" + user);
        }
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
        }
        return "redirect:/index.jsp";
    }

    public String sina(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        String notice = null;
        String redirect = "/index.jsp";
        if (code != null && !code.trim().equals("")) {
            Oauth oauth = new Oauth();
            try {
                AccessToken accessTokenByCode = oauth.getAccessTokenByCode(code);
                Users um = new Users();
                um.client.setToken(accessTokenByCode.getAccessToken());
                Account am = new Account();
                am.client.setToken(accessTokenByCode.getAccessToken());
                JSONObject uid = am.getUid();
                User user = um.showUserById(uid.get("uid").toString());
                Yhb sinaYh = UserUtil.getSinaYh(user.getId());
                if (sinaYh.getId() == null) {
                    boolean registerBySina = UserUtil.registerBySina(user);
                    if (registerBySina) {
                        notice = "已经用您的新浪账号在HSOJ上注册账号，您以后可以使用新浪微博登录HSOJ！您还可以在个人信息里面与原来的HSOJ账号绑定！";
                        login(request, request.getSession(), UserUtil.getSinaYh(user.getId()));
//                        HsojLogger.log(request, "用微博账号注册并登录");
                        redirect = "/user/bangding.jsp";
                    } else {
                        notice = "用您的新浪账号在HSOJ上注册账号注册失败！";
//                        HsojLogger.log(request, "用微博账号注册失败");
                    }
                } else {
                    login(request, request.getSession(), sinaYh);
                }
            } catch (Exception e) {
                notice = "没有登录新浪微博！";
//                LoggerManage.logger.getLogger(request, "用微博账号登录失败", e);
            }
        } else {
            notice = "没有登录新浪微博！";
//            HsojLogger.log(request, "用微博账号登录失败");
        }
//        setNotice(request, notice);
        return "redirect:" + redirect;

    }

    public void execute(int event, HttpServletRequest request, HttpServletResponse response) {
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
