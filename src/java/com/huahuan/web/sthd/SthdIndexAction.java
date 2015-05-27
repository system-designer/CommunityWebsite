package com.huahuan.web.sthd;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.tools.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author evance
 */
@WebServlet(name = "SthdIndexAction", urlPatterns = "/SthdIndexAction.jsp")
public class SthdIndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWONE = 1;
    public final static int SHOWHDLIST = 2;//显示活动列表

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
     * 显示社团活动的列表,包含分页查询
     */
    private void showhdlist(HttpServletRequest request, HttpServletResponse response) {
        String index = request.getParameter("index");
        getHdlbList(request, response);//得到类别列表
        String where = "";
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String hdlb = request.getParameter("hdlb");
        if (Util.isEmpty(hdlb)) {
            hdlb = "1";
        }
        //存放活动类别的信息
        request.setAttribute("hdlb", Integer.parseInt(hdlb));
        emm.setPreparedParameter(Integer.parseInt(hdlb));
        request.setAttribute("lbmc", emm.executeQuery("select lbid,lbmc from hdlb where lbid=?").get(0).get("lbmc").toString());
        String searchName = request.getParameter("searchName");
        String searchValue = request.getParameter("searchValue");
        if (!Util.isEmpty(searchValue) && !Util.isEmpty(searchName)) {//查询
            where = " where " + searchName + " like ? order by hdid desc";
            emm.setPreparedParameter("%" + searchValue + "%");
        } else {
            where = " where hdlb=? order by hdid desc";
            emm.setPreparedParameter(Integer.parseInt(hdlb));
        }
        String page = request.getParameter("page");
        if (Util.isEmpty(page) || !Util.isInteger(page)) {
            page = "1";
        }
        int pageSize = 4;
        String sql = "SELECT"
                + " `hyb`.`xm`, `hyb`.`hyzp`, `hyb`.`bz`, `sthd`.`hdjjfz`, `sthd`.`hdid`,"
                + " `sthd`.`hdbt`,`sthd`.`hdsj`,`sthd`.`hdnr`"
                + " FROM"
                + " `hyb` INNER JOIN"
                + " `sthd` ON `sthd`.`hdjjfz` = `hyb`.`id`";
        String range = " limit " + (Integer.parseInt(page) - 1) * pageSize + ", " + pageSize;//分页
        ArrayList<HashMap> hdlist = emm.executeQuery(sql + where + range);
        request.setAttribute("hdlist", hdlist);//活动列表
        //用于分页显示的参数
        request.setAttribute("page", Integer.parseInt(page));
        if (!Util.isEmpty(searchValue) && !Util.isEmpty(searchName)) {
            where = " where " + searchName + " like ?";
            emm.setPreparedParameter("%" + searchValue + "%");
        } else {
            where = " where hdlb=?";
            emm.setPreparedParameter(Integer.parseInt(hdlb));
        }
        int total = emm.executeQuery(sql + where).size();
        int totalpages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        request.setAttribute("totalpages", totalpages);
        if (index != null) {
            if (index.equals("index")) {
                request.setAttribute("hdid", Integer.parseInt(request.getParameter("hdid")));
            }
        }
        dao.close();//关闭数据库
        try {
            request.getRequestDispatcher("/web/sthd/sthd.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SthdIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SthdIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 得到活动类别列表
     */
    private void getHdlbList(HttpServletRequest request, HttpServletResponse response) {
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        ArrayList<HashMap> hdlblist = emm.executeQuery("select lbid,lbmc from hdlb");
        dao.close();
        request.setAttribute("hdlblist", hdlblist);
    }

    /**
     * 显示单个社团活动
     */
    private void showSthd(HttpServletRequest request, HttpServletResponse response) {
        String hdid = request.getParameter("hdid");//活动id
        if (!Util.isInteger(hdid)) {
            hdid = "1";
        }
        String sql = "SELECT"
                + " `hyb`.`hyzp` as hyzp, `hyb`.`xm` as xm, `hyb`.`xj` as xj, `sthd`.`hdsj`, `sthd`.`hdbt` as hdbt,"
                + " `sthd`.`hdnr` as hdnr, `sthd`.`hdjjfz`, `sthd`.`hdid` as hdid"
                + " FROM"
                + " `sthd` INNER JOIN"
                + " `hyb` ON `sthd`.`hdjjfz` = `hyb`.`id`"
                + " where hdid=?";
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        emm.setPreparedParameter(Integer.parseInt(hdid));
        ArrayList<HashMap> list = emm.executeQuery(sql);
        dao.close();
        if (!list.isEmpty()) {
            request.setAttribute("hd", list.get(0));
        }
        try {
            request.getRequestDispatcher("/web/sthd/hdnr.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SthdIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SthdIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 下面的模式和方法可以自行增删
     */
    public void execute(int event, HttpServletRequest request, HttpServletResponse response) {
        /**
         * 下面是相关模式下所做的动作*
         */
        switch (event) {
            case SHOWONE:
                showSthd(request, response);
                break;
            case SHOWHDLIST:
                showhdlist(request, response);
                break;
        }
    }
}
