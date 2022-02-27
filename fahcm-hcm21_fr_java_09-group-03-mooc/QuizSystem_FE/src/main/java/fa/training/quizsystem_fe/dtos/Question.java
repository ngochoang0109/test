package fa.training.quizsystem_fe.dtos;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String text;
	private Boolean multiple;
	private String image;
	@JsonIgnore
	private MultipartFile fileImg;
	private List<Answer> answers;
}
