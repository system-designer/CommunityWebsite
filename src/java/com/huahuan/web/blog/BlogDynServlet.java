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
@WebServlet(name = "BlogDynServlet", urlPatterns = {"/BlogDynServlet.jsp"})
public class BlogDynServlet extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWALL = 1;//显示列表

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
                    Logger.getLogger(BlogDynServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BlogDynServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case SHOWALL:
                showAll(request, response);
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
    private void showAll(HttpServletRequest request, HttpServletResponse response) {
        try {
            //显示空间主人的个人资料
            int host = Integer.parseInt(request.getParameter("host"));
            DatabaseAccess dao = new DatabaseAccess();
            EasyMapsManager emm = new EasyMapsManager(dao);
            String sql = "SELECT `yhb`.`yhm`, `yhb`.`id`, `yhb`.`yhtx`, `yhb`.`qx` FROM `yhb` WHERE `yhb`.`id`=?";
            emm.setPreparedParameter(host);
            ArrayList<HashMap> hostlist = emm.executeQuery(sql);
            request.setAttribute("host", hostlist.get(0));
            //空间主人的日志信息
            sql = "SELECT `bloglog`.`top`, `bloglog`.`title`, `bloglog`.`host`, `bloglog`.`pubtime`,`bloglog`.`lid` FROM `bloglog` WHERE `bloglog`.`host`=? LIMIT 0,2";
            emm.setPreparedParameter(host);
            ArrayList<HashMap> hostloglist = emm.executeQuery(sql);
            request.setAttribute("hostloglist", (hostloglist.size() > 0) ? hostloglist : null);
            //主人的发帖信息
            sql = "SELECT `ftb`.`bkid`, `ltbk`.`bkmc`, `ftb`.`tzbt`, `ftb`.`ftid`, `ftb`.`tznr`,`ftb`.`ftsj`, `ftb`.`ftr` FROM `ftb` INNER JOIN `ltbk` ON `ftb`.`bkid` = `ltbk`.`bkid` WHERE `ftb`.`ftr` = ? ORDER BY `ftb`.`ftsj` DESC LIMIT 0,1";
            emm.setPreparedParameter(host);
            ArrayList hostpostlist = emm.executeQuery(sql);
            request.setAttribute("hostpostlist", (hostpostlist.size() > 0 ? hostpostlist : null));
            dao.close();
            request.getRequestDispatcher("/web/blog/blog_dyn.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }
}
