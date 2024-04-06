package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.CandidateComponent;
import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.mappers.ExamMapper;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.services.CandidateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.time.Month;

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
    void getCandidateAverageByCandidateId(){
        // Given //

        // mon candidat
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("TIDJANI")
                .lastname("Manyl")
                .email("Manyl.Tidjani@gmai.com")
                .phoneNumber("08457515")
                .birthDate(LocalDate.of(20003, Month.APRIL, 23))
                .hasExtraTime(true)
                // ATTENTION: pas de set en BD !!! donc .candidateEvaluationGridEntities(Set<candidateEvaluationGridEntity>) pas possible !!
                .build();



        // When //

        // Then //
    }
}