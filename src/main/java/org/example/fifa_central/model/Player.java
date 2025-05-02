package org.example.fifa_central.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {
    private UUID playerId;
    private String playerName;
    private Integer playerNumber;
    private Post post;
    private String playerNationality;
    private Integer playerAge;
    private Club club;
    private Duration_type playingTime;
}
