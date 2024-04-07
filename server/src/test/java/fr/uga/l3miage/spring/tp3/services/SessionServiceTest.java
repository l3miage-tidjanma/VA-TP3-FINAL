package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CreationSessionRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.ExamNotFoundException;
import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationStepCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SessionServiceTest{

    @Autowired
    private SessionService sessionService;

    @MockBean
    private ExamComponent examComponent;

    @MockBean
    private SessionComponent sessionComponent;

    @SpyBean
    private SessionMapper sessionMapper;

    /*--------*/
    /* TESTS */
    /*--------*/

    // Description du Test: création d'une session d'examen à l'aide de 'createSession'
    // Notes: Utilisation de 'public SessionResponse createSession(SessionCreationRequest sessionCreationRequest)' nécessaire

    @Test
    void createSessionTest() throws CreationSessionRestException, ExamNotFoundException {
        // Given //
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endDate = now.plusHours(2);

        // Création d'une requête de création de session factice
        SessionCreationRequest sessionCreationRequest = SessionCreationRequest.builder()
                .name("Session test")
                .startDate(now)
                .endDate(endDate)
                .examsId(Set.of(1L, 2L)) // IDs factices des examens associés
                .ecosSessionProgrammation(null) // Données de programmation factices
                .build();

        // Création d'un examen factice
        ExamEntity examEntity = ExamEntity.builder()
                .id(1L)
                .name("CC1 RH")
                .weight(1)
                .build();

        // et création d'une grille d'évaluation factice associée à 'examEntity'
        CandidateEvaluationGridEntity candidateEvaluationGridEntity = CandidateEvaluationGridEntity.builder()
                .grade(10.5)
                .sheetNumber(25L)
                .submissionDate(now)
                .examEntity(examEntity)
                .build();

        // création de la SessionProgrammationCreationRequest
        SessionProgrammationStepCreationRequest sessionProgrammationStepCreationRequest = SessionProgrammationStepCreationRequest
                .builder()
                .code("test session")
                .description("description de la programmation de la session test")
                .build();

        // sauvegarde de la 'sessionProgrammationStepCreationRequest'


        // Mise à jour des associations bidirectionnelles
        examEntity.setCandidateEvaluationGridEntities(Set.of(candidateEvaluationGridEntity));
        // candidateEvaluationGridEntity


        // Simuler le comportement de la méthode getAllById de ExamComponent pour renvoyer l'examen factice
        when(examComponent.getAllById(anySet())).thenReturn(Set.of(examEntity));

        // transformer la request 'sessionCreationRequest' en entité
        EcosSessionEntity ecosSessionEntity = sessionMapper.toEntity(sessionCreationRequest);

        // sauvegarde de mon examen dans ma session
        ecosSessionEntity.setExamEntities(Set.of(examEntity));

        // réponse attendue
        SessionResponse expectedSessionResponse = sessionMapper.toResponse(ecosSessionEntity);

        // Simuler le comportement de la méthode toResponse de SessionMapper pour renvoyer la réponse de session factice
        when(sessionMapper.toResponse(ecosSessionEntity)).thenReturn(expectedSessionResponse);

        // When //

        // Simuler le comportement de la méthode sessionMapper.toEntity() pour renvoyer une entité EcosSessionEntity valide
        when(sessionMapper.toEntity(sessionCreationRequest)).thenReturn(new EcosSessionEntity());

        SessionResponse sessionResponse = sessionService.createSession(sessionCreationRequest); // Appel de la méthode à tester

        // Then //
        assertNotNull(sessionResponse); // Vérification que la réponse de session n'est pas nulle
        assertEquals(expectedSessionResponse, sessionResponse); // Vérification que la réponse de session est celle attendue
    }
}