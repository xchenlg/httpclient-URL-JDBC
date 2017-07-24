package cn.com.infcn.batch.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionManager {
    private static final ConnectionManager instance = new ConnectionManager();
    private static ComboPooledDataSource cpds = new ComboPooledDataSource(true);

    /**
     * 此处可以不配置，采用默认也行
     */
    static {
        cpds.setDataSourceName("mydatasource");
        cpds.setJdbcUrl(PropertiesUtil.getProperty("jdbc_url"));

        try {
            cpds.setDriverClass(PropertiesUtil.getProperty("driverClassName"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setUser(PropertiesUtil.getProperty("jdbc_username"));
        cpds.setPassword(PropertiesUtil.getProperty("jdbc_password"));
        cpds.setMaxPoolSize(Integer.valueOf(PropertiesUtil.getProperty("c3p0.maxPoolSize")));
        cpds.setMinPoolSize(Integer.valueOf(PropertiesUtil.getProperty("c3p0.minPoolSize")));
        cpds.setAcquireIncrement(Integer.valueOf(PropertiesUtil.getProperty("c3p0.acquireIncrement")));
        cpds.setInitialPoolSize(Integer.valueOf(PropertiesUtil.getProperty("c3p0.initialPoolSize")));
        cpds.setMaxIdleTime(Integer.valueOf(PropertiesUtil.getProperty("c3p0.maxIdleTime")));
    }

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        return instance;
    }

    public static Connection getConnection() {
        try {
            return cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
