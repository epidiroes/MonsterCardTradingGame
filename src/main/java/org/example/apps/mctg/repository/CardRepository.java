package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepository {
    private final Database database = new Database();

    public boolean save(Card card) {
        String SAVE_SQL = "INSERT INTO cards(id, user_id, name, damage) VALUES (?, ?, ?, ?)";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(SAVE_SQL);
        ) {
            stmt.setString(1, card.getId());
            stmt.setString(2, card.getUser_id());
            stmt.setString(3, card.getName());
            stmt.setInt(4, card.getDamage());

            stmt.execute();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public List<Card> findAll(User user) {
        List<Card> cards = new ArrayList<>();
        String FIND_ALL_SQL = "SELECT * FROM cards WHERE user_id = ?";

        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_ALL_SQL);
        ) {
            stmt.setString(1,user.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Card card = new Card(
                        rs.getString("id"),
                        rs.getString("user_id"),
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

    public Optional<Card> findById(String id) {
        String FIND_SQL = "SELECT * FROM cards WHERE id = ?";

        try(
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_SQL);
        ) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            Card card = null;
            while (rs.next()) {
                card = new Card(
                        rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("name"),
                        rs.getInt("damage")
                );
            }

            if (card != null) {
                return Optional.of(card);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.print(e);
            return Optional.empty();
        }
    }

    public void updateUserIdPackage(Package pack, User user) {
        String UPDATE_USER_ID_SQL = "UPDATE cards SET user_id = ? WHERE id = ?";

        List<String> cardIds = new ArrayList<>();
        cardIds.add(pack.getCard1_id());
        cardIds.add(pack.getCard2_id());
        cardIds.add(pack.getCard3_id());
        cardIds.add(pack.getCard4_id());

        for (String cardId : cardIds) {
            try(
                    Connection con = database.getConnection();
                    PreparedStatement stmt = con.prepareStatement(UPDATE_USER_ID_SQL);
            ) {
                stmt.setString(1, user.getId());
                stmt.setString(2, cardId);
                stmt.execute();

            } catch (SQLException e) {
                System.err.print(e);
            }
        }
    }

    public void updateUserId(Card card, User user) {
        String UPDATE_USER_ID_SQL = "UPDATE cards SET user_id = ? WHERE id = ?";
        try(
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_USER_ID_SQL);
        ) {
            stmt.setString(1, user.getId());
            stmt.setString(2, card.getId());
            stmt.execute();

        } catch (SQLException e) {
            System.err.print(e);
        }
    }
}
