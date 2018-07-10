package util;

import com.mysql.jdbc.Connection;
import deal.TestConfig;

import java.io.InputStreamReader;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * Created by admin on 2018/6/25.
 */
public class JdbcUtil {
    public static Connection conn;
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<Connection>();

    public static Connection getConn(){

            Connection conn = connContainer.get();
            if(conn == null){

                Properties prop = new Properties();
                try {
                    prop.load(new InputStreamReader(TestConfig.class.getClassLoader().getResourceAsStream("config.properties"),"UTF-8"));
                String jdbcDriver = (String)prop.getProperty("mysql.jdbc.driver");
                Class.forName(jdbcDriver);
                String encodeUrl = (String)prop.getProperty("mysql.jdbc.url");
                String url = AESUtil.aesDecode(encodeUrl);
                String enCodeuser = (String)prop.getProperty("mysql.jdbc.username");
                String user = AESUtil.aesDecode(enCodeuser);
                String password = (String)prop.getProperty("mysql.jdbc.password");
//                System.out.println(jdbcDriver+","+user+","+password);
                conn = (Connection) DriverManager.getConnection(url,user,password);
                }catch(Exception e){
                    e.printStackTrace();
                }
                connContainer.set(conn);

            }

        return conn;
    }
    public static void closeCon(){
        if(conn == null){
            return;
        }
       try {
            conn.close();
       }catch(Exception e){
            e.printStackTrace();
       }
    }
}
