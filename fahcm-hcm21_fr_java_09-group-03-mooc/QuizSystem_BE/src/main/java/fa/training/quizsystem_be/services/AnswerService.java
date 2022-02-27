package fa.training.quizsystem_be.services;

import fa.training.quizsystem_be.dtos.AnswerDTO;
import fa.training.quizsystem_be.dtos.QuestionDTO;
import fa.training.quizsystem_be.dtos.QuizDTO;

public interface AnswerService {

	AnswerDTO saveAnswer(AnswerDTO newAnswer);

	AnswerDTO getAnswer(Long id);

	double calculateScore(QuizDTO userQuiz);

	double getScoreForMultipleAnswers(QuestionDTO question);

}
