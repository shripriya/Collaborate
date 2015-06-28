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
import com.slabs.collaborate.core.entity.SharedFiles;

@Service(value = "FileSharingService")
public class FileSharingService implements CollaborateService {

	private static final Logger L = LoggerFactory.getLogger(FileSharingService.class);

	@Override
	public Map<String, Object> process(Map<String, String> paramsMap) {

		SqlSession session = null;
		Map<String, Object> outputMap = new HashMap<String, Object>();
		String type = paramsMap.get("requestType");
		try {
			session = DatabaseHelper.openSession();
			SQLMapper mapper = session.getMapper(SQLMapper.class);
			if (Constants.SHARE_FILE.equals(type)) {
				try {
					String sharedWith = paramsMap.get("sharedWith");
					if (sharedWith != null) {
						int recordCount = Integer.parseInt(paramsMap.get("recordCount"));
						if (recordCount == 0) {
							mapper.shareFile(paramsMap);
						}
						String[] split = sharedWith.split(",");
						Map<String, String> temp = new HashMap<String, String>(paramsMap);
						for (String s : split) {
							temp.put("sharedWith", s);
							mapper.updateSharedFileMapping(temp);
						}
						outputMap.put(Constants.SUCCESS, true);
						outputMap.put(Constants.MSG, "Files shared successfully");
					}
				} catch (Exception ex) {
					L.error("Exception : {}", ex);
					outputMap.put(Constants.SUCCESS, false);
					outputMap.put(Constants.MSG, "Exception occured while sharing, please contact customer support");
				}
			} else if (Constants.GET_SHARE_DETAILS_BY_FILENAME.equals(type)) {
				try {
					List<SharedFiles> sharedFiles = mapper.getShareDetailsByFileName(paramsMap);
					outputMap.put(Constants.SUCCESS, true);
					outputMap.put(Constants.MSG, "Details retrieved successfully");
					outputMap.put("SHARED_FILES", sharedFiles);
				} catch (Exception ex) {
					L.error("Exception : {}", ex);
					outputMap.put(Constants.SUCCESS, false);
					outputMap.put(Constants.MSG, "Exception occured while retrieving details, please contact customer support");
				}
			} else if (Constants.UNSHARE_FILE.equals(type)) {
				try {
					int recordCount = Integer.parseInt(paramsMap.get("recordCount"));
					if (recordCount == 1) {
						mapper.removeSharedFileMapping(paramsMap);
						mapper.removeFileSharing(paramsMap);
					} else {
						mapper.removeSharedFileMapping(paramsMap);
					}
					outputMap.put(Constants.SUCCESS, true);
					outputMap.put(Constants.MSG, "File un-shared successfully");
				} catch (Exception ex) {
					L.error("Exception : {}", ex);
					outputMap.put(Constants.SUCCESS, false);
					outputMap.put(Constants.MSG, "Exception occured while trying to unshare the file, please contact customer support");
				}
			}
		} catch (IOException ex) {
			L.error("Exception : {}", ex);
			outputMap.put(Constants.SUCCESS, false);
			outputMap.put(Constants.MSG, "Exception occured, please contact customer support");

		} finally {
			if (session != null) {
				session.commit();
				session.close();
			}
		}
		return outputMap;
	}

}
