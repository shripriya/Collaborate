package com.slabs.collaborate.core.service;

import java.io.IOException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.core.db.SQLMapper;

public class UserUpdateService implements CollaborateService {

	public Map<String, Object> process(Map<String, String> paramsMap) {

		Boolean isUpdatePwd = false;
		Boolean isUpdateEmail = false;
		Boolean isUpdateMobile = false;

		try {
			SqlSession session = DatabaseHelper.openSession();
			SQLMapper mapper = session.getMapper(SQLMapper.class);

			if (paramsMap.get("updatePassword") != null) {
				isUpdatePwd = Boolean.valueOf(paramsMap.get("updatePassword"));
			}

			if (paramsMap.get("updateEmail") != null) {
				isUpdateEmail = Boolean.valueOf(paramsMap.get("isUpdateEmail"));
			}

			if (paramsMap.get("isUpdateMobile") != null) {
				isUpdateMobile = Boolean.valueOf(paramsMap.get("isUpdateMobile"));
			}

			if (isUpdatePwd) {

			} else {
				if (isUpdateEmail && isUpdateMobile) {

				} else if (isUpdateMobile) {

				} else if (isUpdateEmail) {

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
