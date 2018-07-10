package mail;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by admin on 2018/6/26.
 */

public class MailAuthenticator extends Authenticator {

    /**
     * @描述：
     * 发件人账号密码
     *
     * */
    public static String USER_NAME;

    public static String USER_PWD;
    public MailAuthenticator(){
    }

    protected PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(USER_NAME,USER_PWD);
    }
}
