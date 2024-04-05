package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.CandidateRepository;
import fr.uga.l3miage.spring.tp3.TestCenterRepository;
import fr.uga.l3miage.spring.tp3.enums.TestCenterCode;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.TestCenterEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
class CandidateRepositoryTest {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private TestCenterRepository testCenterRepository;

    @Autowired
    private CandidateEvaluationGridRepository candidateEvaluationGridRepository;

    /*********/
    /* TESTS */
    /*********/

    // Test 1: testFindAllByTestCenterEntityCode    // TODO: done
    @Test
    void testFindAllByTestCenterEntityCode(){
        // Given
        // mon centre
        TestCenterEntity centerEntity = TestCenterEntity
                .builder()
                .code(TestCenterCode.GRE)
                .university(null)
                .city(null)
                .build();

        // ajout de mon centre de test dans notre 'testCenterRepository' dans ma fausse BD H2
        testCenterRepository.save(centerEntity);

        // mon candidat
        CandidateEntity candidateEntity = CandidateEntity
                .builder()
                .firstname("TIDJANI")
                .lastname("Manyl")
                .email("Manyl.Tidjani@gmai.com")
                .phoneNumber("08457515")
                .birthDate(LocalDate.of(20003, Month.APRIL, 23))
                .hasExtraTime(true)
                .candidateEvaluationGridEntities(null)
                .testCenterEntity(centerEntity)
                .build();

        // ajout du "candidateEntity" au "centerEntity" avec 'Set.of()'
        centerEntity.setCandidateEntities(Set.of(candidateEntity));

        // mise à jour de mon centre de test dans notre 'testCenterRepository' dans ma fausse BD H2
        testCenterRepository.save(centerEntity);

        // ajout de mon candidat dans notre 'candidateRepository' dans ma fausse BD H2
        candidateRepository.save(candidateEntity);


        // When
        Set<CandidateEntity> candidateEntitiesResponses = candidateRepository.findAllByTestCenterEntityCode(centerEntity.getCode());


        // Then
        assertThat(candidateEntitiesResponses).hasSize(1);
        assertThat(candidateEntitiesResponses.stream().findFirst().get().getTestCenterEntity().getCode()).isEqualTo(TestCenterCode.GRE);
    }


    // Test 2: testFindAllByCandidateEvaluationGridEntitiesGradeLessThan    // TODO
    @Test
    void testFindAllByCandidateEvaluationGridEntitiesGradeLessThan(){
        // Given

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

        // ajout de mon candidat dans notre 'candidateRepository' dans ma fausse BD H2 (équivalent à un INSERT)
        candidateRepository.save(candidateEntity);


        // ma grille d'évaluation
        CandidateEvaluationGridEntity candidateEvaluationGridEntity = CandidateEvaluationGridEntity
                .builder()
                .sheetNumber(null)
                .grade(10.0)
                .submissionDate(null)
                .candidateEntity(candidateEntity)   // OK: candidateEntity est un "attribut" (relation) contenant le @ManytoOne dans 'CandidateEntity'
                .build();

        // ajout de ma grille d'évaluation dans notre 'candidateEvaluationGridRepository' dans ma fausse BD H2 (équivalent à un INSERT)
        candidateEvaluationGridRepository.save(candidateEvaluationGridEntity);


        // When



        // Then

    }



    // Test 3: testFindAllByHasExtraTimeFalseAndBirthDateBefore // TODO
    @Test
    void testFindAllByHasExtraTimeFalseAndBirthDateBefore(){
        // Given

        // When

        // Then
    }
}

