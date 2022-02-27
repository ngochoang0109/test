package fa.training.quizsystem_be;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import fa.training.quizsystem_be.entities.Quiz;
import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.repositories.QuizRepository;
import fa.training.quizsystem_be.repositories.UserRepository;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class QuizRepositoryTests {

	@Autowired
	private QuizRepository quizRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testCreateQuiz() {
		Quiz quiz = new Quiz("title", "description", new Date(), new Date(), true, 0, "University", 30,
				"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c");
		quiz.setUser(userRepository.findById(1L).get());

		Quiz saveQuiz = quizRepository.save(quiz);

		assertThat(saveQuiz.getId()).isGreaterThan(0);
	}
}
