package cn.com.infcn.batch.demo.thread;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分发
 * 
 * @author NOLY DAKE
 *
 */
public class URLCheck2 {

    private static List<String> urlList = null;

    synchronized public static String fetchTask() {

        if (urlList.size() == 0) {
            return null;
        }

        return urlList.remove(0);
    }

    public static void main(String[] args) throws MalformedURLException {

        urlList = readFile();

        List<URLCheckThread2> processList = new ArrayList<URLCheckThread2>();

        for (int i = 0; i < 10; i++) {
            URLCheckThread2 thread = new URLCheckThread2();
            processList.add(thread);
            new Thread(thread).start();
        }
    }

    private static List<String> readFile() {

        List<String> result = new ArrayList<String>();

        for (int i = 0; i < 100; i++) {
            result.add(String.valueOf(i));
        }

        return result;
    }

}
