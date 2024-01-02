package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Deck;
import org.example.apps.mctg.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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


    public Deck create(User user, List<Card> cards) {
        String SAVE_SQL = "INSERT INTO decks(id, user_id, card1_id, card2_id, card3_id, card4_id) VALUES (?,?,?,?,?,?)";
        String id = UUID.randomUUID().toString();
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(SAVE_SQL);
        ) {
            stmt.setString(1, id);
            stmt.setString(2, user.getId());
            int i = 3;
            for (Card card: cards) {
                stmt.setString(i, card.getId());
                i++;
            }
            stmt.execute();
        } catch (SQLException e) {
            return null;
        }

        return new Deck(
                id,
                user.getId(),
                cards.get(0).getId(),
                cards.get(1).getId(),
                cards.get(2).getId(),
                cards.get(3).getId()
        );
    }

    public Deck update(User user, List<Card> cards) {
        Deck oldDeck = this.find(user);
        String UPDATE_SQL = "UPDATE decks SET card1_id = ?, card2_id = ?, card3_id =  ?, card4_id = ? WHERE user_id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_SQL);
        ) {
            stmt.setString(1, cards.get(0).getId());
            stmt.setString(2, cards.get(1).getId());
            stmt.setString(3, cards.get(2).getId());
            stmt.setString(4, cards.get(3).getId());
            stmt.setString(5, user.getId());

            stmt.execute();
        } catch (SQLException e) {
            return null;
        }

        return new Deck(
                oldDeck.getId(),
                user.getId(),
                cards.get(0).getId(),
                cards.get(1).getId(),
                cards.get(2).getId(),
                cards.get(3).getId()
        );
    }

    public Deck find(User user) {
        String FIND_SQL = "SELECT * FROM decks WHERE user_id = ?";
        Deck deck = null;
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_SQL);
        ) {
            stmt.setString(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                deck = new Deck(
                        rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("card1_id"),
                        rs.getString("card2_id"),
                        rs.getString("card3_id"),
                        rs.getString("card4_id")
                );
            }
            return deck;
        } catch (SQLException e) {
            return deck;
        }
    }
}
