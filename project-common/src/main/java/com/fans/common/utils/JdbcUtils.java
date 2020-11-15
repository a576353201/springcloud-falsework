package com.fans.common.utils;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * className: JdbcUtils
 *
 * @author k
 * @version 1.0
 * @description 数据库连接类 说明:封装了 无参，有参 调用
 * @date 2018/11/20 9:50
 **/
@Slf4j
public class JdbcUtils {

    /**
     * 创建数据库连接 对象
     */
    private Connection connection = null;
    /**
     * 创建PreparedStatement对象
     */
    private PreparedStatement preparedStatement = null;
    /**
     * 创建CallableStatement对象
     */
    private final CallableStatement callableStatement = null;
    /**
     * 创建结果集对象
     */
    private ResultSet resultSet = null;

    /**
     * 建立数据库连接
     */
    private Connection getConnection() {
        Properties properties = new Properties();
        try {
            String classPath = this.getClass()
                    .getResource("/")
                    .getPath()
                    .replace("/classes", "")
                    + "config/ds.properties";
            FileInputStream stream = new FileInputStream(classPath);
            properties.load(stream);
            String driver = properties.getProperty("jdbc.driver");
            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭所有资源
     */
    private void closeAll() {
        // 关闭结果集对象
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                log.error("SQLException is {}", e.getMessage());
            }
        }

        // 关闭PreparedStatement对象
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                log.error("SQLException is {}", e.getMessage());
            }
        }

        // 关闭CallableStatement 对象
        if (callableStatement != null) {
            try {
                callableStatement.close();
            } catch (SQLException e) {
                log.error("SQLException is {}", e.getMessage());
            }
        }

        // 关闭Connection 对象
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("SQLException is {}", e.getMessage());
            }
        }
    }

    /**
     * description: 增删改
     *
     * @param sql    sql语句
     * @param params 入参
     * @return int 影响行数
     * @author k
     * @date 2018/11/20 9:52
     **/
    public int update(String sql, Object[] params) {
        int affectedLine = 0;
        try {
            executeSql(sql, params);
            affectedLine = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQLException is {}", e.getMessage());
        } finally {
            closeAll();
        }
        return affectedLine;
    }

    /**
     * description: 执行sql语句
     *
     * @param sql    sql语句
     * @param params 入参
     * @author k
     * @date 2018/11/20 9:52
     **/
    private void executeSql(String sql, Object[] params) throws SQLException {
        connection = this.getConnection();
        preparedStatement = connection.prepareStatement(sql);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
    }

    /**
     * description: 查询 结果放入ResultSet中
     *
     * @param sql    sql语句
     * @param params 入参
     * @return java.sql.ResultSet
     * @author k
     * @date 2018/11/20 9:52
     **/
    public ResultSet query(String sql, Object[] params) {
        try {
            executeSql(sql, params);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            log.error("SQLException is {}", e.getMessage());
        }
        return resultSet;
    }

    /**
     * description: 获取结果集，并将结果放在List中
     *
     * @param sql    sql语句
     * @param params 入参
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @author k
     * @date 2018/11/20 9:52
     **/
    public List<Map<String, Object>> getListMap(String sql, Object[] params) {
        ResultSet rs = query(sql, params);
        ResultSetMetaData resultSetMetaData = null;
        int columnCount = 0;
        try {
            resultSetMetaData = rs.getMetaData();
            columnCount = resultSetMetaData.getColumnCount();
        } catch (SQLException e1) {
            log.error("SQLException is {}", e1.getMessage());
        }
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Map<String, Object> map = Maps.newHashMap();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(resultSetMetaData.getColumnLabel(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            log.error("SQLException is {}", e.getMessage());
        } finally {
            // 关闭所有资源
            closeAll();
        }
        return list;
    }

    /**
     * @Description:
     * @Param: [sql, params]
     * @return: java.util.Map<java.lang.String, java.lang.Object> 结果集
     * @Author: fan
     * @Date: 2018/11/20 9:52
     **/
    /**
     * description: 获取结果集，并将结果放在Map中
     *
     * @param sql    sql语句
     * @param params 入参
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author k
     * @date 2018/11/20 9:52
     **/
    public Map<String, Object> getMap(String sql, Object[] params) {
        ResultSet rs = query(sql, params);
        ResultSetMetaData resultSetMetaData;
        int columnCount;
        Map<String, Object> map = Maps.newHashMap();
        try {
            resultSetMetaData = rs.getMetaData();
            columnCount = resultSetMetaData.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    map.put(resultSetMetaData.getColumnLabel(i), rs.getObject(i));
                }
            }
        } catch (SQLException e) {
            log.error("SQLException is {}", e.getMessage());
        } finally {
            closeAll();
        }
        return map;
    }
}
