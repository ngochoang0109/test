package fa.training.quizsystem_be;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.repositories.QuizRepository;
import fa.training.quizsystem_be.repositories.RecordRepository;
import fa.training.quizsystem_be.repositories.UserRepository;
import fa.training.quizsystem_be.entities.Quiz;
import fa.training.quizsystem_be.entities.Record;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RecordRepositoryTests {

	@Autowired
	private RecordRepository recordRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuizRepository quizRepository;

//	@Test
//	public void testCreateNew() {
//
//		User user = userRepository.findById(1L).get();
//		Quiz quiz = quizRepository.findById(6L).get();
//		Record record = new Record(new Date(),new Date(),10D);
//		record.setUser(user);
//		record.setQuiz(quiz);
//		Record savedRecord = recordRepository.save(record);
//
//		assertThat(savedRecord.getId()).isGreaterThan(0);
//	}

	@Test
	public void testGetUserByUserID() {
		Long userId = 1L;
		List<Record> list = recordRepository.findByUser(userId);
		System.out.print(list);
		assertThat(list.size()).isGreaterThan(0);
	}

}
