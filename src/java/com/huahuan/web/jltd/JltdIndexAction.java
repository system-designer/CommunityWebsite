package com.huahuan.web.jltd;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
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
 * 论坛首页
 *
 * @author Administrator
 */
@WebServlet(name = "JltdIndexAction", urlPatterns = "/JltdIndexAction.jsp")
public class JltdIndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLIST = 2;//显示列表
    
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
     * 得到论坛板块列表
     */
    private void showlist(HttpServletRequest request, HttpServletResponse response) {
        String sql = "SELECT"
                + " `ltbk`.`bkid`, `ltbk`.`bkmc`, `ltbk`.`bktb`, `ltbk`.`bz`, `ltbk`.`bkms`,"
                + " `ltbk`.`tzzs`, `yhb`.`yhm`"
                + " FROM"
                + " `ltbk` LEFT JOIN"
                + " `yhb` ON `ltbk`.`bz` = `yhb`.`id`";
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        ArrayList<HashMap> bklist = emm.executeQuery(sql);
        // 得到板块最后发布时间
        int size = bklist.size();
        for (int i = 0; i < size; i++) {
            emm.setPreparedParameter(Integer.parseInt(bklist.get(i).get("bkid").toString()));
            ArrayList<HashMap> list = emm.executeQuery("select max(ftsj) as zhfbsj from ftb where bkid=?");
            if (list.get(0).get("zhfbsj") == null) {
                bklist.get(i).put("zhfbsj", "");
            } else {
                bklist.get(i).put("zhfbsj", list.get(0).get("zhfbsj").toString());
            }
        }
        dao.close();
        request.setAttribute("bklist", bklist);
        Integer times = bklist.size();//计算前台用到的循环次数
        Integer temp = times % 2 == 0 ? times / 2 : times / 2 + 1;
        int[] arr = new int[temp];
        for (int i = 0; i < temp; i++) {
            arr[i] = i;
        }
        request.setAttribute("arr", arr);
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
        try {
            request.getRequestDispatcher("/web/jltd/jltd.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(JltdIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JltdIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
                break;
        }
    }
}
