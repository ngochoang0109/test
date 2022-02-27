package fa.training.quizsystem_fe.payloads.reponses;

import java.util.ArrayList;
import java.util.List;

import fa.training.quizsystem_fe.dtos.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectResponse {
	private Long id;
	private String name;

	private List<Quiz> quizzes = new ArrayList<>();
}


