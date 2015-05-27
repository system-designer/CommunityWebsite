package com.huahuan.web.stgg;

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
 * @author Administrator
 */
@WebServlet(name = "ShowStggAction", urlPatterns = "/ShowStggAction.jsp")
public class ShowStggAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;//显示单例

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
     * 显示单个的社团公告
     */
    private void showone(HttpServletRequest request, HttpServletResponse response) {
        String ggid = request.getParameter("ggid");
        String gglb = request.getParameter("gglb");//公告类别
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String sql = "select * from"
                + "(SELECT"
                + " `gglb`.`lbid` AS `gglb`, `gglb`.`lbmc`, `stgg`.`ggr`, `stgg`.`ggsj`,"
                + " `stgg`.`ggnr`, `stgg`.`ggid`, `stgg`.`ggbt`, `hyb`.`xm`"
                + " FROM"
                + " `hyb` INNER JOIN"
                + " `stgg` ON `stgg`.`ggr` = `hyb`.`id` INNER JOIN"
                + " `gglb` ON `gglb`.`lbid` = `stgg`.`gglb`"
                + ")as a"
                + " where gglb=? and ggid=?";
        emm.setPreparedParameter(Integer.parseInt(gglb));
        emm.setPreparedParameter(Integer.parseInt(ggid));
        ArrayList<HashMap> gg = emm.executeQuery(sql);
        if (gg.size() > 0) {//如果不判断get方法会报错
            request.setAttribute("gg", gg.get(0));
        }
        dao.close();
        try {
            request.getRequestDispatcher("/web/stgg/ggnr.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ShowStggAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShowStggAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 下面的模式和方法可以自行增删
     */
    public void execute(int event, HttpServletRequest request, HttpServletResponse response) {
        /**
         * 下面是相关模式下所做的动作*
         */
        switch (event) {
            case SHOWONE:
                showone(request, response);
                break;
        }
    }
}
