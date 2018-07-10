package freemarker;

import entity.ReportDetail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import util.DateUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/6/26.
 */
public class FreeMarkerUtil {

    public Template getTemplate(String name){
        //通过freemarker的Configuration读取相应的ftl
        //Version对应jar包的version
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
//            configuration.setDirectoryForTemplateLoading(new File("/ftl"));
            configuration.setClassForTemplateLoading(this.getClass(),"/");
            Template template = configuration.getTemplate(name);

            return template;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void print(String name, Map<String,Object> root){
        Template template = this.getTemplate(name);
        try {
            template.process(root,new PrintWriter(System.out));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //输出html文件
    public void fprint(String name,Map<String,Object> map,String outFile){
        FileWriter out = null;
        try {
            out = new FileWriter(new File("D:/freemarker/"+outFile));
            Template template = this.getTemplate(name);
            template.process(map,out);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(out != null){
                    out.close();;
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args){
        FreeMarkerUtil free = new FreeMarkerUtil();
        Map<String,Object> map = new HashMap<String, Object>();

        List<ReportDetail> list = new ArrayList<ReportDetail>();
        ReportDetail detail = new ReportDetail();
        detail.setReason("he");
        detail.setReportDetail("ni");
        detail.setReportName("hehe");
        ReportDetail detail2 = new ReportDetail();
        detail2.setReason("he2");
        detail2.setReportDetail("ni2");
        detail2.setReportName("hehe2");
        list.add(detail);
        list.add(detail2);
    map.put("date", DateUtils.getYesterDayString());
    map.put("list",list);
        free.fprint("zero_data2.ftl",map,"data2.html");
    }
}
