package cn.com.infcn.batch.demo.thread;

import java.net.MalformedURLException;
import java.net.URL;

public class URLCheckThread2 implements Runnable {

    public void run() {

        while (true) {

            String url = URLCheck2.fetchTask();

            if (url == null) {
            	System.out.println("meile");
                return;
            }

            try {
                System.out.println(url);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
