package fa.training.quizsystem_fe.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("error")
public class ErrorController {

	 @GetMapping("404-page")
	    public String go404Page() {
	        return "common/404";
	    }
	 @GetMapping("403-page")
	 public String go403Page() {
		 return "common/403";
	 }
}
