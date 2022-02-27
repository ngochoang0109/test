package fa.training.quizsystem_be.services;

import fa.training.quizsystem_be.dtos.UserDTO;
import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.paging.PagingAndSortingHelper;
import fa.training.quizsystem_be.payloads.reponses.BooleanReponse;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

	UserDTO findByEmail(String userName);

	Optional<User> findById(Long id);

	UserDetails loadUserById(Long id);

	boolean register(UserDTO user);

	void sendVerificationEmail(User user);

	BooleanReponse verify(String verificationCode);

	void sendCodeForgotPassword(String email);

	void sendMail(String email, String content, String emailSucject);

	BooleanReponse resetPassword(UserDTO user);

	UserDTO save(UserDTO user);

	boolean updatePassword(Long id, String password, String newPassword);

	UserDTO update(Long id, UserDTO userDTO);

	UserDTO update(Long id, boolean enabled);
	
	UserDTO update(Long id, String urlAvatar);
	
	UserDTO updateRole(Long id, UserDTO userDTO);
	
	List<UserDTO> listAll();

	boolean delete(Long id);

	Page<User> listByPage(int pageNum, PagingAndSortingHelper helper);
}
