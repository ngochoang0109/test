package fa.training.quizsystem_be.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fa.training.quizsystem_be.entities.Quiz;
import fa.training.quizsystem_be.entities.Subject;
import fa.training.quizsystem_be.paging.SearchRepository;
@Repository
public interface QuizRepository extends SearchRepository<Quiz, Long> {

	@Query("Select q from Quiz q where q.user.id = ?1")
	List<Quiz> findByUser(Long userId);
	
	Quiz findByStatusAndId(boolean status,Long id);

	@Query("Select q from Quiz q where q.id = ?1 and q.user.id =?2")
	Quiz findByIdAndUser(Long id, Long userId);

	List<Quiz> findByTitleIgnoreCaseContaining(String title);
	
	List<Quiz> findByStatusAndTitleIgnoreCaseContaining(boolean status,String title);

	List<Quiz> findByOrderByPlaysDesc(Pageable page);
	
	List<Quiz> findByStatusOrderByPlaysDesc(boolean status,Pageable page);
	
	Page<Quiz> findDistinctByStatusAndSubjectsIn(boolean status,List<Subject> subjects, Pageable page);
	
	Page<Quiz> findDistinctBySubjectsIn(List<Subject> subjects, Pageable page);

	@Query("SELECT q FROM Quiz q WHERE CONCAT(q.id, ' ', q.title) LIKE %?1%")
	public Page<Quiz> findAll(String keyword, Pageable pageable);

	
	List<Quiz> findAll();

}
