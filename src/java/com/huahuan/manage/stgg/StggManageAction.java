package com.huahuan.manage.stgg;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Gglb;
import com.huahuan.table.Stgg;
import com.huahuan.table.View_stgg;
import com.huahuan.table.Yhb;
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
import javax.servlet.http.HttpSession;
import org.jplus.hyb.database.Hyberbin;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "StggManageAction", urlPatterns = "/manage/StggManageAction.jsp")
public class StggManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;//显示单个
    public final static int SHOWLIST = 2;//显示列表
    public final static int ADD = 3;//添加记录
    public final static int UPDATE = 4;//更新记录
    public final static int DELETE = 6;//删除记录
    public final static int LOADGGLBCOMBO = 7;

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
     * 显示协会公告列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_stgg stgg = new View_stgg();
        Hyberbin hyb = new Hyberbin(stgg);
        List list;
        EasyUiJson easyui = new EasyUiJson(request);
        String where = "";
        if (Util.isEmpty(searchValue)) {
            list = hyb.showByMySqlPage(where + " order by ggid desc", easyui);
        } else {
            where = " where " + searchName + " like ?";
            hyb.addParmeter("%" + searchValue + "%");
            list = hyb.showByMySqlPage(where + " order by ggid desc", easyui);
        }
        easyui.putAll(list);
        ServletUtil.ajaxData(easyui.toDataString(), response);
    }

    /**
     * 显示单个公告
     */
    private void showone(HttpServletRequest request, HttpServletResponse response) {
        String ggid = request.getParameter("ggid");
        View_stgg view_stgg = new View_stgg();
        Hyberbin hyb = new Hyberbin(view_stgg);
        hyb.addParmeter(Integer.parseInt(ggid));
        List list = hyb.showList("select * from View_stgg where ggid=?");
        JSONArray json = new JSONArray();
        json.put(list);
        ServletUtil.ajaxData(CutJsonString.cutObjectJson(json.toString()), response);
    }

    /**
     * 删除公告
     */
    private void deleteGg(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Stgg stgg = new Stgg();
        Hyberbin hyberbin = new Hyberbin(stgg);
        String[] nums = num.split(":");
        String sql = "";
        for (int i = 0; i < nums.length; i++) {
            hyberbin.addParmeter(nums[i]);
            sql += " or ggid=?";
        }
        sql = sql.substring(3);
        boolean b = hyberbin.dell("where" + sql);
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 添加协会公告
     */
    private void addGg(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        Yhb yhb = (Yhb) session.getAttribute("yhb");
        Stgg stgg = new Stgg();
        Hyberbin hyberbin = new Hyberbin(stgg);
        ServletUtil.loadByBean(request, stgg, true);
        stgg.setGgr(yhb.getId());
        boolean b = hyberbin.insert("ggid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 修改协会公告
     */
    private void updateGg(HttpServletRequest request, HttpServletResponse response) {
        Stgg stgg = new Stgg();//社团公告表
        Hyberbin hyberbin = new Hyberbin(stgg);
        ServletUtil.loadByBean(request, stgg, true);
        boolean b = hyberbin.updateByKey("ggid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 加载公告类别的下拉框
     */
    private void loadGglbCombo(HttpServletRequest request, HttpServletResponse response) {
        Gglb gglb = new Gglb();
        Hyberbin hyb = new Hyberbin(gglb);
        List list = hyb.showAll();
        JSONArray json = new JSONArray();
        json.put(list);
        ServletUtil.ajaxData(CutJsonString.cutComboJson(json.toString()), response);
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
                addGg(request, response);
                break;
            case UPDATE:
                updateGg(request, response);
                break;
            case DELETE:
                deleteGg(request, response);
                break;
            case LOADGGLBCOMBO:
                loadGglbCombo(request, response);
                break;
        }
    }
}
