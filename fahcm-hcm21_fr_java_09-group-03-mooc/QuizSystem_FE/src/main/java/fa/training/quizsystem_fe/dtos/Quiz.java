package fa.training.quizsystem_fe.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class Quiz implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
    
    private String title;
    
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd")	
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")	
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;// time start take quiz of user
    private boolean status;
    private Integer plays;
    private Integer maxAttempts;
    
    private String educationLevel;
    private Integer timeLimit;
    private String image;
    @JsonIgnore
    private MultipartFile fileImg;
    private User user;
    private List<Record> records = new ArrayList<Record>();
    private Collection<Question> questions = new ArrayList<>();
    private List<Subject> subjects = new ArrayList<>();

    public boolean find(int id) {
    	for (Subject subject : subjects) {
			if(subject.getId()==id) {
				return true;
			}
		}
		return false;
    }
}
