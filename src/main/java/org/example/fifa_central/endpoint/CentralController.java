package org.example.fifa_central.endpoint;

import org.example.fifa_central.DAO.repository.ChampionshipDAO;
import org.example.fifa_central.DAO.repository.ClubDAO;
import org.example.fifa_central.DAO.repository.PlayersDAO;
import org.example.fifa_central.model.Championship;
import org.example.fifa_central.model.Club;
import org.example.fifa_central.model.Duration_type;
import org.example.fifa_central.model.Player;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CentralController {
    private final PlayersDAO playersDAO = new PlayersDAO();
    private final ClubDAO clubDAO = new ClubDAO();
    private final ChampionshipDAO championshipDAO = new ChampionshipDAO();

    @PostMapping("/synchronisation")
    public String synchronisation() {
        return "Not implemented";
    }

    @GetMapping("/bestPlayers")
    public List<Player> getBestPlayers(
            @RequestParam(required = false, defaultValue = "5") int top,
            @RequestParam Duration_type playingTimeUnit) {

        return playersDAO.getBestPlayers(top, playingTimeUnit);
    }

    @GetMapping("/bestClubs")
    public List<Club> getBestClubs(
            @RequestParam(required = false, defaultValue = "5") int top) {

        return clubDAO.getBestClubs(top);
    }

    @GetMapping("/championshipRanking")
    public List<Championship> getChampionshipRankings() {
        return championshipDAO.getChampionshipRankings();
    }
}
