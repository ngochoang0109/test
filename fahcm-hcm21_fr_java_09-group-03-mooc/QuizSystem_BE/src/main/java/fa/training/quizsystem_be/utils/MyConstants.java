package fa.training.quizsystem_be.utils;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public class MyConstants {
	public static final String FROM_EMAIL_ADRESS = "vkhoi400@gmail.com";
	public static final String SENDER_NAME = "The Quiz Lab team";
	public static final String DOMAIN_WEB = "http://localhost:8080/";
	public static final double MAX_TIME = 1.0;

	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

	public static String randomNumber() {
		return String.valueOf(new Random()
				.nextInt((9 * (int) Math.pow(10, 3)) - 1)
				+ (int) Math.pow(10, 3));
	}
}
