/*
 *这个是黄迎斌的servlet框架
 * http://opdps.hbnu.edu.cn/hyberbin
 */
package com.huahuan.manage.jltd;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Htb;
import com.huahuan.table.View_htb;
import com.huahuan.tools.CutJsonString;
import com.huahuan.tools.Util;
import com.jplus.json.EasyUiJson;
import com.jplus.json.JSONArray;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jplus.hyb.database.Hyberbin;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "HtglManageAction", urlPatterns = "/manage/HtglManageAction.jsp")
public class HtglManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;//显示单例
    public final static int SHOWLIST = 2;//显示列表
    public final static int UPDATE = 4;//更新记录
    public final static int DELETE = 6;//删除记录
    public final static int SH = 7;
    public final static int SHTG = 8;

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
     * 显示单个回帖
     */
    private void showone(HttpServletRequest request, HttpServletResponse response) {
        String htid = request.getParameter("htid");
        View_htb view_htb = new View_htb();
        Hyberbin hyb = new Hyberbin(view_htb);
        hyb.addParmeter(Integer.parseInt(htid));
        List list = hyb.showList("select * from View_htb where htid=?");
        JSONArray json = new JSONArray();
        json.put(list);
        ServletUtil.ajaxData(CutJsonString.cutObjectJson(json.toString()), response);
    }

    /**
     * 显示回帖信息list
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_htb htb = new View_htb();
        Hyberbin hyb = new Hyberbin(htb);
        List list;
        EasyUiJson easyui = new EasyUiJson(request);
        String where = "";
        if (Util.isEmpty(searchValue)) {
            list = hyb.showByMySqlPage(where + " order by ftid desc,htid desc", easyui);
        } else {
            where = " where " + searchName + " like ?";
            hyb.addParmeter("%" + searchValue + "%");
            list = hyb.showByMySqlPage(where + " order by ftid desc,htid desc", easyui);
        }
        easyui.putAll(list);
        ServletUtil.ajaxData(easyui.toDataString(), response);

    }

    /**
     * 删除一个回帖
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Htb htb = new Htb();
        Hyberbin hyb = new Hyberbin(htb);
        String[] nums = num.split(":");
        String htsql = Util.getDeleteSql(hyb, nums, "htid");
        boolean b = hyb.dell("where" + htsql);
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 修改回帖信息
     */
    private void update(HttpServletRequest request, HttpServletResponse response) {
        Htb htb = new Htb();
        Hyberbin hyb = new Hyberbin(htb);
        ServletUtil.loadByBean(request, htb, true);
        boolean b = hyb.updateByKey("htid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");

    }

    /**
     * 审核未通过
     */
    private void sh(HttpServletRequest request, HttpServletResponse response) {
        String htid = request.getParameter("htid");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(htid));
        boolean b = dao.executeUpdate("update htb set sfsh=0 where htid=?");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
        dao.close();
    }

    /**
     * 审核通过
     */
    private void shtg(HttpServletRequest request, HttpServletResponse response) {
        String htid = request.getParameter("htid");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(htid));
        boolean b = dao.executeUpdate("update htb set sfsh=1 where htid=?");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
        dao.close();
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
                showone(request, response);
                break;
            case SHOWLIST:
                showlist(request, response);
                break;
            case DELETE:
                delete(request, response);
                break;
            case UPDATE:
                update(request, response);
                break;
            case SH:
                sh(request, response);
                break;
            case SHTG:
                shtg(request, response);
                break;
        }
    }
}
