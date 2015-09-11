package com.slabs.collaborate.core.service;

import java.io.IOException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.db.DatabaseHelper;

@Service(value = "AccountVerificationService")
public class AccountVerificationService implements CollaborateService {
	
	private static final Logger L = LoggerFactory.getLogger(AccountVerificationService.class);

	public AccountVerificationService() {

	}

	@Override
	public Map<String, Object> process(Map<String, String> paramsMap) {

		String userName = paramsMap.get("userName");
		String vCode = paramsMap.get("vCode");
		SqlSession session = null;
		if ((userName != null || !userName.isEmpty()) && (vCode != null || !vCode.isEmpty())) {
			try {
				session = DatabaseHelper.openSession();
			} catch (IOException ex) {
				L.error("Exception: {}", ex);
			}
		}

		return null;
	}

}
