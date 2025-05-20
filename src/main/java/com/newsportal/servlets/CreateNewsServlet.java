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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

@WebServlet("/create-news")
public class CreateNewsServlet extends HttpServlet {
    private final NewsDao newsDao = new NewsDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        req.getRequestDispatcher("/views/create-news.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String content = req.getParameter("content");
        String tags = req.getParameter("tags");

        try {
            News news = new News();
            news.setUserId(user.getId());
            news.setTitle(title);
            news.setDescription(description);
            news.setContent(content);
            news.setLastChange(Timestamp.valueOf(LocalDateTime.now()));

            if (tags != null && !tags.isEmpty()) {
                String[] tagsArray = Arrays.stream(tags.split(","))
                        .map(String::trim)
                        .toArray(String[]::new);
                news.setTags(tagsArray);
            }

            newsDao.save(news);
            session.setAttribute("message", "Новость успешно создана");
            resp.sendRedirect(req.getContextPath() + "/news");
        } catch (Exception e) {
            session.setAttribute("error", "Ошибка при создании новости: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/create-news");
        }
    }
}