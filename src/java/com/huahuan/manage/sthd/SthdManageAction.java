/*
 *这个是黄迎斌的servlet框架
 * http://opdps.hbnu.edu.cn/hyberbin
 */
package com.huahuan.manage.sthd;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Hdjy;
import com.huahuan.table.Hdlb;
import com.huahuan.table.Hyb;
import com.huahuan.table.Sthd;
import com.huahuan.table.View_sthd;
import com.huahuan.tools.CutJsonString;
import com.huahuan.tools.FileAction;
import com.huahuan.tools.Util;
import com.jplus.json.EasyUiJson;
import com.jplus.json.JSONArray;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
@WebServlet(name = "SthdManageAction", urlPatterns = "/manage/SthdManageAction.jsp")
public class SthdManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;//显示单例
    public final static int SHOWLIST = 2;//显示列表
    public final static int ADD = 3;//添加记录
    public final static int UPDATE = 4;//更新记录
    public final static int DELETE = 6;//删除记录
    public final static int LOADHDLBCOMBO = 7;
    public final static int UPDATETP = 8;

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
     * 显示协会活动列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_sthd sthd = new View_sthd();
        Hyberbin hyb = new Hyberbin(sthd);
        List list;
        EasyUiJson easyui = new EasyUiJson(request);
        String where = "";
        if (Util.isEmpty(searchValue)) {
            list = hyb.showByMySqlPage(where + " order by hdid desc", easyui);
        } else {
            where = " where " + searchName + " like ?";
            hyb.addParmeter("%" + searchValue + "%");
            list = hyb.showByMySqlPage(where + " order by hdid desc", easyui);
        }
        easyui.putAll(list);
        ServletUtil.ajaxData(easyui.toDataString(), response);
    }

    /**
     * 显示单个活动
     */
    private void showone(HttpServletRequest request, HttpServletResponse response) {
        String hdid = request.getParameter("hdid");
        View_sthd view_sthd = new View_sthd();
        Hyberbin hyb = new Hyberbin(view_sthd);
        hyb.addParmeter(Integer.parseInt(hdid));
        List list = hyb.showList("select hdid,xm,hdlb,hdsj,hdnr from View_sthd where hdid=?");
        JSONArray json = new JSONArray();
        json.put(list);
        ServletUtil.ajaxData(CutJsonString.cutObjectJson(json.toString()), response);
    }

    /**
     * 删除活动,同时删除活动对应的所有剪影
     */
    private void deleteHd(HttpServletRequest request, HttpServletResponse response) {
        boolean b = false;
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Sthd sthd = new Sthd();
        Hdjy hdjy = new Hdjy();
        Hyberbin hyb = new Hyberbin(hdjy, true);
        String[] nums = num.split(":");
        dellHdFolder(nums, request);//首先删除活动对应的文件夹
        String hdsql = Util.getDeleteSql(hyb, nums, "hdid");
        List jylist = hyb.showList("select jyid,hdid from hdjy where" + hdsql);
        if (jylist == null || jylist.isEmpty()) {//如果活动下没有剪影，则直接删除活动即可
            hyb.changeTable(sthd);
            hdsql = "";
            hdsql = Util.getDeleteSql(hyb, nums, "hdid");
            b = hyb.dell("where" + hdsql);
        } else {//否则先删除剪影
            hyb.clearParmeter();
            String jysql = "";
            for (int i = 0; i < jylist.size(); i++) {
                Hdjy tmp = (Hdjy) jylist.get(i);
                hyb.addParmeter(tmp.getJyid());
                jysql += " or jyid=?";
            }
            jysql = jysql.substring(3);
            hyb.dell("where" + jysql);
            hyb.changeTable(sthd);
            hdsql = "";
            hdsql = Util.getDeleteSql(hyb, nums, "hdid");
            b = hyb.dell("where" + hdsql);
        }
        hyb.reallyClose();
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 删除的活动对应的文件夹,文件夹名为hdid_hdbt形式
     */
    private void dellHdFolder(String[] nums, HttpServletRequest request) {
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String where = "";
        for (int i = 0; i < nums.length; i++) {
            where += " or hdid=?";
            emm.setPreparedParameter(nums[i]);
        }
        where = where.substring(3);
        ArrayList<HashMap> list = emm.executeQuery("select hdid,hdbt from sthd where " + where);
        dao.close();
        for (int i = 0; i < nums.length; i++) {
            String path = request.getServletContext().getRealPath("/userfiles/image/社团图片/活动剪影/" + list.get(i).get("hdid") + "_" + list.get(i).get("hdbt"));
            FileAction.delFolder(path);
        }
    }

    /**
     * 添加协会活动
     */
    private void addHd(HttpServletRequest request, HttpServletResponse response) {
        String xm = request.getParameter("xm");
        Hyb hyb = new Hyb();//会员表
        Hyberbin hyberbin = new Hyberbin(hyb);
        hyberbin.addParmeter(xm);
        hyb = hyberbin.showOne("select id,xm from hyb where xm=?");
        Sthd sthd = new Sthd();
        hyberbin.changeTable(sthd);
        sthd.setHdjjfz(hyb.getId());
        ServletUtil.loadByBean(request, sthd, true);
        boolean b = hyberbin.insert("hdid");
        hyberbin.reallyClose();
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        ArrayList<HashMap> list = emm.executeQuery("select max(hdid) as id from sthd");
        dao.close();
        String path = request.getServletContext().getRealPath("/userfiles/image/社团图片/活动剪影/" + list.get(0).get("id") + "_" + sthd.getHdbt());
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 修改协会活动
     */
    private void updateHd(HttpServletRequest request, HttpServletResponse response) {
        Sthd sthd = new Sthd();//社团活动表
        Hyberbin hyberbin = new Hyberbin(sthd);
        ServletUtil.loadByBean(request, sthd, true);
        boolean b = hyberbin.updateByKey("hdid");
        String message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 加载活动类别的下拉框
     */
    private void loadHdCombo(HttpServletRequest request, HttpServletResponse response) {
        Hdlb hdlb = new Hdlb();
        Hyberbin hyb = new Hyberbin(hdlb);
        List list = hyb.showAll();
        JSONArray json = new JSONArray();
        json.put(list);
        ServletUtil.ajaxData(CutJsonString.cutComboJson(json.toString()), response);
    }

    /**
     * 修改活动图片
     */
    private void updatetp(HttpServletRequest request, HttpServletResponse response) {
        String newPicPath = request.getParameter("newPicPath");
        String hdid = request.getParameter("id");
        Sthd sthd = new Sthd();//会员表
        Hyberbin hyberbin = new Hyberbin(sthd);
        sthd.setHdtp(newPicPath);
        sthd.setHdid(Integer.parseInt(hdid));
        boolean b = hyberbin.updateByKey("hdid");
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
            case SHOWONE:
                showone(request, response);
                break;
            case SHOWLIST:
                showlist(request, response);
                break;
            case ADD:
                addHd(request, response);
                break;
            case UPDATE:
                updateHd(request, response);
                break;
            case DELETE:
                deleteHd(request, response);
                break;
            case LOADHDLBCOMBO:
                loadHdCombo(request, response);
                break;
            case UPDATETP:
                updatetp(request, response);
                break;
        }
    }
}
