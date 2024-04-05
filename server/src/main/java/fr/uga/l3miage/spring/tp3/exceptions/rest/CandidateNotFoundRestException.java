package fr.uga.l3miage.spring.tp3.exceptions.rest;

import lombok.Getter;

@Getter
public class CandidateNotFoundRestException extends RuntimeException {
    private final Long candidateId;

    public CandidateNotFoundRestException(String message, Long candidateId) {
        super(message);
        this.candidateId = candidateId;
    }
}
