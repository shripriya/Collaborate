package com.slabs.collaborate.core.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.core.db.SQLMapper;
import com.slabs.collaborate.core.entity.User;

@Service(value = "RetrieveUserService")
public class RetrieveUserService implements CollaborateService {

	private static final Logger L = LoggerFactory.getLogger(RetrieveUserService.class);

	public Map<String, Object> process(Map<String, String> paramsMap) {

		SqlSession session = null;
		Map<String, Object> outputMap = new HashMap<String, Object>();
		try {
			session = DatabaseHelper.openSession();
			SQLMapper mapper = session.getMapper(SQLMapper.class);
			User user = mapper.retrieveUser(paramsMap);

			if (user != null) {
				outputMap.put("status_msg", "User available");
				outputMap.put("status_code", "00");
				outputMap.put("user", user);
			} else {
				outputMap.put("status_msg", "User not available");
				outputMap.put("status_code", "01");
				outputMap.put("user", null);
			}

		} catch (IOException ex) {
			L.error("Exception : {}", ex);
			outputMap.put("status_msg", "Exception Occured. Please contact customer support");
			outputMap.put("status_code", "99");

		} catch (Exception ex) {
			L.error("Exception : {}", ex);
			outputMap.put("status_msg", "Exception Occured. Please contact customer support");
			outputMap.put("status_code", "99");
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return outputMap;
	}

}
