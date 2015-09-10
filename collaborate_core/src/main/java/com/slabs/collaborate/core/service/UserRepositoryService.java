package com.slabs.collaborate.core.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slabs.collaborate.core.Constants;
import com.slabs.collaborate.utilities.FileUtil;
import com.slabs.collaborate.utilities.PropertiesUtil;

@Service(value = "UserRepositoryService")
public class UserRepositoryService implements CollaborateService {

	private static final Logger L = LoggerFactory.getLogger(UserRepositoryService.class);

	@Override
	public Map<String, Object> process(Map<String, String> paramsMap) {

		Map<String, Object> outputMap = new HashMap<String, Object>();
		try {
			Properties p = PropertiesUtil.getPropertiesMap().get("collaborate.properties");
			String userName = paramsMap.get("userName");
			String repoService = paramsMap.get("reposervice");
			String collaborateDirectory = p.getProperty("collaborate.userrepository");

			if (RepoService.CREATE == RepoService.valueOf(repoService)) {
				boolean isDirectoryCreated = FileUtil.createDirectory(new File(collaborateDirectory), userName);
				if (isDirectoryCreated) {
					outputMap.put(Constants.SUCCESS, true);
					outputMap.put(Constants.MSG, "User repository created for user " + userName);
				} else {
					outputMap.put(Constants.SUCCESS, false);
					outputMap.put(Constants.MSG, "Exception occurred while creating user repository. Please contact customer support");
				}
			} else if (RepoService.DELETE == RepoService.valueOf(repoService)) {
				boolean isFileDeleted = FileUtil.deleteFile(new File(collaborateDirectory + "\\" + userName));
				if (isFileDeleted) {
					outputMap.put(Constants.SUCCESS, true);
					outputMap.put(Constants.MSG, "User repository deleted");
				} else {
					outputMap.put(Constants.SUCCESS, false);
					outputMap.put(Constants.MSG, "Exception occured while deleting user repository. Please contact customer support");
				}
			}

		} catch (IOException ex) {
			L.error("Exception :  {}", ex);
			outputMap.put(Constants.SUCCESS, false);
			outputMap.put(Constants.MSG, "Exception occurred with User Repository service. Please contact customer support");
		}

		return outputMap;
	}

	public enum RepoService {
		CREATE, DELETE;
	}

}
