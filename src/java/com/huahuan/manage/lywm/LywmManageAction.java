/*
 *这个是黄迎斌的servlet框架
 * http://opdps.hbnu.edu.cn/hyberbin
 */
package com.huahuan.manage.lywm;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Lyb;
import com.huahuan.table.View_lyb;
import com.huahuan.table.Yhb;
import com.huahuan.tools.Util;
import com.jplus.json.EasyUiJson;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
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
@WebServlet(name = "LywmManageAction", urlPatterns = "/manage/LywmManageAction.jsp")
public class LywmManageAction extends HttpServlet {

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
     * 显示留言列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_lyb lyb = new View_lyb();
        Hyberbin hyb = new Hyberbin(lyb);
        List list;
        EasyUiJson easyui = new EasyUiJson(request);
        String where = "";
        if (Util.isEmpty(searchValue)) {
            list = hyb.showByMySqlPage(where + " order by lyid desc", easyui);
        } else {
            where = " where " + searchName + " like ?";
            hyb.addParmeter("%" + searchValue + "%");
            list = hyb.showByMySqlPage(where + " order by lyid desc", easyui);
        }
        easyui.putAll(list);
        ServletUtil.ajaxData(easyui.toDataString(), response);
    }

    /**
     * 删除留言
     */
    private void deleteLy(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Lyb hyb = new Lyb();
        Hyberbin hyberbin = new Hyberbin(hyb);
        String[] nums = num.split(":");
        String sql = "";
        for (int i = 0; i < nums.length; i++) {
            hyberbin.addParmeter(nums[i]);
            sql += " or lyid=?";
        }
        sql = sql.substring(3);
        boolean b = hyberbin.dell("where" + sql);
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 添加留言
     */
    private void addLy(HttpServletRequest request, HttpServletResponse response) {
        Lyb lyb = new Lyb();
        Hyberbin hyb = new Hyberbin(lyb);
        ServletUtil.loadByBean(request, lyb, true);
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        lyb.setHfsj(sj);
        lyb.setLysj(sj);
        HttpSession session = request.getSession(false);
        Yhb yhb = (Yhb) session.getAttribute("yhb");
        lyb.setHfr(yhb.getYhm());
        boolean b = hyb.insert("lyid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 修改留言
     */
    private void updateLy(HttpServletRequest request, HttpServletResponse response) {
        Lyb lyb = new Lyb();//留言表
        Hyberbin hyberbin = new Hyberbin(lyb);
        ServletUtil.loadByBean(request, lyb, true);
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        lyb.setHfsj(sj);
        HttpSession session = request.getSession(false);
        Yhb yhb = (Yhb) session.getAttribute("yhb");
        lyb.setHfr(yhb.getYhm());
        boolean b = hyberbin.updateByKey("lyid");
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
                addLy(request, response);
                break;
            case UPDATE:
                updateLy(request, response);
                break;
            case DELETE:
                deleteLy(request, response);
                break;
        }
    }
}
