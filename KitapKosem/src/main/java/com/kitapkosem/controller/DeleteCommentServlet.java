package com.kitapkosem.controller;

import com.kitapkosem.dao.CommentDAO;
import com.kitapkosem.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteComment")
public class DeleteCommentServlet extends HttpServlet {

    private CommentDAO commentDAO;

    @Override
    public void init() throws ServletException {
        commentDAO = new CommentDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        if (loggedInUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bu işlemi yapmak için giriş yapmalısınız.");
            return;
        }

        try {
            int commentId = Integer.parseInt(request.getParameter("commentId"));
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int userId = loggedInUser.getUserId();
            boolean success = commentDAO.deleteComment(commentId, userId);

            if (success) {
                System.out.println("Yorum (ID: " + commentId + ") başarıyla silindi.");
            } else {
                System.err.println("Yorum silinemedi veya yetki yok. Yorum ID: " + commentId + ", Kullanıcı ID: " + userId);
            }
            response.sendRedirect(request.getContextPath() + "/kitapDetay?id=" + bookId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Geçersiz ID formatı.");
        }
    }
}