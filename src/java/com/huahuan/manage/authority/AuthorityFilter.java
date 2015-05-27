package com.huahuan.manage.authority;

import com.huahuan.table.Qxlb;
import com.huahuan.table.Yhb;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jplus.hyb.database.Hyberbin;

/**
 *
 * @author 刘雷
 * @time 2013-3-19 18:38:54
 */
@WebFilter(filterName = "AuthorityFilter", urlPatterns = {"/*"})
public class AuthorityFilter implements Filter {

    private static String basePath = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //cookie实现自动的登录
        Cookie[] cookies = req.getCookies();
        Cookie cookie_yhm_req = null;
        Cookie cookie_mm_req = null;
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (cookies[i].getName().equals("username")) {
                cookie_yhm_req = cookies[i];
            }
            if (cookies[i].getName().equals("password")) {
                cookie_mm_req = cookies[i];
            }
        }
        if ((req.getSession(false) == null || req.getSession(false).getAttribute("yhb") == null) && cookie_yhm_req != null && cookie_yhm_req != null) {
            //cookie值存在，自动登录
            Yhb yhb = new Yhb();
            Hyberbin hyb = new Hyberbin(yhb, true);
            hyb.addParmeter(cookie_yhm_req.getValue()).addParmeter(cookie_mm_req.getValue());
            yhb = hyb.showOne("select * from yhb where yhm=? and mm=?");
            if (yhb != null && yhb.getId() != null) {
                SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String composetime = ss.format(new java.util.Date());
                Timestamp sj = Timestamp.valueOf(composetime);
                hyb.clearParmeter();
                hyb.addParmeter(sj);
                hyb.addParmeter(yhb.getId());
                hyb.update("update yhb set zhdlsj=? where id=?");
                HttpSession session = req.getSession(true);
                session.setAttribute("yhb", yhb);
                Qxlb qxlb = new Qxlb();
                hyb.changeTable(qxlb);
                qxlb.setQxid(yhb.getQx());
                hyb.showOnebyKey("qxid");
                session.setAttribute("yhqxmc", qxlb.getQxmc());
                session.setMaxInactiveInterval(3600);//表示session生命期1小时
            }
            hyb.reallyClose();
        }
        if (basePath == null) {
            basePath = req.getContextPath();
        }
        String url = req.getServletPath();//地址栏里面的url
        if ("/manage/SthdPicManageAction.jsp".equals(url)) {
            chain.doFilter(request, response);
        }
        //以下代码实现对恶意进入管理页面的行为进行拦截
        //判断是否登录
        HttpSession session = req.getSession(false);
        if (url.startsWith("/manage/") && session == null) {
            res.sendRedirect(basePath + "/web/login/login.jsp");
            return;
        }
        if (session != null) {
            Yhb yhb = (Yhb) session.getAttribute("yhb");
            if (url.startsWith("/manage/") && yhb == null) {
                res.sendRedirect(basePath + "/web/login/login.jsp");
                return;
            }
            //通过用户的qx判断授权页面
            if (yhb != null) {
                Integer qx = yhb.getQx();
                if (url.startsWith("/manage/") && qx > 3) {
                    res.sendRedirect(basePath + "/web/login/login.jsp");
                } else {
                    if (url.startsWith("/manage/") && qx == 1) {
                        chain.doFilter(request, response);
                        return;
                    } else if (url.startsWith("/manage/") && qx == 2 && url.contains("main.jsp")) {
                        res.sendRedirect(basePath + "/manage/main_second.jsp");
                    } else if (url.startsWith("/manage/") && qx == 3 && (url.contains("main.jsp") || url.contains("main_second.jsp"))) {
                        res.sendRedirect(basePath + "/manage/main_third.jsp");
                    }
                }
            }
        }
        chain.doFilter(request, response);//放行
    }

    @Override
    public void destroy() {
    }
}
