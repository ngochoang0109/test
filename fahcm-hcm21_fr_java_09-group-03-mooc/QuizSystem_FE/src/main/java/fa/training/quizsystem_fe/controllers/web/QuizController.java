package fa.training.quizsystem_fe.controllers.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.Record;
import fa.training.quizsystem_fe.dtos.Subject;
import fa.training.quizsystem_fe.export_file.quizzes.QuizCsvExporter;
import fa.training.quizsystem_fe.export_file.quizzes.QuizExcelExporter;
import fa.training.quizsystem_fe.export_file.quizzes.QuizPdfExporter;
import fa.training.quizsystem_fe.export_file.records.RecordCsvExporter;
import fa.training.quizsystem_fe.export_file.records.RecordExcelExporter;
import fa.training.quizsystem_fe.export_file.records.RecordPdfExporter;
import fa.training.quizsystem_fe.import_file.ImportExcel;
import fa.training.quizsystem_fe.services.QuizService;
import fa.training.quizsystem_fe.services.RecordService;
import fa.training.quizsystem_fe.services.SubjectService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuizController {

	private final QuizService quizService;

	private final RecordService recordService;

	private final SubjectService subjectService;

	@GetMapping("auth/quiz/create")
	public String createQuizPage(Quiz quiz, Model model) {
		List<Subject> subjects = subjectService.findAll();
		quiz.setPlays(0);
		model.addAttribute(quiz);
		model.addAttribute("subjectList", subjects);
		model.addAttribute("page", "create");

		return "web/createquiz";
	}

	@PostMapping(value = "auth/quiz/create")
	public String createQuiz(@ModelAttribute Quiz quiz, BindingResult result, HttpServletRequest request)
			throws IOException {
		quiz = quizService.addImg(quiz);
		Long idQuiz = quizService.createQuiz(request, quiz);
		return "redirect:/quiz/view/" + idQuiz;
	}

	@GetMapping("auth/quiz/add-question/{current-question}")
	public String addQuestion(@PathVariable(value = "current-question") Integer questionCount, Model model) {
		model.addAttribute("questionNo", questionCount + 1);
		return "web/fragments/newquestion :: new-question";
	}

	@GetMapping("auth/quiz/add-answer/{current-question}/{current-answer}")
	public String addAnswer(Model model, @PathVariable(value = "current-question") Integer questionNo,
			@PathVariable(value = "current-answer") Integer answerCount) {
		model.addAttribute("questionNo", questionNo);
		model.addAttribute("answerNo", answerCount + 1);
		return "web/fragments/newanswer :: new-answer";
	}

	@GetMapping("auth/quiz/take/{quizId}")
	public String startQuiz(Model model, Quiz quizOfUser, @PathVariable(value = "quizId") Long id,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Quiz promptQuiz = quizService.getQuiz(id);
		if (promptQuiz == null || !promptQuiz.isStatus()) {
			return "redirect:/error/403-page";
		} else if (!recordService.checkNumberOfTakeQuiz(promptQuiz.getMaxAttempts(), id, request)) {
			redirectAttributes.addFlashAttribute("error", "you have run out of turns!");
			return "redirect:/quiz/view/" + promptQuiz.getId();
		}
		quizOfUser.setStartTime(new Date());
		quizOfUser.setId(id);
		quizOfUser.setMaxAttempts(promptQuiz.getMaxAttempts());
		model.addAttribute("promptQuiz", promptQuiz);
		model.addAttribute("userQuiz", quizOfUser);
		return "web/takequiz";
	}

	@PostMapping("auth/quiz/submit")
	public String calculateScore( @ModelAttribute("userQuiz") Quiz userQuiz,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {

		if (!recordService.checkNumberOfTakeQuiz(userQuiz.getMaxAttempts(), userQuiz.getId(), request)) {
			redirectAttributes.addFlashAttribute("error", "you have run out of turns!");
		}
		Record record = recordService.calculateScore(userQuiz, request);
		redirectAttributes.addFlashAttribute("finalScore", record);
		return "redirect:/quiz/view/"+userQuiz.getId();
	}

	@DeleteMapping("auth/quiz/delete/{id}")
	@ResponseBody

	public boolean deleteQuiz(@PathVariable("id") Long id, HttpServletRequest request) {
		System.out.println(id);
		return quizService.delete(id, request);
	}

	@PostMapping("auth/quiz/edit/file")
	public String importToExcel(@RequestParam("file") MultipartFile file, Quiz quiz, Model model) throws IOException {
		quiz = ImportExcel.parseExcelFile(file.getInputStream());
		List<Subject> subjects = subjectService.findAll();

		model.addAttribute(quiz);
		model.addAttribute("page", "edit");
		model.addAttribute("subjectList", subjects);
		return "web/createquiz";
	}

	@GetMapping("auth/quiz/edit/{id}")
	public String editQuiz(@PathVariable("id") Long id, Quiz quiz, Model model) throws IOException {
		quiz = quizService.getQuiz(id);
		List<Subject> subjects = subjectService.findAll();
		model.addAttribute(quiz);
		model.addAttribute("page", "edit");
		model.addAttribute("subjectList", subjects);
		return "web/createquiz";
	}

	@GetMapping("quiz/view/{quizId}")
	public String showQuiz(Model model, Record record, @PathVariable(value = "quizId") long id) {
		model.addAttribute("quiz", quizService.getQuiz(id));
		return "web/viewquiz";
	}

	@GetMapping("autocomplete")
	@ResponseBody
	public List<Quiz> autocomplete(@RequestParam String keySearch) {
		return quizService.search(keySearch);
	}

	@GetMapping("quiz/search")
	public String search(@RequestParam String keySearch, Model model) {
		model.addAttribute("searchList", quizService.search(keySearch));
		return "web/search";
	}

	@GetMapping("auth/quiz/statistics/{id}")
	public String quizStatistics(Model model, @PathVariable Integer id) {
		Quiz quiz = quizService.getQuiz(id);
		model.addAttribute("quiz", quiz);
		model.addAttribute("statistic", quizService.pareStatisticQuiz(quiz.getRecords()));
		return "web/quizstatistics";
	}
	
	@GetMapping(path = "auth/statistics/export/excel/{id}")
	public void exportToExcel(@PathVariable Integer id, HttpServletResponse response) throws IOException {
		List<Record> listRecords = quizService.getQuiz(id).getRecords();
		RecordExcelExporter exporter = new RecordExcelExporter();
		exporter.export(listRecords, response);
	}

	@GetMapping("auth/statistics/export/csv/{id}")
	public void exportToCSV(@PathVariable Integer id, HttpServletResponse response) throws IOException {
		List<Record> listRecords = quizService.getQuiz(id).getRecords();
		RecordCsvExporter exporter = new RecordCsvExporter();
		exporter.export(listRecords, response);
	}

	@GetMapping("auth/statistics/export/pdf/{id}")
	public void exportToPDF(@PathVariable Integer id, HttpServletResponse response) throws IOException {
		List<Record> listRecords = quizService.getQuiz(id).getRecords();
		RecordPdfExporter exporter = new RecordPdfExporter();
		exporter.export(listRecords, response);
	}

	@GetMapping(value = "auth/quiz/download/file-template")
	public void downloadFile(HttpServletResponse response) throws IOException {
		quizService.dowloadFileTemplate(response);
	}
}
