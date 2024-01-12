package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Stat;
import org.example.apps.mctg.entity.User;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class StatsRepository {
    private final Database database = new Database();
    public Stat find(User user) {
        String FIND_BY_ID_SQL = "SELECT * FROM stats WHERE user_id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_BY_ID_SQL);
        ) {
            stmt.setString(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Stat(
                        rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getInt("elo"),
                        rs.getInt("games_played"),
                        rs.getInt("games_won")
                );
            }
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "an error occurred while searching for user stats");
        }
        throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "no stats found for given user");
    }

    public void create(User user) {
        String SAVE_SQL = "INSERT INTO stats(id, user_id, elo, games_played, games_won) VALUES (?,?,?,?,?)";
        String id = UUID.randomUUID().toString();
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(SAVE_SQL);
        ) {
            stmt.setString(1, id);
            stmt.setString(2, user.getId());
            stmt.setInt(3, 100);
            stmt.setInt(4, 0);
            stmt.setInt(5, 0);

            stmt.execute();
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "could not save stat");
        }
    }

    public void update(Stat stat) {
        String UPDATE_SQL = "UPDATE stats SET elo = ?, games_played = ?, games_won = ? WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_SQL);
        ) {
            stmt.setInt(1, stat.getElo());
            stmt.setInt(2, stat.getGames_played());
            stmt.setInt(3, stat.getGames_won());
            stmt.setString(4, stat.getId());

            stmt.execute();
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "could not update stat");
        }
    }
}
