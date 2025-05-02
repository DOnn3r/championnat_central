package org.example.fifa_central.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class Coach {
    private UUID coachId;
    private String coachName;
    private String coachNationality;
    private Integer coachAge;
}
