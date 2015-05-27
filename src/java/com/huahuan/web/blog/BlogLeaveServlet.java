package com.huahuan.web.blog;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jplus.util.Pagger;

/**
 *
 * @author evance
 */
@WebServlet(name = "BlogLeaveServlet", urlPatterns = {"/BlogLeaveServlet.jsp"})
public class BlogLeaveServlet extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLEAVELIST = 1;//显示列表

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        excute(ServletUtil.setModel(request.getParameter("mode"), this), request, response);
    }

    /**
     * 主执行方法
     *
     * @param event 方法ID
     */
    private void excute(int event, HttpServletRequest request, HttpServletResponse response) {
        switch (event) {
            case OTHER:
                try {
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                } catch (ServletException ex) {
                    Logger.getLogger(BlogLeaveServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BlogLeaveServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case SHOWLEAVELIST:
                showLeaveList(request, response);
                break;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * 显示列表
     */
    private void showLeaveList(HttpServletRequest request, HttpServletResponse response) {
        try {
            //显示空间主人的个人资料
            int host = Integer.parseInt(request.getParameter("host"));
            DatabaseAccess dao = new DatabaseAccess();
            EasyMapsManager emm = new EasyMapsManager(dao);
            String sql = "SELECT `yhb`.`yhm`, `yhb`.`id`, `yhb`.`yhtx`, `yhb`.`qx` FROM `yhb` WHERE `yhb`.`id`=?";
            emm.setPreparedParameter(host);
            ArrayList<HashMap> hostlist = emm.executeQuery(sql);
            request.setAttribute("host", hostlist.get(0));
            //空间主人的留言信息
            Pagger pagger = new Pagger(15);
            ServletUtil.loadByParams(request, pagger, true);
            sql = "SELECT `blogleave`.`lid`,`blogleave`.`from`, `blogleave`.`content`, `blogleave`.`leavetime`,`yhb`.`yhm`, `yhb`.`yhtx` FROM`yhb` INNER JOIN `blogleave` ON `blogleave`.`from` = `yhb`.`id` WHERE `blogleave`.`host`=?";
            emm.setPreparedParameter(host);
            ArrayList hostleavelist = emm.executeQuery(sql, pagger);
            dao.close();
            request.setAttribute("pagger", pagger);
            request.setAttribute("hostleavelist", hostleavelist);
            request.getRequestDispatcher("/web/blog/blog_leave.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }
}
