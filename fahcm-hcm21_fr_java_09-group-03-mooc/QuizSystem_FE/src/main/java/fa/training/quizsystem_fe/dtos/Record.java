package fa.training.quizsystem_fe.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Record {
	private Long id;

//	private Quiz quiz;

	private User user;

	private Date startTime;

	private Date submitTime;

	private Double score;
	
	@JsonIgnore
	public String getName() {
		return user.getName();
	}
	@JsonIgnore
	public String getEducationLevel() {
		return user.getEducationLevel();
	}
	@JsonIgnore
	public String getEmail() {
		return user.getEmail();
	}

}
