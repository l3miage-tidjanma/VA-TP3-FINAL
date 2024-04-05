package fr.uga.l3miage.spring.tp3;

import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.models.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity,Long> {
    Set<ExamEntity> findAllBySkillEntitiesContaining(SkillEntity skillEntity);
}
