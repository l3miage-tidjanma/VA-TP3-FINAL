package fr.uga.l3miage.spring.tp3.controllers;

import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;

import fr.uga.l3miage.spring.tp3.responses.CandidateResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@AutoConfigureTestDatabase
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class CandidateControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private CandidateRepository candidateRepository;

    @BeforeEach
    @Transactional
    public void cleanBDsetUp() {
        candidateRepository.deleteAll();
    }

    @Test
    void getAverageCandidateFound() {
        // Given //
        final HttpHeaders headers = new HttpHeaders();

        // création des examens
        ExamEntity examEntity1 = ExamEntity.builder().weight(1).build();
        ExamEntity examEntity2 = ExamEntity.builder().weight(2).build();

        // création des grilles d'évaluation associées
        CandidateEvaluationGridEntity candidateEvaluationGrid1 = CandidateEvaluationGridEntity
                .builder()
                .grade(10.5)
                .examEntity(examEntity1)
                .build();
        CandidateEvaluationGridEntity candidateEvaluationGrid2 = CandidateEvaluationGridEntity
                .builder()
                .grade(15.25)
                .examEntity(examEntity2)
                .build();

        // création du candidat
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .id(9L)
                .email("rayane@yahoo.fr")
                .candidateEvaluationGridEntities(Set.of(candidateEvaluationGrid1, candidateEvaluationGrid2))
                .build();

        // sauvegarde du candidat dans la BD H2
        candidateRepository.save(candidateEntity);

        // When //
        // récupération de la moyenne a travers l'api
        ResponseEntity<CandidateResponse> response = testRestTemplate.exchange("/api/candidates/{id}/average", HttpMethod.GET, new HttpEntity<>(null, headers), CandidateResponse.class, Map.of("id",9));

        // Then //
        // vérifier que le code statut est OK (=200)
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Vérification que le corps de la réponse ne contient pas de "NaN"
        assertNotNull(response.getBody());
    }


    @Test
    void getAverageCandidateNotFound(){
        // Given //
        final HttpHeaders headers = new HttpHeaders();

        // When //
        ResponseEntity<CandidateNotFoundRestException> response = testRestTemplate.exchange("/api/candidates/9/average", HttpMethod.GET, new HttpEntity<>(null, headers), CandidateNotFoundRestException.class);

        // Then //
        // vérifier que le code statut est NOT_FOUND (=404)
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}