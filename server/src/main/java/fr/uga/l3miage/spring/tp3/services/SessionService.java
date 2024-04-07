package fr.uga.l3miage.spring.tp3.services;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.enums.SessionStatus;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CreationSessionRestException;
import fr.uga.l3miage.spring.tp3.exceptions.rest.SessionChangeStateRestException;
import fr.uga.l3miage.spring.tp3.exceptions.technical.ExamNotFoundException;
import fr.uga.l3miage.spring.tp3.mappers.ExamMapper;
import fr.uga.l3miage.spring.tp3.mappers.SessionMapper;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationStepEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.ExamResponse;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionMapper sessionMapper;
    private final ExamMapper examMapper;
    private final ExamComponent examComponent;
    private final SessionComponent sessionComponent;

    public SessionResponse createSession(SessionCreationRequest sessionCreationRequest){
        try {
            EcosSessionEntity ecosSessionEntity = sessionMapper.toEntity(sessionCreationRequest);
            EcosSessionProgrammationEntity programmation = sessionMapper.toEntity(sessionCreationRequest.getEcosSessionProgrammation());
            Set<EcosSessionProgrammationStepEntity> stepEntities = sessionCreationRequest.getEcosSessionProgrammation()
                    .getSteps()
                    .stream()
                    .map(sessionMapper::toEntity)
                    .collect(Collectors.toSet());

            Set<ExamEntity> exams = examComponent.getAllById(sessionCreationRequest.getExamsId());

            ecosSessionEntity.setExamEntities(exams);
            programmation.setEcosSessionProgrammationStepEntities(stepEntities);
            ecosSessionEntity.setEcosSessionProgrammationEntity(programmation);

            ecosSessionEntity.setStatus(SessionStatus.CREATED);

            return sessionMapper.toResponse(sessionComponent.createSession(ecosSessionEntity));
        }catch (RuntimeException | ExamNotFoundException e){
            throw new CreationSessionRestException(e.getMessage());
        }
    }

    // vérifier si ma session est terminée
    private boolean isSessionEnded(EcosSessionEntity ecosSessionEntity) {
        // Initialiser un tableau avec un seul élément (fais automatiquement), qui contient la valeur true par défaut
        final boolean[] finished = {true};

        // Parcourir les étapes de programmation de la session
        ecosSessionEntity.getEcosSessionProgrammationEntity().getEcosSessionProgrammationStepEntities().forEach(val -> {
            // Si la date et l'heure de l'étape sont après la date et l'heure actuelles, alors la valeur du tableau est false
            if (val.getDateTime().isAfter(LocalDateTime.now()))
                finished[0] = false;
        });

        // Renvoyer la valeur du tableau, qui indique si la session est terminée ou non
        return finished[0];
    }

    // implémentation de 'changeState' qui à partir d'un sessionId, change l'état de la session concernée
    public Set<ExamResponse> changeState(Long sessionId){
        // Récupérer l'entité de la session 'ecosSessionEntity' à partir du composant de session
        EcosSessionEntity ecosSessionEntity = sessionComponent.getSessionById(sessionId);

        // Vérifier si la session est terminée et si son état est EVAL_STARTED
        if (isSessionEnded(ecosSessionEntity) && ecosSessionEntity.getStatus() == SessionStatus.EVAL_STARTED)
            // Changer l'état de la session en EVAL_ENDED
            ecosSessionEntity.setStatus(SessionStatus.EVAL_ENDED);
        else {
            throw new SessionChangeStateRestException("ERREUR: impossible de changer le statut de cette session", ecosSessionEntity.getStatus());
        }

        // Mapper les entités d'examen en réponses d'examen et les collecter dans un ensemble
        return ecosSessionEntity.getExamEntities()
                .stream()
                .map(examMapper::toResponse)
                .collect(Collectors.toSet());
    }
}
