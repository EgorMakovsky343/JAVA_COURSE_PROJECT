package com.newsportal.dao.interfaces;

import com.newsportal.entity.Bookmark;
import java.util.List;

public interface BookmarkDao {
    void addBookmark(Bookmark bookmark);
    void removeBookmark(Long bookmarkId); // Изменено - теперь принимает только ID закладки
    List<Bookmark> findByUserId(Long userId);
    boolean existsByUserAndNews(Long userId, Long newsId);
}