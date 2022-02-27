package fa.training.quizsystem_be.services;

import java.util.List;

import fa.training.quizsystem_be.dtos.QuizDTO;
import fa.training.quizsystem_be.dtos.RecordDTO;
import fa.training.quizsystem_be.payloads.reponses.RecordResponse;

public interface RecordService {
	
	RecordDTO calculateScore(QuizDTO quizDTO, Long idUser );

	List<RecordResponse> listAll(Long userId);
	
	List<RecordDTO> ranking(Long id);
	
	Long countNumberofTake(Long userId, Long quizId);
}
