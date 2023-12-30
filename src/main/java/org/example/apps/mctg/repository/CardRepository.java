package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            //throw new RuntimeException(e);
        }
        return true;
    }
}
