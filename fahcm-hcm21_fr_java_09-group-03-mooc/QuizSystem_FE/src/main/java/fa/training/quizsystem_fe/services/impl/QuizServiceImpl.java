package fa.training.quizsystem_fe.services.impl;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import fa.training.quizsystem_fe.dtos.Question;
import fa.training.quizsystem_fe.dtos.Quiz;
import fa.training.quizsystem_fe.dtos.Record;
import fa.training.quizsystem_fe.dtos.User;
import fa.training.quizsystem_fe.paging.RestResponsePage;
import fa.training.quizsystem_fe.services.QuizService;
import fa.training.quizsystem_fe.utils.HeaderUtils;
import fa.training.quizsystem_fe.utils.MyConstants;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
	
	private RestTemplate restTemplate = new RestTemplate();

	private final ServletContext context;

	private final Cloudinary cloudinary;

	private HeaderUtils header = new HeaderUtils();

	@Override
	public List<Quiz> getAllByUser(HttpServletRequest request) {
		String url = MyConstants.URL_DOMAIN + MyConstants.GET_ALL_QUIZ_BY_USER_URI;
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		try {
			ResponseEntity<List<Quiz>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<Quiz>>() {
					});
			List<Quiz> quizz = response.getBody();

			System.out.print(quizz);
			return quizz;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public boolean delete(Long id, HttpServletRequest request) {
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		String url = MyConstants.URL_DOMAIN + MyConstants.DELTE_QUIZ_URI + id;
		System.out.println(url);
		try {
			return restTemplate.exchange(url, HttpMethod.DELETE, entity, Boolean.class).getBody();
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public Long createQuiz(HttpServletRequest request, Quiz quiz) {

		Long result;
		String url = MyConstants.URL_DOMAIN + MyConstants.CREATE_QUIZ_URI;

		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, quiz);
		try {
			result = restTemplate.exchange(url, HttpMethod.POST, entity, Long.class).getBody();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return result;
	}

	@Override
	public Quiz getQuiz(long id) {
		String url = MyConstants.URL_DOMAIN + MyConstants.GET_QUIZ_URI + id;
		Quiz quiz = null;
		try {
			quiz = restTemplate.getForObject(url, Quiz.class);
		} catch (Exception e) {
			return null;
		}
		return quiz;
	}

	@Override
	public Quiz addImg(Quiz quiz) throws IOException {
		if (!quiz.getFileImg().isEmpty()) {
			Map uploadResult = this.cloudinary.uploader().upload(quiz.getFileImg().getBytes(), ObjectUtils.asMap(
					"resource_type", "auto", "public_id", "user/images/" + quiz.getFileImg().getOriginalFilename()));
			quiz.setImage(uploadResult.get("secure_url").toString());
			quiz.setFileImg(null);
		}
		for (Question question : quiz.getQuestions()) {
			if (!question.getFileImg().isEmpty()) {
				Map uploadImgQuestion = this.cloudinary.uploader().upload(question.getFileImg().getBytes(),
						ObjectUtils.asMap("resource_type", "auto", "public_id",
								"quiz/images/" + question.getFileImg().getOriginalFilename()));
				question.setImage(uploadImgQuestion.get("secure_url").toString());
			}
		}
		return quiz;
	}

	@Override
	public List<Quiz> search(String key) {
		String url = MyConstants.URL_DOMAIN + MyConstants.SEARCH_QUIZ + "/" + key;
		try {
			List quizs = restTemplate.getForEntity(url, List.class).getBody();
			return quizs;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public List<Quiz> getPopularQuiz() {
		String url = MyConstants.URL_DOMAIN + MyConstants.POPULAR_QUIZ;
		try {
			List quizz = restTemplate.getForEntity(url, List.class).getBody();
			return quizz;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			return null;
		}
	}

	@Override
	public List<Quiz> getRecommendation(HttpServletRequest request) {
		String url = MyConstants.URL_DOMAIN + MyConstants.RECOMMENDATION_QUIZ;
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		try {
			List quizz = restTemplate.exchange(url, HttpMethod.GET, entity, List.class).getBody();
			return quizz;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}

	public List<Integer> pareStatisticQuiz(List<Record> list) {
		List<Integer> listResult = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			listResult.add(countNumberUserOfStatisticQuiz(list, i, i + 1));
		}
		return listResult;
	}

	public int countNumberUserOfStatisticQuiz(List<Record> list, double start, double end) {
		int count = 0;
		for (Record record : list) {
			if(record.getScore()==10&&end==10) {
				count++;
			}else if (record.getScore() >= start && record.getScore() < end) {
				count++;
			}
			
		}
		return count;
	}

	@Override
	public Page<Quiz> listByPage(int pageNum, String sortField, String sortDir, String keyword,
			HttpServletRequest request) {
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);

		if (keyword == null) {
			keyword = "";
		}
//		/users/page/1?sortField=email&sortDir=asc
		String url = MyConstants.URL_DOMAIN + MyConstants.LIST_QUIZ + pageNum + "?sortField=" + sortField + "&sortDir="
				+ sortDir + "&keyword=" + keyword;

		System.out.print(url);
		try {

			final ParameterizedTypeReference<RestResponsePage<Quiz>> responseType = new ParameterizedTypeReference<RestResponsePage<Quiz>>() {
			};
			final ResponseEntity<RestResponsePage<Quiz>> result = restTemplate.exchange(url, HttpMethod.GET, entity,
					responseType);

			Page<Quiz> quizes = result.getBody();

			System.out.print(quizes.getContent());
			return quizes;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}

	@Override
	public boolean updateQuizEnabledStatus(Integer id, boolean status, HttpServletRequest request) {
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);

		String requestUrl = MyConstants.URL_DOMAIN + "quiz/" + id + "/enabled/" + (status == true ? "true" : "false");
		try {
			User user = restTemplate.exchange(requestUrl, HttpMethod.PUT, entity, User.class).getBody();
			if (user != null) {
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Quiz> listAll(HttpServletRequest request) {
		String url = MyConstants.URL_DOMAIN + MyConstants.GET_ALL_QUIZ_URI;
		HttpEntity<String> entity = (HttpEntity<String>) header.getHeader(request, null);
		try {
			ResponseEntity<List<Quiz>> response = restTemplate.exchange(url, HttpMethod.GET, entity,
					new ParameterizedTypeReference<List<Quiz>>() {
					});
			List<Quiz> quizz = response.getBody();

			System.out.print(quizz);
			return quizz;
		} catch (Exception e) {
			System.out.print(e.getLocalizedMessage());
			// TODO Auto-generated method stub
			return null;
		}
	}

	public void dowloadFileTemplate(HttpServletResponse response) {
		try {
			File file = new File(context.getRealPath("/static/fileTemplate/sample-quiz-1.xlsx"));
			byte[] data = FileUtils.readFileToByteArray(file);
			//
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
			response.setContentLength(data.length);
			InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}


}
