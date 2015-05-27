package com.huahuan.manage.jltd;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Ftb;
import com.huahuan.table.Ltbk;
import com.huahuan.table.View_ftb;
import com.huahuan.table.Yhb;
import com.huahuan.tools.CutJsonString;
import com.huahuan.tools.Util;
import com.jplus.json.EasyUiJson;
import com.jplus.json.JSONArray;
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
@WebServlet(name = "ZtglManageAction", urlPatterns = "/manage/ZtglManageAction.jsp")
public class ZtglManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;
    public final static int SHOWLIST = 2;//显示列表
    public final static int ADD = 3;//添加记录
    public final static int UPDATE = 4;//更新记录
    public final static int DELETE = 6;//删除记录
    public final static int LOADBKLBCOMBO = 7;
    public final static int SH = 8;
    public final static int SHTG = 13;
    public final static int JJ = 9;
    public final static int ZD = 10;
    public final static int QXJJ = 11;
    public final static int QXZD = 12;
    
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
     * 显示主贴信息list
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_ftb ftb = new View_ftb();
        Hyberbin hyb = new Hyberbin(ftb);
        List list;
        EasyUiJson easyui = new EasyUiJson(request);
        String where = "";
        if (Util.isEmpty(searchValue)) {
            list = hyb.showByMySqlPage(where + " order by ftid desc", easyui);
        } else {
            where = " where " + searchName + " like ?";
            hyb.addParmeter("%" + searchValue + "%");
            list = hyb.showByMySqlPage(where + " order by ftid desc", easyui);
        }
        easyui.putAll(list);
        ServletUtil.ajaxData(easyui.toDataString(), response);
    }

    /**
     * 显示单个主贴
     */
    private void showone(HttpServletRequest request, HttpServletResponse response) {
        String ftid = request.getParameter("ftid");
        View_ftb view_ftb = new View_ftb();
        Hyberbin hyb = new Hyberbin(view_ftb);
        hyb.addParmeter(Integer.parseInt(ftid));
        List list = hyb.showList("select * from View_ftb where ftid=?");
        JSONArray json = new JSONArray();
        json.put(list);
        ServletUtil.ajaxData(CutJsonString.cutObjectJson(json.toString()), response);
    }

    /**
     * 删除一个主贴,同时删除底下的所有回帖
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Ftb ftb = new Ftb();
        Hyberbin hyb = new Hyberbin(ftb);
        String[] nums = num.split(":");
        String sql = "";
        for (int i = 0; i < nums.length; i++) {//得到所要删除的板块的id
            hyb.addParmeter(nums[i]);
            sql += " or ftid=?";
        }
        sql = sql.substring(3);
        boolean b = hyb.dell("where" + sql);
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 添加一个主贴
     */
    private void add(HttpServletRequest request, HttpServletResponse response) {
        Ftb ftb = new Ftb();
        Hyberbin hyberbin = new Hyberbin(ftb);
        ServletUtil.loadByBean(request, ftb, true);
        HttpSession session = request.getSession(false);
        Yhb yhb = (Yhb) session.getAttribute("yhb");
        ftb.setFtr(yhb.getId());
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        ftb.setFtsj(sj);
        boolean b = hyberbin.insert("ftid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 更改一个主贴信息
     */
    private void update(HttpServletRequest request, HttpServletResponse response) {
        Ftb ftb = new Ftb();//论坛发帖表
        Hyberbin hyberbin = new Hyberbin(ftb);
        ServletUtil.loadByBean(request, ftb, true);
        boolean b = hyberbin.updateByKey("ftid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 加载板块类别的下拉框
     */
    private void loadBklbCombo(HttpServletRequest request, HttpServletResponse response) {
        Ltbk ltbk = new Ltbk();
        Hyberbin hyb = new Hyberbin(ltbk);
        List list = hyb.showList("select bkid,bkmc from ltbk");
        JSONArray json = new JSONArray();
        json.put(list);
        ServletUtil.ajaxData(CutJsonString.cutComboJson(json.toString()), response);
    }

    /**
     * 审核未通过
     */
    private void sh(HttpServletRequest request, HttpServletResponse response) {
        String ftid = request.getParameter("ftid");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(ftid));
        boolean b = dao.executeUpdate("update ftb set sfsh=0 where ftid=?");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
        dao.close();
    }

    /**
     * 审核通过
     */
    private void shtg(HttpServletRequest request, HttpServletResponse response) {
        String ftid = request.getParameter("ftid");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(ftid));
        boolean b = dao.executeUpdate("update ftb set sfsh=1 where ftid=?");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
        dao.close();
    }

    /**
     * 加精
     */
    private void jj(HttpServletRequest request, HttpServletResponse response) {
        String ftid = request.getParameter("ftid");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(ftid));
        boolean b = dao.executeUpdate("update ftb set sfjj=1 where ftid=?");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
        dao.close();
    }

    /**
     * 置顶
     */
    private void zd(HttpServletRequest request, HttpServletResponse response) {
        String ftid = request.getParameter("ftid");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(ftid));
        boolean b = dao.executeUpdate("update ftb set sfzd=1 where ftid=?");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
        dao.close();
    }

    /**
     * 取消加精
     */
    private void qxjj(HttpServletRequest request, HttpServletResponse response) {
        String ftid = request.getParameter("ftid");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(ftid));
        boolean b = dao.executeUpdate("update ftb set sfjj=0 where ftid=?");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);
        dao.close();
    }

    /**
     * 取消置顶
     */
    private void qxzd(HttpServletRequest request, HttpServletResponse response) {
        String ftid = request.getParameter("ftid");
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(ftid));
        boolean b = dao.executeUpdate("update ftb set sfzd=0 where ftid=?");
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
            case SH:
                sh(request, response);
                break;
            case SHTG:
                shtg(request, response);
                break;
            case JJ:
                jj(request, response);
                break;
            case ZD:
                zd(request, response);
                break;
            case QXJJ:
                qxjj(request, response);
                break;
            case QXZD:
                qxzd(request, response);
                break;
        }
    }
}
