package org.example.fifa_central.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CentralController {

    @PostMapping("/synchronisation")
    public String synchronisation() {
        return "Not implemented";
    }

    @GetMapping("/bestPlayers")
    public String bestPlayers() {
        return "Not implemented";
    }

    @GetMapping("/bestClubs")
    public String bestClubs() {
        return "Not implemented";
    }

    @GetMapping("/championshipRanking")
    public String championshipRanking() {
        return "Not implemented";
    }
}
