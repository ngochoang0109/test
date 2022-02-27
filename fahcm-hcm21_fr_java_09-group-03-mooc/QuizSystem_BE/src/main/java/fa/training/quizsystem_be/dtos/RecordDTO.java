package fa.training.quizsystem_be.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class RecordDTO {
	private Long id;

//    private QuizDTO quiz;
//    @JsonIgnore
    private UserDTO user;

    private Date startTime;

    private Date submitTime;
    
    private Double score;
    
    private double time;

	public RecordDTO(Double score, int time) {
		super();
		this.score = score;
		this.time = time;
	}

	public RecordDTO() {
		super();
	}
    
}
