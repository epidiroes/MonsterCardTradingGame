package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Trade;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TradingsRepository {
    private final Database database = new Database();

    public Trade findById(String id) {
        String FIND_BY_ID_SQL = "SELECT * FROM tradings WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_BY_ID_SQL);
        ) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Trade(
                        rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("card_to_trade"),
                        rs.getString("type"),
                        rs.getInt("minimum_damage")
                );
            }
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching for all trades");
        }
        throw new HttpException(HttpStatus.BAD_REQUEST, "trade not found");
    }

    public List<Trade> findAll() {
        String FIND_ALL_SQL = "SELECT * FROM tradings";
        List<Trade> trades = new ArrayList<>();
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_ALL_SQL);
                ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                Trade trade = new Trade(
                        rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("card_to_trade"),
                        rs.getString("type"),
                        rs.getInt("minimum_damage")
                );
                trades.add(trade);
            }
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching for all trades");
        }
        return trades;
    }

    public Trade save(Trade trade) {
        String SAVE_SQL = "INSERT INTO tradings(id, user_id, card_to_trade, type, minimum_damage) VALUES (?,?,?,?,?)";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(SAVE_SQL);
        ) {
            stmt.setString(1, trade.getId());
            stmt.setString(2, trade.getUserId());
            stmt.setString(3, trade.getCardToTrade());
            stmt.setString(4, trade.getType());
            stmt.setInt(5, trade.getMinimumDamage());

            stmt.execute();
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "error while saving trade");
        }
        return trade;
    }

    public void delete(String id) {
        String DELETE_SQL = "DELETE FROM tradings WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(DELETE_SQL);
        ) {
            stmt.setString(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "error while deleting trade");
        }
    }
}
