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

@WebServlet("/add-comment")
public class AddCommentServlet extends HttpServlet {
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
            Long newsId = Long.parseLong(req.getParameter("newsId"));
            String content = req.getParameter("content");

            if (content == null || content.trim().isEmpty()) {
                session.setAttribute("error", "Комментарий не может быть пустым");
                resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + newsId);
                return;
            }

            Comment comment = new Comment();
            comment.setUserId(user.getId());
            comment.setNewsId(newsId);
            comment.setContent(content.trim());

            commentDao.save(comment);

            resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + newsId);

        } catch (NumberFormatException e) {
            session.setAttribute("error", "Неверный ID новости");
            resp.sendRedirect(req.getContextPath() + "/news");
        } catch (Exception e) {
            session.setAttribute("error", "Ошибка при добавлении комментария: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/news");
        }
    }
}