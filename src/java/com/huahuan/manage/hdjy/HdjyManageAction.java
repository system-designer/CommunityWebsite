/*
 *这个是黄迎斌的servlet框架
 * http://opdps.hbnu.edu.cn/hyberbin
 */
package com.huahuan.manage.hdjy;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Hdjy;
import com.huahuan.table.View_hdjy;
import com.huahuan.tools.Util;
import com.jplus.json.EasyUiJson;
import com.jplus.json.JSONException;
import com.jplus.json.JSONObject;
import java.io.IOException;
import java.util.List;
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
 * @author Administrator
 */
@WebServlet(name = "HdjyManageAction", urlPatterns = "/manage/HdjyManageAction.jsp")
public class HdjyManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;//显示单例
    public final static int SHOWLIST = 2;//显示列表
    public final static int ADD = 3;//添加记录
    public final static int UPDATE = 4;//更新记录
    public final static int EDIT = 5;//编辑刻录
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
     * 显示一个活动的活动剪影列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_hdjy hdjy = new View_hdjy();
        Hyberbin hyb = new Hyberbin(hdjy);
        List list;
        EasyUiJson easyui = new EasyUiJson(request);
        String where = "";
        if (Util.isEmpty(searchValue)) {
            list = hyb.showByMySqlPage(where + " order by jyid desc", easyui);
        } else {
            where = " where " + searchName + " like ?";
            hyb.addParmeter("%" + searchValue + "%");
            list = hyb.showByMySqlPage(where + " order by jyid desc", easyui);
        }
        easyui.putAll(list);
        ServletUtil.ajaxData(easyui.toDataString(), response);
    }

    /**
     * 显示单个活动剪影
     */
    private void showone(HttpServletRequest request, HttpServletResponse response) {
        String hdid = request.getParameter("hdid");
        View_hdjy hdjy = new View_hdjy();
        Hyberbin hyb = new Hyberbin(hdjy);
        hdjy.setHdid(Integer.parseInt(hdid));
        hyb.addParmeter(Integer.parseInt(hdid));
        hyb.showOne("select * from View_hdjy where hdid=?");
        JSONObject json = new JSONObject();
        try {
            json.put("hdjy", hdjy);
        } catch (JSONException ex) {
            Logger.getLogger(HdjyManageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServletUtil.ajaxData(json.toString(), response);
    }

    /**
     * 删除活动剪影
     */
    private void deleteHdjy(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Hdjy hdjy = new Hdjy();
        Hyberbin hyberbin = new Hyberbin(hdjy);
        String[] nums = num.split(":");
        String sql = Util.getDeleteSql(hyberbin, nums, "jyid");
        boolean b = hyberbin.dell("where" + sql);
        String res = b ? "1" : "0";
        ServletUtil.ajaxData(res, response, "html");
    }

    /**
     * 添加活动剪影
     */
    private void addHdjy(HttpServletRequest request, HttpServletResponse response) {
        Hdjy hdjy = new Hdjy();
        Hyberbin hyberbin = new Hyberbin(hdjy);
        ServletUtil.loadByBean(request, hdjy, true);
        boolean b = hyberbin.insert("jyid");
    }

    /**
     * 修改活动剪影
     */
    private void updateHdjy(HttpServletRequest request, HttpServletResponse response) {
        Hdjy hdjy = new Hdjy();//社团公告表
        Hyberbin hyberbin = new Hyberbin(hdjy);
        ServletUtil.loadByBean(request, hdjy, true);
        boolean b = hyberbin.updateByKey("jyid");
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
            case ADD:
                addHdjy(request, response);
                break;
            case UPDATE:
                updateHdjy(request, response);
                break;
            case DELETE:
                deleteHdjy(request, response);
                break;
        }
    }
}
