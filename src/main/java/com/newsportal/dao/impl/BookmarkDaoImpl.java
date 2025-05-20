package com.newsportal.dao.impl;

import com.newsportal.dao.interfaces.BookmarkDao;
import com.newsportal.entity.Bookmark;
import com.newsportal.servlets.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkDaoImpl implements BookmarkDao {

    @Override
    public void addBookmark(Bookmark bookmark) {
        String query = "INSERT INTO bookmarks (user_id, news_id, saved_time) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, bookmark.getUserId());
            stmt.setLong(2, bookmark.getNewsId());
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    bookmark.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeBookmark(Long bookmarkId) {
        String query = "DELETE FROM bookmarks WHERE bookmark_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, bookmarkId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeBookmark(Long userId, Long newsId) {
        String query = "DELETE FROM bookmarks WHERE user_id = ? AND news_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, newsId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Bookmark> findByUserId(Long userId) {
        String query = "SELECT b.*, n.title as news_title FROM bookmarks b " +
                "JOIN news n ON b.news_id = n.news_id " +
                "WHERE b.user_id = ? ORDER BY b.saved_time DESC";
        List<Bookmark> bookmarks = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getLong("bookmark_id"));
                bookmark.setUserId(rs.getLong("user_id"));
                bookmark.setNewsId(rs.getLong("news_id"));
                bookmark.setSavedTime(rs.getTimestamp("saved_time"));
                bookmark.setNewsTitle(rs.getString("news_title"));
                bookmarks.add(bookmark);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookmarks;
    }

    @Override
    public boolean existsByUserAndNews(Long userId, Long newsId) {
        String query = "SELECT 1 FROM bookmarks WHERE user_id = ? AND news_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, newsId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}