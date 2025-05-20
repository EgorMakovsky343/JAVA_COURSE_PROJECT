package com.newsportal.servlets;

import com.newsportal.dao.impl.NewsDaoImpl;
import com.newsportal.dao.interfaces.NewsDao;
import com.newsportal.entity.News;
import com.newsportal.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/edit-news")
public class EditNewsServlet extends HttpServlet {
    private final NewsDao newsDao = new NewsDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        try {
            long newsId = Long.parseLong(req.getParameter("id"));
            News news = newsDao.getById(newsId);

            if (news == null || news.getUserId() != user.getId()) {
                session.setAttribute("error", "Новость не найдена или у вас нет прав на ее редактирование");
                resp.sendRedirect(req.getContextPath() + "/news");
                return;
            }

            req.setAttribute("news", news);
            req.getRequestDispatcher("/views/edit-news.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Неверный ID новости");
            resp.sendRedirect(req.getContextPath() + "/news");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        try {
            long newsId = Long.parseLong(req.getParameter("newsId"));

            // Обработка архивации
            if (req.getParameter("archive") != null) {
                newsDao.archiveNews(newsId);
                session.setAttribute("message", "Новость успешно отправлена в архив");
                resp.sendRedirect(req.getContextPath() + "/news");
                return;
            }

            News existingNews = newsDao.getById(newsId);

            if (existingNews == null || existingNews.getUserId() != user.getId()) {
                session.setAttribute("error", "Новость не найдена или у вас нет прав на ее редактирование");
                resp.sendRedirect(req.getContextPath() + "/news");
                return;
            }

            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String content = req.getParameter("content");
            String tags = req.getParameter("tags");

            existingNews.setTitle(title);
            existingNews.setDescription(description);
            existingNews.setContent(content);

            if (tags != null && !tags.isEmpty()) {
                existingNews.setTags(Arrays.stream(tags.split(","))
                        .map(String::trim)
                        .toArray(String[]::new));
            } else {
                existingNews.setTags(new String[0]);
            }

            newsDao.update(existingNews);
            session.setAttribute("message", "Новость успешно обновлена");
            resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + newsId);
        } catch (Exception e) {
            session.setAttribute("error", "Ошибка при обновлении новости: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/news");
        }
    }
}