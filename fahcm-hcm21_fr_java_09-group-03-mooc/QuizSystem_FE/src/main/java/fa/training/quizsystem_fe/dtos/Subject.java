package fa.training.quizsystem_fe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    private Long id;
    private String name;
    
    
	@Override
	public String toString() {
		return name;
	}
    
    
}
