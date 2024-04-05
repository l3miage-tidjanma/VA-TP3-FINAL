package fr.uga.l3miage.spring.tp3.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcosSessionProgrammationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String label;

    @OneToMany(mappedBy = "ecosSessionProgrammationEntity")
    private Set<EcosSessionProgrammationStepEntity> ecosSessionProgrammationStepEntities;

}
