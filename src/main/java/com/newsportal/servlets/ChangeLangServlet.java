package com.newsportal.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/change-lang")
public class ChangeLangServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String lang = request.getParameter("lang");
        HttpSession session = request.getSession();
        
        // Получаем текущий язык из сессии
        String currentLang = (String) session.getAttribute("lang");
        
        // Если текущий язык английский или не установлен, переключаем на русский
        // Если текущий язык русский, переключаем на английский
        if ("en".equals(currentLang)) {
            session.setAttribute("lang", "ru");
            session.setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", new Locale("ru"));
        } else {
            session.setAttribute("lang", "en");
            session.setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", new Locale("en"));
        }
        
        // Перенаправляем обратно на страницу, с которой пришел запрос
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect(request.getContextPath() + "/news");
        }
    }
} 