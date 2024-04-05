package fr.uga.l3miage.spring.tp3.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CandidatNotFoundResponse {
    private Long candidateId;
    private String errorMessage;
    private String uri;
}
