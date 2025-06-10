package com.kitapkosem.controller;

import com.kitapkosem.dao.UserDAO;
import com.kitapkosem.model.User;

import  jakarta.servlet.ServletException;
import  jakarta.servlet.annotation.WebServlet;
import  jakarta.servlet.http.HttpServlet;
import  jakarta.servlet.http.HttpServletRequest;
import  jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
// İleride şifre hashleme için kütüphane import edilecek
// import org.mindrot.jbcrypt.BCrypt; // Örnek jBCrypt kütüphanesi

@WebServlet("/register") // register.jsp'deki form action ile aynı olmalı
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        // Servlet başlatıldığında UserDAO nesnesini oluştur
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordToStoreInDb = password;


        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPasswordHash(passwordToStoreInDb);
        try {
            boolean registrationSuccess = userDAO.registerUser(newUser);
            if (registrationSuccess) {
                System.out.println("Kullanıcı başarıyla kaydedildi: " + username);
                response.sendRedirect(request.getContextPath() + "/login.jsp?registration=success");
            } else {
                request.setAttribute("errorMessage", "Kayıt başarısız oldu. Kullanıcı adı veya e-posta zaten mevcut olabilir ya da bir veritabanı hatası oluştu.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Beklenmedik bir hata oluştu: " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}