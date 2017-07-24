package cn.com.infcn.batch.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties cache;

    public static String getProperty(String key) {

        if (cache == null) {
            initCache();
        }

        return cache.getProperty(key);
    }

    private static void initCache() {

        cache = new Properties();

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(new File(FileUtil.getInstallPath(), "config.properties"));
            cache.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}
