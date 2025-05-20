package com.newsportal.servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Проверка авторизации
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("auth");
            return;
        }

        request.getRequestDispatcher("/views/news.jsp").forward(request, response);
    }
}
