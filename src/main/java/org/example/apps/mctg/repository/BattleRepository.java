package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Battle;
import org.example.apps.mctg.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class BattleRepository {
    private  final Database database = new Database();
    public Battle findBattle(String id) {
        String FIND_BY_ID_SQL = "SELECT * FROM battles WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_BY_ID_SQL);
        ) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Battle(
                        rs.getString("id"),
                        rs.getString("player1"),
                        rs.getString("player2"),
                        rs.getString("winner"),
                        rs.getString("log"),
                        rs.getBoolean("open")
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Optional<Battle> findOpenBattle() {
        String FIND_OPEN_SQL = "SELECT * FROM battles WHERE open = true";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_OPEN_SQL);
                ResultSet rs = stmt.executeQuery();
        ) {
            Battle battle = null;
            while (rs.next()) {
                battle = new Battle(
                        rs.getString("id"),
                        rs.getString("player1")
                );
            }

            if (battle != null) {
                return Optional.of(battle);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    public Battle openBattle(User user) {
        String SAVE_SQL = "INSERT INTO battles(id, player1, open) VALUES (?, ?, ?)";
        String id = UUID.randomUUID().toString();
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(SAVE_SQL);
        ) {
            stmt.setString(1, id);
            stmt.setString(2, user.getId());
            stmt.setBoolean(3, true);

            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Battle(id, user.getId());
    }

    public boolean isOpen(String id) {
        String FIND_BY_ID_SQL = "SELECT * FROM battles WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_BY_ID_SQL);
        ) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("open");
            } else {
                return true;
            }

        } catch (SQLException e) {
            return true;
        }
    }
}
