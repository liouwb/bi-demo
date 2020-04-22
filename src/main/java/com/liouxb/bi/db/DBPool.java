package com.liouxb.bi.db;

import com.liouxb.bi.entity.req.TestConnectReq;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库连接池
 *
 * @author liouwb
 */
@Component
public class DBPool {
    private static DBPool instance = new DBPool();

    /**
     * 存放所有数据库链接
     */
    public static Map<String, DataSource> dbMap = new HashMap<>();

    private DBPool() {
    }

    public static DBPool getInstance() {
        return instance;
    }

    /**
     * 测试数据库链接
     *
     * @param c3p0 数据库配置
     * @return
     */
    public boolean testConnection(ComboPooledDataSource c3p0) {

        Statement statement = null;
        ResultSet rs = null;
        try {
            statement = c3p0.getConnection().createStatement();
            rs = statement.executeQuery("select 1");

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }

                if (statement != null) {
                    statement.close();
                }
//                c3p0.close();
            } catch (Exception e) {

            }
        }

        return true;
    }


    /**
     * 创建数据源，并存放到池中
     *
     * @param key      数据源名称（不可重复）
     * @param driver   数据库驱动
     * @param url      数据库链接
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public DBPool create(String key, String driver, String url, String username, String password) throws PropertyVetoException {
        if (!dbMap.containsKey(key)) {
            ComboPooledDataSource pool = new ComboPooledDataSource();
            pool.setDataSourceName(key);
            pool.setDriverClass(driver);
            pool.setJdbcUrl(url);
            pool.setUser(username);
            pool.setPassword(password);
            pool.setDebugUnreturnedConnectionStackTraces(true);
            pool.setUnreturnedConnectionTimeout(60);

            dbMap.put(key, pool);
        }
        return this;
    }

    public DBPool create(String key, DataSource dataSource) {
        dbMap.put(key, dataSource);
        return this;
    }

    /**
     * 获取数据源
     *
     * @param key
     * @return
     */
    public DataSource getDataSource(String key) {
        return dbMap.get(key);
    }

    /**
     * 获取用于交换/汇集的数据库
     *
     * @return
     */
    public DataSource getBIExchangeDataSource() {
        return dbMap.get("BIExchange");
    }

    /**
     * 获取数据库链接
     *
     * @param key
     * @return
     * @throws SQLException
     */
    public Connection getConnection(String key) throws SQLException {
        DataSource pool = dbMap.get(key);
        if (pool == null) {
            return null;
        } else {
            return pool.getConnection();
        }
    }

    /**
     * 销毁指定数据源
     *
     * @param key
     */
    public void destroy(String key) {
        DataSource pool = dbMap.get(key);
        if (pool != null) {
            if (pool instanceof ComboPooledDataSource) {
                ((ComboPooledDataSource) pool).close();
            }
            dbMap.remove(key);
        }
    }

    /**
     * 销毁所有数据源
     */
    public void destroyAll() {
        for (String s : dbMap.keySet()) {
            destroy(s);
        }
        dbMap.clear();
    }

    /**
     * 数据源数量
     *
     * @return
     */
    public int size() {
        return dbMap.size();
    }


    /**
     * 获取ComboPooledDataSource
     *
     * @param req 数据库配置信息
     * @return ComboPooledDataSource对象
     */
    public ComboPooledDataSource getComboPooledDataSource(TestConnectReq req) {
        ComboPooledDataSource c3p0 = new ComboPooledDataSource();
        try {
            c3p0.setDriverClass(req.getDriver());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
//        c3p0.setJdbcUrl(req.getJbdcUrl());
        // jdbc-url拼接
        StringBuilder builder = new StringBuilder();

        builder.append("jdbc:mysql://")
                .append(req.getDbServer())
                .append(":")
                .append(req.getPort())
                .append("/")
                .append(req.getDataBase())
                .append("?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true");
        c3p0.setJdbcUrl(builder.toString());

        c3p0.setUser(req.getUserName());
        c3p0.setPassword(req.getPwd());
        // 10秒超时
        c3p0.setCheckoutTimeout(10000);

        return c3p0;
    }
}
