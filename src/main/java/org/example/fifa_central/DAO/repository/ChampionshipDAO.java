package org.example.fifa_central.DAO.repository;

import org.example.fifa_central.DAO.DataSource;
import org.example.fifa_central.model.Championnat;
import org.example.fifa_central.model.Championship;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChampionshipDAO {
    private final DataSource dataSource;

    public ChampionshipDAO() {
        this.dataSource = new DataSource();
    }

    public List<Championship> getChampionshipRankings() {
        List<Championship> championships = new ArrayList<>();
        String sql = """
            WITH championship_stats AS (
                SELECT 
                    c.id_championnat,
                    c.nom AS championship_name,
                    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY scs.difference_buts) AS median_diff_goals
                FROM 
                    championnat c
                JOIN 
                    club cl ON c.id_championnat = cl.id_championnat
                JOIN 
                    stats_club_saison scs ON cl.id_club = scs.id_club
                GROUP BY 
                    c.id_championnat, c.nom
            )
            SELECT 
                cs.championship_name,
                cs.median_diff_goals
            FROM 
                championship_stats cs
            ORDER BY 
                cs.median_diff_goals ASC
            """;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                Championship championnat = new Championship(
                        null,
                        rs.getString("championship_name"),
                        null,
                        null
                );

                championships.add(championnat);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching championship rankings", e);
        }

        return championships;
    }
}