package com.liouxb.bi.db;

import com.liouxb.bi.entity.resp.ResultSetResp;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liouwb
 */
@Component
public class SqlCommandUtil {

    /**
     * 获取数据库执行结果集合
     *
     * @param connection 数据库连接
     * @param sql        执行的sql语句
     * @return 数据库结果集
     */
    public ResultSetResp getResultSet(Connection connection, String sql) {
        ResultSetResp rsp = new ResultSetResp();

        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(sql);
            rs = statement.executeQuery();

            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                ResultSetMetaData metaData = rs.getMetaData();

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String fieldName = metaData.getColumnLabel(i);
                    map.put(fieldName, rs.getObject(fieldName));
                }

                rsp.getList().add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.close(rs, statement, connection);
        }

        return rsp;
    }

    /**
     * 关闭resultSet
     *
     * @param statement
     * @param resultSet
     */
    private void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
