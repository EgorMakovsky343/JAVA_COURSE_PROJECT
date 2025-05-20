package com.newsportal.dao.impl;

import com.newsportal.dao.interfaces.UserDao;
import com.newsportal.entity.User;
import com.newsportal.servlets.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    @Override
    public User findByEmail(String email) {
        String query = "SELECT * FROM users WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error finding user by email: " + email);
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        String query = "SELECT * FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error finding user by ID: " + id);
        }
        return null;
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO users (email, password, is_admin, user_name, " +
                "date_of_birth, gender, interests) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            setUserParameters(stmt, user);

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error saving user");
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE users SET email = ?, " +
                (user.getPassword() != null ? "password = ?, " : "") +
                "is_admin = ?, user_name = ?, date_of_birth = ?, " +
                "gender = ?, interests = ? WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            int paramIndex = 1;
            stmt.setString(paramIndex++, user.getEmail());

            if (user.getPassword() != null) {
                stmt.setString(paramIndex++, user.getPassword());
            }

            stmt.setBoolean(paramIndex++, user.isAdmin());
            stmt.setString(paramIndex++, user.getUserName());

            if (user.getDateOfBirth() != null) {
                stmt.setDate(paramIndex++, Date.valueOf(user.getDateOfBirth()));
            } else {
                stmt.setNull(paramIndex++, Types.DATE);
            }

            stmt.setString(paramIndex++, user.getGender());
            stmt.setString(paramIndex++, user.getInterests());
            stmt.setLong(paramIndex, user.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error updating user with ID: " + user.getId());
        }
    }

    @Override
    public void delete(User user) {
        String query = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, user.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e, "Error deleting user with ID: " + user.getId());
        }
    }

    @Override
    public boolean verifyPassword(Long userId, String password) {
        String query = "SELECT password FROM users WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return password.equals(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error verifying password for user ID: " + userId);
        }
        return false;
    }

    @Override
    public boolean emailExists(String email) {
        String query = "SELECT 1 FROM users WHERE LOWER(email) = LOWER(?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email.trim());

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            handleSQLException(e, "Error checking email existence: " + email);
        }
        return false;
    }

    // Вспомогательные методы
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user_id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setAdmin(rs.getBoolean("is_admin"));
        user.setUserName(rs.getString("user_name"));

        Date dateOfBirth = rs.getDate("date_of_birth");
        if (dateOfBirth != null) {
            user.setDateOfBirth(dateOfBirth.toLocalDate());
        }

        user.setGender(rs.getString("gender"));
        user.setInterests(rs.getString("interests"));

        Timestamp regDate = rs.getTimestamp("registrationdate");
        if (regDate != null) {
            user.setRegistrationDate(regDate.toLocalDateTime());
        }

        return user;
    }

    private void setUserParameters(PreparedStatement stmt, User user) throws SQLException {
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getPassword());
        stmt.setBoolean(3, user.isAdmin());
        stmt.setString(4, user.getUserName());

        if (user.getDateOfBirth() != null) {
            stmt.setDate(5, Date.valueOf(user.getDateOfBirth()));
        } else {
            stmt.setNull(5, Types.DATE);
        }

        stmt.setString(6, user.getGender());
        stmt.setString(7, user.getInterests());
    }

    private void handleSQLException(SQLException e, String message) {
        System.err.println(message);
        e.printStackTrace();
        // В реальном приложении можно использовать логгер и пробрасывать кастомное исключение
    }
}