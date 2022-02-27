package fa.training.quizsystem_fe.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.payloads.reponses.AuthenticationResponse;
import fa.training.quizsystem_fe.payloads.reponses.BooleanReponse;
import fa.training.quizsystem_fe.payloads.requests.AuthenticationRequest;
import fa.training.quizsystem_fe.payloads.requests.UserPasswordRequest;

public interface UserService {
	boolean register(User user, HttpServletRequest request);

	void sendCode(String email);

	BooleanReponse forgotPassword(User user);

	BooleanReponse verify(String code);

	AuthenticationResponse getAuthentication(AuthenticationRequest authenticationRequest);

	public User getAuthentication(HttpServletRequest request);

	User updateImage(HttpServletRequest request, String urlAvatar);

	boolean updatePass(HttpServletRequest request, UserPasswordRequest userPasswordRequest);

	boolean updateUser(HttpServletRequest request, User user);

	List<User> listAll(HttpServletRequest request);

	boolean delete(Integer id, HttpServletRequest request);

	boolean updateUserEnabledStatus(Integer id, boolean enabled, HttpServletRequest request);

	boolean updateUserRole(HttpServletRequest request, User user);

	Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword, HttpServletRequest request);
}
