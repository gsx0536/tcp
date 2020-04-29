package com.tieda.db;

import com.tieda.entity.Receive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

public class SqlOperation {

    public static void insertData(Receive receive) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String insertSql = "insert into receive(infor,date,ip) values(?,?,?)";
        try {
            conn = DBUtilMysql.getConn();
            pstmt = conn.prepareStatement(insertSql);
            pstmt.setString(1, receive.getInfor());
            pstmt.setTimestamp(2, new Timestamp(receive.getDate().getTime()));
            pstmt.setString(3, receive.getIp());
            pstmt.executeUpdate();
            DBUtilMysql.commit(conn);
            if (conn != null) {
                conn.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }

        } catch (Exception e) {
            DBUtilMysql.rollback(conn);
            e.printStackTrace();
        } finally {
            DBUtilMysql.close(pstmt);
            DBUtilMysql.close(conn);
        }
    }
}
