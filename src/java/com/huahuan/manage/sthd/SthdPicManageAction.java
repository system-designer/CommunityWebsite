package com.huahuan.manage.sthd;

import com.huahuan.servletutil.ServletUtil;
import com.huahuan.tools.Constants;
import com.huahuan.tools.Util;
import com.jplus.json.JSONArray;
import com.jplus.json.JSONException;
import com.jplus.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
 *
 * @author Administrator
 */
@WebServlet(name = "SthdPicManageAction", urlPatterns = "/manage/SthdPicManageAction.jsp")
public class SthdPicManageAction extends HttpServlet {

    /**
     * 下面是模式关键字 可以自行删除和增加自定义模式，关键字一定要大写 默认模式为OTHER=0,所以OTHER不能删除
     */
    public final static int OTHER = 0;//其它
    public final static int DELETE = 6;//删除记录
    public final static int HDTPGL = 7;//删除记录
    public final static int CREATENEWFOLDER = 8;
    public final static int UPLOAD = 9;
    public final static int TPXZLB = 10;

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
     * 获得某一个目录下的所有文件
     */
    private void getIMG(String path, HttpServletRequest request, HttpServletResponse response) {
        File file = new File(path);
        String relativePath = path.substring(path.indexOf("userfiles") + "userfiles".length() + 1).replace("\\", "/");
        String[] files = file.list();
        List<Folder> fileList = new ArrayList<Folder>();
        if (files != null) {
            for (String name : files) {
                if (!name.contains(".svn")) {//避免文件中存在.svn的文件夹
                    Folder fl = new Folder();
                    fl.setName(name);
                    fl.setFullPath(relativePath + "/" + name);
                    fileList.add(fl);
                }
            }
        }
        String json = "{\"img\":" + new JSONArray(fileList) + "}";
        ServletUtil.ajaxData(json, response);
    }

    /**
     * 显示文件树结构
     */
    private void showFolderTree(HttpServletRequest request, HttpServletResponse response) {
        String folder = request.getParameter("folder");
        String root = "";
        if (!folder.contains("WEB-INF")) {//防止他人恶心查看WEB-INF下面的文件
            if (Util.isEmpty(folder) || !folder.contains("\\")) {
                root = request.getServletContext().getRealPath("/userfiles/" + folder);
            } else {
                root = folder;
            }
            File file = new File(root);
            List<Folder> folders = new ArrayList<Folder>();
            if (file.list() != null) {
                for (String name : file.list()) {
                    File temp = new File(root + "\\" + name);
                    if (temp.isDirectory() && !name.equals(".svn")) {
                        Folder fl = new Folder();
                        fl.setName(name);
                        fl.setFullPath(temp.getPath());
                        folders.add(fl);
                    }
                }
                if (folders.isEmpty()) {//执行文件显示
                    getIMG(root, request, response);
                    return;
                }
            }
            JSONArray array = new JSONArray(folders);
            ServletUtil.ajaxData("{\"folder\":" + array + "}", response);
        }
    }

    /**
     * 新建文件夹
     */
    private void createNewFolder(HttpServletRequest request, HttpServletResponse response) {
        String folder = request.getParameter("folder");
        if (!Util.isEmpty(folder) && !folder.contains("WEB-INF")) {
            String newFolder = request.getParameter("newFolder");
            File file = new File(folder + "\\" + newFolder);
            boolean create = false;
            if (!file.exists()) {
                create = file.mkdirs();
            }
            ServletUtil.ajaxData(create ? "1" : " 0", response, "html");
        }
    }

    /**
     * 删除文件操作
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        String jsonImg = request.getParameter("jsonImg");
        Integer delete = 0;
        JSONArray array = null;
        try {
            array = new JSONArray(jsonImg);
        } catch (JSONException ex) {
            delete = -1;
            Logger.getLogger(SthdPicManageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        JSONObject obj;
        File file;
        String path = "", basePath = request.getServletContext().getRealPath("/");
        try {
            if (array != null) {
                for (int i = 0; i < array.length(); i++) {
                    obj = array.getJSONObject(i);
                    path = obj.getString("path");
                    path = basePath + path.substring(path.indexOf("userfiles")).replace("/", "\\");
                    file = new File(path);
                    if (file.exists() && file.canWrite()) {//避免文件不存在或不能读写产生错误
                        boolean d = file.delete();
                        if (d) {
                            delete++;
                        }
                    }
                }
            }
        } catch (JSONException ex) {
            Logger.getLogger(SthdPicManageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServletUtil.ajaxData(delete + "", response, "html");
    }

    /**
     * 完成uploadify的上传
     */
    private void upload(HttpServletRequest request, HttpServletResponse response) {
        String folder = request.getParameter("folder");
        String hdid = request.getParameter("hdid");
        if (!Util.isEmpty(folder) && !folder.contains("WEB-INF")) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> fileList = null;
            try {
                fileList = upload.parseRequest(request);
            } catch (FileUploadException ex) {
                ex.printStackTrace(System.out);
            }
            String str = "";
            if (!Util.isEmpty(hdid)) {//上传活动剪影模块图片
                str = Util.uploadHdjy(fileList, folder, Integer.parseInt(hdid));
            } else {//其他模块图片
                str = Util.upload(fileList, folder);
            }

            ServletUtil.ajaxData((!str.equals("") || str != null) ? "1" : "0", response, "html");
        }
    }

    /**
     * 得到图片选择列表
     */
    private void tpxzlb(HttpServletRequest request, HttpServletResponse response) {
        String dir = request.getParameter("dir");
        String path = request.getServletContext().getRealPath("/userfiles" + dir);
        File file = new File(path);
        File[] files = file.listFiles();
        LinkedList<String> list = new LinkedList<String>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String temp = "/" + files[i].getPath().substring(path.indexOf("userfiles") + "userfiles".length() + 1).replace("\\", "/");
                list.add(temp);
            }
        }
        request.setAttribute("imglist", list);
        request.setAttribute("foldername", dir);
        try {
            request.getRequestDispatcher("/manage/browser.jsp").forward(request, response);
        } catch (ServletException ex) {
            Logger.getLogger(SthdPicManageAction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SthdPicManageAction.class.getName()).log(Level.SEVERE, null, ex);
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
            case DELETE:
                delete(request, response);
                break;
            case HDTPGL:
                showFolderTree(request, response);
                break;
            case CREATENEWFOLDER:
                createNewFolder(request, response);
                break;
            case UPLOAD:
                upload(request, response);
                break;
            case TPXZLB:
                tpxzlb(request, response);
                break;
        }
    }
}
