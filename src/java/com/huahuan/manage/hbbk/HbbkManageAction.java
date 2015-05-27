package com.huahuan.manage.hbbk;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Bklb;
import com.huahuan.table.Hbbk;
import com.huahuan.table.View_hbbk;
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
@WebServlet(name = "HbbkManageAction", urlPatterns = "/manage/HbbkManageAction.jsp")
public class HbbkManageAction extends HttpServlet {

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
    public final static int LOADBKLBCOMBO = 7;
    
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
     * 显示百科信息的列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_hbbk hbbk = new View_hbbk();
        Hyberbin hyb = new Hyberbin(hbbk);
        List list;
        EasyUiJson easyui = new EasyUiJson(request);
        String where = "";
        if (Util.isEmpty(searchValue)) {
            list = hyb.showByMySqlPage(where + " order by bkid desc", easyui);
        } else {
            where = " where " + searchName + " like ?";
            hyb.addParmeter("%" + searchValue + "%");
            list = hyb.showByMySqlPage(where + " order by bkid desc", easyui);
        }
        easyui.putAll(list);
        ServletUtil.ajaxData(easyui.toDataString(), response);
    }

    /**
     * 显示单个百科
     */
    private void showone(HttpServletRequest request, HttpServletResponse response) {
        String bkid = request.getParameter("bkid");
        View_hbbk hbbk = new View_hbbk();
        Hyberbin hyb = new Hyberbin(hbbk);
        hyb.addParmeter(Integer.parseInt(bkid));
        List list = hyb.showList("select * from View_hbbk where bkid=?");
        JSONArray json = new JSONArray();
        json.put(list);
        ServletUtil.ajaxData(CutJsonString.cutObjectJson(json.toString()), response);
    }

    /**
     * 批量删除百科
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        String[] nums = num.split(":");
        Hbbk hbbk = new Hbbk();
        Hyberbin hyb = new Hyberbin(hbbk);
        String bksql = Util.getDeleteSql(hyb, nums, "bkid");
        boolean b = hyb.dell("where" + bksql);//删除所有指定的百科
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 添加一个百科
     */
    private void add(HttpServletRequest request, HttpServletResponse response) {
        Hbbk hbbk = new Hbbk();
        Hyberbin hyberbin = new Hyberbin(hbbk);
        ServletUtil.loadByBean(request, hbbk, true);
        boolean b = hyberbin.insert("bkid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 更改一个百科的信息
     */
    private void update(HttpServletRequest request, HttpServletResponse response) {
        Hbbk hbbk = new Hbbk();//环保百科表
        Hyberbin hyberbin = new Hyberbin(hbbk);
        ServletUtil.loadByBean(request, hbbk, true);
        boolean b = hyberbin.updateByKey("bkid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 加载百科类别的下拉框
     */
    private void loadBklbCombo(HttpServletRequest request, HttpServletResponse response) {
        Bklb bklb = new Bklb();
        Hyberbin hyb = new Hyberbin(bklb);
        List list = hyb.showList("select lbid,lbmc from bklb");
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
                add(request, response);
                break;
            case UPDATE:
                update(request, response);
                break;
            case DELETE:
                delete(request, response);
                break;
            case LOADBKLBCOMBO:
                loadBklbCombo(request, response);
                break;
        }
    }
}
