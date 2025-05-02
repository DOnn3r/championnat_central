package org.example.fifa_central.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Club {
    private UUID clubId;
    private String clubName;
    private String clubAcronym;
    private Integer creationYear;
    private String stadium;
    private Coach coach;
}
