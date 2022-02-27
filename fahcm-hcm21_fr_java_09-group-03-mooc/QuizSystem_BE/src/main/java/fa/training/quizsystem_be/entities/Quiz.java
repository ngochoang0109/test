package fa.training.quizsystem_be.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 150, nullable = false)
	private String title;
	@Column(length = 200, nullable = false)
	private String description;

	@Column
	@CreationTimestamp
	private Date createdTime;
	@Column
	private Date updateTime;
	private boolean status;
	private Integer plays;

	private String educationLevel;

	private Integer timeLimit;
	private String image;
	private Integer maxAttempts;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	private User user;

	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Record> records;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "quiz_id")
	private List<Question> questions = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.REFRESH)
	@EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	@ToString.Exclude // Khoonhg sử dụng trong toString()
	@JoinTable(name = "subject_quizz", // Tạo ra một join Table tên là ""
			joinColumns = @JoinColumn(name = "quizz_id"), // TRong đó, khóa ngoại chính là trỏ tới class hiện tại ()
			inverseJoinColumns = @JoinColumn(name = "subject_id") // Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới
	)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Subject> subjects = new ArrayList<>();

	public Quiz(String title, String description, Date createdTime, Date updateTime, boolean status, Integer plays,
			String educationLevel, Integer timeLimit, String image) {
		super();
		this.title = title;
		this.description = description;
		this.createdTime = createdTime;
		this.updateTime = updateTime;
		this.status = status;
		this.plays = plays;
		this.educationLevel = educationLevel;
		this.timeLimit = timeLimit;
		this.image = image;
	}

}
