package com.huahuan.web.jltd;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Ftb;
import com.huahuan.table.Ltbk;
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
@WebServlet(name = "TzlbIndexAction", urlPatterns = "/TzlbIndexAction.jsp")
public class TzlbIndexAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLIST = 2;//显示列表
    public final static int FT = 3;
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
     * 显示帖子列表
     */
    private void showTzlb(HttpServletRequest request, HttpServletResponse response) {
        String bkid = request.getParameter("bkid");
        if (Util.isEmpty(bkid)) {
            bkid = "1";
        }
        String page = request.getParameter("page");
        if (Util.isEmpty(page) || !Util.isInteger(page)) {
            page = "1";
        }
        int pageSize = 10;
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String sql = "SELECT"
                + " `yhb`.`yhm`, `ftb`.`ftid`, `ftb`.`bkid`, `ftb`.`tzbt`, `ftb`.`tznr`,"
                + " `ftb`.`ftsj`, `ftb`.`sfsh`, `ftb`.`sfjj`, `ftb`.`sfzd`, `ftb`.`llcs`,"
                + " `ftb`.`htzs`"
                + " FROM"
                + " `ftb` INNER JOIN"
                + " `yhb` ON `ftb`.`ftr` = `yhb`.`id`";
        String where = "";
        ArrayList<HashMap> tzlist;
        //帖子排序规则：是否置顶，是否加精，发帖id
        if (bkid.equals("0")) {
            String cxtzbt = request.getParameter("cxtzbt");
            emm.setPreparedParameter("%" + cxtzbt + "%");
            emm.setPreparedParameter("%" + cxtzbt + "%");
            where = " where tzbt like ? or yhm like ? and sfsh=1 order by sfzd desc,sfjj desc,ftid desc";
            String range = " limit " + (Integer.parseInt(page) - 1) * pageSize + ", " + pageSize;
            tzlist = emm.executeQuery(sql + where + range);
            emm.setPreparedParameter("%" + cxtzbt + "%");
            emm.setPreparedParameter("%" + cxtzbt + "%");
        } else {
            emm.setPreparedParameter(Integer.parseInt(bkid));
            where = " where bkid=? and sfsh=1 order by sfzd desc,sfjj desc,ftid desc";
            String range = " limit " + (Integer.parseInt(page) - 1) * pageSize + ", " + pageSize;
            tzlist = emm.executeQuery(sql + where + range);
            emm.setPreparedParameter(Integer.parseInt(bkid));
        }
        //分页信息
        int total = emm.executeQuery(sql + where).size();
        int totalpages = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        request.setAttribute("totalpages", totalpages);
        request.setAttribute("page", Integer.parseInt(page));
        //存放板块信息
        if (!bkid.equals("0")) {
            request.setAttribute("bkid", Integer.parseInt(bkid));
            emm.setPreparedParameter(Integer.parseInt(bkid));
            request.setAttribute("bkmc", emm.executeQuery("select bkid,bkmc from ltbk where bkid=?").get(0).get("bkmc").toString());
        } else {
            request.setAttribute("bkid", 0);//区分查询
            request.setAttribute("cxtzbt", request.getParameter("cxtzbt"));
        }
        dao.close();
        getZhHtxx(tzlist);
        request.setAttribute("tzlist", tzlist);
    }

    /**
     * 得到最后回帖信息
     *
     * @param bklist 板块列表
     * @return
     */
    private void getZhHtxx(ArrayList<HashMap> tzlist) {
        String sql = "SELECT"
                + " `yhb`.`yhm` as htyh, `htb`.`htr`, max(`htb`.`htsj`) as zhhtsj, `htb`.`ftid`, `htb`.`htid`"
                + " FROM"
                + " `yhb` INNER JOIN"
                + " `htb` ON `htb`.`htr` = `yhb`.`id` where ftid=?";
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        int size = tzlist.size();
        for (int i = 0; i < size; i++) {
            emm.setPreparedParameter(Integer.parseInt(tzlist.get(i).get("ftid").toString()));
            ArrayList<HashMap> list = emm.executeQuery(sql);
            if (list.get(0).get("zhhtsj") == null || list.get(0).get("htyh") == null) {
                tzlist.get(i).put("zhhtsj", "");
                tzlist.get(i).put("htyh", "");
            } else {
                tzlist.get(i).put("zhhtsj", list.get(0).get("zhhtsj").toString());
                tzlist.get(i).put("htyh", list.get(0).get("htyh").toString());
            }
        }
        dao.close();
    }

    /**
     * 发帖
     */
    private void ft(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        String code = request.getParameter("code");
        if (!code.equals(session.getAttribute("code").toString())) {
            return;
        }
        Yhb yhb = (Yhb) session.getAttribute("yhb");
        Ftb ftb = new Ftb();
        ServletUtil.loadByBean(request, ftb, true);
        Hyberbin hyb = new Hyberbin(ftb, true);
        ftb.setFtr(yhb.getId());
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String composetime = ss.format(new java.util.Date());
        Timestamp sj = Timestamp.valueOf(composetime);
        ftb.setFtsj(sj);
        hyb.insert("ftid");
        //每发帖一次,该板块帖子总数加1
        Ltbk ltbk = new Ltbk();
        hyb.changeTable(ltbk);
        String bkid = request.getParameter("bkid");
        if (Util.isInteger(bkid)) {
            bkid = "1";
        }
        hyb.addParmeter(Integer.parseInt(bkid));
        hyb.update("update ltbk set tzzs=tzzs+1 where bkid=?");
        //每回一次贴，用户增加10分
        hyb.changeTable(yhb);
        hyb.addParmeter(yhb.getId());
        hyb.update("update yhb set jf=jf+10 where id=?");
        hyb.reallyClose();
        showbklb(request, response);
        showTzlb(request, response);
        try {
            response.sendRedirect(request.getContextPath() + "/TzlbIndexAction.jsp?mode=SHOWLIST&bkid=" + ftb.getBkid());
        } catch (IOException ex) {
            Logger.getLogger(TznrIndexAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 加载论坛版块的下拉框
     */
    private void showbklb(HttpServletRequest request, HttpServletResponse response) {
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        ArrayList<HashMap> bklist = emm.executeQuery("select bkid,bkmc from ltbk");
        dao.close();
        request.setAttribute("bklist", bklist);
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
                showbklb(request, response);
                showTzlb(request, response);
                try {
                    request.getRequestDispatcher("/web/jltd/tzlb.jsp").forward(request, response);
                } catch (ServletException ex) {
                    Logger.getLogger(TzlbIndexAction.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(TzlbIndexAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case FT:
                ft(request, response);
                break;
            case CHECKCODE:
                checkCode(request, response);
                break;
        }
    }
}
