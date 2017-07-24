package cn.com.infcn.batch.demo.thread;

import java.net.URL;

public class URLCheckThread1 implements Runnable {

    private URL url = null;
    
    private String url1 = null;

    private boolean isRunning = false;

    public boolean isRunning() {
        return isRunning;
    }

    public URLCheckThread1() {
        // this.url = url;
    }

    public void setURL(URL url) {
        isRunning = true;
        this.url = url;
    }
    
    public void setURL1(String url1) {
        isRunning = true;
        this.url1 = url1;
    }

    public void run() {

        while (true) {

            if (url1 != null) {
                // 执行
                System.out.println(url1);
                isRunning = false;
            }
            

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
        }
    }
}
