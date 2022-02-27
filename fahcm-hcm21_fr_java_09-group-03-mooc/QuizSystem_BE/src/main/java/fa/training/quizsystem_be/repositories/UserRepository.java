package fa.training.quizsystem_be.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.paging.SearchRepository;

/**
 * @author ThanhTC17
 * @version 1.0
 */
@Repository
public interface UserRepository extends SearchRepository<User, Long> {
	
	Optional<User> findByVerificationCode(String verificationCode);
	
	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
}
