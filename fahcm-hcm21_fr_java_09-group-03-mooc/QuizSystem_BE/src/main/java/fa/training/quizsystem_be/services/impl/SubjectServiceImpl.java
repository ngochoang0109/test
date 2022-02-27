package fa.training.quizsystem_be.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fa.training.quizsystem_be.dtos.SubjectDTO;
import fa.training.quizsystem_be.repositories.SubjectReponsitory;
import fa.training.quizsystem_be.services.SubjectService;
import fa.training.quizsystem_be.utils.ObjectMapperUtils;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectReponsitory subjectReponsitory;

	@Override
	public List<SubjectDTO> getAll() {
		// TODO Auto-generated method stub
		return ObjectMapperUtils.mapAll(subjectReponsitory.findAll(), SubjectDTO.class);

	}

}
