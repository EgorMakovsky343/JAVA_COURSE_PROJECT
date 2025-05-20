<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html>
<html>
<head>
  <title>Редактировать новость</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 40px;
      background-color: #f5f5f5;
    }
    .news-form-container {
      max-width: 800px;
      margin: 0 auto;
      padding: 20px;
      background: white;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }
    .form-group {
      margin-bottom: 20px;
    }
    label {
      display: block;
      margin-bottom: 8px;
      font-weight: bold;
    }
    input[type="text"], textarea {
      width: 100%;
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 4px;
      font-family: Arial, sans-serif;
    }
    textarea {
      min-height: 200px;
    }
    .submit-btn {
      background: #3498db;
      color: white;
      padding: 12px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 16px;
    }
    .submit-btn:hover {
      background: #2980b9;
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

    .btn-danger {
      background-color: #e74c3c;
      color: white;
      padding: 10px 15px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      margin-top: 20px;
    }
    .btn-danger:hover {
      background-color: #c0392b;
    }

  </style>
</head>
<body>
<div class="news-form-container">
  <h1>Редактировать новость</h1>

  <c:if test="${not empty error}">
    <div class="error-message">${error}</div>
  </c:if>

  <c:if test="${not empty message}">
    <div class="success-message">${message}</div>
  </c:if>

  <form action="${pageContext.request.contextPath}/edit-news" method="post">
    <input type="hidden" name="newsId" value="${news.id}">

    <div class="form-group">
      <label for="title">Заголовок:</label>
      <input type="text" id="title" name="title" value="${news.title}" required>
    </div>

    <div class="form-group">
      <label for="description">Краткое описание:</label>
      <input type="text" id="description" name="description" value="${news.description}" required>
    </div>

    <div class="form-group">
      <label for="content">Содержание:</label>
      <textarea id="content" name="content" required>${news.content}</textarea>
    </div>

    <div class="form-group">
      <label for="tags">Теги (через запятую):</label>
      <input type="text" id="tags" name="tags"
             value="<c:forEach items="${news.tags}" var="tag" varStatus="loop">${tag}<c:if test="${!loop.last}">, </c:if></c:forEach>"
             placeholder="например, политика, экономика, спорт">
    </div>

    <button type="submit" class="submit-btn">Сохранить изменения</button>
  </form>

  <c:if test="${not empty news && sessionScope.user.admin}">
    <form action="${pageContext.request.contextPath}/edit-news" method="post">
      <input type="hidden" name="newsId" value="${news.id}">
      <input type="hidden" name="archive" value="true">
      <button type="submit" class="btn-danger"
              onclick="return confirm('Вы уверены, что хотите отправить эту новость в архив?')">
        <i class="fas fa-archive"></i> Отправить в архив
      </button>
    </form>
  </c:if>
</div>
</body>
</html>