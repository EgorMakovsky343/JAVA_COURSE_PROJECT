<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Регистрация</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 40px;
      background-color: #f5f5f5;
    }
    .registration-container {
      max-width: 500px;
      margin: 0 auto;
      padding: 20px;
      background: white;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    .form-group {
      margin-bottom: 15px;
    }
    label {
      display: block;
      margin-bottom: 5px;
      font-weight: bold;
    }
    input, select {
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
    .error {
      color: red;
      margin-bottom: 15px;
      padding: 10px;
      background: #ffeeee;
      border-radius: 3px;
    }
    .message {
      color: green;
      margin-bottom: 15px;
      padding: 10px;
      background: #eeffee;
      border-radius: 3px;
    }
  </style>
</head>
<body>
<div class="registration-container">
  <h2>Регистрация нового пользователя</h2>

  <c:if test="${not empty error}">
    <div class="error">${error}</div>
  </c:if>

  <form action="${pageContext.request.contextPath}/register" method="post">
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" id="email" name="email" required>
    </div>

    <div class="form-group">
      <label for="password">Пароль:</label>
      <input type="password" id="password" name="password" required>
    </div>

    <div class="form-group">
      <label for="confirmPassword">Подтвердите пароль:</label>
      <input type="password" id="confirmPassword" name="confirmPassword" required>
    </div>

    <div class="form-group">
      <label for="userName">Имя пользователя:</label>
      <input type="text" id="userName" name="userName" required>
    </div>

    <div class="form-group">
      <label for="birthDate">Дата рождения:</label>
      <input type="date" id="birthDate" name="birthDate">
    </div>

    <div class="form-group">
      <label for="gender">Пол:</label>
      <select id="gender" name="gender">
        <option value="мужской">Мужской</option>
        <option value="женский">Женский</option>
      </select>
    </div>

    <div class="form-group">
      <label for="interests">Интересы:</label>
      <textarea id="interests" name="interests"></textarea>
    </div>

    <div class="form-group">
      <label for="role">Роль:</label>
      <select id="role" name="role" required>
        <option value="reader">Читатель</option>
        <option value="admin">Администратор</option>
      </select>
    </div>

    <button type="submit">Зарегистрироваться</button>
  </form>
</div>
</body>
</html>