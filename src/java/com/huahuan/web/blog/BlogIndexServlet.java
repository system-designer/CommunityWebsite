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

/**
 *
 * @author evance
 */
@WebServlet(name = "BlogIndexServlet", urlPatterns = {"/BlogIndexServlet.jsp"})
public class BlogIndexServlet extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWALL = 1;//显示所有信息

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
                    Logger.getLogger(BlogIndexServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BlogIndexServlet.class.getName()).log(Level.SEVERE, null, ex);
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
     * 显示所有信息
     */
    private void showAll(HttpServletRequest request, HttpServletResponse response) {
        //显示个人资料
        String yhid = request.getParameter("host");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String sql = "SELECT `yhb`.`yhm`, `yhb`.`id`, `yhb`.`xb`, `yhb`.`csrq`, `yhb`.`zcsj`,`yhb`.`zhdlsj`,`yhb`.`sfjh`, `yhb`.`yhtx`, `yhb`.`jf`, `yhb`.`qx`, `yhb`.`gxqm`,`yhb`.`sfzx` FROM `yhb`";
        ArrayList<HashMap> hostlist = emm.executeQuery(sql);
        request.setAttribute("host", hostlist.get(0));
        //显示日志信息
        sql = "SELECT `bloglog`.`top`, `bloglog`.`title`, `bloglog`.`host`, `bloglog`.`pubtime`,`bloglog`.`lid` FROM `bloglog` WHERE `bloglog`.`host`=? LIMIT 0,2";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> loglist = emm.executeQuery(sql);
        request.setAttribute("hostlog", (loglist.size() > 0) ? loglist : null);
        //显示留言信息
        sql = "SELECT `blogleave`.`content`, `blogleave`.`leavetime`, `blogleave`.`host`,`blogleave`.`lid`, `yhb`.`yhm` FROM `blogleave` INNER JOIN `yhb` ON `blogleave`.`from` = `yhb`.`id` WHERE `blogleave`.`host`=? LIMIT 0,1";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> leavelist = emm.executeQuery(sql);
        request.setAttribute("hostleave", (leavelist.size() > 0) ? leavelist : null);
        //显示好友信息
        sql = "SELECT `blogfriends`.`friend`, `yhb`.`yhm`, `blogfriends`.`host`, `yhb`.`yhtx` FROM `blogfriends` INNER JOIN `yhb` ON `blogfriends`.`friend` = `yhb`.`id` WHERE `blogfriends`.`host`=? LIMIT 0,6";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> friendlist = emm.executeQuery(sql);
        request.setAttribute("hostfriend", (friendlist.size() > 0) ? friendlist : null);
        //显示访客信息
        sql = "SELECT `yhb`.`yhm`, `blogvisit`.`vid`, `blogvisit`.`host`, `blogvisit`.`visitor`,`blogvisit`.`visittime` FROM `yhb` INNER JOIN `blogvisit` ON `blogvisit`.`visitor` = `yhb`.`id` WHERE `blogvisit`.`host`=? LIMIT 0,6";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> visitlist = emm.executeQuery(sql);
        request.setAttribute("hostvisit", (visitlist.size() > 0) ? visitlist : null);
        //显示动态（帖子）信息
        sql = "SELECT `ftb`.`tzbt`, `ftb`.`ftid`, `ftb`.`ftsj`, `bklb`.`lbmc` FROM `yhb` INNER JOIN `ftb` ON `ftb`.`ftr` = `yhb`.`id` INNER JOIN `bklb` ON `ftb`.`bkid` = `bklb`.`lbid` WHERE `yhb`.`id`=? ORDER BY `ftb`.`ftsj` DESC LIMIT 0,2";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> posttlist = emm.executeQuery(sql);
        request.setAttribute("hostpost", (posttlist.size() > 0) ? posttlist : null);
        //最近访客信息
        sql = "SELECT DISTINCT `blogvisit`.`host`, `blogvisit`.`visitor`, `yhb`.`yhm`,`yhb`.`yhtx`,`blogvisit`.`visittime`, `blogvisit`.`action` FROM `yhb` INNER JOIN `blogvisit` ON `blogvisit`.`visitor` = `yhb`.`id` WHERE `blogvisit`.`host` = ? ORDER BY `blogvisit`.`visittime` DESC LIMIT 0, 6";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> visittlist = emm.executeQuery(sql);
        request.setAttribute("hostvisit", (visittlist.size() > 0) ? visittlist : null);
        //显示统计信息
        dao.close();
        try {
            request.getRequestDispatcher("/web/blog/blog_index.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(BlogIndexServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogIndexServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
