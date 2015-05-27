package com.huahuan.web.lywm;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Lyb;
import com.huahuan.table.Yhb;
import com.huahuan.tools.Util;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "LywmIndexAction", urlPatterns = "/LywmIndexAction.jsp")
public class LywmIndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLIST = 2;//显示列表
    public final static int ADD = 3;//添加记录
    public final static int CHECKCODE = 4;

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
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String page = request.getParameter("page");
        if (Util.isEmpty(page) || !Util.isInteger(page)) {
            page = "1";
        }
        int pageSize = 6;
        String sql = "select * from lyb order by lyid desc";
        String range = " limit " + (Integer.parseInt(page) - 1) * pageSize + ", " + pageSize;
        ArrayList<HashMap> list = emm.executeQuery(sql + range);
        request.setAttribute("page", Integer.parseInt(page));
        int total = emm.executeQuery(sql).size();
        int totalpages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        dao.close();
        request.setAttribute("totalpages", totalpages);
        request.setAttribute("lylist", list);
        try {
            request.getRequestDispatcher("/web/lywm/lywm.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(LywmIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LywmIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 用户添加留言
     */
    private void add(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        HttpSession session = request.getSession(false);
        if (!code.equals(session.getAttribute("code").toString())) {
            return;
        }
        Yhb yhb = (Yhb) session.getAttribute("yhb");
        Lyb lyb = new Lyb();
        Hyberbin hyb = new Hyberbin(lyb);
        ServletUtil.loadByParams(request, lyb, true);
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        lyb.setLysj(sj);
        if (yhb != null && yhb.getId() != null) {
            lyb.setLyr(yhb.getYhm());
        }
        hyb.insert("lyid");
        showlist(request, response);
        try {
            response.sendRedirect(request.getContextPath() + "/LywmIndexAction.jsp?mode=SHOWLIST");
        } catch (IOException ex) {
            Logger.getLogger(LywmIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 检测验证码
     */
    private void checkCode(HttpServletRequest request, HttpServletResponse response) {
        String message = "";
        String code = request.getParameter("code");
        HttpSession session = request.getSession(false);
        if (!code.equals(session.getAttribute("code").toString())) {
            message = "验证码错误";
        } else {
            message = "验证码正确";
        }
        ServletUtil.ajaxData("{\"notice\":\"" + message + "\"}", response);

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
                add(request, response);
                break;
            case CHECKCODE:
                checkCode(request, response);
                break;
        }
    }
}
