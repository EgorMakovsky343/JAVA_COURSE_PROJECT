package com.newsportal.servlets;

import com.newsportal.dao.impl.NewsDaoImpl;
import com.newsportal.entity.News;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/news")
public class NewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NewsDaoImpl newsDao = new NewsDaoImpl();
        List<News> newsList = newsDao.getAllNonArchivedNews(); // Используем метод для неархивированных новостей
        request.setAttribute("newsList", newsList);
        request.getRequestDispatcher("/views/news.jsp").forward(request, response);
    }
}