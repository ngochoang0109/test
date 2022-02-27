package fa.training.quizsystem_be.dtos;

import lombok.Data;

@Data
public class AnswerDTO {

    private Long id;
    private String text;
    private boolean correct;
}
