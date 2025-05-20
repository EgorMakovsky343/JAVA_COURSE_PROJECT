package com.newsportal.servlets;

import com.newsportal.dao.impl.CommentDaoImpl;
import com.newsportal.dao.interfaces.CommentDao;
import com.newsportal.entity.Comment;
import com.newsportal.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/update-comment")
public class UpdateCommentServlet extends HttpServlet {
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
            String content = req.getParameter("content").trim();

            // Проверяем существование комментария
            Comment existingComment = commentDao.findById(commentId);
            if (existingComment == null) {
                session.setAttribute("error", "Комментарий не найден");
                resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + newsId);
                return;
            }

            // Проверяем права пользователя
            if (!commentDao.isCommentOwner(commentId, user.getId())) {
                session.setAttribute("error", "Вы не можете редактировать этот комментарий");
                resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + newsId);
                return;
            }

            // Проверяем содержание комментария
            if (content.isEmpty()) {
                session.setAttribute("error", "Комментарий не может быть пустым");
                resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + newsId);
                return;
            }

            // Обновляем комментарий
            Comment updatedComment = new Comment();
            updatedComment.setCommentId(commentId);
            updatedComment.setContent(content);
            commentDao.update(updatedComment);

            session.setAttribute("message", "Комментарий успешно обновлен");
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Неверный идентификатор");
        } catch (Exception e) {
            session.setAttribute("error", "Ошибка при обновлении комментария: " + e.getMessage());
        }

        resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + req.getParameter("newsId"));
    }
}