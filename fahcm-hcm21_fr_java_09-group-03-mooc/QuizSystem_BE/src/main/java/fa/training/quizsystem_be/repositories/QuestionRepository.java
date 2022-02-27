package fa.training.quizsystem_be.repositories;

import fa.training.quizsystem_be.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
}
