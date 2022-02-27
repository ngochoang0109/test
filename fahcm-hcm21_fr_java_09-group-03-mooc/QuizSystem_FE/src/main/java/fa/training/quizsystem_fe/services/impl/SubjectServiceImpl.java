package fa.training.quizsystem_fe.services.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fa.training.quizsystem_fe.dtos.Subject;
import fa.training.quizsystem_fe.services.SubjectService;
import fa.training.quizsystem_fe.utils.MyConstants;

@Service
public class SubjectServiceImpl implements SubjectService {
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public List<Subject> findAll() {
		String url = MyConstants.URL_DOMAIN + MyConstants.GET_ALL_SUBJECT_URI;
		System.out.print(url);
		try {
			ResponseEntity<List<Subject>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Subject>>() {
					});
			List<Subject> subjects = response.getBody();

			System.out.print(subjects);
			return subjects;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}

}
