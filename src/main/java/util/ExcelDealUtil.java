package util;

import entity.EmailConfig;
import entity.ReportDetail;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by admin on 2018/6/28.
 */
public class ExcelDealUtil {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    private static final String PATH="path.properties";
    private static final String REPORT_PATH="email.config.report.path";
    private static final String EMAIL_USER_PATH="email.user.config.path";
    public static Properties getProp(){
        Properties prop = new Properties();
        try {
                prop.load(new InputStreamReader(ExcelDealUtil.class.getClassLoader().getResourceAsStream(ExcelDealUtil.PATH),"UTF-8"));
                return prop;

        }catch(Exception e){
            e.printStackTrace();
        }
    return prop;
    }

    public static Workbook getWorkBook(File file) throws  Exception{
        Workbook wb = null;
        FileInputStream in = new FileInputStream(file);
        if(file.getName().endsWith(EXCEL_XLS)) {
            wb = new HSSFWorkbook(in);
        }else if(file.getName().endsWith(EXCEL_XLSX)){
            wb = new XSSFWorkbook(in);
        }

        return wb;
    }

    /***
     * @描述 往excel写文件
     *
     *
     */
    public static void writeExcel(List<ReportDetail> list, String writePath){
        OutputStream out = null;
        try {
            File writeFile = new File(writePath);
            Workbook workbook = getWorkBook(writeFile);
            //sheet工作页
            Sheet sheet = workbook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             *
             * */
            int rowNumber = sheet.getLastRowNum();//第一行从0开始算
            for(int i = 1;i <= rowNumber;i++){
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            out = new FileOutputStream(writePath);
            workbook.write(out);

            for(int i = 0;i<list.size();i++){
                Row row = sheet.createRow(i + 1);

                String reportName = list.get(i).getReportName();
                String reportComment = list.get(i).getReportComment();
                String reportDetail = list.get(i).getReportDetail();
                int source = list.get(i).getSource();
                String reason = list.get(i).getReason();
                String date = list.get(i).getDate();
                Cell first = row.createCell(0);
                first.setCellValue(reportComment);
                Cell second = row.createCell(1);
                second.setCellValue(reportName);
                Cell thrid = row.createCell(2);
                thrid.setCellValue(reportDetail);
                Cell four = row.createCell(3);
                four.setCellValue(source);
                Cell five = row.createCell(4);
                five.setCellValue(reason);
                Cell six = row.createCell(5);
                six.setCellValue(date);
            }
            //创建电子表格，输出表格
            out = new FileOutputStream(writePath);
            workbook.write(out);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @描述 读取excel(不支持读取2007版excel)
     *
     * */
    public static List<EmailConfig> getObjectExcel() {
        Properties prop = getProp();
        if(prop ==null){
            throw new RuntimeException("找不到配置excel的path");
        }
        try {
            String path = (String)prop.getProperty(ExcelDealUtil.EMAIL_USER_PATH);
            File file = new File(path);
            if(!file.exists()){
                return null;
            }
            List<EmailConfig> resultList = new ArrayList<EmailConfig>();
            InputStream is = new FileInputStream(file.getAbsolutePath());
            jxl.Workbook wb = jxl.Workbook.getWorkbook(file);
//        int sheetSize = wb.getNumberOfSheets();
            jxl.Sheet sheet = wb.getSheet(0);
            EmailConfig email = new EmailConfig();
            for(int i = 1;i < sheet.getRows();i++){//得到sheet的总行数
                    email.setUserid(sheet.getCell(0,i).getContents());
                    email.setEmail(sheet.getCell(1,i).getContents());
                    email.setAuth(sheet.getCell(2,i).getContents());
                    email.setIsSend(sheet.getCell(3,i).getContents());
                    resultList.add(email);
                }
            return resultList;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<List<String>> readExcel(String path) {
        try {
            File file = new File(path);
            if(!file.exists()){
                return null;
            }
            InputStream is = new FileInputStream(file.getAbsolutePath());
            jxl.Workbook wb = jxl.Workbook.getWorkbook(file);
//        int sheetSize = wb.getNumberOfSheets();
            jxl.Sheet sheet = wb.getSheet(0);
            List<List<String>> resultList = new ArrayList<List<String>>();
            for(int i = 1;i < sheet.getRows();i++){//得到sheet的总行数
                List<String> innerList = new ArrayList<String>();
                for(int j =0;j < sheet.getColumns();j++){//得到sheet的总列数
                    String content = sheet.getCell(j,i).getContents();
                    innerList.add(content);
                }
                resultList.add(innerList);
            }

            return resultList;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    //获取想要通知的表
    public static String getTables() {
        Properties prop = getProp();
        if (prop == null) {
            throw new RuntimeException("配置文件找不到");
        }
        String path = (String) prop.getProperty(ExcelDealUtil.REPORT_PATH);
        List<List<String>> list = ExcelDealUtil.readExcel(path);
        List<String> resultList = new ArrayList<String>();
        //获取表list
        for (List<String> lt : list) {
            for (String s : lt) {
                if (s == null || s == "") {
                    continue;
                }
                resultList.add(s);
            }
        }
        String arrayStr = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < resultList.size(); i++) {
            if (i < resultList.size() - 1) {//一直到倒数第二个元素
                sb.append("'")
                        .append(resultList.get(i))
                        .append("',");
            } else {
                sb.append("'")
                        .append(resultList.get(i)).append("'");
            }
            arrayStr = sb.toString();
        }
        return arrayStr;
    }
    public static void main(String[] args){
     String tables = getTables();
     System.out.println(tables);
     List<EmailConfig> list = getObjectExcel();
     for(EmailConfig email:list){
         System.out.println(email.getEmail());
     }
    }
}
