package com.kitapkosem.dao;

import com.kitapkosem.model.Comment;
import com.kitapkosem.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    public List<Comment> getCommentsByBookId(int bookId) {
        List<Comment> commentList = new ArrayList<>();
        String sql = "SELECT c.*, u.username FROM Comments c JOIN Users u ON c.user_id = u.user_id WHERE c.book_id = ? ORDER BY c.comment_date DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getInt("comment_id"));
                comment.setBookId(rs.getInt("book_id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setCommentText(rs.getString("comment_text"));
                comment.setCommentDate(rs.getTimestamp("comment_date"));
                comment.setUsername(rs.getString("username"));

                commentList.add(comment);
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (CommentDAO - getCommentsByBookId): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }
        return commentList;
    }

    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO Comments (book_id, user_id, comment_text) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, comment.getBookId());
            pstmt.setInt(2, comment.getUserId());
            pstmt.setString(3, comment.getCommentText());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (CommentDAO - addComment): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            DatabaseUtil.close(pstmt);
            DatabaseUtil.close(conn);
        }
    }
    public boolean deleteComment(int commentId, int userId) {
        String sql = "DELETE FROM Comments WHERE comment_id = ? AND user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, commentId);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (CommentDAO - deleteComment): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            DatabaseUtil.close(pstmt);
            DatabaseUtil.close(conn);
        }
    }
}