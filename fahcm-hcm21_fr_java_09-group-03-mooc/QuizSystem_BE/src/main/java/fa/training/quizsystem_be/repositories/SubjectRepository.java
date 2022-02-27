package fa.training.quizsystem_be.repositories;

import fa.training.quizsystem_be.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
