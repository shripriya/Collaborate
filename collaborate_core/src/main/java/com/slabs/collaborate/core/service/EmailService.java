package com.slabs.collaborate.core.service;

import java.util.Map;

import com.slabs.collaborate.utilities.RandomGenerator;

public class EmailService implements CollaborateService {

	private static final String VERIFY_EMAIL = "VERIFY_EMAIL";

	public EmailService() {

	}

	@Override
	public Map<String, Object> process(Map<String, String> paramsMap) {

		String emailType = paramsMap.get("emailType");
		if (VERIFY_EMAIL.equals(emailType)) {
			String toUserName = paramsMap.get("userName");
			String toEmail = paramsMap.get("toEmail");
			sendVerifyEmail(toUserName, toEmail);
		}
		return null;
	}

	private void sendVerifyEmail(String toUserName, String toEmail) {

		String randomCode = RandomGenerator.generateRandomText();
		

	}

}
