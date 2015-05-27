package com.huahuan.web.hdjy;

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
@WebServlet(name = "ShowHdjyIndexAction", urlPatterns = "/ShowHdjyIndexAction.jsp")
public class ShowHdjyIndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWHDJYLIST = 2;//显示活动剪影列表

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
     * 显示一个活动的剪影列表
     */
    private void showhdjylist(HttpServletRequest request, HttpServletResponse response) {
        String hdid = request.getParameter("hdid");//通过活动id来查找这个活动的剪影列表
        String page = request.getParameter("page");
        if (Util.isEmpty(page) || !Util.isInteger(page)) {
            page = "1";
        }
        int pageSize = 9;
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String sql = "select hdid,jyid,jybt,jytp from hdjy where hdid=?";
        String range = " limit " + (Integer.parseInt(page) - 1) * pageSize + ", " + pageSize;
        emm.setPreparedParameter(Integer.parseInt(hdid));
        ArrayList<HashMap> hdjylist = emm.executeQuery(sql + range);
        request.setAttribute("hdid", Integer.parseInt(hdid));
        emm.setPreparedParameter(Integer.parseInt(hdid));
        int total = emm.executeQuery(sql).size();
        int totalpages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        request.setAttribute("totalpages", totalpages);
        request.setAttribute("page", Integer.parseInt(page));
        request.setAttribute("hdjylist", hdjylist);
        dao.close();
        try {
            request.getRequestDispatcher("/web/hdjy/jynr.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(ShowHdjyIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ShowHdjyIndexAction.class.getName()).log(Level.SEVERE, null, ex);
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
            case SHOWHDJYLIST:
                showhdjylist(request, response);
                break;
        }
    }
}
