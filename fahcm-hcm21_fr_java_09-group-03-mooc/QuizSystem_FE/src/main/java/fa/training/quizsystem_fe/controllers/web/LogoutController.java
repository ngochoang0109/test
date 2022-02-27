package fa.training.quizsystem_fe.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("logout")
public class LogoutController {
	@GetMapping()
	public String handelLogout(WebRequest request, SessionStatus status,RedirectAttributes attributes) {
		status.setComplete();
		request.removeAttribute("account", WebRequest.SCOPE_SESSION);
		System.out.println("User Logout: ");
		attributes.addFlashAttribute("success", "Logout success");
		return "redirect:/login";
	}
}
