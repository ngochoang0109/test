package fa.training.quizsystem_be.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fa.training.quizsystem_be.dtos.QuizDTO;
import fa.training.quizsystem_be.dtos.RecordDTO;
import fa.training.quizsystem_be.paging.PagingAndSortingHelper;
import fa.training.quizsystem_be.paging.PagingAndSortingParam;
import fa.training.quizsystem_be.security.CurrentUser;
import fa.training.quizsystem_be.security.UserPrincipal;
import fa.training.quizsystem_be.services.QuizService;
import fa.training.quizsystem_be.services.RecordService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/quiz")
public class QuizAPI {

	
	private final QuizService quizService;


	private final RecordService recordService;

	@PostMapping("create")
	public Long createQuiz(@RequestBody QuizDTO quizDTO, BindingResult result,
			@CurrentUser UserPrincipal userPrincipal) {
		try {
			return quizService.saveQuiz(quizDTO, userPrincipal.getId());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't create quiz", ex);
		}
	}

	@GetMapping("{id}")
	public QuizDTO getQuiz(@PathVariable Long id) {
		try {
			return quizService.getQuiz(id);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get quiz", ex);
		}
	}

	@PostMapping("calculate-score")
	public RecordDTO saveRecord(@RequestBody QuizDTO quizDTO, @CurrentUser UserPrincipal userPrincipal) {
		try {
			return recordService.calculateScore(quizDTO, userPrincipal.getId());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save record", ex);
		}
	}
	
	@GetMapping("get-number-take/{quizid}")
	public long getNumberOfTake( @CurrentUser UserPrincipal userPrincipal, @PathVariable Long quizid) {
		try {
			return recordService.countNumberofTake(userPrincipal.getId(), quizid);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save record", ex);
		}
	}

	@GetMapping("all-user")
	public ResponseEntity<?> getAllByUser(@CurrentUser UserPrincipal userPrincipal) throws IOException {
		try {
			return ResponseEntity.ok().body(quizService.getAllByUser(userPrincipal.getId()));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getAll() throws IOException {
		try {
			return ResponseEntity.ok().body(quizService.getAll());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> getAll(@PathVariable(value = "id") Long id)
			throws IOException {
		try {
			return ResponseEntity.ok().body(quizService.delete(id));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't delete", ex);
		}
	}

	@GetMapping("rank/{id}")
	public List<RecordDTO> rank(@PathVariable Long id) {
		try {
			return recordService.ranking(id);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't ranking", ex);
		}
	}

	@GetMapping("popular")
	public List<QuizDTO> getPopularQuiz() {
		try {
			return quizService.getPopularQuiz();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}
	
	@GetMapping("recommendation")
	public List<QuizDTO> getRecommendation(@CurrentUser UserPrincipal userPrincipal) {
		try {
			return quizService.getRecommendationQuiz(userPrincipal.getId());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get recommendation", ex);
		}
	}

	@GetMapping("search/{key}")
	public List<QuizDTO> search(@PathVariable String key) {
		try {
			return quizService.searchQuiz(key);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}
	
	@GetMapping("page/{pageNum}")
	public ResponseEntity<?> listByPage(
			@PagingAndSortingParam(listName = "listQuizzes", moduleURL = "/quizzes") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {
		try {
			return ResponseEntity.ok().body(quizService.listByPage(pageNum, helper));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}

	@PutMapping("{id}/enabled/{status}")
	public ResponseEntity<?> updateQuizEnabledStatus(@PathVariable("id") Long id,
			@PathVariable("status") boolean enabled) {
		try {
			return ResponseEntity.ok().body(quizService.update(id,enabled));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save", ex);
		}
	}
}
