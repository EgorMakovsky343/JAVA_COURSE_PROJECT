package com.newsportal.dao.impl;

import com.newsportal.dao.interfaces.NewsDao;
import com.newsportal.entity.News;
import com.newsportal.servlets.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewsDaoImpl implements NewsDao {

    @Override
    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();
        String query = "SELECT n.*, u.user_name FROM news n JOIN users u ON n.user_id = u.user_id ORDER BY n.last_change DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                newsList.add(mapResultSetToNews(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    public News getById(long id) {
        String query = "SELECT n.*, u.user_name FROM news n JOIN users u ON n.user_id = u.user_id WHERE n.news_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToNews(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(News news) {
        String query = "INSERT INTO news (user_id, is_archived, title, description, content, tags) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, news.getUserId());
            stmt.setBoolean(2, false); // Устанавливаем is_archived = false по умолчанию
            stmt.setString(3, news.getTitle());
            stmt.setString(4, news.getDescription());
            stmt.setString(5, news.getContent());

            if (news.getTags() != null && news.getTags().length > 0) {
                stmt.setArray(6, conn.createArrayOf("varchar", news.getTags()));
            } else {
                stmt.setArray(6, conn.createArrayOf("varchar", new String[0]));
            }

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    news.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при сохранении новости: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(News news) {
        String query = "UPDATE news SET title = ?, description = ?, content = ?, tags = ?, last_change = CURRENT_TIMESTAMP WHERE news_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, news.getTitle());
            stmt.setString(2, news.getDescription());
            stmt.setString(3, news.getContent());

            if (news.getTags() != null && news.getTags().length > 0) {
                stmt.setArray(4, conn.createArrayOf("varchar", news.getTags()));
            } else {
                stmt.setArray(4, conn.createArrayOf("varchar", new String[0]));
            }

            stmt.setLong(5, news.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void incrementViewsCount(long newsId) {
        String query = "UPDATE news SET views_count = views_count + 1 WHERE news_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, newsId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean titleExists(String title) {
        String query = "SELECT 1 FROM news WHERE LOWER(title) = LOWER(?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, title.trim());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void archiveNews(long newsId) {
        String query = "UPDATE news SET is_archived = true WHERE news_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, newsId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<News> getAllNonArchivedNews() {
        List<News> newsList = new ArrayList<>();
        String query = "SELECT n.*, u.user_name FROM news n JOIN users u ON n.user_id = u.user_id " +
                "WHERE n.is_archived = false ORDER BY n.last_change DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                newsList.add(mapResultSetToNews(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    @Override
    public List<News> getAllArchivedNews() {
        List<News> newsList = new ArrayList<>();
        String query = "SELECT n.*, u.user_name FROM news n JOIN users u ON n.user_id = u.user_id " +
                "WHERE n.is_archived = true ORDER BY n.last_change DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                newsList.add(mapResultSetToNews(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsList;
    }

    private News mapResultSetToNews(ResultSet rs) throws SQLException {
        News news = new News();
        news.setId(rs.getLong("news_id"));
        news.setUserId(rs.getLong("user_id"));
        news.setUserName(rs.getString("user_name"));
        news.setArchived(rs.getBoolean("is_archived"));
        news.setTitle(rs.getString("title"));
        news.setDescription(rs.getString("description"));
        news.setContent(rs.getString("content"));
        news.setViewsCount(rs.getInt("views_count"));
        news.setLastChange(rs.getTimestamp("last_change"));

        Array tagsArray = rs.getArray("tags");
        if (tagsArray != null) {
            news.setTags((String[]) tagsArray.getArray());
        } else {
            news.setTags(new String[0]);
        }

        return news;
    }
}