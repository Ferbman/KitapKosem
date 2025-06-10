package com.kitapkosem.controller;

import com.kitapkosem.dao.BookDAO;
import com.kitapkosem.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/kitaplar")
public class BookListServlet extends HttpServlet {

    private BookDAO bookDAO;

    @Override
    public void init() throws ServletException {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            String searchQuery = request.getParameter("aramaKelimesi");
            List<Book> kitapListesi;

            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                System.out.println("Arama yapılıyor: " + searchQuery);
                kitapListesi = bookDAO.searchBooks(searchQuery);
                request.setAttribute("searchQuery", searchQuery);
            } else {
                System.out.println("Tüm kitaplar listeleniyor.");
                kitapListesi = bookDAO.getAllBooks();
            }
            request.setAttribute("kitapListesiAttribute", kitapListesi);
            request.getRequestDispatcher("kitapListesi.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Kitap listesi getirilirken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}