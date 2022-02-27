package fa.training.quizsystem_be;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import fa.training.quizsystem_be.entities.Quiz;
import fa.training.quizsystem_be.entities.Subject;
import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.repositories.QuizRepository;
import fa.training.quizsystem_be.repositories.SubjectRepository;
import fa.training.quizsystem_be.repositories.UserRepository;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SubjectRepositoryTests {

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Test
	public void testCreateQuiz() {

		List<Subject> listSubjects = Arrays.asList(
				new Subject("Mathematics"),
				new Subject("English"),
				new Subject("Literature"),
				new Subject("Information Technology"),
				new Subject("Chemistry"),
				new Subject("Physics"),
				new Subject("Philosophy"),
				new Subject("Biology"),
				new Subject("French"),
				new Subject("Japanese"),
				new Subject("Economics"),
				new Subject("Geography"),
				new Subject("Music"),
				new Subject("History"),
				new Subject("Art")
			);

		
		subjectRepository.saveAll(listSubjects);

		Iterable<Subject> iterable = subjectRepository.findAll();

		assertThat(iterable).size().isGreaterThanOrEqualTo(15);
	}
	
//	@Test
//	public void testDelete() {
//		
//		subjectRepository.deleteById(1L);
//		assertThat(subjectRepository.findAll().size()).isEqualTo(0);
//	}
}
