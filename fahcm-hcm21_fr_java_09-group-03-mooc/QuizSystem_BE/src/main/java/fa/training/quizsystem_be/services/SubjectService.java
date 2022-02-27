package fa.training.quizsystem_be.services;

import java.util.List;

import fa.training.quizsystem_be.dtos.SubjectDTO;

public interface SubjectService {
	List<SubjectDTO> getAll();
}
