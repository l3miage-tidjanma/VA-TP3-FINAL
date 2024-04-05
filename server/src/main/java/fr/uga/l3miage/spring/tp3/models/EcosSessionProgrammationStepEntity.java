package fr.uga.l3miage.spring.tp3.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcosSessionProgrammationStepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(updatable = false)
    private LocalDateTime dateTime;

    @ManyToOne
    private EcosSessionProgrammationEntity ecosSessionProgrammationEntity;
}
