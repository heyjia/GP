package com.heihei.bookrecommendsystem.calculator;

import com.heihei.bookrecommendsystem.util.DBConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRateCountCalculator {
    private static final int BOOK_NUM = 56274;
    public static void main(String[] args) {
        Connection conn = null;
        int count = 0;
        try {
            conn = DBConnectionUtil.getConn();
            int bookId = 1;
            for (;bookId <= BOOK_NUM;bookId++) {
                String sql = "SELECT COUNT(1) countNum FROM user_book_score u WHERE u.`book_id` = ?";
                PreparedStatement preparedStatement1 = conn.prepareStatement(sql);
                preparedStatement1.setLong(1,bookId);
                ResultSet rs = preparedStatement1.executeQuery();
                while(rs.next()) {
                    int countNum = 0;
                    countNum = rs.getInt("countNum");
                    System.out.println(bookId + "评分人数为：" + countNum);
                    String updateSql = "UPDATE book b SET b.`rate_count` = ? WHERE b.`id` = ?";
                    PreparedStatement preparedStatement2 = conn.prepareStatement(updateSql);
                    preparedStatement2.setDouble(1,countNum);
                    preparedStatement2.setInt(2,bookId);
                    preparedStatement2.executeUpdate();
                    count++;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                }catch (Exception e) {
                    System.out.println("关闭数据库连接失败");
                    e.printStackTrace();
                }
            }
            System.out.println("计算数据评分人数结束,共更新" + count + "条数据");
        }
    }
}
