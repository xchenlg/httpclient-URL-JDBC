package cn.com.infcn.batch.demo.thread;

import java.net.URL;

public class URLUtil {

    public static boolean canAccess(URL url) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

}
