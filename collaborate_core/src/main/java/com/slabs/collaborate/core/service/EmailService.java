package com.slabs.collaborate.core.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.core.db.SQLMapper;
import com.slabs.collaborate.utilities.CollaborateUtilityException;
import com.slabs.collaborate.utilities.EmailUtil;
import com.slabs.collaborate.utilities.FileUtil;
import com.slabs.collaborate.utilities.MarkerEngine;
import com.slabs.collaborate.utilities.RandomGenerator;

@Service(value = "EmailService")
public class EmailService implements CollaborateService {

	private static final Logger L = LoggerFactory.getLogger(EmailService.class);

	private static final String VERIFY_EMAIL = "VERIFY_EMAIL";

	public EmailService() {

	}

	@Override
	public Map<String, Object> process(Map<String, String> paramsMap) {

		try {
			SqlSession session = DatabaseHelper.openSession();
			SQLMapper mapper = session.getMapper(SQLMapper.class);

			String emailType = paramsMap.get("emailType");
			if (VERIFY_EMAIL.equals(emailType)) {
				paramsMap.put("vCode", RandomGenerator.generateRandomText());
				/*mapper.createUserActivationEnrty(paramsMap);*/
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

		try {
			String message = MarkerEngine.process("VERIFY_EMAIL.ftl", paramsMap);
			Email mail = EmailUtil.createHtmlEmail(message, "Verify your email", paramsMap.get("email"));
			mail.send();
		} catch (CollaborateUtilityException ex) {
			L.error("Exception: {}", ex);
		} catch (EmailException ex) {
			L.error("Exception: {}", ex);
		}
	}
}
