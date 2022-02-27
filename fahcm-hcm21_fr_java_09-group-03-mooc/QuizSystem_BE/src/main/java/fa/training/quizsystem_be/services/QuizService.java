package fa.training.quizsystem_be.services;

import java.util.List;

import org.springframework.data.domain.Page;

import fa.training.quizsystem_be.dtos.QuizDTO;
import fa.training.quizsystem_be.entities.Quiz;
import fa.training.quizsystem_be.paging.PagingAndSortingHelper;

public interface QuizService {

	List<QuizDTO> getAllByUser(Long userId);

	List<QuizDTO> getPopularQuiz();

	List<QuizDTO> getRecommendationQuiz(Long idUser);

	List<QuizDTO> searchQuiz(String key);

	boolean delete(Long id);

	Long saveQuiz(QuizDTO quizDTO, Long idUser);

	QuizDTO getQuiz(Long id);

	Page<Quiz> listByPage(int pageNum, PagingAndSortingHelper helper);

	QuizDTO update(Long id, boolean enabled);

	List<QuizDTO> getAll();
}
