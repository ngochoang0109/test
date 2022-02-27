package fa.training.quizsystem_fe.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;

import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.Record;
import fa.training.quizsystem_fe.payloads.reponses.RecordResponse;

public interface RecordService {
	Record saveRecord(Record record);
	
	Record calculateScore(Quiz quiz,HttpServletRequest request);

	List<RecordResponse> getAllByUser(HttpServletRequest request);

	boolean checkNumberOfTakeQuiz(int maxNumber,Long quizId, HttpServletRequest request);
}
