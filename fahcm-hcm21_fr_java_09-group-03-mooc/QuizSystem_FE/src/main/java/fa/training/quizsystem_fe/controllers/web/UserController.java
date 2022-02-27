package fa.training.quizsystem_fe.controllers.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.Subject;
import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.export_file.users.UserCsvExporter;
import fa.training.quizsystem_fe.export_file.users.UserExcelExporter;
import fa.training.quizsystem_fe.export_file.users.UserPdfExporter;
import fa.training.quizsystem_fe.payloads.reponses.AuthenticationResponse;
import fa.training.quizsystem_fe.payloads.reponses.RecordResponse;
import fa.training.quizsystem_fe.payloads.requests.UserPasswordRequest;
import fa.training.quizsystem_fe.services.QuizService;
import fa.training.quizsystem_fe.services.RecordService;
import fa.training.quizsystem_fe.services.SubjectService;
import fa.training.quizsystem_fe.services.UserService;
import fa.training.quizsystem_fe.utils.HeaderUtils;
import fa.training.quizsystem_fe.utils.SessionUtil;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth/user")
public class UserController {

	private HeaderUtils header = new HeaderUtils();

	private final UserService userService;

	private final SubjectService subjectService;

	private final QuizService quizService;

	private final Cloudinary cloudinary;

	private final RecordService recordService;

	@GetMapping("myprofile")
	public String getMyProfile(Model model, HttpServletRequest request) {

		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);

		User user = userService.getAuthentication(request);
		List<Quiz> listQuiz = quizService.getAllByUser(request);
		List<RecordResponse> listRecord = recordService.getAllByUser(request);

		System.out.println(listRecord);
		model.addAttribute("user", user);
		model.addAttribute("listQuiz", listQuiz);
		model.addAttribute("listRecord", listRecord);
		return "web/profile";
	}

	@GetMapping("editprofile")
	public String getEditProfile(Model model, HttpServletRequest request) {
		AuthenticationResponse account = (AuthenticationResponse) SessionUtil.getInstance().getValue(request,
				"account");
		account.setUser(userService.getAuthentication(request));
		SessionUtil.getInstance().putValue(request, "account", account);
		List<Subject> subjects = subjectService.findAll();

		model.addAttribute("user", account.getUser());
		model.addAttribute("userpass", new UserPasswordRequest());

		model.addAttribute("subjectList", subjects);

		return "web/editprofile";
	}

	@PostMapping("update-image")

	public String updatarUserAvatar(@RequestParam("file") MultipartFile file, HttpServletRequest request,RedirectAttributes redirectAttributes) throws IOException {
		try {
			Map uploadResult = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type",
					"auto", "public_id", "user/images/" + file.getOriginalFilename()));
			String urlAvatar = uploadResult.get("secure_url").toString();
			System.out.println(urlAvatar);
			HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, urlAvatar);

			User user = userService.updateImage(request,urlAvatar);
			String message = "User update Image successful";
			redirectAttributes.addFlashAttribute("success", message);
			
			return "redirect:/auth/user/editprofile";
		} catch (HttpStatusCodeException e) {
			System.out.println(e.getMessage());
			return "redirect:/error";
		}
	}

	@PostMapping("update-password")
	public String doUpdatePassword(@ModelAttribute("userpass") UserPasswordRequest userPasswordRequest,
			HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {

			boolean statusUpdate = userService.updatePass(request,userPasswordRequest);
			String message = "User update password successful";
			redirectAttributes.addFlashAttribute("success", message);

			return "redirect:/auth/user/myprofile";
		} catch (HttpStatusCodeException e) {
			System.out.println(e.getMessage());
			return "redirect:/error";
		}
	}

	@PostMapping("update")
	public String doUpdateUser(@ModelAttribute("user") User user, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			boolean check = userService.updateUser(request,user);
			String message = "User update successful";
			redirectAttributes.addFlashAttribute("success", message);

			return "redirect:/auth/user/myprofile";
		} catch (HttpStatusCodeException e) {
			System.out.println(e.getMessage());
			return "redirect:/error";
		}
	}

	@PostMapping("role")
	public String doUpdateUserRole(@ModelAttribute("user") User user, HttpServletRequest request) {
		try {
			if (user.getRole()==null) {
				user.setRole("TEACHER");
			}
			boolean check = userService.updateUserRole(request, user);
			AuthenticationResponse account = (AuthenticationResponse) SessionUtil.getInstance().getValue(request,
					"account");
			account.setUser(userService.getAuthentication(request));
			SessionUtil.getInstance().putValue(request, "account", account);

			return "redirect:/";
		} catch (HttpStatusCodeException e) {
			System.out.println(e.getMessage());
			return "redirect:/error";
		}
	}
}
