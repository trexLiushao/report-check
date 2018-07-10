package deal;

import entity.ReportDetail;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by admin on 2018/6/26.
 */
public class DealReportThread extends Thread {
    private BlockingQueue<ReportDetail> queue;
    private List<ReportDetail> list;
    private boolean success=false;
    public AtomicInteger count = new AtomicInteger(0);
    public DealReportThread(BlockingQueue<ReportDetail> queue, List<ReportDetail> list){
        this.queue = queue;
        this.list = list;
    }
    public void setFlag(boolean success){
        this.success = success;
    }
    @Override
    public void run(){
        dealMessage();
    }

    public void dealMessage(){
        while(true){
            try {
//                System.out.println("到这了吗2");
                ReportDetail detail = (ReportDetail)queue.poll(2, TimeUnit.SECONDS);
//                System.out.println("到这了吗3");
                if(queue == null){
                    if(success) {
                        break;
                    }
                }
                //不能允许list种存在null值
                if(detail == null)
                    continue;
               list.add(detail);
            }catch (Exception e){
                e.printStackTrace();
            }
        count.incrementAndGet();
        }
        System.out.println("处理的消息共:"+count.get()+"条");
    }

}
