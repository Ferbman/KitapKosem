package com.kitapkosem.controller;

import com.kitapkosem.dao.BookDAO;
import com.kitapkosem.dao.CommentDAO;
import com.kitapkosem.dao.RatingDAO;
import com.kitapkosem.model.Book;
import com.kitapkosem.model.Comment;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/kitapDetay")
public class BookDetailServlet extends HttpServlet {

    private BookDAO bookDAO;
    private CommentDAO commentDAO;
    private RatingDAO ratingDAO;

    @Override
    public void init() throws ServletException {

        bookDAO = new BookDAO();
        commentDAO = new CommentDAO();
        ratingDAO = new RatingDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        int bookId;
        if (idStr == null || idStr.trim().isEmpty()) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Kitap ID'si belirtilmemiş.");
            return;
        }

        try {
            bookId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Geçersiz Kitap ID'si formatı.");
            return;
        }

        try {
            Book book = bookDAO.getBookById(bookId);

            if (book == null) {

                System.err.println("Kitap bulunamadı. ID: " + bookId);
                request.setAttribute("errorMessage", "Aradığınız kitap bulunamadı.");
                request.getRequestDispatcher("/kitaplar").forward(request, response);
                return;
            }


            List<Comment> comments = commentDAO.getCommentsByBookId(bookId);

            double averageRating = ratingDAO.getAverageRatingByBookId(bookId);

            request.setAttribute("book", book);
            request.setAttribute("comments", comments);
            request.setAttribute("averageRating", averageRating);

            request.getRequestDispatcher("bookDetail.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Kitap detayları getirilirken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Sayfa yüklenirken bir hata oluştu.");
        }
    }
}