package fa.training.quizsystem_fe.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.payloads.reponses.BooleanReponse;
import fa.training.quizsystem_fe.services.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ForgotPassController {

	private final UserService userService;

	@GetMapping("forgot-password")
	public String goForgotPage(@ModelAttribute User user, Model model) {
		model.addAttribute(user);
		return "web/forgot";
	}

	@PostMapping("forgot-password")
	public String forgotPassword(@ModelAttribute User user, Model model, RedirectAttributes redirectAttributes) {

		BooleanReponse booleanReponse = userService.forgotPassword(user);

		if (booleanReponse.isResult()) {
			redirectAttributes.addFlashAttribute("success", booleanReponse.getMessage());
			return "redirect:/login";
		}
		model.addAttribute("error", booleanReponse.getMessage());
		return "web/forgot";
	}

	@GetMapping("send-code")
	@ResponseBody
	public void sendCode(@RequestParam String email) {
		userService.sendCode(email);
	}
}
