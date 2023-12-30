package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class PackageRepository {
    private final Database database = new Database();

    public Package save(User user, List<Card> cards) {
        String SAVE_SQL = "INSERT INTO packages(id, user_id, card1_id, card2_id, card3_id, card4_id, card5_id) VALUES (?,?,?,?,?,?,?)";
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

        return new Package(
                id,
                user.getId(),
                cards.get(0).getId(),
                cards.get(1).getId(),
                cards.get(2).getId(),
                cards.get(3).getId(),
                cards.get(4).getId()
        );
    }

}
