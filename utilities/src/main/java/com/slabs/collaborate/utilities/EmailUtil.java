package com.slabs.collaborate.utilities;

import java.util.Properties;
import java.util.Set;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailUtil {

	private static final Logger L = LoggerFactory.getLogger(EmailUtil.class);

	private static Properties properties;

	private static String APP_NAME;

	private static String EMAIL_USERNAME;

	private static String EMAIL_PASSWORD;

	private static String EMAIL_SMTP_PORT;

	private static String EMAIL_HOST;

	private static String EMAIL_ADMIN;

	private static String EMAIL_ADMIN_NAME;

	public static void initialize(Properties props, String appName) {

		APP_NAME = appName;

		EMAIL_USERNAME = APP_NAME + ".email.admin.username";
		EMAIL_PASSWORD = APP_NAME + ".email.admin.password";
		EMAIL_SMTP_PORT = APP_NAME + ".email.smtp.port";
		EMAIL_HOST = APP_NAME + ".email.host";
		EMAIL_ADMIN = APP_NAME + ".email.admin.email";
		EMAIL_ADMIN_NAME = APP_NAME + ".email.admin.name";

		if (properties == null) {
			properties = props;
		}
	}

	public static Email createSimpleEmail(String content, String subject, String toEmail) throws CollaborateUtilityException {

		String[] email = { toEmail };
		return createSimpleEmail(content, subject, email);
	}

	public static Email createSimpleEmail(String content, String subject, Set<String> toEmailList) throws CollaborateUtilityException {

		String[] toEmails = (String[]) toEmailList.toArray();
		return createSimpleEmail(content, subject, toEmails);
	}

	private static Email createSimpleEmail(String content, String subject, String... toEmails) throws CollaborateUtilityException {

		Email email = null;
		try {
			email = configure(new SimpleEmail());
			email.setMsg(content);
			email.setSubject(subject);
			email.addTo(toEmails);
		} catch (EmailException e) {
			throw new CollaborateUtilityException("Exception occurred while creating email", e, true);
		}
		return email;
	}

	public static Email createHtmlEmail(String content, String subject, String toEmail) throws CollaborateUtilityException {

		String[] email = { toEmail };
		return createHtmlEmail(content, subject, email);
	}

	public static Email createHtmlEmail(String content, String subject, Set<String> toEmailList) throws CollaborateUtilityException {

		String[] toEmails = (String[]) toEmailList.toArray();
		return createHtmlEmail(content, subject, toEmails);
	}

	private static Email createHtmlEmail(String content, String subject, String... toEmails) throws CollaborateUtilityException {

		Email email = null;
		try {
			email = configure(new HtmlEmail());
			email.setMsg(content);
			email.setSubject(subject);
			email.addTo(toEmails);

		} catch (EmailException e) {
			throw new CollaborateUtilityException("Exception occurred while creating email", e, true);
		}
		return email;
	}

	private static Email configure(Email email) throws CollaborateUtilityException {

		if (properties == null) {
			throw new CollaborateUtilityException("Initialize Email Utility, before creating email", true);
		}

		try {
			email.setAuthenticator(new DefaultAuthenticator(properties.getProperty(EMAIL_USERNAME), properties.getProperty(EMAIL_PASSWORD)));
			email.setSmtpPort(Integer.parseInt(properties.getProperty(EMAIL_SMTP_PORT)));
			email.setHostName(properties.getProperty(EMAIL_HOST));
			email.setFrom(properties.getProperty(EMAIL_ADMIN), properties.getProperty(EMAIL_ADMIN_NAME));
			email.setStartTLSRequired(true);
			email.setStartTLSEnabled(true);
		} catch (EmailException e) {
			throw new CollaborateUtilityException("Exception occurred while configuring email", e, true);
		}
		return email;
	}
}
