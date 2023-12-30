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
    private  final Database database = new Database();

    public Optional<User> find(String username) {
        String FIND_SQL = "SELECT * FROM users WHERE username = ?";

        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_SQL);
        ) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            User user = null;
            while (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("coins")
                );
            }

            if (user != null) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.print(e);
            return Optional.empty();
        }
    }
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        String FIND_ALL_SQL = "SELECT * FROM users";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_ALL_SQL);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("coins")
                );

                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            return users;
        }
    }

    public User save(User user) {
        String SAVE_SQL = "INSERT INTO users(id, username, password, coins) VALUES (?, ?, ?, ?)";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(SAVE_SQL);
        ) {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4,user.getCoins());

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }
}
