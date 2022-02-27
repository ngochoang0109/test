package fa.training.quizsystem_fe.utils;


import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import fa.training.quizsystem_fe.payloads.reponses.AuthenticationResponse;

public class HeaderUtils {

	public <T> HttpEntity<?> getHeader(HttpServletRequest request, T typeBody) {
		AuthenticationResponse loginResponse = (AuthenticationResponse) SessionUtil.getInstance().getValue(request, "account");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON,MediaType.APPLICATION_OCTET_STREAM, MediaType.ALL));
		
		headers.set("Authorization","Bearer "+ loginResponse.getJwt());
		HttpEntity<T> entity = new HttpEntity<T>(typeBody, headers);
		return entity;
	}

	public HttpEntity<MultiValueMap<String, Object>> getHeader(HttpServletRequest request, MultiValueMap<String, Object>  typeBody,Boolean check) {
		AuthenticationResponse loginResponse = (AuthenticationResponse) SessionUtil.getInstance().getValue(request, "account");
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);	
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("Authorization","Bearer "+ loginResponse.getJwt());
		
	    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(typeBody, headers);

		return requestEntity;
	}
}
