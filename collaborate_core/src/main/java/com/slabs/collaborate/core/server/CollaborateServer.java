package com.slabs.collaborate.core.server;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slabs.collaborate.core.PropertyConstants;
import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.utilities.CollaborateUtilityException;
import com.slabs.collaborate.utilities.EmailUtil;
import com.slabs.collaborate.utilities.FileUtil;
import com.slabs.collaborate.utilities.MarkerEngine;
import com.slabs.collaborate.utilities.PropertiesUtil;
import com.slabs.collaborate.utilities.ResourceUtil;

public class CollaborateServer {

	private static final Logger L = LoggerFactory.getLogger(CollaborateServer.class);

	private static CollaborateServer server;

	private CollaborateServer() {

	}

	public static CollaborateServer getInstance() {

		synchronized (CollaborateServer.class) {
			if (server == null) {
				synchronized (CollaborateServer.class) {
					server = new CollaborateServer();
				}
			}

			return server;
		}
	}

	public void initialize() {

		try {
			L.info("Inititalizing ResourceUtil....");
			ResourceUtil.initialize(PropertyConstants.COLLABORATE_HOME);
			L.info("ResourceUtil initialization complete....");

			L.info("Initializing PropertiesUtil....");
			PropertiesUtil.loadProperties(ResourceUtil.getResourceDirectory(PropertyConstants.PROPERTIES_DIRECTORY, false), ".properties");
			L.info("PropertiesUtil initialization is complete....");

			Map<String, Properties> map = PropertiesUtil.getPropertiesMap();
			Properties p = map.get(PropertyConstants.COLLABORATE_PROPERTIES_FILE);

			L.info("Initializing EmailUtil....");
			EmailUtil.initialize(map.get(PropertyConstants.EMAIL_PROPERTIES_FILE), p.getProperty(PropertyConstants.APP_NAME));

			L.info("Initializing Marker Engine....");
			MarkerEngine.initialize();

			L.info("Looking for Collaborate file upload directory....");
			FileUtil.isDirectoryAvailable(p.getProperty(PropertyConstants.COLLABORATE_PROPS_USER_REPO), true);

			L.info("Initializing DataBaseHelper....");
			DatabaseHelper.initialize();

			L.info("Collaborate Server initialized....");
		} catch (IOException ex) {
			L.error("Exception: {}", ex);
			System.exit(1);
		} catch (CollaborateUtilityException e) {
			System.exit(1);
		}
	}

}
