package com.huahuan.web.blog;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Bloglog;
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
import org.jplus.hyb.database.Hyberbin;
import org.jplus.util.Pagger;

/**
 *
 * @author hp
 */
@WebServlet(name = "BlogLogServlet", urlPatterns = {"/BlogLogServlet.jsp"})
public class BlogLogServlet extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLOGLIST = 1;//显示列表
    public final static int SHOWONE = 2;
    public final static int UPDATE = 3;
    public final static int DELETE = 4;
    public final static int INSERT = 5;
    public final static int SHOWPOSTLIST = 6;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("textml;charset=UTF-8");
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
                    Logger.getLogger(BlogLogServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BlogLogServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case SHOWLOGLIST:
                showLogList(request, response);
                break;
            case SHOWONE:
                showOne(request, response);
                break;
            case UPDATE:
                updateLog(request, response);
                break;
            case DELETE:
                deleteLog(request, response);
                break;
            case INSERT:
                insertLog(request, response);
                break;
            case SHOWPOSTLIST:
                showpostlist(request, response);
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
    private void showLogList(HttpServletRequest request, HttpServletResponse response) {
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
            Pagger pagger = new Pagger(15);
            ServletUtil.loadByParams(request, pagger, true);
            sql = "select * from bloglog where host=?";
            emm.setPreparedParameter(host);
            ArrayList hostloglist = emm.executeQuery(sql, pagger);
            dao.close();
            request.setAttribute("pagger", pagger);
            request.setAttribute("hostloglist", hostloglist);
            request.getRequestDispatcher("/web/blog/blog_log.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }

    private void showpostlist(HttpServletRequest request, HttpServletResponse response) {
        try {
            //显示空间主人的个人资料
            int host = Integer.parseInt(request.getParameter("host"));
            request.setAttribute("host", request.getParameter("host"));
            DatabaseAccess dao = new DatabaseAccess();
            EasyMapsManager emm = new EasyMapsManager(dao);
            String sql = "SELECT `yhb`.`yhm`, `yhb`.`id`, `yhb`.`yhtx`, `yhb`.`qx` FROM `yhb` WHERE `yhb`.`id`=?";
            emm.setPreparedParameter(host);
            ArrayList<HashMap> hostlist = emm.executeQuery(sql);
            request.setAttribute("host", hostlist.get(0));
            //空间主人的发帖（主题）信息
            Pagger pagger = new Pagger(15);
            ServletUtil.loadByParams(request, pagger, true);
            sql = "SELECT `ftb`.`bkid`, `ltbk`.`bkmc`, `ftb`.`tzbt`, `ftb`.`ftid`, `ftb`.`tznr`,`ftb`.`ftsj`, `ftb`.`ftr` FROM `ftb` INNER JOIN `ltbk` ON `ftb`.`bkid` = `ltbk`.`bkid` WHERE `ftb`.`ftr` = ? ORDER BY `ftb`.`ftsj` DESC";
            emm.setPreparedParameter(host);
            ArrayList hostpostlist = emm.executeQuery(sql, pagger);
            request.setAttribute("hostpostlist", hostpostlist);
            dao.close();
            request.getRequestDispatcher("/web/blog/blog_post.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }

    private void showOne(HttpServletRequest request, HttpServletResponse response) {
        int lid = Integer.parseInt(request.getParameter("lid"));
        Bloglog bl = new Bloglog();
        bl.setLid(lid);
        Hyberbin hyberbin = new Hyberbin(bl);
        Bloglog bloglog = hyberbin.showOnebyKey("lid");
        request.setAttribute("bloglog", bloglog);
    }

    private void updateLog(HttpServletRequest request, HttpServletResponse response) {
        int lid = Integer.parseInt(request.getParameter("lid"));
        Bloglog bl = new Bloglog();
        Hyberbin hyberbin = new Hyberbin(bl);
        ServletUtil.loadByBean(request, bl, true);
        hyberbin.updateByKey("lid");
    }

    private void deleteLog(HttpServletRequest request, HttpServletResponse response) {
        int lid = Integer.parseInt(request.getParameter("lid"));
        Bloglog bl = new Bloglog();
        bl.setLid(lid);
        Hyberbin hyberbin = new Hyberbin(bl);
        hyberbin.addParmeter(lid);
        hyberbin.dellOneByKey("lid");
    }

    private void insertLog(HttpServletRequest request, HttpServletResponse response) {
        Bloglog bl = new Bloglog();
        Hyberbin hyberbin = new Hyberbin(bl);
        ServletUtil.loadByBean(request, bl, true);
        hyberbin.updateByKey("lid");
    }
}
