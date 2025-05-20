package com.newsportal.servlets;

import com.newsportal.dao.impl.BookmarkDaoImpl;
import com.newsportal.dao.interfaces.BookmarkDao;
import com.newsportal.entity.Bookmark;
import com.newsportal.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/add-bookmark")
public class AddBookmarkServlet extends HttpServlet {
    private final BookmarkDao bookmarkDao = new BookmarkDaoImpl();

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

            // Удаляем предыдущие сообщения
            session.removeAttribute("message");
            session.removeAttribute("error");

            if (bookmarkDao.existsByUserAndNews(user.getId(), newsId)) {
                // Находим ID закладки для удаления
                List<Bookmark> userBookmarks = bookmarkDao.findByUserId(user.getId());
                Long bookmarkIdToRemove = null;

                for (Bookmark bookmark : userBookmarks) {
                    if (bookmark.getNewsId().equals(newsId)) {
                        bookmarkIdToRemove = bookmark.getId();
                        break;
                    }
                }

                if (bookmarkIdToRemove != null) {
                    bookmarkDao.removeBookmark(bookmarkIdToRemove);
                    session.setAttribute("message", "Новость удалена из закладок");
                }
            } else {
                // Добавляем новую закладку
                Bookmark bookmark = new Bookmark();
                bookmark.setUserId(user.getId());
                bookmark.setNewsId(newsId);
                bookmarkDao.addBookmark(bookmark);
                session.setAttribute("message", "Новость добавлена в закладки");
            }

            resp.sendRedirect(req.getContextPath() + "/news-detail?id=" + newsId);
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Неверный ID новости");
            resp.sendRedirect(req.getContextPath() + "/news");
        }
    }
}