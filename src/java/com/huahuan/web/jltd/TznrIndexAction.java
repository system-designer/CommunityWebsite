package com.huahuan.web.jltd;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Ftb;
import com.huahuan.table.Htb;
import com.huahuan.table.Yhb;
import com.huahuan.tools.Util;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
 * @author Administrator
 */
@WebServlet(name = "TznrIndexAction", urlPatterns = "/TznrIndexAction.jsp")
public class TznrIndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;//显示单例
    public final static int HT = 3;

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
     * 显示帖子内容
     */
    private void showTznr(HttpServletRequest request, HttpServletResponse response) {
        String ftid = request.getParameter("ftid");
        if (!Util.isInteger(ftid)) {
            ftid = "1";
        }
        request.setAttribute("ftid", Integer.parseInt(ftid));
        String bkid = request.getParameter("bkid");
        if (!Util.isInteger(bkid)) {
            bkid = "1";
        }
        String page = request.getParameter("page");
        if (!Util.isInteger(page)) {
            page = "1";
        }
        int pageSize = 10;
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        //用户每查看一次，主贴的浏览次数加1
        emm.setPreparedParameter(Integer.parseInt(ftid));
        dao.executeUpdate("update ftb set llcs=llcs+1 where ftid=?");
        //主贴信息
        String sql = "SELECT"
                + "`yhb`.`id`, `yhb`.`yhm`, `ftb`.`ftr`, `ftb`.`ftsj`, `ftb`.`tznr`, `ftb`.`tzbt`,"
                + " `ftb`.`bkid`, `ftb`.`ftid`, `ftb`.`sfsh`, `ftb`.`sfjj`, `ftb`.`sfzd`,"
                + " `ftb`.`llcs`, `ftb`.`htzs`, `yhb`.`xb`, `yhb`.`yhtx`, `yhb`.`zcsj`,"
                + " `yhb`.`gxqm`, `yhb`.`jf`, `yhb`.`sfzx`, `yhb`.`qx`"
                + " FROM"
                + " `ftb` INNER JOIN"
                + " `yhb` ON `ftb`.`ftr` = `yhb`.`id` where ftid=?";
        emm.setPreparedParameter(Integer.parseInt(ftid));
        ArrayList<HashMap> zt = emm.executeQuery(sql);
        request.setAttribute("zt", zt.get(0));
        //回帖信息
        sql = "SELECT"
                + " `yhb`.`id`,`yhb`.`yhm`, `yhb`.`xb`, `yhb`.`yhtx`, `yhb`.`zcsj`, `yhb`.`qx`, `yhb`.`gxqm`,"
                + " `yhb`.`jf`, `yhb`.`sfzx`, `htb`.`htr`, `htb`.`htsj`, `htb`.`htnr`,"
                + " `htb`.`ftid`, `htb`.`htid`, `htb`.`sfsh`"
                + " FROM"
                + " `htb` INNER JOIN"
                + " `yhb` ON `htb`.`htr` = `yhb`.`id` where ftid=? and sfsh=1";
        emm.setPreparedParameter(Integer.parseInt(ftid));
        String range = " limit " + (Integer.parseInt(page) - 1) * pageSize + ", " + pageSize;
        ArrayList<HashMap> htlist = emm.executeQuery(sql + range);
        request.setAttribute("htlist", htlist);
        //分页信息
        emm.setPreparedParameter(Integer.parseInt(ftid));
        int total = emm.executeQuery(sql).size();
        int totalpages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        request.setAttribute("totalpages", totalpages);
        request.setAttribute("page", Integer.parseInt(page));
        //存放板块信息
        request.setAttribute("bkid", Integer.parseInt(bkid));
        emm.setPreparedParameter(Integer.parseInt(bkid));
        request.setAttribute("bkmc", emm.executeQuery("select bkid,bkmc from ltbk where bkid=?").get(0).get("bkmc").toString());
        dao.close();
    }

    /**
     * 加载论坛版块的下拉框
     */
    private void showbklb(HttpServletRequest request, HttpServletResponse response) {
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        ArrayList<HashMap> bklist = emm.executeQuery("select bkid,bkmc from ltbk");
        dao.close();
        request.setAttribute("bklist", bklist);
    }

    /**
     * 用户回帖操作
     */
    private void ht(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String code = request.getParameter("code");
        if (!code.equals(session.getAttribute("code").toString())) {
            return;
        }
        Yhb yhb = (Yhb) session.getAttribute("yhb");
        Htb htb = new Htb();
        ServletUtil.loadByBean(request, htb, true);
        Hyberbin hyb = new Hyberbin(htb, true);
        htb.setHtr(yhb.getId());
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        htb.setHtsj(sj);
        hyb.insert("htid");
        //增加用户的积分，每回一次贴加1分
        hyb.changeTable(yhb);
        hyb.addParmeter(yhb.getId());
        hyb.update("update yhb set jf=jf+1 where id=?");
        //增加主贴的回帖总数
        Ftb ftb = new Ftb();
        hyb.changeTable(ftb);
        hyb.addParmeter(htb.getFtid());
        hyb.update("update ftb set htzs=htzs+1 where ftid=?");
        hyb.reallyClose();
        showbklb(request, response);
        showTznr(request, response);
        String bkid = request.getParameter("bkid");
        try {
            response.sendRedirect(request.getContextPath() + "/TznrIndexAction.jsp?mode=SHOWONE&ftid=" + htb.getFtid() + "&bkid=" + Integer.parseInt(bkid));
        } catch (IOException ex) {
            Logger.getLogger(TznrIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 实现父类的抽象方法，下面的模式和方法可以自行增删
     */
    public void execute(int event, HttpServletRequest request, HttpServletResponse response) {
        /**
         * 下面是相关模式下所做的动作*
         */
        switch (event) {
            case SHOWONE:
                showbklb(request, response);
                showTznr(request, response);
                try {
                    request.getRequestDispatcher("/web/jltd/nr.jsp").forward(request, response);
                } catch (ServletException ex) {
                    Logger.getLogger(TznrIndexAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TznrIndexAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case HT:
                ht(request, response);
                break;
        }
    }
}
