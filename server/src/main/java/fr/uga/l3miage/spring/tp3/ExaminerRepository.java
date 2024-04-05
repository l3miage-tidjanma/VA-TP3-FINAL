package fr.uga.l3miage.spring.tp3;

import fr.uga.l3miage.spring.tp3.models.ExaminerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExaminerRepository extends JpaRepository<ExaminerEntity,Long> {
}
