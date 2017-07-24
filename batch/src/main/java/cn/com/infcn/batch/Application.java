package cn.com.infcn.batch;

import cn.com.infcn.batch.util.ConnectionManager;

/**
 * Hello world!
 *
 */
public class Application {

    private static Application instance = null;

    public static Application getInstance() {

        if (instance == null) {
            instance = new Application();
        }

        return instance;
    }

    public void startApp() {
        System.out.println(ConnectionManager.getConnection());
    }
}
