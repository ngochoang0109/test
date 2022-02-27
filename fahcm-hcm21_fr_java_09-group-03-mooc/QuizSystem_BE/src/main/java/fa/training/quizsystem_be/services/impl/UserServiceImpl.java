package fa.training.quizsystem_be.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fa.training.quizsystem_be.dtos.UserDTO;
import fa.training.quizsystem_be.entities.Subject;
import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.exceptions.ResourceNotFoundException;
import fa.training.quizsystem_be.paging.PagingAndSortingHelper;
import fa.training.quizsystem_be.payloads.reponses.BooleanReponse;
import fa.training.quizsystem_be.repositories.UserRepository;
import fa.training.quizsystem_be.security.UserPrincipal;
import fa.training.quizsystem_be.services.UserService;
import fa.training.quizsystem_be.utils.MyConstants;
import fa.training.quizsystem_be.utils.ObjectMapperUtils;
import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl implements UserService {

	private static final int USERS_PER_PAGE = 4;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private BCryptPasswordEncoder encoder;

	private String emailSubject;

	@Override
	public boolean register(UserDTO userDTO) {

		User user = ObjectMapperUtils.map(userDTO, User.class);
		if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
			String encodedPassword = encoder.encode(user.getPassword());
			user.setPassword(encodedPassword);

			String randomCode = RandomString.make(64);
			user.setVerificationCode(randomCode);

			user.setCreatedTime(new Date());
			user.setUpdateTime(new Date());
			user.setStatus(false);

			userRepository.save(user);

			sendVerificationEmail(user);
			return true;
		}
		return false;
	}

	@Override
	public void sendVerificationEmail(User user) {
		String toAddress = user.getEmail();
		String content = "Dear [[name]],<br>"
				+ "We received a request to register an account with email '[[email]]'.<br>"
				+ "Please click this link to verify your registration:"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "<br>"
				+ "If you did not request a new password, please let us know immediately by replying to this email.<br>"
				+ "<br>" + "Thanks,<br>" + MyConstants.SENDER_NAME;

		String verifyURL = MyConstants.DOMAIN_WEB + "verify?code=" + user.getVerificationCode();
		content = content.replace("[[name]]", user.getName());
		content = content.replace("[[email]]", user.getEmail());
		content = content.replace("[[URL]]", verifyURL);
		emailSubject = "Please Verify Your Registration";
		sendMail(toAddress, content, emailSubject);
	}

	@Override
	public BooleanReponse verify(String verificationCode) {
		Optional<User> userOptional = userRepository.findByVerificationCode(verificationCode);
		BooleanReponse booleanReponse = new BooleanReponse();
		String message = "Verify success !!!";
		boolean result = true;
		if (userOptional.isEmpty()) {
			message = "your link invalid !!!";
			result = false;
		} else {
			User user = userOptional.get();
			if (user.getTime() > MyConstants.MAX_TIME) {
				userRepository.delete(user);
				message = "Your link has expired !!!";
				result = false;
			} else {
				user.setVerificationCode(null);
				user.setStatus(true);
				userRepository.save(user);
			}
		}
		booleanReponse.setMessage(message);
		booleanReponse.setResult(result);
		return booleanReponse;
	}

	public void sendMail(String email, String content, String emailSubject) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom(MyConstants.FROM_EMAIL_ADRESS, MyConstants.SENDER_NAME);
			helper.setTo(email);
			helper.setSubject(emailSubject);
			helper.setText(content, true);

			mailSender.send(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	@Override
	public BooleanReponse resetPassword(UserDTO user) {
		User userOld = userRepository.findByEmail(user.getEmail()).get();
		BooleanReponse booleanReponse = new BooleanReponse();
		String message = "your code invalid!!!";
		boolean result = false;

		if (userOld != null && userOld.getVerificationCode().equals(user.getVerificationCode())) {
			if (userOld.getTime() > MyConstants.MAX_TIME) {
				message = "Your code has expired !!!";
				result = false;
			} else {
				message = "Change password success!!!";
				result = true;
				userOld.setPassword(encoder.encode(user.getPassword()));
				user.setUpdateTime(new Date());
				userOld.setVerificationCode(null);
				userRepository.save(userOld);
			}

		}
		booleanReponse.setMessage(message);
		booleanReponse.setResult(result);
		return booleanReponse;
	}

	@Override
	public void sendCodeForgotPassword(String email) {
		User user = userRepository.findByEmail(email).get();
		if (user != null) {
			String code = MyConstants.randomNumber();

			String content = "Hello [[name]],<br>"
					+ "Someone requested to change the password on our system with email '[[email]]' .<br>"
					+ "No changes have been made to your account yet.<br>"
					+ "This is your code to you can change password: [[code]]<br>"
					+ "If you did not request a new password, please let us know immediately by replying to this email.<br>"
					+ "<br>" + "Thanks,<br>" + MyConstants.SENDER_NAME;

			content = content.replace("[[name]]", user.getName());
			content = content.replace("[[email]]", user.getEmail());
			content = content.replace("[[code]]", code);

			user.setUpdateTime(new Date());
			user.setVerificationCode(code);
			userRepository.save(user);
			emailSubject = "Forgot Your Password?";
			sendMail(email, content, emailSubject);
		}
	}

	@Override
	public UserDTO findByEmail(String userName) {
		User user = userRepository.findByEmail(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User", "userName", userName));

		return ObjectMapperUtils.map(user, UserDTO.class);
	}

	@Override
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		return UserPrincipal.create(user);
	}

	@Override
	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}

	@Override
	public UserDTO save(UserDTO userDTO) {
		User user = ObjectMapperUtils.map(userDTO, User.class);

		return ObjectMapperUtils.map(userRepository.save(user), UserDTO.class);
	}

	@Override
	public boolean updatePassword(Long id, String password, String newPassword) {
		User user = userRepository.findById(id).get();

		boolean matches = encoder.matches(password, user.getPassword());
		if (!matches) {
			return false;
		}

		user.setPassword(encoder.encode(newPassword));
		userRepository.save(user);

		return true;
	}

	@Override
	public UserDTO update(Long id, UserDTO userDTO) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		user.setName(userDTO.getName());
		user.setBirthdate(userDTO.getBirthdate());
		user.setEducationLevel(userDTO.getEducationLevel());

		List<Subject> list = userDTO.getInterests().stream().filter(interest -> null != interest.getId())
				.collect(Collectors.toList());
		user.setInterests(list);

		return ObjectMapperUtils.map(userRepository.save(user), UserDTO.class);
	}

	@Override
	public List<UserDTO> listAll() {
		List<User> userList = new ArrayList<>();
		userRepository.findAll().forEach(userList::add);
		return ObjectMapperUtils.mapAll(userList, UserDTO.class);
	}

	@Override
	public boolean delete(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		userRepository.delete(user);
		return true;
	}

	@Override
	public Page<User> listByPage(int pageNum, PagingAndSortingHelper helper) {
		// TODO Auto-generated method stub
		return (Page<User>) helper.listEntities(pageNum, USERS_PER_PAGE, userRepository);
	}

	@Override
	public UserDTO updateRole(Long id, UserDTO userDTO) {
		User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		if (userDTO.getRole() != "STUDENT" && userDTO.getRole() != "TEACHER") {
			new ResourceNotFoundException("User", "Role", user.getRole());
		}

		if (userDTO.getRole() != null) {
			user.setRole(userDTO.getRole());
		}
		if (userDTO.getEducationLevel() != null) {
			user.setEducationLevel(userDTO.getEducationLevel());
		}

		List<Subject> list = userDTO.getInterests().stream().filter(interest -> null != interest.getId())
				.collect(Collectors.toList());
		user.setInterests(list);

		return ObjectMapperUtils.map(userRepository.save(user), UserDTO.class);
	}

	@Override
	public UserDTO update(Long id, boolean enabled) {
		try {
			User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quizz", "id", id));

			user.setStatus(enabled);

			return ObjectMapperUtils.map(userRepository.save(user), UserDTO.class);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return null;
		}
	}

	@Override
	public UserDTO update(Long id, String urlAvatar) {
		try {
			User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quizz", "id", id));

			user.setAvatar(urlAvatar);

			return ObjectMapperUtils.map(userRepository.save(user), UserDTO.class);
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
			return null;
		}
	}
}
