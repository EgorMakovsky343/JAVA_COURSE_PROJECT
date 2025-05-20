package com.newsportal.service;

import com.newsportal.dao.impl.CommentDaoImpl;
import com.newsportal.dao.interfaces.CommentDao;
import com.newsportal.entity.Comment;
import java.util.List;

public class CommentService {
    private final CommentDao commentDao = new CommentDaoImpl();

    public void addComment(Long userId, Long newsId, String content) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setNewsId(newsId);
        comment.setContent(content.trim());
        commentDao.save(comment);
    }

    public boolean isCommentOwner(Long commentId, Long userId) {
        return commentDao.isCommentOwner(commentId, userId);
    }

    public void deleteComment(Long commentId) {
        commentDao.delete(commentId);
    }

    public Comment findById(Long commentId) {
        return commentDao.findById(commentId);
    }

    public void updateComment(Long commentId, String content) {
        Comment updatedComment = new Comment();
        updatedComment.setCommentId(commentId);
        updatedComment.setContent(content);
        commentDao.update(updatedComment);
    }

    public List<Comment> findByNewsId(Long newsId) {
        return commentDao.findByNewsId(newsId);
    }
}
