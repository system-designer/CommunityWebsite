package com.huahuan.web.blog;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Yhb;
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

/**
 *
 * @author evance
 */
@WebServlet(name = "BlogYhbServlet", urlPatterns = {"/BlogYhbServlet.jsp"})
public class BlogYhbServlet extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;
    public final static int SHOWINFO = 2;
    public final static int UPDATE = 3;

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
                    Logger.getLogger(BlogYhbServlet.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BlogYhbServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case SHOWONE:
                showOne(request, response);
                break;
            case SHOWINFO:
                showInfo(request, response);
                break;
            case UPDATE:
                update(request, response);
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
    private void showOne(HttpServletRequest request, HttpServletResponse response) {
        //显示个人资料
        String yhid = request.getParameter("host");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String sql = "SELECT `yhb`.`yhm`, `yhb`.`id`, `yhb`.`xb`, `yhb`.`csrq`, `yhb`.`zcsj`,`yhb`.`zhdlsj`, `yhb`.`yhtx`, `yhb`.`jf`, `yhb`.`lsjf`,`yhb`.`qx`, `yhb`.`gxqm`,`yhb`.`sfzx`, `ltbk`.`bkmc`, `ltbk`.`bkid`, `qxlb`.`qxmc` FROM `yhb` LEFT JOIN `ltbk` ON `ltbk`.`bz` = `yhb`.`id` INNER JOIN `qxlb` ON `yhb`.`qx` = `qxlb`.`qxid` WHERE `yhb`.`id`=?";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> hostlist = emm.executeQuery(sql);
        request.setAttribute("host", (hostlist.size() > 0) ? hostlist.get(0) : null);
        //访问数
        sql = "SELECT COUNT(*) FROM `blogvisit` WHERE `blogvisit`.`host` = ?";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> visittlist = emm.executeQuery(sql);
        request.setAttribute("visitnum", (visittlist.size() > 0) ? visittlist.get(0).get("visitnum") : null);
        //好友数
        sql = "SELECT COUNT(*) AS friendnum FROM  `blogfriends` WHERE `blogfriends`.`host`=?";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> friendlist = emm.executeQuery(sql);
        request.setAttribute("friendnum", (friendlist.size() > 0) ? friendlist.get(0).get("friendnum") : null);
        //日志数
        sql = "SELECT COUNT(*) AS lognum FROM `bloglog` WHERE `bloglog`.`host`=?";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> loglist = emm.executeQuery(sql);
        request.setAttribute("lognum", (loglist.size() > 0) ? loglist.get(0).get("lognum") : null);
        //回帖数
        sql = "SELECT COUNT(*) AS htnum FROM `htb` WHERE `htb`.`htr`=?";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> htlist = emm.executeQuery(sql);
        request.setAttribute("htnum", (htlist.size() > 0) ? htlist.get(0).get("htnum") : null);
        //回帖(主题)数
        sql = "SELECT COUNT(*) AS ftnum FROM `ftb` WHERE `ftb`.`ftr`=?";
        emm.setPreparedParameter(Integer.parseInt(yhid));
        ArrayList<HashMap> ftlist = emm.executeQuery(sql);
        request.setAttribute("ftnum", (ftlist.size() > 0) ? ftlist.get(0).get("ftnum") : null);
        //显示统计信息
        dao.close();
        try {
            request.getRequestDispatcher("/web/blog/blog_yhb.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(BlogIndexServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogIndexServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        try {
            Yhb yhb = new Yhb();
            ServletUtil.loadByParams(request, yhb, true);
            Hyberbin hyb = new Hyberbin(yhb, true);
            hyb.updateByKey("id");
            yhb = hyb.showOnebyKey("id");
            hyb.reallyClose();
            request.setAttribute("host", yhb);
            request.getRequestDispatcher("/web/blog/blog_personInfo.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }

    private void showInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter("host");
            Yhb yhb = new Yhb();
            yhb.setId(Integer.parseInt(id));
            Hyberbin hyb = new Hyberbin(yhb);
            yhb = hyb.showOnebyKey("id");
            request.setAttribute("host", yhb);
            request.getRequestDispatcher("/web/blog/blog_personInfo.jsp").forward(request, response);
        } catch (Exception ex) {
        }
    }
}
