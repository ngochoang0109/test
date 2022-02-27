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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String email;
	private String password;
	private String name;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthdate;

	private String educationLevel;
	private AuthProvider provider;
	private String providerId;

	@Column(unique = true)
	private String verificationCode;
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdTime;
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date updateTime;
	
	@Formula(value = "TIME_TO_SEC(TIMEDIFF(NOW(), update_time))/60")
	private Double time;

	@PrePersist
	protected void onCreate() {
		this.createdTime = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updateTime = new Date();
	}

	private String avatar;
	private boolean status;

	private String role;

	@ManyToMany(cascade = CascadeType.PERSIST) 
	@EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
	@JoinTable(name = "user_interests", // Tạo ra một join Table tên là ""
			joinColumns = @JoinColumn(name = "user_id"), // TRong đó, khóa ngoại chính là trỏ tới class hiện tại ()
			inverseJoinColumns = @JoinColumn(name = "subject_id") // Khóa ngoại thứ 2 trỏ tới thuộc tính ở dưới
	)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Subject> interests = new ArrayList<>();

	public User(String email, String password, String name, Date birthdate, String avatar, String educationLevel,
			boolean status, String role) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.birthdate = birthdate;
		this.educationLevel = educationLevel;
		this.avatar = avatar;
		this.status = status;
		this.role = role;
	}

}
