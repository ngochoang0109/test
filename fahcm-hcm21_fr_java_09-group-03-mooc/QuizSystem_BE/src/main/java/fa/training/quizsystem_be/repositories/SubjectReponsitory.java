package fa.training.quizsystem_be.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import fa.training.quizsystem_be.entities.Subject;

public interface SubjectReponsitory extends JpaRepository<Subject, Long>{
}
