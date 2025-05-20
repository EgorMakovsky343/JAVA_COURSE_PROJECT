package com.newsportal.dao.interfaces;

import com.newsportal.entity.Comment;
import java.util.List;

public interface CommentDao {
    void save(Comment comment);
    List<Comment> findByNewsId(Long newsId);
    boolean isCommentOwner(Long commentId, Long userId);
    void delete(Long commentId);
    void update(Comment comment); // Изменено - теперь принимает объект Comment
    Comment findById(Long commentId); // Новый метод
}