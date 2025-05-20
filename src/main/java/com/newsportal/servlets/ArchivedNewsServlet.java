package com.newsportal.servlets;

import com.newsportal.dao.impl.NewsDaoImpl;
import com.newsportal.entity.News;
import com.newsportal.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/archived-news")
public class ArchivedNewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/auth");
            return;
        }

        NewsDaoImpl newsDao = new NewsDaoImpl();
        List<News> newsList = newsDao.getAllArchivedNews();
        request.setAttribute("newsList", newsList);
        request.getRequestDispatcher("/views/archived-news.jsp").forward(request, response);
    }
}