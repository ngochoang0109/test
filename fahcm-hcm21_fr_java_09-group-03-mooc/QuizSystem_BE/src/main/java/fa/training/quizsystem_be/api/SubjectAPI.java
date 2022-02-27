package fa.training.quizsystem_be.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fa.training.quizsystem_be.services.SubjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/subject")
@RequiredArgsConstructor
public class SubjectAPI {
	private final SubjectService subjectService;

	@GetMapping("all")
	public ResponseEntity<?> getListCategory() {
		try {
			return ResponseEntity.ok(subjectService.getAll());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}
}
