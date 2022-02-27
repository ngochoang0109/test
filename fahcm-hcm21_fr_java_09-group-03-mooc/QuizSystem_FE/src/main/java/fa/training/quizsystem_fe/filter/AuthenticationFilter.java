package fa.training.quizsystem_fe.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.payloads.reponses.AuthenticationResponse;
import fa.training.quizsystem_fe.utils.SessionUtil;

@WebFilter(urlPatterns = { "/auth/*", "/admin/*"})
public class AuthenticationFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		AuthenticationResponse authenticationResponse = (AuthenticationResponse) SessionUtil.getInstance()
				.getValue(request, "account");
		String urlLogin = request.getContextPath() + "/login";
		String url403 = request.getContextPath() + "/error/403-page";
		String urlCurrent = request.getRequestURI();

		if (authenticationResponse == null) {
			response.sendRedirect(urlLogin);

		} else 	if (authenticationResponse.getUser().getRole() == null) {
			if(urlCurrent.contains("/auth/user/role")) {
				chain.doFilter(req, resp);
			}else {
				response.sendRedirect(url403);
			}
		} else if (authenticationResponse.getUser().getRole().equals("STUDENT")) {
			if(urlCurrent.contains("/create-quiz")||urlCurrent.contains("/edit-quiz")||urlCurrent.contains("/admin")) {
				response.sendRedirect(url403);
			}else {
				chain.doFilter(req, resp);
			}

		} else if (authenticationResponse.getUser().getRole().equals("TEACHER")) {
			if(urlCurrent.contains("/take-quiz")||urlCurrent.contains("/submit-quiz")||urlCurrent.contains("/admin")) {
				response.sendRedirect(url403);
			}else {
				chain.doFilter(req, resp);
			}

		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
}