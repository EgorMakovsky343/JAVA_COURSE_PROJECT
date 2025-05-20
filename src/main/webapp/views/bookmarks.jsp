<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Мои закладки</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }

        .bookmarks-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        h1 {
            color: #2c3e50;
            margin-bottom: 30px;
            text-align: center;
        }

        .bookmark-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px;
            margin-bottom: 15px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            border-left: 4px solid #f39c12;
        }

        .bookmark-info {
            flex-grow: 1;
        }

        .bookmark-title {
            font-weight: bold;
            font-size: 18px;
            margin-bottom: 5px;
            color: #2c3e50;
        }

        .bookmark-time {
            color: #7f8c8d;
            font-size: 14px;
        }

        .bookmark-actions {
            margin-left: 20px;
        }

        .bookmark-link {
            display: inline-block;
            padding: 8px 15px;
            background: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
        }

        .bookmark-link:hover {
            background: #2980b9;
        }

        .delete-btn {
            padding: 8px 15px;
            background: #e74c3c;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .delete-btn:hover {
            background: #c0392b;
        }

        .no-bookmarks {
            text-align: center;
            padding: 30px;
            color: #7f8c8d;
            background: white;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }

        .error-message {
            color: #e74c3c;
            padding: 12px;
            margin-bottom: 20px;
            background: #fdecea;
            border-radius: 4px;
            border-left: 4px solid #e74c3c;
        }

        .success-message {
            color: #27ae60;
            padding: 12px;
            margin-bottom: 20px;
            background: #e8f8f0;
            border-radius: 4px;
            border-left: 4px solid #27ae60;
        }
    </style>
</head>
<body>
<div class="bookmarks-container">
    <h1><i class="fas fa-bookmark"></i> Мои закладки</h1>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <c:if test="${not empty message}">
        <div class="success-message">${message}</div>
    </c:if>

    <c:choose>
        <c:when test="${not empty bookmarks}">
            <c:forEach items="${bookmarks}" var="bookmark">
                <div class="bookmark-item">
                    <div class="bookmark-info">
                        <div class="bookmark-title">${bookmark.newsTitle}</div>
                        <div class="bookmark-time">
                            Добавлено: <fmt:formatDate value="${bookmark.savedTime}" pattern="dd.MM.yyyy HH:mm"/>
                        </div>
                    </div>
                    <div class="bookmark-actions">
                        <a href="${pageContext.request.contextPath}/news-detail?id=${bookmark.newsId}"
                           class="bookmark-link">
                            <i class="fas fa-eye"></i> Перейти
                        </a>
                        <form action="${pageContext.request.contextPath}/bookmarks" method="post" style="display: inline;">
                            <input type="hidden" name="bookmarkId" value="${bookmark.id}">
                            <button type="submit" class="delete-btn">
                                <i class="fas fa-trash-alt"></i> Удалить
                            </button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="no-bookmarks">
                <i class="far fa-bookmark" style="font-size: 48px; margin-bottom: 15px;"></i>
                <p>У вас пока нет закладок</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</body>
</html>