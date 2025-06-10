package com.kitapkosem.controller;

import com.kitapkosem.dao.BookDAO;
import com.kitapkosem.model.Book;
import com.kitapkosem.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/addBook")
@MultipartConfig
public class AddBookServlet extends HttpServlet {

    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");


        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=loginRequired");
            return;
        }
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        String title = request.getParameter("title");
        String authorName = request.getParameter("authorName");
        String description = request.getParameter("description");
        Part filePart = request.getPart("bookImage");

        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";


        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        String filePath = uploadPath + File.separator + uniqueFileName;

        filePart.write(filePath);

        String dbImagePath = "uploads/" + uniqueFileName;

        Book newBook = new Book();
        newBook.setTitle(title);
        newBook.setAuthorName(authorName);
        newBook.setDescription(description);
        newBook.setImagePath(dbImagePath);
        newBook.setAddedByUserId(loggedInUser.getUserId());

        boolean isSuccess = bookDAO.addBook(newBook);

        if (isSuccess) {
            System.out.println("Kitap başarıyla eklendi: " + title);
            request.setAttribute("successMessage", "Kitap başarıyla eklendi!");
        } else {
            System.err.println("Kitap eklenirken bir hata oluştu.");
            request.setAttribute("errorMessage", "Kitap eklenirken bir hata oluştu. Lütfen tekrar deneyin.");
        }
        request.getRequestDispatcher("addBook.jsp").forward(request, response);
    }
}