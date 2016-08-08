package com.wavemaker.todo.dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by sainihala on 5/8/16.
 */
public class MysqlDBConnection {
    private static Connection e;

    public static Connection getConnection() {
        if (e != null) {
            return e;
        }
        try {
            Class.forName(MysqlDBProperties.Driver);
            e = DriverManager.getConnection(MysqlDBProperties.url, MysqlDBProperties.user, MysqlDBProperties.password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return e;
    }
}

