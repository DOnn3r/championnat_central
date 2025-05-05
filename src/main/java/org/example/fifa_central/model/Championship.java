package org.example.fifa_central.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class Championship {
    private String championnatId;
    private String championnatName;
    private String pays;
    private List<Club> clubs;
}
