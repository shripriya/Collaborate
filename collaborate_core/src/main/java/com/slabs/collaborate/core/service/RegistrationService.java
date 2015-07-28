package com.slabs.collaborate.core.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.core.db.SQLMapper;
import com.slabs.collaborate.core.entity.User;
import com.slabs.collaborate.core.exception.CollaborateException;

@Service(value = "RegistrationService")
public class RegistrationService implements CollaborateService {

	private static final Logger L = LoggerFactory.getLogger(RegistrationService.class);

	@Autowired
	private UserRepositoryService repoService;

	@Autowired
	private RetrieveUserService userService;

	@Autowired
	private EmailService emailService;

	public Map<String, Object> process(Map<String, String> paramsMap) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		SqlSession session = null;
		try {
			session = DatabaseHelper.openSession();
			SQLMapper mapper = session.getMapper(SQLMapper.class);

			if (paramsMap.size() < 7) {
				CollaborateException ex = new CollaborateException("Registration Failed");
				L.error("Exception: Required input for registration is missing. {}, {} ", paramsMap, ex);
				populateFailureResponse(outputMap, "Please provide all mandatory information");
				return outputMap;
			}

			Map<String, Object> userServiceOutput = userService.process(paramsMap);
			String status = (String) userServiceOutput.get("status_code");

			if ("01".equals(status)) {

				paramsMap.put("reposervice", UserRepositoryService.RepoService.CREATE.name());
				Map<String, Object> repoServiceOutput = repoService.process(paramsMap);

				if ((boolean) repoServiceOutput.get("success")) {

					User u = new User(paramsMap.get("userName"), paramsMap.get("password"), paramsMap.get("firstName"), paramsMap.get("lastName"), paramsMap.get("sex"), paramsMap.get("email"),
							paramsMap.get("mobile"), "N");
					mapper.createUser(u);
					session.commit();
					sendVerificationMail(paramsMap);

					outputMap.put("status_msg", "Registration Complete");
					outputMap.put("status_code", "00");

				} else {
					populateFailureResponse(outputMap, "Error creating repository for user. Please contact customer support");
				}
			} else if ("00".equals(status)) {
				populateFailureResponse(outputMap, "Username " + paramsMap.get("userName") + " is already taken");
			} else if ("99".equals(status)) {
				populateFailureResponse(outputMap, "Please contact customer support");
			}

		} catch (Exception ex) {
			L.error("Exception: {}", ex);

			paramsMap.put("reposervice", UserRepositoryService.RepoService.DELETE.name());
			repoService.process(paramsMap);
			populateFailureResponse(outputMap, "Please contact customer support");

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return outputMap;
	}

	private void populateFailureResponse(Map<String, Object> outputMap, String msg) {

		outputMap.put("status_msg", "Registration Failed. " + msg);
		outputMap.put("status_code", "99");
	}

	private void sendVerificationMail(Map<String, String> paramsMap) {

		paramsMap.put("emailType", "VERIFY_EMAIL");
		emailService.process(paramsMap);
	}

}
