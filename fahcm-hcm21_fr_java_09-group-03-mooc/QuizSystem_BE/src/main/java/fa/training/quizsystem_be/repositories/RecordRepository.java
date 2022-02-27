package fa.training.quizsystem_be.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fa.training.quizsystem_be.entities.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {

	@Query("SELECT r FROM Record r WHERE r.user.id = ?1")
	List<Record> findByUser(Long userId);
	
//	@Query("SELECT new  fa.training.quizsystem_be.dtos.RecordDTO(r.score, TIME_TO_SEC(TIMEDIFF(r.submitTime, r.startTime))) FROM Record r")
//	List<RecordDTO> ranking();
	
	@Query("SELECT r FROM Record r WHERE r.quiz.id = ?1 order by r.score desc, r.time asc")
	List<Record> ranking(Long id);
	
	@Query("SELECT r FROM Record r WHERE r.user.id = ?1 AND r.quiz.id= ?2")
	List<Record> findByUserAndQuiz(Long userId, Long quizId);
}
