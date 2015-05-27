package com.huahuan.web.stgg;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.tools.Util;
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
@WebServlet(name = "StggIndexAction", urlPatterns = "/StggIndexAction.jsp")
public class StggIndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWGGLIST = 2;//显示公告列表

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
     * 显示公告类别列表
     */
    private ArrayList<HashMap> showgglblist() {
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String sql = "select lbid,lbmc from gglb";
        ArrayList<HashMap> gglblist = emm.executeQuery(sql);
        dao.close();
        return gglblist;
    }

    /**
     * 显示公告列表
     */
    private void showgglist(HttpServletRequest request, HttpServletResponse response) {
        String index = request.getParameter("index");
        String gglb = request.getParameter("gglb");
        String page = request.getParameter("page");
        if (Util.isEmpty(page) || !Util.isInteger(page)) {
            page = "1";
        }
        if (Util.isEmpty(gglb) || !Util.isInteger(gglb)) {
            gglb = "1";
        }
        int pageSize = 10;
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String where = "";
        String searchName = request.getParameter("searchName");
        String searchValue = request.getParameter("searchValue");
        if (!Util.isEmpty(searchValue) && !Util.isEmpty(searchName)) {
            where = " where " + searchName + " like ? order by ggid desc";
            emm.setPreparedParameter("%" + searchValue + "%");
        } else {
            where = " where gglb=? order by ggid desc";
            emm.setPreparedParameter(Integer.parseInt(gglb));
        }
        String sql = "select ggid,ggbt,ggsj,gglb from stgg";
        String range = " limit " + (Integer.parseInt(page) - 1) * pageSize + ", " + pageSize;
        ArrayList<HashMap> gglist = emm.executeQuery(sql + where + range);
        //分别存放公告类别和类别名称信息
        request.setAttribute("gglb", Integer.parseInt(gglb));
        emm.setPreparedParameter(Integer.parseInt(gglb));
        request.setAttribute("lbmc", emm.executeQuery("select lbid,lbmc from gglb where lbid=?").get(0).get("lbmc").toString());
        if (!Util.isEmpty(searchValue) && !Util.isEmpty(searchName)) {//查询
            where = " where " + searchName + " like ?";
            emm.setPreparedParameter("%" + searchValue + "%");
        } else {
            where = " where gglb=?";
            emm.setPreparedParameter(Integer.parseInt(gglb));
        }
        int total = emm.executeQuery(sql + where).size();//分页
        int totalpages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        request.setAttribute("totalpages", totalpages);
        request.setAttribute("page", Integer.parseInt(page));
        ArrayList<HashMap> gglblist = showgglblist();
        request.setAttribute("gglblist", gglblist);
        request.setAttribute("gglist", gglist);
        if (index != null) {
            if (index.equals("index")) {
                request.setAttribute("ggid", Integer.parseInt(request.getParameter("ggid")));
            }
        }
        dao.close();
        try {
            request.getRequestDispatcher("/web/stgg/stgg.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(StggIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StggIndexAction.class.getName()).log(Level.SEVERE, null, ex);
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
            case SHOWGGLIST:
                showgglist(request, response);
                break;
        }
    }
}
