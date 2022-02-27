package fa.training.quizsystem_be.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fa.training.quizsystem_be.dtos.AnswerDTO;
import fa.training.quizsystem_be.dtos.QuestionDTO;
import fa.training.quizsystem_be.dtos.QuizDTO;
import fa.training.quizsystem_be.entities.Answer;
import fa.training.quizsystem_be.entities.Question;
import fa.training.quizsystem_be.exceptions.ResourceNotFoundException;
import fa.training.quizsystem_be.repositories.AnswerRepository;
import fa.training.quizsystem_be.services.AnswerService;
import fa.training.quizsystem_be.utils.ObjectMapperUtils;

@Service
public class AnswerServiceImpl implements AnswerService {
	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public double calculateScore(QuizDTO quiz) {

		double score = 0.0;
		double scoreForOneQuestion = 10.0 / (double) quiz.getQuestions().size();
		System.err.println("scoreForOneQuestion:" + scoreForOneQuestion);
		for (QuestionDTO question : quiz.getQuestions()) {
			if (question.isMultiple()) {
				score += getScoreForMultipleAnswers(question) * scoreForOneQuestion;
			} else {
				if (question.getAnswers() != null) {
					for (AnswerDTO answer : question.getAnswers()) {
						if (answer.getId() != null && getAnswer(answer.getId()).isCorrect()) {
							score += scoreForOneQuestion;
						}
					}
				}
			}
		}

		return score;
	}

	@Override
	public double getScoreForMultipleAnswers(QuestionDTO ques) {
		Question question = ObjectMapperUtils.map(ques, Question.class);
		double score = 0.0;
		double correctAnswerCount = 0.0;
		double totalCorrectAnswers = answerRepository.getTotalCorrectAnswers(question.getId());
		System.err.println("totalCorrectAnswers:" + totalCorrectAnswers);
		if (question.getAnswers() != null) {
			for (Answer answer : question.getAnswers()) {
				if (answer.getId() != null) {
					if (getAnswer(answer.getId()).isCorrect()) {
						correctAnswerCount++;
					}else {
						return 0.0;
					}
				}
			}
		}
		try {
			score = correctAnswerCount / totalCorrectAnswers;
		} catch (ArithmeticException e) {
			System.err.println(e.getMessage());
		}
		return score;
	}

	@Override
	public AnswerDTO saveAnswer(AnswerDTO answerDTO) {
		Answer answer = ObjectMapperUtils.map(answerDTO, Answer.class);

		return ObjectMapperUtils.map(answerRepository.save(answer), AnswerDTO.class);
	}

	@Override
	public AnswerDTO getAnswer(Long id) {
		Answer answer = answerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Answer", "id", id));

		return ObjectMapperUtils.map(answer, AnswerDTO.class);
	}
}
