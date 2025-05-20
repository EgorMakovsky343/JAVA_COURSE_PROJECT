<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html>
<html>
<head>
  <title>Архив новостей</title>
  <style>
    /* Стили такие же как в news.jsp, можно вынести в отдельный CSS файл */
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 40px;
      background-color: #f5f5f5;
    }
    .news-container {
      max-width: 1200px;
      margin: 0 auto;
    }
    .news-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 20px;
    }
    .news-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: 25px;
    }
    .news-card {
      background-color: white;
      border-radius: 8px;
      padding: 20px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    .archive-title {
      color: #666;
      font-size: 24px;
      margin-bottom: 20px;
    }
    .news-title {
      font-size: 18px;
      font-weight: 600;
      margin-bottom: 10px;
      color: #2c3e50;
      word-wrap: break-word;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 2;
      -webkit-box-orient: vertical;
    }
    .news-excerpt {
      color: #95a5a6;
      margin-bottom: 15px;
      font-size: 14px;
      word-wrap: break-word;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
    }
    .tags-container {
      margin-top: 10px;
      display: flex;
      flex-wrap: wrap;
    }
    .tag {
      display: inline-block;
      background-color: #ecf0f1;
      color: #2c3e50;
      padding: 4px 10px;
      border-radius: 20px;
      font-size: 12px;
      margin-right: 8px;
      margin-bottom: 8px;
      word-break: break-word;
    }
  </style>
</head>
<body>
<div class="news-container">
  <div class="news-header">
    <h1 class="archive-title"><i class="fas fa-archive"></i> Архив новостей</h1>
    <a href="${pageContext.request.contextPath}/news" class="btn btn-primary">
      <i class="fas fa-arrow-left"></i> Назад к новостям
    </a>
    <form action="${pageContext.request.contextPath}/change-lang" method="post" style="display:inline; margin-right:10px; float:right;">
      <button type="submit" name="lang" value="en" style="background:#2980b9; color:white; padding:8px 15px; border:none; border-radius:4px; cursor:pointer;">Translate</button>
    </form>
  </div>

  <div class="news-grid">
    <c:choose>
      <c:when test="${not empty newsList}">
        <c:forEach items="${newsList}" var="news">
          <div class="news-card">
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
              <span><i class="far fa-calendar-alt"></i>
                <fmt:formatDate value="${news.lastChange}" pattern="dd.MM.yyyy"/>
              </span>
              <span><i class="far fa-eye"></i> ${news.viewsCount}</span>
            </div>
            <a href="${pageContext.request.contextPath}/news-detail?id=${news.id}">
              Читать далее
            </a>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <div class="no-news">
          <p>В архиве нет новостей</p>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>
</body>
</html>