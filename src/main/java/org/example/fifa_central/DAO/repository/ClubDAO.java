package org.example.fifa_central.DAO.repository;

import org.example.fifa_central.DAO.DataSource;
import org.example.fifa_central.model.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ClubDAO {
    private final DataSource dataSource;

    public ClubDAO() {
        this.dataSource = new DataSource();
    }

    public List<Club> getBestClubs(int top) {
        List<Club> clubs = new ArrayList<>();
        String sql = """
            SELECT 
                cl.id_club, cl.nom, cl.acronyme, cl.annee_creation, cl.nom_stade,
                e.id_entraineur, e.nom AS coach_name, e.nationalite AS coach_nationality, e.age AS coach_age,
                scs.points, scs.buts_marques, scs.buts_encaisses, scs.difference_buts, scs.clean_sheets,
                ch.nom AS championnat_name
            FROM 
                stats_club_saison scs
            JOIN 
                club cl ON scs.id_club = cl.id_club
            LEFT JOIN 
                entraineur e ON cl.id_club = e.id_club
            JOIN 
                championnat ch ON cl.id_championnat = ch.id_championnat
            ORDER BY 
                scs.points DESC, 
                scs.difference_buts DESC, 
                scs.buts_marques DESC
            LIMIT ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, top);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Club club = new Club(
                            UUID.fromString(rs.getString("id_club")),
                            rs.getString("nom"),
                            rs.getString("acronyme"),
                            rs.getInt("annee_creation"),
                            rs.getString("nom_stade"),
                            new Coach(
                                    UUID.fromString(rs.getString("id_entraineur")),
                                    rs.getString("coach_name"),
                                    rs.getString("coach_nationality"),
                                    rs.getInt("coach_age")
                            )
                    );

                    clubs.add(club);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching best clubs", e);
        }

        return clubs;
    }
}