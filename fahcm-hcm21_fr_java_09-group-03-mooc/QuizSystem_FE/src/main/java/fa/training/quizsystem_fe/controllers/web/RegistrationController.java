package fa.training.quizsystem_fe.controllers.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.payloads.reponses.BooleanReponse;
import fa.training.quizsystem_fe.services.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

	private final UserService userService;

	@GetMapping("signup")
	public String signUpPage(User user, Model model) {
		model.addAttribute("user", user);
		return "web/signup";
	}

	@PostMapping("signup")
	public String signUpNewUser(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes,
			HttpServletRequest request) {
		if (!userService.register(user, request)) {
			model.addAttribute("success", "Register failed!");
			return "web/signup";
		}
		redirectAttributes.addFlashAttribute("error", "Please check your email for verification!!");
		return "redirect:/login";
	}

	@GetMapping("verify")
	public String signUpPage(@RequestParam String code, RedirectAttributes redirectAttributes) {
		BooleanReponse booleanReponse = userService.verify(code);
		if (booleanReponse.isResult()) {
			redirectAttributes.addFlashAttribute("success", booleanReponse.getMessage());
		} else {
			redirectAttributes.addFlashAttribute("error", booleanReponse.getMessage());
		}
		return "redirect:/login";
	}

}
