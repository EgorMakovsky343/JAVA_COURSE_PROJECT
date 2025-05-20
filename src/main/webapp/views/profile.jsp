<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="profile.title"/></title>
    <style>
        .profile-container {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            background-color: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            position: relative;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input, textarea, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .user-role-block {
            display: flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 15px;
            font-weight: bold;
            padding: 4px 8px;
            border-radius: 4px;
        }
        .admin-role {
            background-color: #f39c12;
            color: white;
        }
        .user-role {
            background-color: #3498db;
            color: white;
        }
        .alert {
            padding: 12px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .alert-success {
            background-color: #e8f8f0;
            color: #27ae60;
            border-left: 4px solid #27ae60;
        }
        .alert-error {
            background-color: #fdecea;
            color: #e74c3c;
            border-left: 4px solid #e74c3c;
        }
        button {
            background-color: #3498db;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #2980b9;
        }
        .bookmarks-link {
            position: absolute;
            top: 20px;
            right: 20px;
            padding: 8px 15px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
        }
        .bookmarks-link:hover {
            background: #45a049;
        }
        .translate-button {
            position: absolute;
            top: 20px;
            right: 150px;
            padding: 8px 15px;
            background: #2980b9;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .translate-button:hover {
            background: #2471a3;
        }
    </style>
</head>
<body>
<div class="profile-container">
    <h1><fmt:message key="profile.title"/></h1>

    <a href="${pageContext.request.contextPath}/bookmarks" class="bookmarks-link">
        <i class="fas fa-bookmark"></i> <fmt:message key="button.bookmarks"/>
    </a>

    <form action="${pageContext.request.contextPath}/change-lang" method="post" style="display:inline;">
        <button type="submit" name="lang" value="en" class="translate-button"><fmt:message key="button.translate"/></button>
    </form>

    <div class="${user.admin ? 'admin-role' : 'user-role'} user-role-block">
        <i class="fas ${user.admin ? 'fa-user-shield' : 'fa-user'}"></i>
        <span>
            <c:choose>
                <c:when test="${user.admin}"><fmt:message key="profile.role.admin"/></c:when>
                <c:otherwise><fmt:message key="profile.role.reader"/></c:otherwise>
            </c:choose>
        </span>
    </div>

    <c:if test="${not empty message}">
        <div class="alert alert-success">${message}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/profile" method="post">
        <div class="form-group">
            <label><fmt:message key="profile.email"/>:</label>
            <input type="email" name="email" value="${user.email}" readonly>
        </div>
        <div class="form-group">
            <label><fmt:message key="profile.username"/>:</label>
            <input type="text" name="username" value="${user.userName}" required>
        </div>
        <div class="form-group">
            <label><fmt:message key="profile.birthdate"/>:</label>
            <input type="date" name="birthDate" value="${user.dateOfBirth}">
        </div>
        <div class="form-group">
            <label><fmt:message key="profile.gender"/>:</label>
            <select name="gender">
                <option value="мужской" ${user.gender == 'мужской' ? 'selected' : ''}><fmt:message key="profile.gender.male"/></option>
                <option value="женский" ${user.gender == 'женский' ? 'selected' : ''}><fmt:message key="profile.gender.female"/></option>
            </select>
        </div>
        <div class="form-group">
            <label><fmt:message key="profile.interests"/>:</label>
            <textarea name="interests">${user.interests}</textarea>
        </div>
        <div class="form-group">
            <label><fmt:message key="profile.new.password"/>:</label>
            <input type="password" name="newPassword">
        </div>
        <div class="form-group">
            <label><fmt:message key="profile.current.password"/>:</label>
            <input type="password" name="currentPassword" required>
        </div>
        <div style="text-align: center; margin-top: 20px;">
            <button type="submit"><fmt:message key="button.save"/></button>
        </div>
    </form>
</div>
</body>
</html>