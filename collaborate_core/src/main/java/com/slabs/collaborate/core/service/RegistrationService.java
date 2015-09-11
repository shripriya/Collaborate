package com.slabs.collaborate.core.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.Constants;
import com.slabs.collaborate.core.PropertyConstants;
import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.core.db.SQLMapper;
import com.slabs.collaborate.core.entity.User;
import com.slabs.collaborate.core.exception.CollaborateException;
import com.slabs.collaborate.utilities.PropertiesUtil;

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

		final Map<String, Object> outputMap = new HashMap<String, Object>();
		final Map<String, Properties> propsMap = PropertiesUtil.getPropertiesMap();
		final Properties cProps = propsMap.get(PropertyConstants.COLLABORATE_PROPERTIES_FILE);
		SqlSession session = null;
		User u = null;
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
			String status = (String) userServiceOutput.get(Constants.STATUS_CODE);

			if ("01".equals(status)) {

				paramsMap.put("reposervice", UserRepositoryService.RepoService.CREATE.name());
				Map<String, Object> repoServiceOutput = repoService.process(paramsMap);

				if ((boolean) repoServiceOutput.get("success")) {
					boolean verifyEnabled = Boolean.parseBoolean((String) cProps.get(PropertyConstants.COLLABORATE_EMAIL_VERIFY_ENABLED));
					if(verifyEnabled) {
					 u = new User(paramsMap.get("userName"), paramsMap.get("password"), paramsMap.get("firstName"), paramsMap.get("lastName"), paramsMap.get("sex"), paramsMap.get("email"),
							paramsMap.get("mobile"), "N");
					}else{
						 u = new User(paramsMap.get("userName"), paramsMap.get("password"), paramsMap.get("firstName"), paramsMap.get("lastName"), paramsMap.get("sex"), paramsMap.get("email"),
									paramsMap.get("mobile"), "Y");
					}
					
					mapper.createUser(u);
					session.commit();

					if (verifyEnabled) {
						sendVerificationMail(paramsMap);
					}

					outputMap.put(Constants.STATUS_MSG, "Registration Complete");
					outputMap.put(Constants.STATUS_CODE, "00");

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

		outputMap.put(Constants.STATUS_MSG, "Registration Failed. " + msg);
		outputMap.put(Constants.STATUS_CODE, "99");
	}

	private void sendVerificationMail(Map<String, String> paramsMap) {

		paramsMap.put("emailType", Constants.VERIFY_EMAIL);
		emailService.process(paramsMap);
	}

}
