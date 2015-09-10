package com.slabs.collaborate.core.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.Constants;
import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.core.db.SQLMapper;
import com.slabs.collaborate.core.entity.User;

@Service(value = "RetrieveUserDetailService")
public class RetrieveUserDetailService implements CollaborateService {

	private static final Logger L = LoggerFactory.getLogger(RetrieveUserDetailService.class);

	@Override
	public Map<String, Object> process(Map<String, String> paramsMap) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		String userName = paramsMap.get("userName");
		String requestType = paramsMap.get("requestType");

		try {
			SqlSession session = DatabaseHelper.openSession();
			SQLMapper mapper = session.getMapper(SQLMapper.class);
			if (userName != null) {
				if ("ALL".equals(requestType)) {
					List<User> users = mapper.retrieveAllUsers();
					removePassword(users);
					outputMap.put("users", users);
				} else if ("OTHERS".equals(requestType)) {
					List<User> users = mapper.retrieveOtherUsers(userName);
					removePassword(users);
					outputMap.put("users", users);
				} else if ("SELF".equals(requestType)) {
					User user = mapper.retrieveUser(paramsMap);
					user.setPassword("");
					outputMap.put("users", user);
				}
				outputMap.put(Constants.SUCCESS, true);
				outputMap.put(Constants.MSG, "User details retrieved");
			} else {
				outputMap.put(Constants.SUCCESS, false);
				outputMap.put(Constants.MSG, "Request parameter username is mandatory");
			}

		} catch (IOException ex) {
			L.error("Exception :  {}", ex);
			outputMap.put(Constants.SUCCESS, false);
			outputMap.put(Constants.MSG, "Error retrieving user detail. Please contact customer support");
		} catch (Exception ex) {
			L.error("Exception :  {}", ex);
			outputMap.put(Constants.SUCCESS, false);
			outputMap.put(Constants.MSG, "Error retrieving user detail. Please contact customer support");
		}

		return outputMap;
	}

	private void removePassword(List<User> users) {

		for (User u : users) {
			u.setPassword("");
		}
	}

}
