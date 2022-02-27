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

import fa.training.quizsystem_be.entities.Subject;
import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.repositories.UserRepository;

@DataJpaTest(showSql = true)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository userRepository;


//	@Test
//	public void testCreateNewUser() {
//
//		
//		User user = new User("thongchuthanh2000@gmail.com",
//				"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
//				"cheng", new Date(), 
//				"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "University",true, "ADMIN");
//
//		User savedUser = userRepository.save(user);
//
//		assertThat(savedUser.getId()).isGreaterThan(0);
//	}

	@Test
	public void testCreateListUser() {

		List<User> listUser = Arrays.asList(
				new User("thaole301000@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"thao", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "",true, "ADMIN"),
				new User("tunglam@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"tunglam", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "University",true, "STUDENT"),
				new User("khanhngo@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"khanhngo", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "University",true, "STUDENT"),
				new User("tangyucheng@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"cheng123", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "University",false, "STUDENT"),
				new User("senko@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"senko", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "University",false, "TEACHER"),
				new User("panasonic@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"panasonic", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "University",true, "STUDENT"),
				new User("toshiba@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"toshiba", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "KINDERGARTEN",true, "STUDENT"),
				new User("huawei@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"huawei", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "University",false, "STUDENT"),
				new User("test@gmail.com",
						"$2a$12$F4MEWmryeZ8MQa9/UZUCCO4ksvp1cWmBM.Q2NnKSRSaLLdtEKWsuq", 
						"test", new Date(), 
						"https://lh3.googleusercontent.com/a-/AOh14GgnhPBnc4QmkixYivKu5gZWNWOMmaBwQmlXJOWc=s96-c", "KINDERGARTEN",true, "TEACHER")
				
			);

		
		userRepository.saveAll(listUser);

		Iterable<User> iterable = userRepository.findAll();

		assertThat(iterable).size().isGreaterThanOrEqualTo(8);
	}
	
	@Test
	public void testGetUserByUsername() {
		String username = "thongchuthanh2000@gmail.com";
		User user = userRepository.findByEmail(username).get();

		assertThat(user).isNotNull();
	}

}
