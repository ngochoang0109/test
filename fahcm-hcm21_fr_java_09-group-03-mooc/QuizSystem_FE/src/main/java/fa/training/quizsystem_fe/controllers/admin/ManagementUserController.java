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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.export_file.users.UserCsvExporter;
import fa.training.quizsystem_fe.export_file.users.UserExcelExporter;
import fa.training.quizsystem_fe.export_file.users.UserPdfExporter;
import fa.training.quizsystem_fe.services.UserService;
import fa.training.quizsystem_fe.utils.HeaderUtils;
import fa.training.quizsystem_fe.utils.MyConstants;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("admin/users")
@RequiredArgsConstructor
public class ManagementUserController {

	private HeaderUtils header = new HeaderUtils();

	private final UserService userService;

	@GetMapping
	public String listFirstPage(Model model,@RequestParam(name = "mess",required = false)String mess ,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		System.out.println(mess);
		if ("success".equals(mess)) {
			String message = "The user has been deleted successfully";
			model.addAttribute("success", message);
		} 
		return listByPage(1, model, "name", "asc", "", request);
	}

	@GetMapping("page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword,
			HttpServletRequest request) {
		Page<User> page = userService.listByPage(pageNum, sortField, sortDir, keyword, request);
		List<User> listUsers = page.getContent();

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
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		System.out.println("totalPages" + page.getTotalPages());
		return "admin/users";
	}

	@DeleteMapping("delete/{id}")
	@ResponseBody
	public void deleteUser(@PathVariable(name = "id") Integer id, HttpServletRequest request) {
		System.out.println(id);
		userService.delete(id, request);
	}

	@GetMapping("{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		userService.updateUserEnabledStatus(id, enabled, request);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("success", message);
		return "redirect:/admin/users";
	}

	@GetMapping(path = "export/excel")
	public void exportToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll(request);
		UserExcelExporter exporter = new UserExcelExporter();
		exporter.export(listUsers, response);
	}

	@GetMapping("export/csv")
	public void exportToCSV(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll(request);
		UserCsvExporter exporter = new UserCsvExporter();

		exporter.export(listUsers, response);
	}

	@GetMapping("export/pdf")
	public void exportToPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<User> listUsers = userService.listAll(request);
		UserPdfExporter exporter = new UserPdfExporter();
		exporter.export(listUsers, response);
	}

}