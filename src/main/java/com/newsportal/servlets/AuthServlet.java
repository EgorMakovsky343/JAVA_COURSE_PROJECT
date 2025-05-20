package com.newsportal.servlets;

import com.newsportal.dao.impl.UserDaoImpl;
import com.newsportal.entity.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Attempt login with: " + email + "/" + password); // Логирование

        UserDaoImpl userDao = new UserDaoImpl();
        User user = userDao.findByEmail(email);

        if (user != null) {
            System.out.println("User found: " + user.getEmail());
            System.out.println("DB pass: " + user.getPassword());
            System.out.println("Input pass: " + password);
        }

        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("news");
        } else {
            request.setAttribute("error", "Неверный логин или пароль");
            request.getRequestDispatcher("/views/login.jsp").forward(request, response);
        }
    }

}