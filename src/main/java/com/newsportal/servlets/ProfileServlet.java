package com.newsportal.servlets;

import com.newsportal.dao.impl.UserDaoImpl;
import com.newsportal.dao.interfaces.UserDao;
import com.newsportal.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        // Обновляем данные пользователя из БД перед отображением
        User updatedUser = userDao.findById(user.getId());
        req.getSession().setAttribute("user", updatedUser);

        req.setAttribute("user", updatedUser);
        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        String currentPassword = req.getParameter("currentPassword");

        if (!userDao.verifyPassword(sessionUser.getId(), currentPassword)) {
            req.setAttribute("error", "Неверный текущий пароль");
            doGet(req, resp);
            return;
        }

        try {
            User updatedUser = userDao.findById(sessionUser.getId());
            updatedUser.setUserName(req.getParameter("username"));

            String birthDateStr = req.getParameter("birthDate");
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                updatedUser.setDateOfBirth(LocalDate.parse(birthDateStr));
            }

            updatedUser.setGender(req.getParameter("gender"));
            updatedUser.setInterests(req.getParameter("interests"));

            String newPassword = req.getParameter("newPassword");
            if (newPassword != null && !newPassword.trim().isEmpty()) {
                updatedUser.setPassword(newPassword);
            }

            userDao.update(updatedUser);
            session.setAttribute("user", updatedUser);
            req.setAttribute("message", "Данные успешно обновлены");
        } catch (Exception e) {
            req.setAttribute("error", "Ошибка при обновлении данных: " + e.getMessage());
        }

        doGet(req, resp);
    }
}