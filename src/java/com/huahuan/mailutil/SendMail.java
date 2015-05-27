/*
 * 
 * 
 */
package com.huahuan.mailutil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author 菡枫
 */
public class SendMail {

//    public static String email = Statics.getProperties("mail_addr");
    public static String email = "hbnudandangzhe@qq.com";
//    public static String pass = Statics.getProperties("mail_pass");
    public static String pass = "112358@my";

    /**
     * 初始化邮箱设置
     *
     * @param mailInfo
     */
    private static void mailIni(MailSenderInfo mailInfo) {
//        mailInfo.setMailServerHost(Statics.getProperties("mail_server_host"));
        mailInfo.setMailServerHost("smtp.exmail.qq.com");
        mailInfo.setValidate(true);
        mailInfo.setUserName(email);
        mailInfo.setPassword(pass);//您的邮箱密码    
        mailInfo.setFromAddress(email);
    }

    /**
     * 调用该方法单发邮件
     *
     * @param request
     * @param toMail
     * @param MailTitle邮件主题
     * @param MailContent 邮件类容
     * @param MailType 以哪种格式发送邮件，小于1：文本，单发；其他：html,单发
     */
    public static void sendEmail(HttpServletRequest request, String toMail, String MailTitle, String MailContent, Integer MailType) {
        //这个类主要是设置邮件   
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailIni(mailInfo);
        mailInfo.setToAddress(toMail);
        mailInfo.setSubject(MailTitle);
        mailInfo.setContent(getEmailContent(request, MailContent));
        //这个类主要来发送邮件  
        if (MailType >= 1) {
            SimpleMailSender.sendHtmlMail(mailInfo);//发送html格式   
        } else {
            SimpleMailSender.sendTextMail(mailInfo); //发送文体格式   
        }
    }

    /**
     * 调用该方法群发邮件
     *
     * @param request
     * @param toMail
     * @param MailTitle邮件主题
     * @param MailContent 邮件类容
     * @param MailType 以哪种格式发送邮件，小于1：文本，群发；其他：html,群发
     */
    public static void sendGroupEmail(HttpServletRequest request, String[] toMail, String MailTitle, String MailContent, Integer MailType) {
        //这个类主要是设置邮件   
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailIni(mailInfo);
        mailInfo.setToGroupAddress(toMail);
        mailInfo.setSubject(MailTitle);
        mailInfo.setContent(getEmailContent(request, MailContent));
        //这个类主要来发送邮件  
        if (MailType >= 1) {
            SimpleMailSender.sendGroupHtmlMail(mailInfo);//发送html格式   
        } else {
            SimpleMailSender.sendGroupTextMail(mailInfo); //发送文体格式   
        }
    }

    private static boolean isVaildEmail(String email) {
        //普通格式
        //String emailPattern = "[a-zA-Z0-9][a-zA-Z0-9._-]{2,16}[a-zA-Z0-9]@[a-zA-Z0-9]+.[a-zA-Z0-9]+";
        //vip格式
        //String emailPattern = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        //boolean result = Pattern.matches(emailPattern, email);
        //return result;

        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        return m.find();
    }

    public static String getEmailContent(HttpServletRequest request, String content) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        String emainHeader = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
                + "<html xmlns=\"http://www.w3.org/1999/xhtml\">	\n"
                + "    <body>\n"
                + "        <div style=\"height:510px;width:619px;background:url(" + basePath + "web/image/404_not.jpg) no-repeat;margin:0 auto;\">\n"
                + "            <div style=\"height:160px;\"></div>\n"
                + "            <div style=\"height:235px;border:0px solid #FFF;padding:0px 35px;\">";

        String emailEnd = "</div>\n"
                + "            <div style=\"height:30px;padding:0px 40px;font-size:14px;color:#747E75;\">\n"
                + "                联系我们&nbsp;&nbsp;http://www.cs.hbnu.edu.cn/huahuan&nbsp;&nbsp;QQ:897929321\n"
                + "            </div>\n"
                + "        </div>\n"
                + "    </body>\n"
                + "</html>";
        return emainHeader + content + emailEnd;

    }
}
