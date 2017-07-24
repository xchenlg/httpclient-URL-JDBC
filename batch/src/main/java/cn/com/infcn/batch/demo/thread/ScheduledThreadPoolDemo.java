package cn.com.infcn.batch.demo.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 创建一个定长线程池，支持定时及周期性任务执行
 * 
 * @author NOLY DAKE
 *
 */
public class ScheduledThreadPoolDemo {

    // 示例代码如下

    // 表示延迟3秒执行
    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.schedule(new Runnable() {
            public void run() {
                System.out.println("delay 3 seconds");
            }
        }, 3, TimeUnit.SECONDS);

        // 表示延迟1秒后每3秒执行一次
        ScheduledExecutorService scheduledThreadPool2 = Executors.newScheduledThreadPool(5);
        scheduledThreadPool2.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
            }
        }, 1, 3, TimeUnit.SECONDS);
    }
}
