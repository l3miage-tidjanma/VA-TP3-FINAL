package fr.uga.l3miage.spring.tp3.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SessionProgrammationCreationRequest {
    private Long id;
    private String label;

    private Set<SessionProgrammationStepCreationRequest> steps;
}