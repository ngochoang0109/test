package fa.training.quizsystem_fe.controllers.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fa.training.quizsystem_fe.dtos.Subject;
import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.payloads.reponses.AuthenticationResponse;
import fa.training.quizsystem_fe.services.QuizService;
import fa.training.quizsystem_fe.services.SubjectService;
import fa.training.quizsystem_fe.utils.SessionUtil;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final QuizService quizService;

	private final SubjectService subjectService;

	@GetMapping("/")
	public String homePage(HttpServletRequest request, Model model) {
		List<Subject> subjectList = subjectService.findAll();
		model.addAttribute("popularList", quizService.getPopularQuiz());
		model.addAttribute("user", new User());
		model.addAttribute("subjectList", subjectList);
		AuthenticationResponse loginResponse = (AuthenticationResponse) SessionUtil.getInstance().getValue(request,
				"account");
		if (loginResponse != null && loginResponse.getUser().getRole() != null
				&& loginResponse.getUser().getRole().equals("STUDENT")) {
			model.addAttribute("recommendationList", quizService.getRecommendation(request));
		}

		return "web/home";
	}

}
