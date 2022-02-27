package fa.training.quizsystem_be.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fa.training.quizsystem_be.dtos.QuizDTO;
import fa.training.quizsystem_be.entities.Quiz;
import fa.training.quizsystem_be.entities.Subject;
import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.exceptions.ResourceNotFoundException;
import fa.training.quizsystem_be.paging.PagingAndSortingHelper;
import fa.training.quizsystem_be.repositories.QuizRepository;
import fa.training.quizsystem_be.repositories.SubjectReponsitory;
import fa.training.quizsystem_be.repositories.UserRepository;
import fa.training.quizsystem_be.services.QuizService;
import fa.training.quizsystem_be.utils.ObjectMapperUtils;

@Service
public class QuizServiceImpl implements QuizService {
	private static final int QUIZZES_PER_PAGE = 4;
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<QuizDTO> getAllByUser(Long userId) {
		return ObjectMapperUtils.mapAll(quizRepository.findByUser(userId), QuizDTO.class);
	}

	@Override
	public boolean delete(Long id) {
		Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quiz", "id", id));

		quizRepository.delete(quiz);
		return true;
	}

	@Override
	public Long saveQuiz(QuizDTO quizDTO, Long idUser) {
		Quiz quiz = null;
		try {
			User user = userRepository.findById(idUser)
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", idUser));
			quiz = ObjectMapperUtils.map(quizDTO, Quiz.class);
			quiz.setUpdateTime(new Date());
			quiz.setUser(user);
			List<Subject> subjects= new ArrayList<Subject>();
			for (Subject sb : quiz.getSubjects()) {
				if (sb.getId() != null) {
					subjects.add(sb);
				}
			}
			quiz.setSubjects(subjects);;
			quiz = quizRepository.save(quiz);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return null;
		}
		return quiz.getId();
	}

	@Override
	public QuizDTO getQuiz(Long id) {
		Optional<Quiz> quizOptional = quizRepository.findById(id);
		QuizDTO quizDTO = null;
		if (quizOptional.isPresent()) {
			quizDTO = ObjectMapperUtils.map(quizOptional.get(), QuizDTO.class);
		}

		return quizDTO;
	}

	@Override
	public List<QuizDTO> getPopularQuiz() {
		List<Quiz> listQuiz = quizRepository.findByStatusOrderByPlaysDesc(true,PageRequest.of(0, 11));
		return ObjectMapperUtils.mapAll(listQuiz, QuizDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Quiz> listByPage(int pageNum, PagingAndSortingHelper helper) {
		// TODO Auto-generated method stub
		return (Page<Quiz>) helper.listEntities(pageNum, QUIZZES_PER_PAGE, quizRepository);
	}

	@Override
	public List<QuizDTO> searchQuiz(String key) {

		if (key.equals("") || key == null) {
			return null;
		}
		Pageable page = PageRequest.of(0, 2);
		List<Quiz> listQuiz = quizRepository.findByStatusAndTitleIgnoreCaseContaining(true,key);
		return ObjectMapperUtils.mapAll(listQuiz, QuizDTO.class);
	}

	@Override
	public List<QuizDTO> getRecommendationQuiz(Long idUser) {
		List<Subject> subjects = userRepository.findById(idUser).get().getInterests();
		List<Quiz> listQuiz = quizRepository.findDistinctByStatusAndSubjectsIn(true,subjects, PageRequest.of(0, 10)).getContent();
		return ObjectMapperUtils.mapAll(listQuiz, QuizDTO.class);
	}
	public QuizDTO update(Long id, boolean enabled) {
		try {
			Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quizz", "id", id));
			quiz.setStatus(enabled);

			return ObjectMapperUtils.map(quizRepository.save(quiz), QuizDTO.class);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return null;
		}
	}


	@Override
	public List<QuizDTO> getAll() {
		return ObjectMapperUtils.mapAll(quizRepository.findAll(), QuizDTO.class);
	}
}
