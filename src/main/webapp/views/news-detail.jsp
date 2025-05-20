<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<!DOCTYPE html>
<html>
<head>
    <title>${news.title} - Новостной портал</title>
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
        }

        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        .news-article {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
            position: relative;
            word-wrap: break-word;
            overflow-wrap: break-word;
        }

        .news-header {
            margin-bottom: 25px;
            padding-right: 180px;
            position: relative;
        }

        .news-title {
            font-size: 28px;
            font-weight: 700;
            margin-bottom: 15px;
            color: var(--primary);
            word-break: break-word;
        }

        .news-meta {
            display: flex;
            gap: 20px;
            color: var(--gray);
            font-size: 14px;
            margin-bottom: 20px;
            flex-wrap: wrap;
        }

        .news-meta-item {
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .news-content {
            font-size: 16px;
            line-height: 1.8;
            word-break: break-word;
        }

        .news-content p {
            margin-bottom: 20px;
        }

        .news-actions {
            position: absolute;
            top: 30px;
            right: 30px;
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            max-width: 180px;
            justify-content: flex-end;
        }

        .bookmark-btn, .edit-news-btn {
            padding: 8px 15px;
            border-radius: 4px;
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 8px;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }

        .bookmark-btn {
            background-color: var(--secondary);
            color: white;
        }

        .bookmark-btn:hover {
            background-color: #2980b9;
        }

        .edit-news-btn {
            background-color: #17a2b8;
            color: white;
        }

        .edit-news-btn:hover {
            background-color: #138496;
        }

        /* Остальные стили остаются без изменений */
        .comments-section {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.1);
        }

        .section-title {
            font-size: 22px;
            font-weight: 600;
            margin-bottom: 20px;
            color: var(--primary);
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .comment-form {
            margin-bottom: 30px;
        }

        .comment-textarea {
            width: 100%;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            min-height: 120px;
            margin-bottom: 15px;
            font-family: inherit;
            resize: vertical;
            max-width: 100%;
        }

        .comment-submit {
            background-color: var(--secondary);
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 500;
            transition: background-color 0.3s ease;
        }

        .comment-submit:hover {
            background-color: #2980b9;
        }

        .comment-list {
            margin-top: 30px;
        }

        .comment-item {
            border-bottom: 1px solid #eee;
            padding: 20px 0;
            overflow-wrap: break-word;
        }

        .comment-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .comment-author {
            font-weight: 600;
            color: var(--primary);
        }

        .comment-date {
            color: var(--gray);
            font-size: 13px;
        }

        .comment-content {
            margin-bottom: 15px;
            white-space: pre-wrap;
            overflow-wrap: anywhere;
        }

        .comment-edit-form {
            display: none;
            margin-top: 15px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 8px;
        }

        .comment-edit-textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            min-height: 100px;
            margin-bottom: 10px;
            resize: vertical;
        }

        .edit-form-actions {
            display: flex;
            gap: 10px;
        }

        .save-edit-btn, .cancel-edit-btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .save-edit-btn {
            background: #28a745;
            color: white;
        }

        .cancel-edit-btn {
            background: #6c757d;
            color: white;
        }

        .comment-actions {
            display: flex;
            gap: 10px;
            margin-top: 10px;
        }

        .edit-comment-btn, .delete-comment-btn {
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        .edit-comment-btn {
            background: #17a2b8;
            color: white;
        }

        .delete-comment-btn {
            background: #dc3545;
            color: white;
        }

        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }

        .alert-success {
            background-color: #e8f8f0;
            color: var(--success);
            border-left: 4px solid var(--success);
        }

        .alert-error {
            background-color: #fdecea;
            color: var(--accent);
            border-left: 4px solid var(--accent);
        }

        .tags-container {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin: 20px 0;
        }

        .tag {
            background-color: var(--light);
            color: var(--dark);
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 14px;
            word-break: break-word;
        }

        @media (max-width: 768px) {
            .news-header {
                padding-right: 0; /* На мобильных кнопки переносятся под заголовок */
            }

            .news-actions {
                position: static;
                margin-top: 15px;
                max-width: 100%;
                justify-content: flex-start;
            }

            .news-title {
                max-width: 100%;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <article class="news-article">
        <div class="news-header">
            <h1 class="news-title">${news.title}</h1>
            <div class="news-meta">
                <span class="news-meta-item">
                    <i class="fas fa-user"></i> ${news.userName}
                </span>
                <span class="news-meta-item">
                    <i class="far fa-calendar-alt"></i>
                    <fmt:formatDate value="${news.lastChange}" pattern="dd.MM.yyyy HH:mm"/>
                </span>
                <span class="news-meta-item">
                    <i class="far fa-eye"></i> ${news.viewsCount} просмотров
                </span>
            </div>
        </div>

        <div class="news-actions">
            <c:if test="${not empty sessionScope.user && sessionScope.user.admin && sessionScope.user.id == news.userId}">
                <a href="${pageContext.request.contextPath}/edit-news?id=${news.id}"
                   class="edit-news-btn">
                    <i class="fas fa-edit"></i> Редактировать статью
                </a>
            </c:if>

            <c:if test="${not empty sessionScope.user}">
                <form action="${pageContext.request.contextPath}/add-bookmark" method="post">
                    <input type="hidden" name="newsId" value="${news.id}">
                    <button type="submit" class="bookmark-btn">
                        <i class="fas fa-bookmark"></i>
                        <c:choose>
                            <c:when test="${isBookmarked}">Удалить из закладок</c:when>
                            <c:otherwise>Добавить в закладки</c:otherwise>
                        </c:choose>
                    </button>
                </form>
            </c:if>
        </div>

        <div class="news-content">
            ${news.content}
        </div>

        <c:if test="${not empty news.tags}">
            <div class="tags-container">
                <c:forEach items="${news.tags}" var="tag">
                    <span class="tag">#${tag}</span>
                </c:forEach>
            </div>
        </c:if>
    </article>

    <section class="comments-section">
        <h2 class="section-title">
            <i class="far fa-comments"></i> Комментарии
            <span style="font-size: 16px; color: var(--gray); margin-left: 10px;">
                    (${not empty comments ? comments.size() : 0})
                </span>
        </h2>

        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>

        <c:if test="${not empty sessionScope.user}">
            <form class="comment-form" action="${pageContext.request.contextPath}/add-comment" method="post">
                <input type="hidden" name="newsId" value="${news.id}">
                <textarea class="comment-textarea" name="content" placeholder="Напишите ваш комментарий..." required></textarea>
                <button type="submit" class="comment-submit">Отправить комментарий</button>
            </form>
        </c:if>

        <div class="comment-list">
            <c:choose>
                <c:when test="${not empty comments}">
                    <c:forEach items="${comments}" var="comment">
                        <div class="comment-item" id="comment-${comment.commentId}">
                            <div class="comment-header">
                                <span class="comment-author">${comment.userName}</span>
                                <span class="comment-date">
                                        <fmt:formatDate value="${comment.creationDate}" pattern="dd.MM.yyyy HH:mm"/>
                                    </span>
                            </div>

                            <div class="comment-content">${comment.content}</div>

                            <c:if test="${not empty sessionScope.user && sessionScope.user.id == comment.userId}">
                                <div class="comment-actions">
                                    <button class="edit-comment-btn" onclick="showEditForm(${comment.commentId})">
                                        <i class="fas fa-edit"></i> Редактировать
                                    </button>
                                    <form action="${pageContext.request.contextPath}/delete-comment" method="post" class="delete-form">
                                        <input type="hidden" name="commentId" value="${comment.commentId}">
                                        <input type="hidden" name="newsId" value="${news.id}">
                                        <button type="submit" class="delete-comment-btn">
                                            <i class="fas fa-trash"></i> Удалить
                                        </button>
                                    </form>
                                </div>

                                <form id="edit-form-${comment.commentId}" class="comment-edit-form"
                                      action="${pageContext.request.contextPath}/update-comment" method="post">
                                    <input type="hidden" name="commentId" value="${comment.commentId}">
                                    <input type="hidden" name="newsId" value="${news.id}">
                                    <textarea name="content" class="comment-edit-textarea">${comment.content}</textarea>
                                    <div class="edit-form-actions">
                                        <button type="submit" class="save-edit-btn">
                                            <i class="fas fa-save"></i> Сохранить
                                        </button>
                                        <button type="button" class="cancel-edit-btn" onclick="hideEditForm(${comment.commentId})">
                                            <i class="fas fa-times"></i> Отмена
                                        </button>
                                    </div>
                                </form>
                            </c:if>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div style="text-align: center; padding: 30px 0; color: var(--gray);">
                        <i class="far fa-comment-dots fa-3x" style="margin-bottom: 15px;"></i>
                        <p>Пока нет комментариев. Будьте первым!</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</div>

<form action="${pageContext.request.contextPath}/change-lang" method="post" style="display:inline; margin-right:10px; float:right;">
  <button type="submit" name="lang" value="en" style="background:#2980b9; color:white; padding:8px 15px; border:none; border-radius:4px; cursor:pointer;">Translate</button>
</form>

<script>
    function showEditForm(commentId) {
        // Скрываем все открытые формы редактирования
        document.querySelectorAll('.comment-edit-form').forEach(form => {
            form.style.display = 'none';
            const itemId = form.id.split('-')[2];
            document.getElementById('comment-' + itemId).querySelector('.comment-actions').style.display = 'flex';
            document.getElementById('comment-' + itemId).querySelector('.comment-content').style.display = 'block';
        });

        // Показываем нужную форму
        const form = document.getElementById('edit-form-' + commentId);
        form.style.display = 'block';

        // Скрываем кнопки действий и содержимое комментария
        const commentItem = document.getElementById('comment-' + commentId);
        commentItem.querySelector('.comment-actions').style.display = 'none';
        commentItem.querySelector('.comment-content').style.display = 'none';

        // Прокручиваем к форме
        form.scrollIntoView({ behavior: 'smooth', block: 'nearest' });

        // Фокусируемся на текстовом поле
        form.querySelector('textarea').focus();
    }

    function hideEditForm(commentId) {
        const form = document.getElementById('edit-form-' + commentId);
        form.style.display = 'none';

        const commentItem = document.getElementById('comment-' + commentId);
        commentItem.querySelector('.comment-actions').style.display = 'flex';
        commentItem.querySelector('.comment-content').style.display = 'block';
    }
</script>
</body>
</html>