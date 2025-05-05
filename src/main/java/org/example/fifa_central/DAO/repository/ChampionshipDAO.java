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

    public ChampionshipDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Championship> getChampionshipRankings() {
        List<Championship> rankings = new ArrayList<>();

        String sql = """
            WITH match_differences AS (
                SELECT 
                    c.id_championnat,
                    c.nom AS championnat_nom,
                    ABS(m.score_domicile - m.score_exterieur) AS ecart_buts
                FROM 
                    match m
                JOIN 
                    championnat c ON m.id_championnat = c.id_championnat
                WHERE 
                    m.status = 'FINISHED'
            ),
            championship_stats AS (
                SELECT 
                    id_championnat,
                    championnat_nom,
                    PERCENTILE_CONT(0.5) WITHIN GROUP (ORDER BY ecart_buts) AS mediane_ecart_buts
                FROM 
                    match_differences
                GROUP BY 
                    id_championnat, championnat_nom
            )
            SELECT 
                championnat_nom,
                mediane_ecart_buts
            FROM 
                championship_stats
            ORDER BY 
                mediane_ecart_buts ASC
            """;

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Championship champ = new Championship();
                champ.setChampionnatName(rs.getString("championnat_nom"));

                rankings.add(champ);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors du calcul du classement des championnats", e);
        }

        return rankings;
    }
}