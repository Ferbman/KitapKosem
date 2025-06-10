package com.kitapkosem.controller;

import com.kitapkosem.dao.BookDAO;
import com.kitapkosem.model.Book;
import com.kitapkosem.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {

    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loggedInUser = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        if (loggedInUser == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int userId = loggedInUser.getUserId();

            Book bookToDelete = bookDAO.getBookById(bookId);
            if (bookToDelete != null && bookToDelete.getAddedByUserId() == userId) {
                String imagePath = bookToDelete.getImagePath();
                if (imagePath != null && !imagePath.isEmpty()) {
                    String fullPath = getServletContext().getRealPath("/") + imagePath;
                    File imageFile = new File(fullPath);
                    if (imageFile.exists()) {
                        imageFile.delete();
                    }
                }
            }

            bookDAO.deleteBook(bookId, userId);
            response.sendRedirect(request.getContextPath() + "/myBooks");
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Geçersiz Kitap ID'si.");
        }
    }
}