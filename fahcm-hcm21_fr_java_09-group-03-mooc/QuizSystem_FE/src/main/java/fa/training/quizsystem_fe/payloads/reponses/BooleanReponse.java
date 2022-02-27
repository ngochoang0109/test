package fa.training.quizsystem_fe.payloads.reponses;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BooleanReponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean result;
	private String message;

}
