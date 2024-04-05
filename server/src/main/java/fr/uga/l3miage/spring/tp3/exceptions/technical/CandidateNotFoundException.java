package fr.uga.l3miage.spring.tp3.exceptions.technical;

import lombok.Getter;

@Getter
public class CandidateNotFoundException extends Exception{
    private final Long candidateId;

    public CandidateNotFoundException(String message, Long candidateId) {
        super(message);
        this.candidateId = candidateId;
    }
}
