<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>

<!DOCTYPE html>
<html>
<head>
    <title>Вход в систему</title>
    <meta charset="UTF-8">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 40px;
            background-color: #f5f5f5;
        }
        .login-container {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .login-form {
            width: 100%;
        }
        .error {
            color: red;
            margin-bottom: 15px;
            padding: 10px;
            background: #ffeeee;
            border-radius: 3px;
        }
        input {
            width: 100%;
            padding: 10px;
            margin: 5px 0 15px;
            border: 1px solid #ddd;
            border-radius: 3px;
            box-sizing: border-box;
        }
        button {
            background: #4CAF50;
            color: white;
            padding: 12px;
            border: none;
            width: 100%;
            border-radius: 3px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background: #45a049;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .register-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #3498db;
            text-decoration: none;
        }
        .register-link:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>Вход в систему</h2>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form class="login-form" action="${pageContext.request.contextPath}/auth" method="post">
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required
                   value="${param.email}" placeholder="Введите ваш email">
        </div>

        <div class="form-group">
            <label for="password">Пароль:</label>
            <input type="password" id="password" name="password" required
                   placeholder="Введите ваш пароль">
        </div>

        <button type="submit">Войти</button>
    </form>

    <a href="${pageContext.request.contextPath}/register" class="register-link">Зарегистрироваться</a>
</div>
</body>
</html>