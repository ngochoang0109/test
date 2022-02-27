package fa.training.quizsystem_be.api;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fa.training.quizsystem_be.security.CurrentUser;
import fa.training.quizsystem_be.security.UserPrincipal;
import fa.training.quizsystem_be.services.RecordService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/record")
@RequiredArgsConstructor
public class RecordAPI {
	private final RecordService recordService;

	@GetMapping("all")
	public ResponseEntity<?> getAll(@CurrentUser UserPrincipal userPrincipal) throws IOException {
		try {
			return ResponseEntity.ok().body(recordService.listAll(userPrincipal.getId()));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}

}
