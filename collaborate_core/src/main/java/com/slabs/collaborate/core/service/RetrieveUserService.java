package com.slabs.collaborate.core.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.Constants;
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
				outputMap.put(Constants.STATUS_MSG, "User available");
				outputMap.put(Constants.STATUS_CODE, "00");
				outputMap.put("user", user);
			} else {
				outputMap.put(Constants.STATUS_MSG, "User not available");
				outputMap.put(Constants.STATUS_CODE, "01");
				outputMap.put("user", null);
			}

		} catch (IOException ex) {
			L.error("Exception : {}", ex);
			outputMap.put(Constants.STATUS_MSG, "Exception Occured. Please contact customer support");
			outputMap.put(Constants.STATUS_CODE, "99");

		} catch (Exception ex) {
			L.error("Exception : {}", ex);
			outputMap.put(Constants.STATUS_MSG, "Exception Occured. Please contact customer support");
			outputMap.put(Constants.STATUS_CODE, "99");
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return outputMap;
	}

}
