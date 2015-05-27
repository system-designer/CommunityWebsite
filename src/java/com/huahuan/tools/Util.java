package com.huahuan.tools;

import com.huahuan.table.Hdjy;
import com.huahuan.table.Sthd;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.apache.commons.fileupload.FileItem;
import org.jplus.hyb.database.Hyberbin;

/**
 * 项目中通用的工具类
 *
 * @author evance
 */
public class Util {

    /**
     * 用来检查字符串是否为空，为空返回true，否则返回false
     *
     * @param str 字符串
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0 || str.equals("");
    }

    /**
     * 判断一个字符串是否为整数，是返回true
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        return str != null && str.trim().length() > 0 && str.matches("^[0-9]\\d*$");
    }

    /**
     * 判断一个字符串是否为浮点数，是返回true
     *
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {
        return str != null && str.trim().length() > 0 && str.matches("^([0-9]+|[0-9]+\\.{0,1}[0-9]{1,2})$");
    }

    /**
     * 上传一般模块的图片
     *
     * @param items
     * @param dir
     * @return
     */
    public static String upload(List<FileItem> items, String dir) {
        String filePath = "";
        String fileName;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
        if (items == null || items.isEmpty()) {
            return "";
        }
        for (FileItem item : items) {
            if (!item.isFormField()) {
                try {
                    String type = item.getName().substring((item.getName().lastIndexOf(".")));
                    fileName = item.getName().substring(0, item.getName().indexOf("."));
                    fileName = fileName + "(" + format.format(date) + ")" + type;
                    if (!Constants.PICTURE_TYPE.contains(type.toLowerCase())) {//限制文件上传类型
                        return "";
                    }
                    File tempFile = new File(dir);
                    if (!tempFile.exists()) {//如果文件夹不存在
                        tempFile.mkdirs();//创建一个新的空文件夹
                    }
                    zoomOut(item.getInputStream(), fileName, dir);
                    filePath = dir + "\\" + fileName;
                } catch (Exception ex) {
                    Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return filePath;
    }

    /**
     * 上传活动剪影模块的图片
     *
     * @param items
     * @param dir
     * @return
     */
    public static String uploadHdjy(List<FileItem> items, String dir, int hdid) {
        String filePath = "";
        String fileName;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
        if (items == null || items.isEmpty()) {
            return "";
        }
        for (FileItem item : items) {
            if (!item.isFormField()) {
                try {
                    String type = item.getName().substring((item.getName().lastIndexOf(".")));
                    fileName = item.getName().substring(0, item.getName().indexOf("."));
                    fileName = fileName + "(" + format.format(date) + ")" + type;
                    if (!Constants.PICTURE_TYPE.contains(type.toLowerCase())) {//限制文件上传类型
                        return "";
                    }
                    File tempFile = new File(dir);
                    if (!tempFile.exists()) {//如果文件夹不存在
                        tempFile.mkdirs();//创建一个新的空文件夹
                    }
                    zoomOut(item.getInputStream(), fileName, dir);
                    filePath = dir + "\\" + fileName;
                    //把上传的活动剪影的信息同步写入数据库
                    String filename = dir + "\\" + fileName;
                    String temp = filename.substring(filename.indexOf("image") - 1);
                    String databaseName = temp.replaceAll("\\\\", "/");
                    Sthd sthd = new Sthd();
                    sthd.setHdid(hdid);
                    Hyberbin hyb = new Hyberbin(sthd, true);
                    sthd = hyb.showOne("select hdid,hdsj from sthd");
                    Hdjy hdjy = new Hdjy();
                    hyb.changeTable(hdjy);
                    hdjy.setHdid(hdid);
                    hdjy.setJytp(databaseName);
                    hdjy.setJysj(sthd.getHdsj());
                    hyb.insert("jyid");
                    hyb.reallyClose();
                } catch (Exception ex) {
                    Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return filePath;
    }

    /**
     * 将比较大的图片按比例缩小图片 例如几M的图片刚将缩小为宽度为1024的图片缩小后大概只有几百K
     *
     * @param in 图片的输入流
     * @param imgName 图片的名称
     * @param dir 存放的路径
     * @return
     */
    public static boolean zoomOut(InputStream in, String imgName, String dir) {
        boolean zoom = false;
        int new_w, new_h;
        File file = new File(dir + "\\" + imgName);
        try {
            if (!file.exists()) {//如果文件不存在，则创建新的空文件
                file.createNewFile();
            }
            BufferedImage image = ImageIO.read(in);
            int width = image.getWidth();
            int height = image.getHeight();
            new_w = width;
            new_h = height;
            if (width > Constants.MAX_WIDTH) {//按比例缩小图片
                new_w = Constants.MAX_WIDTH;
                new_h = new_w * height / width;
            }
            BufferedImage result = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
            Graphics gs = result.createGraphics();
            gs.drawImage(image, 0, 0, new_w, new_h, null);
            gs.dispose();
            FileOutputStream out = new FileOutputStream(file);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(result);
            out.close();
        } catch (IOException e) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, e);
        }
        return zoom;
    }

    /**
     * 批量删除时用到的简便方法
     *
     * @param hyb 数据库操作
     * @param str 批量值
     * @param fieldName 删除的条件
     * @return
     */
    public static String getDeleteSql(Hyberbin hyb, String[] str, String fieldName) {
        String sql = "";
        for (int i = 0; i < str.length; i++) {//得到所要删除的主贴的id
            hyb.addParmeter(str[i]);
            sql += " or " + fieldName + "=?";
        }
        sql = sql.substring(3);
        return sql;
    }
}
