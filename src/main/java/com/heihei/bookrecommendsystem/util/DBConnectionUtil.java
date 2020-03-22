package com.heihei.bookrecommendsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
    public static Connection conn = null;
    private DBConnectionUtil(){

    }
    public static Connection getConn() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName("org.gjt.mm.mysql.Driver").newInstance();
        String url = "jdbc:mysql://127.0.0.1:3306/gp?user=root&password=123456&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false";
        conn = DriverManager.getConnection(url);
        return conn;
    }
    public static void closeConn(Connection conn) throws SQLException {
        if(conn != null) {
            conn.close();
        }
    }
}
