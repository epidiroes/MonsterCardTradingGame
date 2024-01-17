package org.example.apps.mctg.repository;

import org.example.apps.mctg.database.Database;
import org.example.apps.mctg.entity.Card;
import org.example.apps.mctg.entity.Package;
import org.example.apps.mctg.entity.User;
import org.example.server.http.HttpException;
import org.example.server.http.HttpStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<Package> findAll(User user) {
        List<Package> packages = new ArrayList<>();
        String FIND_ALL_SQL = "SELECT * FROM packages WHERE user_id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(FIND_ALL_SQL);
        ) {
            stmt.setString(1,user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Package pack = new Package(
                        rs.getString("id"),
                        rs.getString("user_id"),
                        rs.getString("card1_id"),
                        rs.getString("card2_id"),
                        rs.getString("card3_id"),
                        rs.getString("card4_id"),
                        rs.getString("card5_id")
                );
                packages.add(pack);
            }
            return packages;
        } catch (SQLException e) {
            return packages;
        }
    }

    public Package transaction(User user, Package pack) {
        String UPDATE_USER_ID_SQL = "UPDATE packages SET user_id = ? WHERE id = ?";
        try (
                Connection con = database.getConnection();
                PreparedStatement stmt = con.prepareStatement(UPDATE_USER_ID_SQL);
        ) {
            stmt.setString(1, user.getId());
            stmt.setString(2, pack.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new HttpException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while updating package");
        }
        pack.setUser_id(user.getId());
        return pack;
    }

}
