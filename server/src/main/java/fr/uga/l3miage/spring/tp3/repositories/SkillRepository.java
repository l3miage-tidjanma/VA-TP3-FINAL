package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.models.SkillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity,Long> {
    //Récupérer la liste des exam qui évaluent au moins une compétence en lien avec 'cardio' (l'idée et de leur faire utiliser un LIKE sur SkillEntity.name)
    Optional<SkillEntity> findByNameLike(String name);
}
