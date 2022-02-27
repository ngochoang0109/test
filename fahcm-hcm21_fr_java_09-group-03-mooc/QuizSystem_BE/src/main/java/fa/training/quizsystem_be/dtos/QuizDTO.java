package fa.training.quizsystem_be.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class QuizDTO {

	
	private Long id;

	private String title;

	private String description;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdTime;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date startTime;// time start take quiz of user

	private boolean status;
	private Integer plays;

	private String educationLevel;
	private Integer timeLimit;
	private String image;
	private Integer maxAttempts;
	
	private UserDTO user;
	private List<QuestionDTO> questions = new ArrayList<>();

	private List<SubjectDTO> subjects = new ArrayList<>();

    private List<RecordDTO> records = new ArrayList<>();
}
