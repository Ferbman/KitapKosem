package com.kitapkosem.controller;

import com.kitapkosem.dao.UserDAO;
import com.kitapkosem.model.User;

import  jakarta.servlet.ServletException;
import  jakarta.servlet.annotation.WebServlet;
import  jakarta.servlet.http.HttpServlet;
import  jakarta.servlet.http.HttpServletRequest;
import  jakarta.servlet.http.HttpServletResponse;
import  jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User loggedInUser = userDAO.loginUser(username, password);

        if (loggedInUser != null) {

            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", loggedInUser);
            System.out.println("Kullanıcı başarıyla giriş yaptı: " + loggedInUser.getUsername());
            response.sendRedirect(request.getContextPath() + "/kitaplar");
            request.setAttribute("errorMessage", "Kullanıcı adı veya şifre hatalı!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}