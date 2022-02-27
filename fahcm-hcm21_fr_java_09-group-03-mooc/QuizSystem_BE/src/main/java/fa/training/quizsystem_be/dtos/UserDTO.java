package fa.training.quizsystem_be.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import fa.training.quizsystem_be.entities.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;

	private String email;

	private String name;

	private String password;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
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

	private List<Subject> interests = new ArrayList<>();
}
