package fa.training.quizsystem_fe.controllers.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.export_file.quizzes.QuizCsvExporter;
import fa.training.quizsystem_fe.export_file.quizzes.QuizExcelExporter;
import fa.training.quizsystem_fe.export_file.quizzes.QuizPdfExporter;
import fa.training.quizsystem_fe.export_file.users.UserCsvExporter;
import fa.training.quizsystem_fe.export_file.users.UserExcelExporter;
import fa.training.quizsystem_fe.export_file.users.UserPdfExporter;
import fa.training.quizsystem_fe.services.QuizService;
import fa.training.quizsystem_fe.services.UserService;
import fa.training.quizsystem_fe.utils.HeaderUtils;
import fa.training.quizsystem_fe.utils.MyConstants;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/quizzes")
@RequiredArgsConstructor
public class ManagementQuizController {

	private final QuizService quizService;

	@GetMapping
	public String listFirstPage(Model model,@RequestParam(name = "mess",required = false)String mess ,HttpServletRequest request) {
		System.out.println(mess);
		if ("success".equals(mess)) {
			String message = "The Quiz has been deleted successfully";
			model.addAttribute("success", message);
		} 
		return listByPage(1, model, "title", "asc", "", request);
	}

	@GetMapping("page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword,
			HttpServletRequest request) {
		
		Page<Quiz> page = quizService.listByPage(pageNum, sortField, sortDir, keyword, request);
		List<Quiz> listQuizzes = page.getContent();

		long startCount = (pageNum - 1) * MyConstants.PER_PAGE + 1;
		long endCount = startCount + MyConstants.PER_PAGE - 1;
		if (endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}

		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listQuizzes", listQuizzes);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		System.out.println("totalPages" + page.getTotalPages());
		return "admin/quizzes";
	}

	@DeleteMapping("delete/{id}")
	public @ResponseBody String deleteUser(@PathVariable(name = "id") Long id, HttpServletRequest request) {
		System.out.println(id);
		quizService.delete(id, request);
		return id.toString();
	}

	@GetMapping("{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		quizService.updateQuizEnabledStatus(id, enabled, request);
		String status = enabled ? "enabled" : "disabled";
		String message = "The Quiz ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("success", message);
		return "redirect:/admin/quizzes";
	}
	
	@GetMapping(path = "export/excel")
	public void exportToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Quiz> listQuizzes = quizService.listAll(request);
		QuizExcelExporter exporter = new QuizExcelExporter();
		exporter.export(listQuizzes, response);
	}

	@GetMapping("export/csv")
	public void exportToCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Quiz> listQuizzes = quizService.listAll(request);
		QuizCsvExporter exporter = new QuizCsvExporter();
		exporter.export(listQuizzes, response);
	}

	@GetMapping("export/pdf")
	public void exportToPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Quiz> listQuizzes = quizService.listAll(request);
		QuizPdfExporter exporter = new QuizPdfExporter();
		exporter.export(listQuizzes, response);
	}
}