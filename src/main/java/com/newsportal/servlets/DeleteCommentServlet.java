package com.newsportal.servlets;

import com.newsportal.dao.impl.CommentDaoImpl;
import com.newsportal.dao.interfaces.CommentDao;
import com.newsportal.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/delete-comment")
public class DeleteCommentServlet extends HttpServlet {
    private final CommentDao commentDao = new CommentDaoImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        try {
            Long commentId = Long.parseLong(req.getParameter("commentId"));
            Long newsId = Long.parseLong(req.getParameter("newsId"));

            // Проверяем, что пользователь является владельцем комментария
            if (commentDao.isCommentOwner(commentId, user.getId())) {
                commentDao.delete(commentId);
                session.setAttribute("message", "Комментарий успешно удален");
            } else {
                session.setAttribute("error", "Вы не можете удалить этот комментарий");
            }

            resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + newsId);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Неверный ID комментария");
            resp.sendRedirect(req.getContextPath() + "/news");
        } catch (Exception e) {
            session.setAttribute("error", "Ошибка при удалении комментария: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/news");
        }
    }
}