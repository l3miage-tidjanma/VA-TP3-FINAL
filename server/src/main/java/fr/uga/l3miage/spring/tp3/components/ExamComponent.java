package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.exceptions.technical.ExamNotFoundException;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.models.SkillEntity;
import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
import fr.uga.l3miage.spring.tp3.repositories.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ExamComponent {
    private final SkillRepository skillRepository;
    private final ExamRepository examRepository;


    public Set<ExamEntity> getAllCardioExam(){
        final SkillEntity skillEntity = skillRepository.findByNameLike("cardio").orElseThrow();
        return examRepository.findAllBySkillEntitiesContaining(skillEntity);
    }

    public Set<ExamEntity> getAllById(Set<Long> examIds) throws ExamNotFoundException {
        List<ExamEntity> examEntities = examRepository.findAllById(examIds);
        if(examEntities.size() == examIds.size()) {
            return new HashSet<>(examEntities);
        }else throw new ExamNotFoundException("Un exam n'a pas été trouvé");
    }

}
