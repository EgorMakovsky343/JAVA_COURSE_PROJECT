package com.newsportal.dao.interfaces;

import com.newsportal.entity.News;
import java.util.List;

public interface NewsDao {
    List<News> getAllNews();
    List<News> getAllNonArchivedNews();
    List<News> getAllArchivedNews();
    News getById(long id);
    void save(News news);
    void update(News news);
    void incrementViewsCount(long newsId);
    boolean titleExists(String title);
    void archiveNews(long newsId);
}