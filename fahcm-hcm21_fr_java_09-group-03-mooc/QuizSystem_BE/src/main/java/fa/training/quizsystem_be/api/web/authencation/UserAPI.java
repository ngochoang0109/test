
package fa.training.quizsystem_be.api.web.authencation;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import fa.training.quizsystem_be.dtos.UserDTO;
import fa.training.quizsystem_be.entities.User;
import fa.training.quizsystem_be.exceptions.ResourceNotFoundException;
import fa.training.quizsystem_be.paging.PagingAndSortingHelper;
import fa.training.quizsystem_be.paging.PagingAndSortingParam;
import fa.training.quizsystem_be.payloads.requests.UserPasswordRequest;
import fa.training.quizsystem_be.security.CurrentUser;
import fa.training.quizsystem_be.security.UserPrincipal;
import fa.training.quizsystem_be.services.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/user")
@RequiredArgsConstructor
public class UserAPI {

	private final UserService userService;

	@GetMapping("profile")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		return userService.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}

	@PostMapping("update-image")
	public ResponseEntity<?> create(@RequestBody String urlAvatar, @CurrentUser UserPrincipal userPrincipal)
			throws IOException {
		try {
			return ResponseEntity.ok().body(userService.update(userPrincipal.getId(), urlAvatar));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save", ex);
		}
	}

	/*
	 * /api/v1/user/update-password --------------------- { "id": 2, "password":
	 * "123456" }
	 */
	@PutMapping("update-password")
	public ResponseEntity<?> updatePassword(@CurrentUser UserPrincipal userPrincipal,
			@RequestBody UserPasswordRequest userPasswordRequest) {
		try {
			boolean check = userService.updatePassword(userPrincipal.getId(), userPasswordRequest.getPassword(),
					userPasswordRequest.getNewPassword());
			return ResponseEntity.ok(check);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't update", ex);
		}
	}

	/*
	 * /api/v1/user/update-password --------------------- { "id": 2, "password":
	 * "123456" }
	 */

	@PutMapping("update")
	public ResponseEntity<?> updateUser(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserDTO userDTO) {
		try {
			UserDTO user = userService.update(userPrincipal.getId(), userDTO);
			return ResponseEntity.ok(user);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't update", ex);
		}
	}

	@GetMapping("all")
	public ResponseEntity<?> getAll() throws IOException {
		try {
			return ResponseEntity.ok().body(userService.listAll());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> getAll(@PathVariable(value = "id") Long id) throws IOException {
		try {
			return ResponseEntity.ok().body(userService.delete(id));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save", ex);
		}
	}

	@PutMapping("{id}/enabled/{status}")
	public ResponseEntity<?> updateUserEnabledStatus(@PathVariable("id") Long id,
			@PathVariable("status") boolean enabled) {
		try {
			return ResponseEntity.ok().body(userService.update(id, enabled));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save", ex);
		}
	}

	@GetMapping("page/{pageNum}")
	public ResponseEntity<?> listByPage(
			@PagingAndSortingParam(listName = "listUsers", moduleURL = "/users") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum) {
		try {
			return ResponseEntity.ok().body(userService.listByPage(pageNum, helper));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't get", ex);
		}
	}

	@PostMapping("role")
	public ResponseEntity<?> updateUserRole(@CurrentUser UserPrincipal userPrincipal, @RequestBody UserDTO userDTO) {
		try {
			UserDTO user = userService.updateRole(userPrincipal.getId(), userDTO);
			return ResponseEntity.ok(user);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't save", ex);
		}
	}

}
