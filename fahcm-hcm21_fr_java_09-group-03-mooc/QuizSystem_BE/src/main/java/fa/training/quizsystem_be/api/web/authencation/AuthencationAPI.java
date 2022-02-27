package fa.training.quizsystem_be.api.web.authencation;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fa.training.quizsystem_be.dtos.UserDTO;
import fa.training.quizsystem_be.payloads.reponses.AuthenticationResponse;
import fa.training.quizsystem_be.payloads.reponses.BooleanReponse;
import fa.training.quizsystem_be.payloads.requests.AuthenticationRequest;
import fa.training.quizsystem_be.security.TokenProvider;
import fa.training.quizsystem_be.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthencationAPI {

	private final UserService userService;

	private final AuthenticationManager authenticationManager;

	private final TokenProvider tokenProvider;

	/*
	 * /api/v1/auth/login --------------------- { "username": "tangyucheng",
	 * "password": "123456" }
	 */
	@PostMapping("login")
	public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.createToken(authentication);

		UserDTO user = userService.findByEmail(authentication.getName());
		try {

			return ResponseEntity.ok(new AuthenticationResponse(jwt, user));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't login", ex);
		}
	}

	@PostMapping("register")
	public boolean register(@RequestBody UserDTO user) {
		try {
			return userService.register(user);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't register", ex);
		}
	}

	@GetMapping("verify")
	public BooleanReponse verifyUser(@Param("code") String code) {
		try {
			return userService.verify(code);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't verify", ex);
		}
	}

	@GetMapping("send-code")
	public void sendCode(@Param("email") String email) {
		try {
			userService.sendCodeForgotPassword(email);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't send code", ex);
		}
	}

	@PostMapping("forgot-password")
	public BooleanReponse resetPassword(@RequestBody UserDTO user) {
		try {
			return userService.resetPassword(user);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't reset password", ex);
		}
	}
}
