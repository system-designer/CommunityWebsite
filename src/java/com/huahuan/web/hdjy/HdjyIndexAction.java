/*
 *这个是黄迎斌的servlet框架
 * http://opdps.hbnu.edu.cn/hyberbin
 */
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
@WebServlet(name = "HdjyIndexAction", urlPatterns = "/HdjyIndexAction.jsp")
public class HdjyIndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWHDLIST = 3;//显示活动封面列表

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
     * 得到活动类别的列表,这个只在第一次进入页面时需要存放
     */
    private ArrayList<HashMap> showhdlblist() {
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String sql = "select lbid,lbmc from hdlb";
        ArrayList<HashMap> hdlblist = emm.executeQuery(sql);
        return hdlblist;
    }

    /**
     * 活动剪影页面的翻页操作
     */
    private void showhdlist(HttpServletRequest request, HttpServletResponse response) {
        String index = request.getParameter("index");
        String hdlb = request.getParameter("hdlb");
        if (Util.isEmpty(hdlb) || !Util.isInteger(hdlb)) {
            hdlb = "1";
        }
        String page = request.getParameter("page");
        if (Util.isEmpty(page) || !Util.isInteger(page)) {
            page = "1";
        }
        int pageSize = 9;
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String where = "";
        String searchName = request.getParameter("searchName");
        String searchValue = request.getParameter("searchValue");
        if (!Util.isEmpty(searchValue) && !Util.isEmpty(searchName)) {//查询
            where = " where " + searchName + " like ? order by hdid desc";
            emm.setPreparedParameter("%" + searchValue + "%");
        } else {
            where = " where hdlb=? order by hdid desc";
            emm.setPreparedParameter(Integer.parseInt(hdlb));
        }
        String sql = "select hdid,hdbt,hdsj,hdlb,hdtp from sthd";
        String range = " limit " + (Integer.parseInt(page) - 1) * pageSize + "," + pageSize;
        ArrayList<HashMap> hdlist = emm.executeQuery(sql + where + range);
        //存放分页的信息
        if (!Util.isEmpty(searchValue) && !Util.isEmpty(searchName)) {
            where = " where " + searchName + " like ?";
            emm.setPreparedParameter("%" + searchValue + "%");
        } else {
            where = " where hdlb=?";
            emm.setPreparedParameter(Integer.parseInt(hdlb));
        }
        int total = emm.executeQuery(sql + where).size();
        int totalpages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        request.setAttribute("totalpages", totalpages);
        request.setAttribute("page", Integer.parseInt(page));
        //存放活动类别的信息
        request.setAttribute("hdlb", Integer.parseInt(hdlb));
        emm.setPreparedParameter(Integer.parseInt(hdlb));
        request.setAttribute("lbmc", emm.executeQuery("select lbid,lbmc from hdlb where lbid=?").get(0).get("lbmc").toString());
        emm.setPreparedParameter(Integer.parseInt(hdlb));
        request.setAttribute("hdlist", hdlist);
        ArrayList<HashMap> hdlblist = showhdlblist();
        request.setAttribute("hdlblist", hdlblist);
        if (index != null) {
            if (index.equals("index")) {
                request.setAttribute("hdid", Integer.parseInt(request.getParameter("hdid")));
            }
        }
        dao.close();
        try {
            request.getRequestDispatcher("/web/hdjy/hdjy.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(HdjyIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HdjyIndexAction.class.getName()).log(Level.SEVERE, null, ex);
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
            case SHOWHDLIST:
                showhdlist(request, response);
                break;
        }
    }
}
