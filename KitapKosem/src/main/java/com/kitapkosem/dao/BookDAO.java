package com.kitapkosem.dao;

import com.kitapkosem.model.Book;
import com.kitapkosem.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public boolean addBook(Book book) {
        String sql = "INSERT INTO Books (title, author_name, description, image_path, added_by_user_id) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthorName());
            pstmt.setString(3, book.getDescription());
            pstmt.setString(4, book.getImagePath()); // Resim yolu
            pstmt.setInt(5, book.getAddedByUserId()); // Ekleyen kullanıcı ID'si

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (BookDAO - addBook): " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            DatabaseUtil.close(pstmt);
            DatabaseUtil.close(conn);
        }
    }


    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM Books ORDER BY creation_date DESC"; // En yeni eklenen en üstte olsun
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthorName(rs.getString("author_name"));
                book.setDescription(rs.getString("description"));
                book.setImagePath(rs.getString("image_path"));
                book.setAddedByUserId(rs.getInt("added_by_user_id"));
                book.setCreationDate(rs.getTimestamp("creation_date"));

                bookList.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (BookDAO - getAllBooks): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }
        return bookList;
    }


    public Book getBookById(int bookId) {
        String sql = "SELECT * FROM Books WHERE book_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Book book = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            rs = pstmt.executeQuery();

            if (rs.next()) { // Kitap bulunduysa
                book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthorName(rs.getString("author_name"));
                book.setDescription(rs.getString("description"));
                book.setImagePath(rs.getString("image_path"));
                book.setAddedByUserId(rs.getInt("added_by_user_id"));
                book.setCreationDate(rs.getTimestamp("creation_date"));
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (BookDAO - getBookById): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }
        return book;
    }
    public List<Book> searchBooks(String query) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author_name LIKE ? ORDER BY creation_date DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            String searchQuery = "%" + query + "%";
            pstmt.setString(1, searchQuery); // title için arama
            pstmt.setString(2, searchQuery); // author_name için arama

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthorName(rs.getString("author_name"));
                book.setDescription(rs.getString("description"));
                book.setImagePath(rs.getString("image_path"));
                book.setAddedByUserId(rs.getInt("added_by_user_id"));
                book.setCreationDate(rs.getTimestamp("creation_date"));

                bookList.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Veritabanı hatası (BookDAO - searchBooks): " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }
        return bookList;
    }
    public List<Book> getBooksByUserId(int userId) {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE added_by_user_id = ? ORDER BY creation_date DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthorName(rs.getString("author_name"));
                book.setDescription(rs.getString("description"));
                book.setImagePath(rs.getString("image_path"));
                book.setAddedByUserId(rs.getInt("added_by_user_id"));
                book.setCreationDate(rs.getTimestamp("creation_date"));
                bookList.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseUtil.close(conn, pstmt, rs);
        }
        return bookList;
    }


    public boolean deleteBook(int bookId, int userId) {
        String sql = "DELETE FROM Books WHERE book_id = ? AND added_by_user_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseUtil.close(pstmt);
            DatabaseUtil.close(conn);
        }
    }

}