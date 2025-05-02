package org.example.fifa_central.DAO.repository;

import org.example.fifa_central.DAO.DataSource;
import org.example.fifa_central.model.Duration_type;
import org.example.fifa_central.model.Player;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PlayersDAO {
    private DataSource dataSource;

    public PlayersDAO(){
        this.dataSource = new DataSource();
    }

    public List<Player> getBestPlayers(int top, Duration_type playingTimeUnit){
        List<Player> players = new ArrayList<>();
        String sql = 'select p.id_joueur, p.nom, p.numero, p.poste, p.nationalite, p.age, c.nom'
    }
}
