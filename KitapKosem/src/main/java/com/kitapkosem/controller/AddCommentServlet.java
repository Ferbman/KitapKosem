package com.kitapkosem.controller;

import com.kitapkosem.dao.CommentDAO;
import com.kitapkosem.dao.RatingDAO;
import com.kitapkosem.model.Comment;
import com.kitapkosem.model.Rating;
import com.kitapkosem.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addComment")
public class AddCommentServlet extends HttpServlet {

    private CommentDAO commentDAO;
    private RatingDAO ratingDAO;

    @Override
    public void init() throws ServletException {
        commentDAO = new CommentDAO();
        ratingDAO = new RatingDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;


        if (loggedInUser == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String commentText = request.getParameter("commentText");
        String bookIdStr = request.getParameter("bookId");
        String ratingStr = request.getParameter("rating");
        int bookId = 0;

        if (bookIdStr != null && !bookIdStr.trim().isEmpty()) {
            try {
                bookId = Integer.parseInt(bookIdStr);
            } catch (NumberFormatException e) {
                System.err.println("Geçersiz kitap ID'si: " + bookIdStr);

                response.sendRedirect(request.getContextPath() + "/kitaplar?error=invalidBookId");
                return;
            }
        } else {

            response.sendRedirect(request.getContextPath() + "/kitaplar?error=missingBookId");
            return;
        }

        if (commentText != null && !commentText.trim().isEmpty()) {
            Comment newComment = new Comment();
            newComment.setBookId(bookId);
            newComment.setUserId(loggedInUser.getUserId());
            newComment.setCommentText(commentText);

            boolean commentSuccess = commentDAO.addComment(newComment);
            if(commentSuccess) {
                System.out.println("Yorum başarıyla eklendi. Kitap ID: " + bookId);
            } else {
                System.err.println("Yorum eklenirken hata oluştu. Kitap ID: " + bookId);
            }
        }

        if (ratingStr != null && !ratingStr.trim().isEmpty()) {
            try {
                int ratingValue = Integer.parseInt(ratingStr);
                if (ratingValue >= 1 && ratingValue <= 5) {
                    Rating newRating = new Rating();
                    newRating.setBookId(bookId);
                    newRating.setUserId(loggedInUser.getUserId());
                    newRating.setRatingValue(ratingValue);

                    boolean ratingSuccess = ratingDAO.addOrUpdateRating(newRating);
                    if(ratingSuccess) {
                        System.out.println("Puan başarıyla eklendi/güncellendi. Kitap ID: " + bookId);
                    } else {
                        System.err.println("Puan eklenirken/güncellenirken hata oluştu. Kitap ID: " + bookId);
                    }
                }
            } catch (NumberFormatException e) {
                System.err.println("Geçersiz puan formatı: " + ratingStr);
            }
        }
        response.sendRedirect(request.getContextPath() + "/kitapDetay?id=" + bookId);
    }
}