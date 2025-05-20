package com.newsportal.dao.impl;

import com.newsportal.dao.interfaces.CommentDao;
import com.newsportal.entity.Comment;
import com.newsportal.servlets.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements CommentDao {

    @Override
    public void save(Comment comment) {
        String query = "INSERT INTO comments (user_id, news_id, content) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, comment.getUserId());
            stmt.setLong(2, comment.getNewsId());
            stmt.setString(3, comment.getContent());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    comment.setCommentId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при сохранении комментария: " + e.getMessage());
            throw new RuntimeException("Не удалось сохранить комментарий", e);
        }
    }

    @Override
    public List<Comment> findByNewsId(Long newsId) {
        String query = "SELECT c.*, u.user_name FROM comments c " +
                "JOIN users u ON c.user_id = u.user_id " +
                "WHERE c.news_id = ? ORDER BY c.creation_date DESC";
        List<Comment> comments = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, newsId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                comments.add(mapResultSetToComment(rs));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске комментариев: " + e.getMessage());
            throw new RuntimeException("Не удалось загрузить комментарии", e);
        }
        return comments;
    }

    @Override
    public Comment findById(Long commentId) {
        String query = "SELECT c.*, u.user_name FROM comments c " +
                "JOIN users u ON c.user_id = u.user_id " +
                "WHERE c.comment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, commentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentId(rs.getLong("comment_id"));
                comment.setUserId(rs.getLong("user_id"));
                comment.setNewsId(rs.getLong("news_id"));
                comment.setContent(rs.getString("content"));
                comment.setCreationDate(rs.getTimestamp("creation_date"));
                comment.setUserName(rs.getString("user_name"));
                return comment;
            }
        } catch (SQLException e) {
            System.err.println("Error finding comment by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean isCommentOwner(Long commentId, Long userId) {
        String query = "SELECT 1 FROM comments WHERE comment_id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, commentId);
            stmt.setLong(2, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке владельца комментария: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void delete(Long commentId) {
        String query = "DELETE FROM comments WHERE comment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, commentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении комментария: " + e.getMessage());
            throw new RuntimeException("Не удалось удалить комментарий", e);
        }
    }

    @Override
    public void update(Comment comment) {
        String query = "UPDATE comments SET content = ?, creation_date = CURRENT_TIMESTAMP WHERE comment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, comment.getContent());
            stmt.setLong(2, comment.getCommentId());

            int updatedRows = stmt.executeUpdate();
            if (updatedRows == 0) {
                throw new SQLException("Комментарий не был обновлен, возможно он не существует");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении комментария: " + e.getMessage());
            throw new RuntimeException("Не удалось обновить комментарий", e);
        }
    }

    private Comment mapResultSetToComment(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(rs.getLong("comment_id"));
        comment.setUserId(rs.getLong("user_id"));
        comment.setNewsId(rs.getLong("news_id"));
        comment.setContent(rs.getString("content"));
        comment.setCreationDate(rs.getTimestamp("creation_date"));
        comment.setUserName(rs.getString("user_name"));
        return comment;
    }
}