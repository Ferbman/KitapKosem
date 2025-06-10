package com.kitapkosem.dao;

import com.kitapkosem.model.User;
import com.kitapkosem.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class UserDAO {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO Users (username, password_hash, email) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getEmail());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Kullanıcı adı veya e-posta zaten mevcut: " + e.getMessage());
            return false;
        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (UserDAO - registerUser): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            DatabaseUtil.close(pstmt);
            DatabaseUtil.close(conn);
        }
    }


    public User loginUser(String username, String plainPassword) {
        String sql = "SELECT user_id, username, password_hash, email, registration_date FROM Users WHERE username = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPasswordHash = rs.getString("password_hash");

                if (plainPassword.equals(storedPasswordHash)) {
                    user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(storedPasswordHash);
                    user.setEmail(rs.getString("email"));
                    user.setRegistrationDate(rs.getTimestamp("registration_date"));
                } else {

                    System.out.println("Şifre yanlış: " + username);
                }
            } else {

                System.out.println("Kullanıcı bulunamadı: " + username);
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (UserDAO - loginUser): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }
        return user;
    }

}