package fa.training.quizsystem_fe.payloads.reponses;

import java.util.ArrayList;
import java.util.List;

import fa.training.quizsystem_fe.dtos.Subject;
import lombok.Data;

@Data
public class QuizResponse {
	private Long id;

	private String title;

	private String description;
	private List<Subject> subjects = new ArrayList<>();
}
