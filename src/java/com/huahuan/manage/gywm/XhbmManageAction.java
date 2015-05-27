package com.huahuan.manage.gywm;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Stbm;
import com.huahuan.tools.Util;
import com.jplus.json.EasyUiJson;
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
@WebServlet(name = "XhbmManageAction", urlPatterns = "/manage/XhbmManageAction.jsp")
public class XhbmManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLIST = 2;//显示列表
    public final static int ADD = 3;//添加记录
    public final static int UPDATE = 4;//更新记录
    public final static int DELETE = 6;//删除记录
    
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
     * 显示协会部门列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        Stbm stbm = new Stbm();
        Hyberbin hyb = new Hyberbin(stbm);
        List list;
        EasyUiJson easyui = new EasyUiJson(request);
        String where = "";
        if (Util.isEmpty(searchValue)) {
            list = hyb.showByMySqlPage(where, easyui);
        } else {
            where = " where " + searchName + " like ?";
            hyb.addParmeter("%" + searchValue + "%");
            list = hyb.showByMySqlPage(where, easyui);
        }
        easyui.putAll(list);
        ServletUtil.ajaxData(easyui.toDataString(), response);
    }

    /**
     * 删除部门
     */
    private void deleteBm(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Stbm stbm = new Stbm();
        Hyberbin hyberbin = new Hyberbin(stbm);
        String[] nums = num.split(":");
        String sql = Util.getDeleteSql(hyberbin, nums, "bmid");
        boolean b = hyberbin.dell("where" + sql);
        String res = b ? "1" : "0";
        ServletUtil.ajaxData(res, response, "html");
    }

    /**
     * 添加协会部门
     */
    private void addBm(HttpServletRequest request, HttpServletResponse response) {
        Stbm stbm = new Stbm();
        Hyberbin hyberbin = new Hyberbin(stbm);
        ServletUtil.loadByBean(request, stbm, true);
        boolean b = hyberbin.insert("bmid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 修改协会成员
     */
    private void updateBm(HttpServletRequest request, HttpServletResponse response) {
        Stbm stbm = new Stbm();//社团部门表
        Hyberbin hyberbin = new Hyberbin(stbm);
        ServletUtil.loadByBean(request, stbm, true);
        boolean b = hyberbin.updateByKey("bmid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 实现父类的抽象方法，下面的模式和方法可以自行增删
     */
    public void execute(int event, HttpServletRequest request, HttpServletResponse response) {
        /**
         * 下面是相关模式下所做的动作*
         */
        switch (event) {
            case SHOWLIST:
                showlist(request, response);
                break;
            case ADD:
                addBm(request, response);
                break;
            case UPDATE:
                updateBm(request, response);
                break;
            case DELETE:
                deleteBm(request, response);
                break;
        }
    }
}
