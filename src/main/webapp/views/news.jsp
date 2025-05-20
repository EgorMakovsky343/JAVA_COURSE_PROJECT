<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="news.portal"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --primary: #2c3e50;
            --secondary: #3498db;
            --accent: #e74c3c;
            --light: #ecf0f1;
            --dark: #2c3e50;
            --gray: #95a5a6;
            --success: #2ecc71;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Roboto', sans-serif;
            line-height: 1.6;
            color: var(--dark);
            background-color: #f5f7fa;
            padding: 0;
            margin: 0;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        header {
            background-color: var(--primary);
            color: white;
            padding: 15px 0;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .header-content {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
        }

        .logo {
            font-size: 24px;
            font-weight: 700;
            color: white;
            text-decoration: none;
        }

        .user-controls {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            padding: 8px 16px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
            cursor: pointer;
            border: none;
        }

        .btn-primary {
            background-color: var(--secondary);
            color: white;
        }

        .btn-outline {
            background-color: transparent;
            border: 1px solid var(--light);
            color: white;
        }

        .news-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 25px;
            margin-top: 30px;
        }

        .news-card {
            background-color: white;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            display: flex;
            flex-direction: column;
        }

        .news-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }


        .news-content {
            padding: 20px;
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .news-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 10px;
            color: var(--dark);
            word-wrap: break-word;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }

        .news-excerpt {
            color: var(--gray);
            margin-bottom: 15px;
            font-size: 14px;
            word-wrap: break-word;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            flex: 1;
        }

        .news-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 12px;
            color: var(--gray);
            margin-top: auto;
        }

        .news-date {
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .news-views {
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .read-more {
            display: inline-block;
            margin-top: 15px;
            color: var(--secondary);
            font-weight: 500;
            text-decoration: none;
            transition: color 0.3s ease;
            align-self: flex-start;
        }

        .read-more:hover {
            color: var(--primary);
        }

        .tag {
            display: inline-block;
            background-color: var(--light);
            color: var(--dark);
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 12px;
            margin-right: 8px;
            margin-bottom: 8px;
            word-break: break-word;
        }

        .tags-container {
            margin-top: 10px;
            display: flex;
            flex-wrap: wrap;
        }

        .no-news {
            text-align: center;
            padding: 50px 20px;
            color: var(--gray);
            grid-column: 1 / -1;
        }

        @media (max-width: 768px) {
            .news-grid {
                grid-template-columns: 1fr;
            }

            .header-content {
                flex-direction: column;
                gap: 15px;
            }
        }
    </style>
</head>
<body>
<header>
    <div class="header-content">
        <a href="${pageContext.request.contextPath}/news" class="logo"><fmt:message key="news.portal"/></a>
        <div class="user-controls">
            <c:if test="${not empty user}">
                <c:if test="${user.admin}">
                    <a href="${pageContext.request.contextPath}/create-news" class="btn btn-primary">
                        <i class="fas fa-plus"></i> <fmt:message key="button.create.news"/>
                    </a>
                    <a href="${pageContext.request.contextPath}/archived-news" class="btn btn-outline">
                        <i class="fas fa-archive"></i> <fmt:message key="button.archive"/>
                    </a>
                </c:if>
                <a href="${pageContext.request.contextPath}/profile" class="btn btn-outline">
                    <i class="fas fa-user"></i> <fmt:message key="button.profile"/>
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline">
                    <i class="fas fa-sign-out-alt"></i> <fmt:message key="button.logout"/>
                </a>
            </c:if>
            <c:if test="${empty user}">
                <a href="${pageContext.request.contextPath}/auth" class="btn btn-primary">
                    <i class="fas fa-sign-in-alt"></i> <fmt:message key="button.login"/>
                </a>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-outline">
                    <i class="fas fa-user-plus"></i> <fmt:message key="button.register"/>
                </a>
            </c:if>
        </div>
    </div>
</header>

<main class="container">
    <h1><fmt:message key="news.latest"/></h1>

    <div class="news-grid">
        <c:choose>
            <c:when test="${not empty newsList}">
                <c:forEach items="${newsList}" var="news">
                    <div class="news-card">
                        <div class="news-content">
                            <h3 class="news-title">${news.title}</h3>
                            <p class="news-excerpt">${news.description}</p>

                            <c:if test="${not empty news.tags}">
                                <div class="tags-container">
                                    <c:forEach items="${news.tags}" var="tag">
                                        <span class="tag">#${tag}</span>
                                    </c:forEach>
                                </div>
                            </c:if>

                            <div class="news-meta">
                                <span class="news-author">
                                    <i class="fas fa-user"></i> ${news.userName}
                                </span>
                                <span class="news-date">
                                    <i class="far fa-calendar-alt"></i>
                                    <fmt:formatDate value="${news.lastChange}" pattern="dd.MM.yyyy"/>
                                </span>
                                <span class="news-views">
                                    <i class="far fa-eye"></i> ${news.viewsCount} <fmt:message key="news.views"/>
                                </span>
                            </div>

                            <a href="${pageContext.request.contextPath}/news-detail?id=${news.id}" class="read-more">
                                <fmt:message key="news.read.more"/> <i class="fas fa-arrow-right"></i>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-news">
                    <i class="far fa-newspaper fa-3x" style="margin-bottom: 15px;"></i>
                    <p><fmt:message key="news.no.news"/></p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>
</body>
</html>