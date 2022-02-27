package fa.training.quizsystem_be.dtos;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDTO {

	private Long id;
	private String text;
	private boolean multiple;
	private String image;
	private List<AnswerDTO> answers;
}
