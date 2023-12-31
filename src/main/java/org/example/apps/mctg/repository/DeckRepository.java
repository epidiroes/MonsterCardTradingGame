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

public class DeckRepository {
    private final Database database = new Database();
    public List<Card> findAll(User user) {
        List<Card> cards = new ArrayList<>();
        String FIND_ALL_SQL = """
                SELECT cards.*
                FROM users
                         JOIN decks ON users.id = decks.user_id
                         JOIN cards ON cards.id IN (decks.card1_id, decks.card2_id, decks.card3_id, decks.card4_id)
                WHERE users.id = ?""";

        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_ALL_SQL);
        ) {
            stmt.setString(1, user.getId());
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
