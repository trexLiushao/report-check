package deal;

/**
 * Created by admin on 2018/6/26.
 */
public enum ProblemSourceEnum {

    ZERO_RESON(0,"数据为0的原因可能是ks3日志未上报，需要查看kingsight下面sql对应的库地址"),
    ONE_REASON(1,"数据溢出的原因可能是脚本执行多次，或者查询数据源是否上报两份"),;
    private int source;
    private String reason;
    ProblemSourceEnum(int source, String reason){
        this.source = source;
        this.reason = reason;
    }

    public void setSource(int source){
        this.source = source;
    }

    public int getSource(){
        return this.source;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public String getReason(){
        return this.reason;
    }
}
