package com.newsportal.service;

import com.newsportal.dao.impl.BookmarkDaoImpl;
import com.newsportal.dao.interfaces.BookmarkDao;
import com.newsportal.entity.Bookmark;
import java.util.List;

public class BookmarkService {
    private final BookmarkDao bookmarkDao = new BookmarkDaoImpl();

    public List<Bookmark> findByUserId(Long userId) {
        return bookmarkDao.findByUserId(userId);
    }

    public boolean existsByUserAndNews(Long userId, Long newsId) {
        return bookmarkDao.existsByUserAndNews(userId, newsId);
    }

    public void addBookmark(Long userId, Long newsId) {
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(userId);
        bookmark.setNewsId(newsId);
        bookmarkDao.addBookmark(bookmark);
    }

    public void removeBookmark(Long bookmarkId) {
        bookmarkDao.removeBookmark(bookmarkId);
    }
}
