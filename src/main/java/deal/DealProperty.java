package deal;

import entity.ReportDetail;
import net.sf.json.JSONArray;
import util.DateUtils;
import util.JdbcUtil;

import java.io.File;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2018/6/24.
 */
public class DealProperty {
//   private static final Logger log = LoggerFactory.getLogger(DealProperty.class);
    public static class DealClass extends Thread{
        private int threadId;
        private CountDownLatch latch;
        private Map<String,String> tableMap;
        private BlockingQueue<ReportDetail> queue;
        private String date;
        public DealClass(int threadId,CountDownLatch latch,Map<String,String> tableMap,BlockingQueue<ReportDetail> queue,String date){
            this.threadId = threadId;
            this.latch = latch;
            this.tableMap = tableMap;
            this.queue = queue;
            this.date = date;
        }
        @Override
        public void run(){
            try {
//                dealPro(threadId);
                dealT(threadId,queue);
            }catch(Exception e){

                e.printStackTrace();
            }
            latch.countDown();
        }


        public static List<String>  getFileList(String basePath){
            List<String> list = new ArrayList<String>();

            File file = new File(basePath);
            if(file.exists()){
                File[] fileList = file.listFiles();
                if(fileList.length == 0){
                    return null;
                }
                for(File filePath:fileList){
                    if(filePath.isDirectory()){
                        getFileList(filePath.getAbsolutePath());
                    }else{
                        String path = filePath.getName();
                        list.add(path);
//                    System.out.println(path);
                    }
                }
            }else{
                System.out.println("文件夹不存在");
                return null;
            }
            return list;
        }

        private void dealTable(String tableName,String tableComment,BlockingQueue<ReportDetail> queue,String date) throws  Exception{
//            String date = DateUtils.getYesterDayString();
            Connection conn = JdbcUtil.getConn();
        SqlConfig config = new SqlConfig(tableName,conn);
        //获取表字段
            int tableIndex = config.getCount();
        //获取详情
           Map<Integer,String> map = config.getColumnDetail();
           //获取0值字段
            List<Integer> list = config.getZeroList(tableIndex,date);

            List<String> resultList = new ArrayList<String>();
//            String table = tableComment + "("+date+")";
//            resultList.add(table);
            for(Integer i:list){
                resultList.add(map.getOrDefault(i,""));
            }
            if(resultList == null ||resultList.size() <= 0){
                return;
            }
            JSONArray array = JSONArray.fromObject(resultList);

            ReportDetail detail = new ReportDetail();
            detail.setDate(date);
            detail.setReason(ProblemSourceEnum.ZERO_RESON.getReason());
            detail.setReportComment(tableComment);
            detail.setReportDetail(array.toString());
//            detail.setReportName(tableName);
            detail.setSource(ProblemSourceEnum.ZERO_RESON.getSource());

            if(!queue.offer(detail,2, TimeUnit.SECONDS)){
                System.out.println("数据生产失败");
                return;
            }else{
                System.out.println("数据生产成功");
            }
//            System.out.println(array.toString());
        }

        private void dealProperty(String configName){
            Properties prop = new Properties();
            try {
                prop.load(new InputStreamReader(TestConfig.class.getClassLoader().getResourceAsStream("config/"+configName), "GBK"));
                String report_name = (String) prop.get("datasource.jdbc.report_name");
                char index = report_name.charAt(0);
                Character.UnicodeBlock ub = Character.UnicodeBlock.of(index);
                if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                        || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                        || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                        || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT
                        || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                        || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                        || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                        || ub == Character.UnicodeBlock.VERTICAL_FORMS) {
                    System.out.println("未乱码");
                } else {
                    System.out.println("乱码了");
                }
                System.out.println(prop.get("datasource.jdbc.report_name"));
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        public void dealPro(int threadId) throws  Exception{
            String re = System.getProperty("user.dir");
            String path = re + "\\src\\config";
            List<String> fileList = getFileList(path);

            for(String file:fileList){
//                System.out.println("到这了吗");
                //给每个文件分配唯一的md5给每个线程
                byte[] fileNameHash =getfileNameHash(file);
//                System.out.println(Thread.currentThread().getName()+":"+threadId+","+Math.abs((int)fileNameHash[fileNameHash.length - 1] % TestConfig.THREAD_NUM));
                if(Math.abs((int)fileNameHash[fileNameHash.length - 1] % TestConfig.THREAD_NUM) != threadId){
                    continue;
                }
                System.out.println("到这了");
                dealProperty(file.toString());

                if(file == null || file == "" ){
                    return;
                }
            }
            return;
        }
        //使用线程处理表
        public void dealT(int threadId,BlockingQueue<ReportDetail> queue) {
            if(tableMap.isEmpty()){
                return;
            }
            //对每个表单独处理
            for(Map.Entry<String,String> map1:tableMap.entrySet()){
                try {
                    String tableName = (String)map1.getKey();
                    String tableComment = (String)map1.getValue();
                    byte[] fileNameHash = getfileNameHash(tableName);
//                System.out.println(Thread.currentThread().getName()+":"+threadId+","+Math.abs((int)fileNameHash[fileNameHash.length - 1] % TestConfig.THREAD_NUM));
                    if(Math.abs((int)fileNameHash[fileNameHash.length - 1] % TestConfig.THREAD_NUM) != threadId){
                        continue;
                    }
//                System.out.println("到这了");
                    dealTable(tableName,tableComment,queue,date);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }


        private byte[] getfileNameHash(String fileName) throws Exception{
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(fileName.getBytes());
                byte[] fileNameHash  =md5.digest();
          return fileNameHash;
        }
    }

}
