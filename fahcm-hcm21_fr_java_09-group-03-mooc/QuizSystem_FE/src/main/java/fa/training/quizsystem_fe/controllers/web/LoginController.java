package fa.training.quizsystem_fe.controllers.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.payloads.reponses.AuthenticationResponse;
import fa.training.quizsystem_fe.payloads.requests.AuthenticationRequest;
import fa.training.quizsystem_fe.services.UserService;
import fa.training.quizsystem_fe.utils.HeaderUtils;
import fa.training.quizsystem_fe.utils.SessionUtil;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes("account")
public class LoginController {

	private final UserService userService;

	private HeaderUtils header = new HeaderUtils();

	@GetMapping("login")
	public String loginPage(@ModelAttribute("account") AuthenticationResponse account) {
//		user already logged in -> redirect to home page

		if (account != null) {
			return "redirect:/";
		}
		return "web/login"; // not login yet
	}

	@ModelAttribute("account")
	public AuthenticationResponse addAttributes(@Nullable @RequestParam String email,
			@Nullable @RequestParam String password) {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
		try {
			AuthenticationResponse authenticationResponse = userService.getAuthentication(authenticationRequest);
			System.out.println("Model Attribute: " + authenticationResponse.getJwt());
			return authenticationResponse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@PostMapping("login")
	public String redirectPage(@ModelAttribute("account") AuthenticationResponse account, HttpServletRequest request,
			RedirectAttributes attributes) {
		System.out.println("login: " + account);
		if (account != null) {
			return "redirect:/";
		} else {
			attributes.addFlashAttribute("error", "Login Fail");
			return "redirect:/login";
		}
	}

	@GetMapping("oauth2/redirect")
	public String homePage(@RequestParam("token") String token, HttpServletRequest request) {

		if (token != null) {
			AuthenticationResponse account = new AuthenticationResponse();
			account.setJwt(token);
			SessionUtil.getInstance().putValue(request, "account", account);
			User user = userService.getAuthentication(request);
			account.setUser(user);

			SessionUtil.getInstance().putValue(request, "account", account);
			return "redirect:/";
		} else {
			return "redirect:/login";
		}

	}

}
