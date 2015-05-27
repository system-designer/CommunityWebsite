package com.huahuan.manage.jltd;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Ltbk;
import com.huahuan.table.View_ltbk;
import com.huahuan.table.Yhb;
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
@WebServlet(name = "BkglManageAction", urlPatterns = "/manage/BkglManageAction.jsp")
public class BkglManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLIST = 2;//显示列表
    public final static int ADD = 3;//添加记录
    public final static int UPDATE = 4;//更新记录
    public final static int DELETE = 6;//删除记录
    public final static int UPDATETP = 7;

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
     * 显示板块信息的列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        //禁止json缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String searchValue = request.getParameter("searchValue");
        String searchName = request.getParameter("searchName");
        View_ltbk ltbk = new View_ltbk();
        Hyberbin hyb = new Hyberbin(ltbk);
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
//                super.AjaxSetContentType("json");
//        super.AjaxSet(easyui.toDataString());
//        super.AjaxSbmit();
        /**
         * *如果异步提交代码有三行，执行如下方法，注意该方法参数为两个，注意json数据不要搞错了**
         */
        ServletUtil.ajaxData(easyui.toDataString(), response);
    }

    /**
     * 删除某一个板块的所有相关信息
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) {//这里的删除量过大，采用级联删除
        String num = request.getParameter("ids");//用于批量删除拼接的id字符串
        Ltbk ltbk = new Ltbk();//论坛版块表
        Hyberbin hyb = new Hyberbin(ltbk);
        String[] nums = num.split(":");
        String bksql = "";
        for (int i = 0; i < nums.length; i++) {//得到所要删除的板块的id
            hyb.addParmeter(nums[i]);
            bksql += " or bkid=?";
        }
        bksql = bksql.substring(3);
        boolean b = hyb.dell("where" + bksql);
        String message = b ? "操作成功" : "操作失败";
//        super.AjaxSet(message);
//        super.AjaxSbmit();
        /**
         * *如果异步提交代码有两行，执行如下方法，注意该方法参数为三个，注意json数据不要搞错了**
         */
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 添加一个板块
     */
    private void add(HttpServletRequest request, HttpServletResponse response) {
        String message = "";
        String yhm = request.getParameter("yhm");
        Ltbk ltbk = new Ltbk();
        Yhb yhb = new Yhb();
        Hyberbin hyb = new Hyberbin(yhb, true);
        hyb.addParmeter(yhm);
        yhb = hyb.showOne("select id,yhm from yhb where yhm=?");
        if (yhb == null || yhb.getId() == null) {
            message = "该用户不存在";
            ServletUtil.ajaxData(message, response, "html");
        }
        hyb.changeTable(ltbk);
//        super.loadByBean(ltbk);
        /**
         * *以super开头的方法，改成ServletUtil类中对应的方法，第一个参数是HttpRequest， 第二个参数是数据库表bean，
         * 第三个参数为true**
         */
        ServletUtil.loadByBean(request, ltbk, true);
        ltbk.setBz(yhb.getId());
        boolean b = hyb.insert("bkid");
        hyb.reallyClose();
        message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 更改一个板块的信息
     */
    private void update(HttpServletRequest request, HttpServletResponse response) {
        String message = "";
        String yhm = request.getParameter("yhm");
        Ltbk ltbk = new Ltbk();//论坛版块表
        Yhb yhb = new Yhb();
        Hyberbin hyb = new Hyberbin(yhb, true);
        hyb.addParmeter(yhm);
        yhb = hyb.showOne("select id,yhm from yhb where yhm=?");
        if (yhb == null || yhb.getId() == null) {
            message = "该用户不存在";
            ServletUtil.ajaxData(message, response, "html");
        }
        hyb.changeTable(ltbk);
//        super.loadByBean(ltbk);
        ltbk.setBz(yhb.getId());
        boolean b = hyb.updateByKey("bkid");
        hyb.reallyClose();
        message = b ? "操作成功" : "操作失败";
        ServletUtil.ajaxData(message, response, "html");
    }

    /**
     * 修改板块图标
     */
    private void updatetp(HttpServletRequest request, HttpServletResponse response) {
        String newPicPath = request.getParameter("newPicPath");
        String bkid = request.getParameter("id");
        Ltbk sthd = new Ltbk();//会员表
        Hyberbin hyberbin = new Hyberbin(sthd);
        sthd.setBktb(newPicPath);
        sthd.setBkid(Integer.parseInt(bkid));
        boolean b = hyberbin.updateByKey("bkid");
        String str = b ? "修改成功" : "修改失败";
        ServletUtil.ajaxData("{\"notice\":\"" + str + "\"}", response);
    }

    /**
     * 下面的模式和方法可以自行增删
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
                add(request, response);
                break;
            case UPDATE:
                update(request, response);
                break;
            case DELETE:
                delete(request, response);
                break;
            case UPDATETP:
                updatetp(request, response);
                break;
        }
    }
}
