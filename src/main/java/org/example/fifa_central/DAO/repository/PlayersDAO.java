package org.example.fifa_central.DAO.repository;

import org.example.fifa_central.DAO.DataSource;
import org.example.fifa_central.model.*;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class PlayersDAO {
    private final DataSource dataSource;

    public PlayersDAO() {
        this.dataSource = new DataSource();
    }

    public List<Player> getBestPlayers(int top, Duration_type playingTimeUnit) {
        List<Player> players = new ArrayList<>();
        String sql = """
            SELECT 
                j.id_joueur, j.nom, j.numero, j.poste, j.nationalite, j.age,
                c.nom AS club_name, c.acronyme, c.annee_creation, c.nom_stade,
                e.nom AS coach_name, e.nationalite AS coach_nationality,
                ch.nom AS championnat_name,
                sjs.buts_marques, sjs.duree_jeu_value
            FROM 
                stats_joueur_saison sjs
            JOIN 
                joueur j ON sjs.id_joueur = j.id_joueur
            JOIN 
                club c ON j.id_club = c.id_club
            JOIN 
                championnat ch ON c.id_championnat = ch.id_championnat
            LEFT JOIN 
                entraineur e ON c.id_club = e.id_club
            ORDER BY 
                sjs.buts_marques DESC,
                sjs.duree_jeu_value DESC
            LIMIT ?
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, top);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    long durationValue = rs.getLong("duree_jeu_value");
                    if (playingTimeUnit == Duration_type.MINUTE) {
                        durationValue = durationValue / 60;
                    } else if (playingTimeUnit == Duration_type.HOUR) {
                        durationValue = durationValue / 3600;
                    }

                    Player player = new Player(
                            UUID.fromString(rs.getString("id_joueur")),
                            rs.getString("nom"),
                            rs.getInt("numero"),
                            Post.valueOf(rs.getString("poste")),
                            rs.getString("nationalite"),
                            rs.getInt("age"),
                            new Club(
                                    null,
                                    rs.getString("club_name"),
                                    rs.getString("acronyme"),
                                    rs.getInt("annee_creation"),
                                    rs.getString("nom_stade"),
                                    new Coach(
                                            null,
                                            rs.getString("coach_name"),
                                            rs.getString("coach_nationality"),
                                            null
                                    )
                            ),
                            playingTimeUnit
                    );

                    players.add(player);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching best players", e);
        }

        return players;
    }
}