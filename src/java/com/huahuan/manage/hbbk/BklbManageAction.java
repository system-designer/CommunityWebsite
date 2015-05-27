/*
 *这个是黄迎斌的servlet框架
 * http://opdps.hbnu.edu.cn/hyberbin
 */
package com.huahuan.manage.hbbk;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Bklb;
import com.huahuan.table.Hbbk;
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
@WebServlet(name = "BklbManageAction", urlPatterns = "/manage/BklbManageAction.jsp")
public class BklbManageAction extends HttpServlet {

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
     * 显示百科类别信息的列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        Bklb bklb = new Bklb();
        Hyberbin hyb = new Hyberbin(bklb);
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
     * 删除百科类别下的所有百科类别信息
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        boolean b = false;
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Bklb bklb = new Bklb();//百科类别表
        Hbbk hbbk = new Hbbk();//环保百科表
        Hyberbin hyb = new Hyberbin(hbbk, true);
        String[] nums = num.split(":");
        String lbsql = Util.getDeleteSql(hyb, nums, "bklb");
        List bklist = hyb.showList("select bkid,bklb from hbbk where" + lbsql);
        if (bklist == null || bklist.isEmpty()) {
            hyb.changeTable(bklb);
            lbsql = "";
            lbsql = Util.getDeleteSql(hyb, nums, "lbid");
            b = hyb.dell("where" + lbsql);
        } else {
            String bksql = "";
            for (int j = 0; j < bklist.size(); j++) {//得到所要删除的百科的id
                Hbbk temp = (Hbbk) bklist.get(j);
                hyb.addParmeter(temp.getBkid());
                bksql += " or bkid=?";
            }
            hyb.dell("where" + bksql);//删除所有百科类别id对应的百科
            hyb.changeTable(bklb);
            lbsql = "";
            lbsql = Util.getDeleteSql(hyb, nums, "lbid");
            b = hyb.dell("where" + lbsql);//删除所有指定的百科类别
        }
        hyb.reallyClose();
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response,"html");
    }

    /**
     * 添加一个百科类别
     */
    private void add(HttpServletRequest request, HttpServletResponse response) {
        Bklb bklb = new Bklb();
        Hyberbin hyberbin = new Hyberbin(bklb);
        //super.loadByBean(bklb);
        ServletUtil.loadByBean(request, bklb, true);
        boolean b = hyberbin.insert("lbid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response,"html");
    }

    /**
     * 更改一个百科类别的信息
     */
    private void update(HttpServletRequest request, HttpServletResponse response) {
        Bklb bklb = new Bklb();//百科类别表
        Hyberbin hyberbin = new Hyberbin(bklb);
        ServletUtil.loadByBean(request, bklb, true);
        boolean b = hyberbin.updateByKey("lbid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response,"html");
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
                showlist(request,response);
                break;
            case ADD:
                add(request,response);
                break;
            case UPDATE:
                update(request,response);
                break;
            case DELETE:
                delete(request,response);
                break;
        }
    }
}
