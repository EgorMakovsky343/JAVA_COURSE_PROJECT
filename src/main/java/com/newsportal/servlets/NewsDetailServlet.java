package com.newsportal.servlets;

import com.newsportal.dao.impl.BookmarkDaoImpl;
import com.newsportal.dao.impl.CommentDaoImpl;
import com.newsportal.dao.impl.NewsDaoImpl;
import com.newsportal.dao.interfaces.BookmarkDao;
import com.newsportal.dao.interfaces.CommentDao;
import com.newsportal.entity.Comment;
import com.newsportal.entity.News;
import com.newsportal.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/news-detail")
public class NewsDetailServlet extends HttpServlet {
    private final NewsDaoImpl newsDao = new NewsDaoImpl();
    private final BookmarkDao bookmarkDao = new BookmarkDaoImpl();
    private final CommentDao commentDao = new CommentDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            long newsId = Long.parseLong(request.getParameter("id"));

            // Всегда увеличиваем счетчик просмотров при заходе на страницу
            newsDao.incrementViewsCount(newsId);

            News news = newsDao.getById(newsId);
            request.setAttribute("news", news);

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null) {
                boolean isBookmarked = bookmarkDao.existsByUserAndNews(user.getId(), newsId);
                request.setAttribute("isBookmarked", isBookmarked);
            }

            List<Comment> comments = commentDao.findByNewsId(newsId);
            request.setAttribute("comments", comments);

            request.getRequestDispatcher("/views/news-detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/news");
        }
    }
}