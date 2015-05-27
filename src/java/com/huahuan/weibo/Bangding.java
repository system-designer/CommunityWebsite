package com.huahuan.weibo;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Yhb;
import com.huahuan.tools.MD5;
import com.huahuan.tools.UserUtil;
import java.io.IOException;
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
@WebServlet(name = "Bangding", urlPatterns = "/Bangding.jsp")
public class Bangding extends HttpServlet {

    public final static int OTHER = 0;//其它

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

    public String bangding(HttpServletRequest request, HttpServletResponse response) {
        String notice = null;
        HttpSession session = request.getSession();
        Yhb weibo = (Yhb) session.getAttribute("weibo");
        if (weibo != null) {
            Yhb yhb = new Yhb();
            ServletUtil.loadByParams(request, yhb, true);
            Hyberbin hyb = new Hyberbin(yhb);
            String pass = MD5.jplusMd5(yhb.getMm());
            hyb.addParmeter(yhb.getYhm()).addParmeter(yhb.getYhm()).addParmeter(yhb.getYhm()).addParmeter(pass);
            hyb.showOne("select * from yhb where (yhm=? or yx=? or xh=? ) and yhmm=?");
            if (yhb.getId() == null) {
                notice = "用户名密码不正确！";
//                    setNotice(request, notice);
                return "redirect:user/bangding.jsp";
            } else {
                if (weibo.getQqid() == null && weibo.getSinaid() != null) {
                    boolean sinaToYhb = UserUtil.sinaToYhb(yhb, weibo);
                    notice = "绑定" + (sinaToYhb ? "成功！" : "失败！");
                } else if (weibo.getQqid() != null && weibo.getSinaid() == null) {
                    boolean qqToYhb = UserUtil.qqToYhb(yhb, weibo);
                    notice = "绑定" + (qqToYhb ? "成功！" : "失败！");
                }
//                    HsojLogger.log(request, "["+yhb.getYhm()+"--"+weibo.getYhm()+"]["+notice+"]");
                session.removeAttribute("user");
                session.setAttribute("user", yhb);
//                org.jplus.hyb.cache.CacheFactory.MINSTANCE.clear("yhb");
            }
        } else {
            notice = "验证码不正确！";
        }
        return "redirect:index.jsp";
    }

    public void execute(int event, HttpServletRequest request, HttpServletResponse response) {
        bangding(request, response);
    }
}
