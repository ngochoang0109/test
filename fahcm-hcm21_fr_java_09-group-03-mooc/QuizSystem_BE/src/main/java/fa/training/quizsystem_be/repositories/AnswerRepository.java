package fa.training.quizsystem_be.repositories;

import fa.training.quizsystem_be.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository  extends JpaRepository<Answer,Long> {
	
	@Query("SELECT COUNT(a) FROM Answer a WHERE a.correct = 1 AND a.question.id = :id")
    Integer getTotalCorrectAnswers(@Param("id") Long questionId);
}
