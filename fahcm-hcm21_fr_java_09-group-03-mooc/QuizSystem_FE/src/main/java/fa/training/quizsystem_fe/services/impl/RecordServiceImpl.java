package fa.training.quizsystem_fe.services.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.Record;
import fa.training.quizsystem_fe.payloads.reponses.RecordResponse;
import fa.training.quizsystem_fe.services.RecordService;
import fa.training.quizsystem_fe.utils.HeaderUtils;
import fa.training.quizsystem_fe.utils.MyConstants;

@Service
public class RecordServiceImpl implements RecordService {

	private RestTemplate restTemplate = new RestTemplate();
	private HeaderUtils header = new HeaderUtils();

	@Override
	public Record saveRecord(Record record) {
		String url = MyConstants.URL_DOMAIN + MyConstants.SAVE_RECORD_URI;
		try {
			record = restTemplate.postForEntity(url, record, Record.class).getBody();
			System.out.println(record.toString() + "fhjkdfhksdj");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return record;
	}

	@Override
	public Record calculateScore(Quiz quiz,HttpServletRequest request) {
		String url = MyConstants.URL_DOMAIN + MyConstants.CALCULATE_SCORE_URI;
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, quiz);
		Record record = null;
		try {
			record = restTemplate.exchange(url, HttpMethod.POST, entity, Record.class).getBody();
		} catch (Exception e) {
			return null;
		}
		return record;
	}

	@Override
	public List<RecordResponse> getAllByUser(HttpServletRequest request) {
		String url = MyConstants.URL_DOMAIN + MyConstants.GET_ALL_RECORD_URI;
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		System.out.print(url);
		try {
			ResponseEntity<List<RecordResponse>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<RecordResponse>>() {
					});
			List<RecordResponse> records = response.getBody();

			System.out.println(records);
			return records;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}
	@Override
	public boolean checkNumberOfTakeQuiz(int maxNumber,Long quizId, HttpServletRequest request) {
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);

		String requestUrl = MyConstants.URL_DOMAIN +MyConstants.URI_GET_NUMBER_OF_TAKE_QUIZ+"/"+quizId;
		try {
			long numberOfTakeQuiz = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, Long.class).getBody();
			if (numberOfTakeQuiz<maxNumber) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
