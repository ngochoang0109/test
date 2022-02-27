package fa.training.quizsystem_be.payloads.reponses;

import java.util.Date;

import lombok.Data;

@Data
public class RecordResponse {
    private Long id;

    private QuizResponse quiz;
    
    private Date startTime;

    private Date submitTime;
    
    private Double score;

}