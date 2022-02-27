package fa.training.quizsystem_fe.dtos;

import lombok.Data;

@Data
public class Answer {

    private Long id;
    private String text;
    private boolean correct;
}
