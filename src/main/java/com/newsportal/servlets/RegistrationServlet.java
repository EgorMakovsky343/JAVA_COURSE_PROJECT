package com.newsportal.servlets;

import com.newsportal.dao.impl.UserDaoImpl;
import com.newsportal.dao.interfaces.UserDao;
import com.newsportal.entity.User;
import com.newsportal.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String userName = req.getParameter("userName");
        String birthDateStr = req.getParameter("birthDate");
        String gender = req.getParameter("gender");
        String interests = req.getParameter("interests");
        String role = req.getParameter("role");

        // Валидация
        if (!password.equals(confirmPassword)) {
            req.setAttribute("error", "Пароли не совпадают");
            req.getRequestDispatcher("/views/registration.jsp").forward(req, resp);
            return;
        }

        if (userService.emailExists(email)) {
            req.setAttribute("error", "Пользователь с таким email уже существует");
            req.getRequestDispatcher("/views/registration.jsp").forward(req, resp);
            return;
        }

        try {
            User user = userService.registerUser(email, password, userName, birthDateStr, gender, interests, role);
            // Автоматический вход после регистрации
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/news");
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при регистрации: " + e.getMessage());
            req.getRequestDispatcher("/views/registration.jsp").forward(req, resp);
        }
    }
}