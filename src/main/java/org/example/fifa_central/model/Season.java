package org.example.fifa_central.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class Season {
    private UUID seasonId;
    private Integer seasonYear;
    private String seasonAlias;
    private Status seasonStatus;
}
