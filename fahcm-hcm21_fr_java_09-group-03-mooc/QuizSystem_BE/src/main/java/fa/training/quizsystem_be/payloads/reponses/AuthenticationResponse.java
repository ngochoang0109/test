package fa.training.quizsystem_be.payloads.reponses;

import java.io.Serializable;

import fa.training.quizsystem_be.dtos.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jwt;
	private UserDTO user;
}
