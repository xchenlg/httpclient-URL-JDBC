package cn.com.infcn.batch.demo.thread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 分发
 * 
 * @author NOLY DAKE
 *
 */
public class URLCheck1 {

    public static void main(String[] args) throws MalformedURLException {

        List<String> urlList = readFile();

        List<URLCheckThread1> processList = new ArrayList<URLCheckThread1>();

        for (int i = 0; i < 10; i++) {
            URLCheckThread1 thread = new URLCheckThread1();
            processList.add(thread);
            new Thread(thread).start();
        }

        for (String string : urlList) {

            while (true) {
                boolean flag = false;
                for (URLCheckThread1 urlCheckThread : processList) {
                    if (!urlCheckThread.isRunning()) {
                        urlCheckThread.setURL1(string);
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    break;
                }
            }
        }
        ///////////////////
    }

    private static List<String> readFile() {

        List<String> result = new ArrayList<String>();

        for (int i = 0; i < 100; i++) {
            result.add(String.valueOf(i));
        }

        return result;
    }

}
