<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Создать новость</title>
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
    </style>
</head>
<body>
<div class="news-form-container">
    <h1>Создать новую новость</h1>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

    <c:if test="${not empty message}">
        <div class="success-message">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/create-news" method="post">
        <div class="form-group">
            <label for="title">Заголовок:</label>
            <input type="text" id="title" name="title" required>
        </div>

        <div class="form-group">
            <label for="description">Краткое описание:</label>
            <input type="text" id="description" name="description" required>
        </div>

        <div class="form-group">
            <label for="content">Содержание:</label>
            <textarea id="content" name="content" required></textarea>
        </div>

        <div class="form-group">
            <label for="tags">Теги (через запятую):</label>
            <input type="text" id="tags" name="tags" placeholder="например, политика, экономика, спорт">
        </div>

        <button type="submit" class="submit-btn">Опубликовать новость</button>
    </form>
</div>
</body>
</html>