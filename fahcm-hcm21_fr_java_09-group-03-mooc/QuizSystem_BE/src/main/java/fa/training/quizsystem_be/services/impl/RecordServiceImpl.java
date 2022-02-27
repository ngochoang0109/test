package fa.training.quizsystem_be.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fa.training.quizsystem_be.dtos.QuizDTO;
import fa.training.quizsystem_be.dtos.RecordDTO;
import fa.training.quizsystem_be.entities.Quiz;
import fa.training.quizsystem_be.entities.Record;
import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.exceptions.ResourceNotFoundException;
import fa.training.quizsystem_be.payloads.reponses.RecordResponse;
import fa.training.quizsystem_be.repositories.QuizRepository;
import fa.training.quizsystem_be.repositories.RecordRepository;
import fa.training.quizsystem_be.repositories.UserRepository;
import fa.training.quizsystem_be.services.AnswerService;
import fa.training.quizsystem_be.services.RecordService;
import fa.training.quizsystem_be.utils.ObjectMapperUtils;

@Service
public class RecordServiceImpl implements RecordService {

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuizRepository quizRepository;

	@Override
	public RecordDTO calculateScore(QuizDTO quizDTO, Long idUser) {
		double score = answerService.calculateScore(quizDTO);
		Record record = new Record();
		RecordDTO recordDTO = null;
		Quiz quiz = quizRepository.findById(quizDTO.getId()).get();
		quiz.setPlays(quiz.getPlays() + 1);
		quizRepository.save(quiz);
		try {
			User user = userRepository.findById(idUser)
					.orElseThrow(() -> new ResourceNotFoundException("User", "id", idUser));
			record.setUser(user);
			record.setQuiz(quiz);
			record.setStartTime(quizDTO.getStartTime());
			record.setScore(score);
			record.setSubmitTime(new Date());
			record = recordRepository.save(record);
			recordDTO = ObjectMapperUtils.map(record, RecordDTO.class);
		} catch (Exception e) {
			return null;
		}
		return recordDTO;
	}

	@Override
	public List<RecordResponse> listAll(Long userId) {
		System.out.println(userId);
		return ObjectMapperUtils.mapAll(recordRepository.findByUser(userId), RecordResponse.class);
	}

	@Override
	public List<RecordDTO> ranking(Long id) {
		return ObjectMapperUtils.mapAll(recordRepository.ranking(id), RecordDTO.class);
	}

	@Override
	public Long countNumberofTake(Long userId, Long quizId) {
		
		return (long) recordRepository.findByUserAndQuiz(userId, quizId).size();
	}
}
