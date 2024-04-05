package fr.uga.l3miage.spring.tp3.repositories;

import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcosSessionProgrammationRepository extends JpaRepository<EcosSessionProgrammationEntity,Long> {
}
