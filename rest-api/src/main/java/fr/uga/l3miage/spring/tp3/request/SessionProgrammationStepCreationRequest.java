package fr.uga.l3miage.spring.tp3.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SessionProgrammationStepCreationRequest {
    private Long id;
    private String code;
    private String description;
    private LocalDateTime dateTime;
}
