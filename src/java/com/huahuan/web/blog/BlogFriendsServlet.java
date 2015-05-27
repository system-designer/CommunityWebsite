package com.huahuan.web.blog;

import com.huahuan.database.DatabaseAccess;
import com.huahuan.database.EasyMapsManager;
import com.huahuan.servletutil.ServletUtil;
import com.huahuan.table.Blogfriends;
import com.huahuan.table.Hyb;
import com.huahuan.tools.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jplus.hyb.database.Hyberbin;
import org.jplus.util.Pagger;

/**
 *
 * @author hp
 */
@WebServlet(name = "BlogFriendServlet", urlPatterns = {"/BlogFriendServlet.jsp"})
public class BlogFriendsServlet extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWLIST = 1;//显示列表
    public final static int ADDFRIEND = 2;
    public final static int DELEDEFRIEND = 3;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("textml;charset=UTF-8");
        excute(ServletUtil.setModel(request.getParameter("mode"), this), request, response);
    }

    /**
     * 主执行方法
     *
     * @param event 方法ID
     */
    private void excute(int event, HttpServletRequest request, HttpServletResponse response) {
        try {
            switch (event) {
                case OTHER:
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    break;
                case SHOWLIST:
                    showList(request, response);
                    break;
                case ADDFRIEND:
                    addFriend(request, response);
                    break;
                case DELEDEFRIEND:
                    deleteFriend(request, response);
                    break;
            }
        } catch (ServletException ex) {
            Logger.getLogger(BlogFriendsServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogFriendsServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * 显示列表
     */
    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int host = Integer.parseInt(request.getParameter("host")), pageSize = 15, page;
        String pageString = request.getParameter("page");
        page = Util.isEmpty(pageString) ? 1 : Integer.parseInt(pageString);
        DatabaseAccess dao = new DatabaseAccess();
        EasyMapsManager emm = new EasyMapsManager(dao);
        String sql = "SELECT\n"
                + "  `hyb`.`xm`\n"
                + "FROM\n"
                + "  `blogfriends`\n"
                + "  INNER JOIN `hyb` ON `blogfriends`.`friend` = `hyb`.`id`";
        String range = " limit " + pageSize * (page - 1) + "," + pageSize * page;
        String where = " where host=?";
        emm.setPreparedParameter(host);
        ArrayList friendList = emm.executeQuery(sql + where + range);
        Pagger pagger = new Pagger(pageSize);
        pagger.setCurrent(page);
        pagger.setItems(emm.executeQuery(sql + where).size());
        dao.close();
        request.setAttribute("pagger", pagger);
        request.setAttribute("friendList", friendList);
        request.getRequestDispatcher("").forward(request, response);
    }

    private void addFriend(HttpServletRequest request, HttpServletResponse response) {
        int host = ((Hyb) request.getSession().getAttribute("user")).getId(), friend = Integer.parseInt(request.getParameter("friend"));
        Blogfriends bf = new Blogfriends();
        bf.setHost(host);
        bf.setFriend(friend);
        Hyberbin hyberbin = new Hyberbin(bf);
        hyberbin.addParmeter(host);
        hyberbin.addParmeter(friend);
        hyberbin.update("insert into blogfriends (host,friend) values (?,?)");
    }

    private void deleteFriend(HttpServletRequest request, HttpServletResponse response) {
        int host = ((Hyb) request.getSession().getAttribute("user")).getId(), friend = Integer.parseInt(request.getParameter("friend"));
        Blogfriends bf = new Blogfriends();
        bf.setHost(host);
        bf.setFriend(friend);
        Hyberbin hyberbin = new Hyberbin(bf);
        hyberbin.setParmeter(host);
        hyberbin.setParmeter(friend);
        hyberbin.update("delete from blogfriends where host=? and friend=?");
    }
}
