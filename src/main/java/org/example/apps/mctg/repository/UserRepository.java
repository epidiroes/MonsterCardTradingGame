package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private final String FIND_ALL_SQL = "SELECT * FROM users";
    private final String SAVE_SQL = "INSERT INTO users(id, username, password) VALUES (?, ?, ?)";
    private  final Database database = new Database();

    public Optional<User> find(String username) {
        String FIND_SQL = "SELECT * FROM users WHERE username = ?";

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_SQL);
        ) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            User user = null;
            while (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
            }

            assert user != null;
            return Optional.of(user);
        } catch (SQLException e) {
            System.err.print(e);
            return Optional.empty();
        }
    }
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(FIND_ALL_SQL);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password")
                );
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            return users;
        }
    }

    public User save(User user) {
        try (
                Connection con = database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(SAVE_SQL);
        ) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());

            pstmt.execute();
        } catch (SQLException e) {
            // dunno yet
        }

        return user;
    }
}
