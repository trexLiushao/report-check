package mail;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Properties;

/**
 * Created by admin on 2018/6/26.
 */
public class MailSend {

    /***
     * 发送邮件
     *@param user 发件人邮箱
     *@param authCode 登录第三方邮箱的密码
     *@param
     *
     *
     * */

    public  String sendMail(String user,String authCode,String host,String from,String to,String subject,String content) throws Exception{

        if(to != null && to != ""){
            Properties prop = System.getProperties();
            prop.setProperty("mail.transport.protocol","SMTP");
            prop.put("mail.smtp.host",host);
            prop.put("mail.smtp.auth","true");
            MailAuthenticator auth = new MailAuthenticator();
            MailAuthenticator.USER_NAME = user;
            MailAuthenticator.USER_PWD = authCode;
            Session session = Session.getInstance(prop,auth);
            session.setDebug(true);

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                if(!to.trim().equals("")){
                    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to.trim()));
                    message.setSubject(subject);
                    //正文
                    MimeBodyPart mbp = new MimeBodyPart();

                    mbp.setContent(content,"text/html;charset=utf-8");
                    Multipart mp = new MimeMultipart();//正文+附件
                    mp.addBodyPart(mbp);
                    message.setContent(mp);
                    message.setSentDate(new Date());
                    message.saveChanges();
                    Transport trans = session.getTransport("smtp");
//                    trans.send(message);
                    trans.connect(user,authCode);
                    trans.sendMessage(message,message.getAllRecipients());

                    trans.close();
                    return "success";
                }else{
                    return "false";
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args){
        MailSend mail = new MailSend();
        try {
            String res = mail.sendMail("chentianxiu@wps.cn","chen854307894",
                    "smtp.wps.cn","chentianxiu@wps.cn","chentianxiu@wps.cn","测试连接","hello");
            System.out.println(res);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
