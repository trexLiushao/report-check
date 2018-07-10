package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/6/29.
 */
public class EmailConfig {
    private String userid;
    private String email;
    private String auth;
    private String isSend;//0是不发送，1是发送

    private List<String> reportList;//用户下拥有权限的报表

    private String reportArray;//报表转成可被sql接收的形式

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public List<String> getReportList() {
        return reportList;
    }

    public void setReportList(List<String> reportList) {
        this.reportList = reportList;
    }

    public String getReportArray() {
        if(!reportList.isEmpty()){
            String arrayStr = "";
            StringBuffer sb = new StringBuffer();
            for(int i = 0;i < reportList.size();i++){
                if(i < reportList.size() - 1){//一直到倒数第二个元素
                    sb.append("'")
                            .append(reportList.get(i))
                            .append("',");
                }else{
                    sb.append("'")
                            .append(reportList.get(i)).append("'");
                }
                arrayStr = sb.toString();
                this.reportArray = arrayStr;
            }
        }
        return reportArray;
    }

    public void setReportArray(String reportArray) {
        this.reportArray = reportArray;
    }

    public static void main(String[] args){
        List<String> list = new ArrayList<String>();
    list.add("chen");
    list.add("tian");
    list.add("xiu");
    list.add("ni hao");
        EmailConfig config = new EmailConfig();
        config.setReportList(list);
        System.out.println(config.getReportArray());
    }
}
