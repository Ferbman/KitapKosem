package com.kitapkosem.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Merhaba KitapKöşem!</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Merhaba Dünya, KitapKöşem Projesinden!</h1>");
            out.println("<p>IntelliJ IDEA, Tomcat ve Servlet başarıyla çalışıyor!</p>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
}