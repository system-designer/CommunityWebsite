/*
 *这个是黄迎斌的servlet框架
 * http://opdps.hbnu.edu.cn/hyberbin
 */
package com.huahuan.web.index;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.tools.Util;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 网页ckeditorweb上传图片的browser
 *
 * @author Administrator
 */
@WebServlet(name = "WebConnector", urlPatterns = "/WebConnector.jsp")
public class WebConnector extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int SHOWPICLIST = 2;//显示列表
    public final static int UPLOADFILE = 3;//显示列表

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
     * 显示文件夹已有的图片
     */
    private void showpiclist(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletContext().getRealPath("/userfiles/image/网站/论坛/用户上传/");
        File file = new File(path);
        File[] files = file.listFiles();
        LinkedList<String> list = new LinkedList<String>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                list.add("/" + files[i].getPath().substring(path.indexOf("userfiles") + "userfiles".length() + 1).replace("\\", "/"));
            }
        }
        request.setAttribute("mark", "view");
        request.setAttribute("imglist", list);
        try {
            request.getRequestDispatcher("/web/public/browser.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(WebConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 实现ckeditorweb的上传图片功能
     */
    private void uploadfile(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getServletContext().getRealPath("/userfiles/image/网站/论坛/用户上传/");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> fileList = null;
        try {
            fileList = upload.parseRequest(request);
        } catch (FileUploadException ex) {
            ex.printStackTrace(System.out);
        }
        String imgurl = Util.upload(fileList, path);
        request.setAttribute("mark", "upload");
        request.setAttribute("imgurl", "/" + imgurl.substring(imgurl.indexOf("userfiles") + "userfiles".length() + 1).replace("\\", "/"));
        try {
            request.getRequestDispatcher("/web/public/browser.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(WebConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebConnector.class.getName()).log(Level.SEVERE, null, ex);
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
            case SHOWPICLIST:
                showpiclist(request, response);
                break;
            case UPLOADFILE:
                uploadfile(request, response);
                showpiclist(request, response);
                break;
        }
    }
}
