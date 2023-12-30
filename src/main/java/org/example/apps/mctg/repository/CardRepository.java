package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {
    private final Database database = new Database();

    public boolean save(Card card) {
        String SAVE_SQL = "INSERT INTO cards(id, name, damage) VALUES (?, ?, ?)";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(SAVE_SQL);
        ) {
            stmt.setString(1, card.getId());
            stmt.setString(2, card.getName());
            stmt.setInt(3, card.getDamage());

            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<Card> findAll(User user) {
        List<Card> cards = new ArrayList<>();
        String FIND_ALL_SQL = """
                SELECT cards.*
                FROM users
                         JOIN packages ON users.id = packages.user_id
                         JOIN cards ON cards.id IN (packages.card1_id, packages.card2_id, packages.card3_id, packages.card4_id, packages.card5_id)
                WHERE users.id = ?""";

        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_ALL_SQL);
        ) {
            stmt.setString(1,user.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Card card = new Card(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("damage")
                );
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            return cards;
        }
    }
}
