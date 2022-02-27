package fa.training.quizsystem_be.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table
public class QuizComment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 200, nullable = false)
	private String text;
	private boolean isFlagged;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "quiz_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private Quiz quiz;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "user_id")
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private User user;
}
