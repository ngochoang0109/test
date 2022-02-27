package fa.training.quizsystem_fe.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.Record;

public interface QuizService {

	List<Quiz> getAllByUser(HttpServletRequest request);

	List<Quiz> search(String key);

	List<Quiz> getPopularQuiz();

	List<Quiz> getRecommendation(HttpServletRequest request);

	boolean delete(Long id, HttpServletRequest request);

	Long createQuiz(HttpServletRequest request, Quiz quiz);

	Quiz addImg(Quiz quiz) throws IOException;

	Quiz getQuiz(long id);

	List<Integer> pareStatisticQuiz(List<Record> list);

	Page<Quiz> listByPage(int pageNum, String sortField, String sortDir, String keyword, HttpServletRequest request);

	boolean updateQuizEnabledStatus(Integer id, boolean enabled, HttpServletRequest request);

	List<Quiz> listAll(HttpServletRequest request);

	void dowloadFileTemplate(HttpServletResponse response);

}
