package fa.training.quizsystem_fe.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.paging.RestResponsePage;
import fa.training.quizsystem_fe.payloads.reponses.AuthenticationResponse;
import fa.training.quizsystem_fe.payloads.reponses.BooleanReponse;
import fa.training.quizsystem_fe.payloads.requests.AuthenticationRequest;
import fa.training.quizsystem_fe.payloads.requests.UserPasswordRequest;
import fa.training.quizsystem_fe.services.UserService;
import fa.training.quizsystem_fe.utils.HeaderUtils;
import fa.training.quizsystem_fe.utils.MyConstants;

@Service
public class UserServiceImpl implements UserService {

	private RestTemplate restTemplate = new RestTemplate();
	private HeaderUtils header = new HeaderUtils();

	@Override
	public boolean register(User user, HttpServletRequest request) {
		boolean result;
		String url = MyConstants.URL_DOMAIN + MyConstants.REGISTER_URI;
		try {
			user.setAvatar(request.getContextPath() + "/static/img/user-default-avatar.png");
			result = restTemplate.postForEntity(url, user, Boolean.class).getBody();
		} catch (Exception e) {
			return false;
		}
		return result;
	}

	public void sendCode(String email) {
		String url = MyConstants.URL_DOMAIN + MyConstants.SEND_CODE_URI + "?email=" + email;
		try {
			restTemplate.getForEntity(url, Void.class).getBody();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public BooleanReponse forgotPassword(User user) {

		BooleanReponse booleanReponse = new BooleanReponse(false, "sorry you we have error!!!");
		String url = MyConstants.URL_DOMAIN + MyConstants.FORGOT_PASS_URI;
		try {
			booleanReponse = restTemplate.postForEntity(url, user, BooleanReponse.class).getBody();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return booleanReponse;
	}

	@Override
	public BooleanReponse verify(String code) {

		BooleanReponse booleanReponse = new BooleanReponse(false, "sorry you we have error!!!");
		String url = MyConstants.URL_DOMAIN + MyConstants.VERIFY_URI + "?code=" + code;
		try {
			booleanReponse = restTemplate.getForEntity(url, BooleanReponse.class).getBody();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return booleanReponse;
	}

	@Override
	public AuthenticationResponse getAuthentication(AuthenticationRequest authenticationRequest) {
		String requestUrl = MyConstants.URL_DOMAIN + MyConstants.AUTHENTICATION;

		return restTemplate.postForEntity(requestUrl, authenticationRequest, AuthenticationResponse.class).getBody();
	}

	public User getAuthentication(HttpServletRequest request) {

		String requestUrl = MyConstants.URL_DOMAIN + MyConstants.USER;
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		return restTemplate.exchange(requestUrl, HttpMethod.GET, entity, User.class).getBody();
	}

	@Override
	public User updateImage(HttpServletRequest request, String urlAvatar) {
		String requestUrl = MyConstants.URL_DOMAIN + MyConstants.UPDATE_IMAGE;
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, urlAvatar);
		return restTemplate.exchange(requestUrl, HttpMethod.POST, entity, User.class).getBody();
	}

	@Override
	public boolean updatePass(HttpServletRequest request, UserPasswordRequest userPasswordRequest) {

		String requestUrl = MyConstants.URL_DOMAIN + MyConstants.UPDATE_PASS;
		HttpEntity<UserPasswordRequest> httpEntity = (HttpEntity<UserPasswordRequest>) header.getHeader(request,
				userPasswordRequest);
		return restTemplate.exchange(requestUrl, HttpMethod.PUT, httpEntity, Boolean.class).getBody();
	}

	@Override
	public boolean updateUser(HttpServletRequest request, User user) {

		String requestUrl = MyConstants.URL_DOMAIN + MyConstants.UPDATE_USER;
		HttpEntity<User> httpEntity = (HttpEntity<User>) header.getHeader(request, user);
		System.out.println(requestUrl);
		try {
			User userRepone = restTemplate.exchange(requestUrl, HttpMethod.PUT, httpEntity, User.class).getBody();
			if (userRepone != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<User> listAll(HttpServletRequest request) {
		String url = MyConstants.URL_DOMAIN + MyConstants.GET_ALL_USER_URI;
		HttpEntity<String> httpEntity = (HttpEntity<String>) header.getHeader(request, null);
		try {
			ResponseEntity<List<User>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<User>>() {
					});
			List<User> users = response.getBody();

			System.out.print(users);
			return users;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public boolean delete(Integer id, HttpServletRequest request) {
		String requestUrl = MyConstants.URL_DOMAIN + MyConstants.DELETE_USER + id;
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		try {
			return restTemplate.exchange(requestUrl, HttpMethod.DELETE, entity, Boolean.class).getBody();

		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateUserEnabledStatus(Integer id, boolean status, HttpServletRequest request) {

		String requestUrl = MyConstants.URL_DOMAIN + "user/" + id + "/enabled/" + (status == true ? "true" : "false");
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		try {
			User user = restTemplate.exchange(requestUrl, HttpMethod.PUT, entity, User.class).getBody();
			if (user != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword,
			HttpServletRequest request) {
		if (keyword == null) {
			keyword = "";
		}
//		/users/page/1?sortField=email&sortDir=asc
		String url = MyConstants.URL_DOMAIN + MyConstants.LIST_USER + pageNum + "?sortField=" + sortField + "&sortDir="
				+ sortDir + "&keyword=" + keyword;

		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		try {

			final ParameterizedTypeReference<RestResponsePage<User>> responseType = new ParameterizedTypeReference<RestResponsePage<User>>() {
			};
			final ResponseEntity<RestResponsePage<User>> result = restTemplate.exchange(url, HttpMethod.GET, entity,
					responseType);

			Page<User> users = result.getBody();

			System.out.print(users.getContent());
			return users;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public boolean updateUserRole(HttpServletRequest request, User user) {
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, user);

		String url = MyConstants.URL_DOMAIN + MyConstants.UPDATE_USER_ROLE;

		System.out.println(url);
		try {
			User userNew = restTemplate.exchange(url, HttpMethod.POST, entity, User.class).getBody();
			if (user != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
