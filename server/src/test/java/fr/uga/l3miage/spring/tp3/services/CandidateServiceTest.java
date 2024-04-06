package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.exceptions.technical.CandidateNotFoundException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Month;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.when;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CandidateServiceTest{

    @Autowired
    private CandidateService candidateService;

    @MockBean
    private CandidateComponent candidateComponent;

    @MockBean
    private ExamComponent examComponent;

//    @SpyBean
//    private ExamMapper examMapper;

    /*--------*/
    /* TESTS */
    /*--------*/

    /* Test 1: Récupération de la moyenne d'un candidat -> Double getCandidateAverage(Long candidateId) */
    // Notes: utilisation de 'public CandidateEntity getCandidatById(Long id)' nécessaire
    @Test
    void getCandidateAverageByCandidateId() throws CandidateNotFoundException {
        // Given //

        // mon candidat
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("TIDJANI")
                .lastname("Manyl")
                .email("Manyl.Tidjani@gmail.com")
                .phoneNumber("08457515")
                .birthDate(LocalDate.of(2003, Month.APRIL, 23))
                .hasExtraTime(true)
                // ATTENTION: pas de set en BD !!! donc .candidateEvaluationGridEntities(Set<candidateEvaluationGridEntity>) pas possible !!
                .build();

        // sa grille d'évaluation
        CandidateEvaluationGridEntity candidateEvaluationGridEntity = CandidateEvaluationGridEntity
                .builder()
                .sheetNumber(null)
                .grade(10.5)
                .submissionDate(null)
                .candidateEntity(candidateEntity)   // OK: candidateEntity est un "attribut" (relation) contenant le @ManytoOne dans 'CandidateEntity'
                .build();

        // Simuler le comportement du repository pour retourner l'objet CandidateEntity
        when(candidateComponent.getCandidatById(anyLong())).thenReturn(candidateEntity);

        // Simuler le comportement de la méthode getCandidateAverage de CandidateService
        Double expectedAverage = 10.5; // Définir la moyenne attendue
        when(candidateService.getCandidateAverage(anyLong())).thenReturn(expectedAverage);

        // When //
        // Appel à la méthode testée 'getCandidateAverage'
        Double average = candidateService.getCandidateAverage(anyLong());

        // Then //
        // Récupération de la note du candidat à partir de la grille d'évaluation
        Double grade = candidateEvaluationGridEntity.getGrade();

        // Vérifie si la moyenne retournée par le service est égal à la moyenne attendue
        assertEquals(expectedAverage, average); // Vérifie si la moyenne retournée par le service est égale à la moyenne attendue

    }
}