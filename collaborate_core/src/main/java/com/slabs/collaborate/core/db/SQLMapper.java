/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slabs.collaborate.core.db;

import java.util.List;
import java.util.Map;

import com.slabs.collaborate.core.entity.SharedFiles;
import com.slabs.collaborate.core.entity.User;

/**
 *
 * @author Shyam
 */
public interface SQLMapper {

	/**
	 * This method will create a user entry in the USERTABLE when the user
	 * register
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void createUser(User user) throws Exception;

	/**
	 * This method will create am entry in USER_ACTIVATION_TABLE when the user
	 * register.
	 * 
	 * @param paramsMap
	 * @throws Exception
	 */
	public void createUserActivationEnrty(Map<String, String> paramsMap) throws Exception;

	/**
	 * This method retrives the user details and it requires one parameter in
	 * the map.
	 * <p>
	 * userName - Username of the user whose detail has to be retrieved.
	 * 
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public User retrieveUser(Map<String, String> paramsMap) throws Exception;

	/**
	 * This method retrives all the users.
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<User> retrieveAllUsers() throws Exception;

	/**
	 * This method requires three parameters in the map.
	 * <p>
	 * fileName - Name of the file being shared.
	 * <p>
	 * filePath - File path of the file being shared.
	 * <p>
	 * userName - Username of the file owner.
	 * 
	 * @param paramsMap
	 * @throws Exception
	 */
	public void shareFile(Map<String, String> paramsMap) throws Exception;

	/**
	 * This method removes the file sharing with all the users and this method
	 * requires two parameters in the map.
	 * <p>
	 * fileName - Name of the file that has been shared.
	 * <p>
	 * userName - Username of the file owner.
	 * 
	 * @param paramsMap
	 * @throws Exception
	 */
	public void removeFileSharing(Map<String, String> paramsMap) throws Exception;

	/**
	 * This method updates with whom the file has been shared and this method
	 * requires three parameters in the map.
	 * <p>
	 * fileName - Name of the file being shared.
	 * <p>
	 * userName - Username of the file owner.
	 * <p>
	 * sharedWith - Username of the user with whom the file is being shared.
	 * 
	 * @param paramsMap
	 * @throws Exception
	 */
	public void updateSharedFileMapping(Map<String, String> paramsMap) throws Exception;

	/**
	 * This method removes the file share mapping to unshare the file with a
	 * specific user. This methods requires three parameters.
	 * <p>
	 * fileName - Name of the file that has been shared.
	 * <p>
	 * userName - Username of the file owner.
	 * <p>
	 * sharedWith - Username of the user with whom the file has been shared.
	 * 
	 * @param paramsMap
	 * @throws Exception
	 */
	public void removeSharedFileMapping(Map<String, String> paramsMap) throws Exception;

	/**
	 * This method requires two parameters in the map.
	 * <p>
	 * 
	 * userName - Username of the file owner.
	 * <p>
	 * fileName - Name of the file that has been shared.
	 * 
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public List<SharedFiles> getShareDetailsByFileName(Map<String, String> paramsMap) throws Exception;

	/**
	 * This method retrieves the list of files that has been shared with the
	 * user.
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<SharedFiles> retrieveSharedFiles(String userName) throws Exception;

	/**
	 * This method retrieves all the user except the user that matches the
	 * userName passed.
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public List<User> retrieveOtherUsers(String userName) throws Exception;

	/**
	 * This method updates the user table.
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateUser(User user) throws Exception;

}
