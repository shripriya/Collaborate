package com.slabs.collaborate.core.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.Constants;
import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.core.db.SQLMapper;
import com.slabs.collaborate.utilities.CollaborateUtilityException;
import com.slabs.collaborate.utilities.EmailUtil;
import com.slabs.collaborate.utilities.MarkerEngine;
import com.slabs.collaborate.utilities.RandomGenerator;

@Service(value = "EmailService")
public class EmailService implements CollaborateService {

	private static final Logger L = LoggerFactory.getLogger(EmailService.class);

	public EmailService() {

	}

	@Override
	public Map<String, Object> process(Map<String, String> paramsMap) {

		SqlSession session = null;
		try {
			session = DatabaseHelper.openSession();
			SQLMapper mapper = session.getMapper(SQLMapper.class);

			String emailType = paramsMap.get("emailType");
			if (Constants.VERIFY_EMAIL.equals(emailType)) {
				paramsMap.put("vCode", RandomGenerator.generateRandomText());
				mapper.createUserActivationEnrty(paramsMap);
				session.commit();
				sendVerificationEmail(paramsMap);
			}
		} catch (IOException ex) {
			L.error("Exception: {}", ex);
		} catch (Exception ex) {
			L.error("Exception: {}", ex);
		}
		return null;
	}

	private void sendVerificationEmail(Map<String, String> paramsMap) {

		String email = paramsMap.get("email");
		try {
			String message = MarkerEngine.process("VERIFY_EMAIL.ftl", paramsMap);
			Email mail = EmailUtil.createHtmlEmail(message, "Verify your email", email);
			L.info("Sending verification email to {}", email);
			mail.send();
		} catch (CollaborateUtilityException ex) {
			L.error("Exception: {}", ex);
		} catch (EmailException ex) {
			L.error("Exception: {}", ex);
		}
	}
}
