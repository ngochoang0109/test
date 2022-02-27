package fa.training.quizsystem_fe.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Long id;

	private String email;
	private String password;
	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthdate;

	private String educationLevel;
	private String verificationCode;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdTime;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;

	private String avatar;
	private boolean status;

	private String role;

//    @Convert(converter = StringListConverter.class)
	private List<Subject> interests = new ArrayList<>();

	@JsonIgnore
	public boolean find(int id) {
		for (Subject subject : interests) {
			if (subject.getId() == id) {
				return true;
			}
		}
		return false;
	}
}
