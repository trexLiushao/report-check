package deal;

import com.mysql.jdbc.Connection;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;
import entity.EmailConfig;
import entity.ReportDetail;
import freemarker.FreeMarkerUtil;
import mail.MailSend;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import util.DateUtils;
import util.ExcelDealUtil;
import util.JdbcUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

/**
 * Created by admin on 2018/6/24.
 */
public class TestConfig {
    public static int THREAD_NUM = 4;
    public void testConfig(){

    }


    public static void main(String[] args) throws Exception{

        String date = (args.length == 0 ? DateUtils.getYesterDayString():args[0]);
        BlockingQueue<ReportDetail> queue = new ArrayBlockingQueue<ReportDetail>(100000);

        List<ReportDetail> resultList = new ArrayList<ReportDetail>();
   long startTime = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(THREAD_NUM);
       Connection conn = JdbcUtil.getConn();
//        hashSet.add();
        //获取一共的表
        StringBuffer sb = new StringBuffer();
        String tables = ExcelDealUtil.getTables();
        sb.append("select TABLE_NAME,TABLE_COMMENT from information_schema.TABLES WHERE TABLE_SCHEMA='v3_stat_inter' " +
                "and table_name in(")
                .append(tables).append(")");
        System.out.println(sb.toString());
        PreparedStatement stat = conn.prepareStatement(sb.toString());
        ResultSet set = stat.executeQuery();
        //存放表名
        Map<String,String> map = new HashMap<String,String>();
        while(set.next()){
            String tableName = set.getString(1);
            String tableComment = set.getString(2);
            map.put(tableName,tableComment);
        }
        for(int threadId = 0;threadId < THREAD_NUM;threadId++) {
            DealProperty.DealClass dealProperty = new DealProperty.DealClass(threadId, latch,map,queue,date);
            dealProperty.setName("threadId="+threadId);
            dealProperty.start();
        }


//        Thread.sleep(3000);
        //消费线程消费消息
        System.out.println("消费者开始消费消息");
        DealReportThread consumer = new DealReportThread(queue,resultList);
        consumer.start();

        //线程统计结束
        try {
            latch.await();
            JdbcUtil.closeCon();
        }catch (Exception e){
            e.printStackTrace();
        }

        consumer.setFlag(true);
        if(consumer.isAlive()){
            Thread.sleep(1000);
        }

        if(consumer.count.get() != resultList.size()){
            System.out.println("线程未消费完整");
            return;
        }

        System.out.println(resultList.toString());
        if(resultList.isEmpty() || resultList.size() == 0){
            System.out.println("没有数据需要发送");
            return;
        }

        Map<String,Object> root = new HashMap<String, Object>();

        root.put("date",date);
        root.put("list",resultList);
        FreeMarkerUtil freeMarkerUtil = new FreeMarkerUtil();
        freemarker.template.Template template = freeMarkerUtil.getTemplate("zero_data2.ftl");

    String content = FreeMarkerTemplateUtils.processTemplateIntoString(template,root);
//        StringBuffer sb = new StringBuffer();
//        String line = System.getProperty("line.separator");
//        sb.append("日期：").append(date)
//                .append("\r\n");
//        for(ReportDetail detail:resultList){
//            String tableName = detail.getReportComment();//中文表名
//            String table = detail.getReportName();
//            String reportDetail = detail.getReportDetail();
//            sb.append("\n表名：")
//                    .append(tableName)
//                    .append("(")
//                    .append(table).append(")").append(line);
//            sb.append("问题字段:").append(line)
//                    .append(reportDetail).append(line);
//            sb.append("问题描述:").append(line)
//                    .append(detail.getReason()).append(line);
//        }
//        System.out.println(sb.toString());
//        JSONArray array = JSONArray.fromObject(resultList);
        List<EmailConfig> list = ExcelDealUtil.getObjectExcel();
        System.out.println("到这了吗");
        if(list.size() <= 0){
            System.out.println("没有报表需要发送");
            throw  new RuntimeException("没有报表需要发送");
        }
        for(EmailConfig email:list){
            MailSend send = new MailSend();
            System.out.println("发邮件：");
            if(email.getEmail() == null || email.getEmail()== "")
                break;
            send.sendMail(email.getEmail(),email.getAuth(),
                    "smtp.wps.cn","chentianxiu@wps.cn",email.getEmail(),"数据异常问题",content);
        }
//        MailSend send = new MailSend();
//        send.sendMail("chentianxiu@wps.cn","chen854307894",
//                "smtp.wps.cn","chentianxiu@wps.cn","chentianxiu@wps.cn","数据异常问题",content);


//        List<String> list = new ArrayList<>();
//        list.add("chen");
//        list.add("ca");
//        JSONArray js = JSONArray.fromObject(list);
//        System.out.println(js.toString());
        System.out.println(System.currentTimeMillis() - startTime);
        return;
    }
}
