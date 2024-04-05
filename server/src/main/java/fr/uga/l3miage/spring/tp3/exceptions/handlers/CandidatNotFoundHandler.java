package fr.uga.l3miage.spring.tp3.exceptions.handlers;


import fr.uga.l3miage.spring.tp3.exceptions.CandidatNotFoundResponse;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class CandidatNotFoundHandler {

    @ExceptionHandler(CandidateNotFoundRestException.class)
    public ResponseEntity<CandidatNotFoundResponse> handle(HttpServletRequest httpServletRequest, Exception exception){
        CandidateNotFoundRestException candidateNotFoundRestException = (CandidateNotFoundRestException) exception;
        CandidatNotFoundResponse response = CandidatNotFoundResponse
                .builder()
                .candidateId(candidateNotFoundRestException.getCandidateId())
                .errorMessage(candidateNotFoundRestException.getMessage())
                .uri(httpServletRequest.getRequestURI())
                .build();
        return ResponseEntity.status(404).body(response);
    }
}
