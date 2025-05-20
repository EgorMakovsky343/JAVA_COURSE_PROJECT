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

@WebServlet("/bookmarks")
public class BookmarksServlet extends HttpServlet {
    private final BookmarkDao bookmarkDao = new BookmarkDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        List<Bookmark> bookmarks = bookmarkDao.findByUserId(user.getId());
        req.setAttribute("bookmarks", bookmarks);
        req.getRequestDispatcher("/views/bookmarks.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/auth");
            return;
        }

        try {
            Long bookmarkId = Long.parseLong(req.getParameter("bookmarkId"));
            bookmarkDao.removeBookmark(bookmarkId); // Теперь этот метод принимает только ID закладки
            session.setAttribute("message", "Закладка успешно удалена");
        } catch (NumberFormatException e) {
            session.setAttribute("error", "Неверный ID закладки");
        }

        resp.sendRedirect(req.getContextPath() + "/bookmarks");
    }
}