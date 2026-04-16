package com.aether.tcm.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class DbUtil {
    private static DataSource dataSource;

    static {
        try {
            Properties pros = new Properties();
            // 驱动配置
            pros.setProperty("driverClassName", "com.mysql.cj.jdbc.Driver");
            // 连接地址（注意端口号 3306 和数据库名 tcm_db）
            pros.setProperty("url", "jdbc:mysql://localhost:3306/tcm_db?useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8");
            pros.setProperty("username", "root");
            // 【重要】请将 123456 修改为你真实的 MySQL 密码
            pros.setProperty("password", "005522");

            // Druid 连接池参数
            pros.setProperty("initialSize", "5");
            pros.setProperty("maxActive", "10");
            pros.setProperty("maxWait", "3000");

            dataSource = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            System.err.println(">>> [ERROR] 数据库连接池初始化失败！");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}