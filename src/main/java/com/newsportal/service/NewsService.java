package com.newsportal.service;

import com.newsportal.dao.impl.NewsDaoImpl;
import com.newsportal.dao.interfaces.NewsDao;
import com.newsportal.entity.News;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class NewsService {
    private final NewsDao newsDao = new NewsDaoImpl();

    public List<News> getAllNonArchivedNews() {
        return newsDao.getAllNonArchivedNews();
    }

    public List<News> getAllArchivedNews() {
        return newsDao.getAllArchivedNews();
    }

    public News getById(Long newsId) {
        return newsDao.getById(newsId);
    }

    public void incrementViewsCount(Long newsId) {
        newsDao.incrementViewsCount(newsId);
    }

    public void createNews(Long userId, String title, String description, String content, String tags) {
        News news = new News();
        news.setUserId(userId);
        news.setTitle(title);
        news.setDescription(description);
        news.setContent(content);
        news.setLastChange(Timestamp.valueOf(LocalDateTime.now()));
        if (tags != null && !tags.isEmpty()) {
            String[] tagsArray = Arrays.stream(tags.split(",")).map(String::trim).toArray(String[]::new);
            news.setTags(tagsArray);
        }
        newsDao.save(news);
    }

    public void updateNews(News news, String title, String description, String content, String tags) {
        news.setTitle(title);
        news.setDescription(description);
        news.setContent(content);
        if (tags != null && !tags.isEmpty()) {
            news.setTags(Arrays.stream(tags.split(",")).map(String::trim).toArray(String[]::new));
        } else {
            news.setTags(new String[0]);
        }
        newsDao.update(news);
    }

    public void archiveNews(Long newsId) {
        newsDao.archiveNews(newsId);
    }
}
