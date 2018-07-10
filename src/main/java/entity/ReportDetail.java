package entity;

import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2018/6/26.
 */
public class ReportDetail {
    //报表名称
    private String reportName;
    //报表描述
    private String reportComment;
    //报表监控细节
    private String reportDetail;
    private List<String> zeroColumnList;


    //报表问题来源
    private int source;
    //报表问题描述
    private String reason;
    //日期
    private String date;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportComment() {
        return reportComment;
    }

    public void setReportComment(String reportComment) {
        this.reportComment = reportComment;
    }

    public String getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(String reportDetail) {
        this.reportDetail = reportDetail;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getZeroColumnList() {
        if(reportDetail != null){
            String[] array = reportDetail.split(",");
            List<String> list = Arrays.asList(array);
            this.zeroColumnList = list;
        }
        return zeroColumnList;
    }

    public void setZeroColumnList(List<String> zeroColumnList) {
        this.zeroColumnList = zeroColumnList;
    }
}
