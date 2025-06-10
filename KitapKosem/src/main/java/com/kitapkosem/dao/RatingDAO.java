package com.kitapkosem.dao;

import com.kitapkosem.model.Rating;
import com.kitapkosem.util.DatabaseUtil;

import java.sql.*;

public class RatingDAO {

    public boolean addOrUpdateRating(Rating rating) {
        String sql = "INSERT INTO Ratings (book_id, user_id, rating_value) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE rating_value = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, rating.getBookId());
            pstmt.setInt(2, rating.getUserId());
            pstmt.setInt(3, rating.getRatingValue());
            pstmt.setInt(4, rating.getRatingValue());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (RatingDAO - addOrUpdateRating): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            DatabaseUtil.close(pstmt);
            DatabaseUtil.close(conn);
        }
    }

    public double getAverageRatingByBookId(int bookId) {
        String sql = "SELECT AVG(rating_value) AS average FROM Ratings WHERE book_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        double average = 0.0;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                average = rs.getDouble("average");
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (RatingDAO - getAverageRatingByBookId): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }
        return average;
    }
}