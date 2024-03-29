package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.dto.Bio;
import org.example.apps.mctg.entity.User;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;

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
            if (rs.next()) {
                user = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("coins"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("image")
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

    public User findById(String id) {
        String FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_BY_ID_SQL);
        ) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            User user = null;
            if (rs.next()) {
                return new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("coins"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("image")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.print(e);
            return null;
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
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "error while looking for all users");
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
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating user");
        }

        return user;
    }

    public User edit(User user, Bio bio) {
        String UPDATE_BIO_SQL = "UPDATE users SET name = ?, bio = ?, image = ? WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_BIO_SQL);
        ) {
            stmt.setString(1, bio.getName());
            stmt.setString(2, bio.getBio());
            stmt.setString(3, bio.getImage());
            stmt.setString(4, user.getId());

            stmt.execute();
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "error while editing user profile");
        }

        return new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getCoins(),
                bio.getName(),
                bio.getBio(),
                bio.getImage()
        );
    }

    public void removeCoins(User user) {
        String UPDATE_COIN_SQL = "UPDATE users SET coins = coins - 5 WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_COIN_SQL);
        ) {
            stmt.setString(1, user.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "could not remove coins");
        }
    }

    public void addCoin(User user) {
        String UPDATE_COIN_ADD_SQL = "UPDATE users SET coins = coins + 1 WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_COIN_ADD_SQL);
        ) {
            stmt.setString(1, user.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "could not add coin");
        }
    }
}
