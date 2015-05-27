package com.huahuan.manage.gywm;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.GetId;
import com.huahuan.table.Hyb;
import com.huahuan.table.Hyzw;
import com.huahuan.table.Stbm;
import com.huahuan.table.View_hygl;
import com.huahuan.table.Zwfl;
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
@WebServlet(name = "XhcyManageAction", urlPatterns = "/manage/XhcyManageAction.jsp")
public class XhcyManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLIST = 2;//显示列表
    public final static int ADD = 3;//添加记录
    public final static int UPDATE = 4;//更新记录
    public final static int DELETE = 6;//删除记录
    public final static int LOADBMMCCOMBO = 7;
    public final static int LOADZWMCCOMBO = 8;
    public final static int UPDATETP = 9;

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
     * 显示协会成员列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_hygl hygl = new View_hygl();
        Hyberbin hyb = new Hyberbin(hygl);
        List<View_hygl> list;
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
     * 删除会员
     */
    private void deleteHy(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Hyb hyb = new Hyb();
        Hyberbin hyberbin = new Hyberbin(hyb);
        String[] nums = num.split(":");
        String sql = "";
        for (int i = 0; i < nums.length; i++) {
            hyberbin.addParmeter(nums[i]);
            sql += " or id=?";
        }
        sql = sql.substring(3);
        boolean b = hyberbin.dell("where" + sql);
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 添加协会成员
     */
    private void addHy(HttpServletRequest request, HttpServletResponse response) {
        Hyzw hyzw = new Hyzw();
        Hyberbin hyberbin = new Hyberbin(hyzw, true);
        ServletUtil.loadByBean(request, hyzw, true);
        hyberbin.insert("id");
        GetId last = new GetId();
        hyberbin.changeTable(last);
        last = hyberbin.showOne("select max(id) as id from hyzw");
        Hyb hyb = new Hyb();//会员表
        hyberbin.changeTable(hyb);
        ServletUtil.loadByBean(request, hyb, true);
        hyb.setZw(last.getId());
        boolean b = hyberbin.insert("id");
        hyberbin.reallyClose();
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 修改协会成员
     */
    private void updateHy(HttpServletRequest request, HttpServletResponse response) {
        Hyb hyb = new Hyb();//会员表
        Hyberbin hyberbin = new Hyberbin(hyb);
        ServletUtil.loadByBean(request, hyb, true);
        boolean b = hyberbin.updateByKey("id");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 加载部门名称下拉框
     */
    private void loadBmmcCombo(HttpServletRequest request, HttpServletResponse response) {
        Stbm stbm = new Stbm();
        Hyberbin hyb = new Hyberbin(stbm);
        List list = hyb.showList("select bmid,bmmc from stbm");
        JSONArray json = new JSONArray(list);
        ServletUtil.ajaxData(json.toString(), response);
    }

    /**
     * 加载职务名称下拉框
     */
    private void loadZwmcCombo(HttpServletRequest request, HttpServletResponse response) {
        Zwfl zwfl = new Zwfl();
        Hyberbin hyb = new Hyberbin(zwfl);
        List list = hyb.showAll();
        JSONArray json = new JSONArray(list);
        ServletUtil.ajaxData(json.toString(), response);
    }

    /**
     * 修改会员图片
     */
    private void updatetp(HttpServletRequest request, HttpServletResponse response) {
        String newPicPath = request.getParameter("newPicPath");
        String id = request.getParameter("id");
        Hyb hyb = new Hyb();//会员表
        Hyberbin hyberbin = new Hyberbin(hyb);
        hyb.setHyzp(newPicPath);
        hyb.setId(Integer.parseInt(id));
        boolean b = hyberbin.updateByKey("id");
        String str = b ? "修改成功" : "修改失败";
        ServletUtil.ajaxData("{\"notice\":\"" + str + "\"}", response);
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
                addHy(request, response);
                break;
            case UPDATE:
                updateHy(request, response);
                break;
            case DELETE:
                deleteHy(request, response);
                break;
            case LOADBMMCCOMBO:
                loadBmmcCombo(request, response);
                break;
            case LOADZWMCCOMBO:
                loadZwmcCombo(request, response);
                break;
            case UPDATETP:
                updatetp(request, response);
                break;
        }
    }
}
