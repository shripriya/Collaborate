package com.slabs.collaborate.core.server;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slabs.collaborate.core.db.DatabaseHelper;
import com.slabs.collaborate.utilities.CollaborateUtilityException;
import com.slabs.collaborate.utilities.EmailUtil;
import com.slabs.collaborate.utilities.FileUtil;
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
			ResourceUtil.initialize("COLLABORATE_HOME");
			L.info("ResourceUtil initialization complete....");

			L.info("Initializing PropertiesUtil....");
			PropertiesUtil.loadProperties(ResourceUtil.getResourceDirectory("properties", false), ".properties");
			L.info("PropertiesUtil initialization is complete....");

			Map<String, Properties> map = PropertiesUtil.getPropertiesMap();
			Properties p = map.get("collaborate.properties");

			L.info("Initializing EmailUtil....");
			EmailUtil.initialize(map.get("email.properties"), p.getProperty("app.name"));

			L.info("Looking for Collaborate file upload directory....");
			FileUtil.isDirectoryAvailable(p.getProperty("collaborate.userrepository"), true);

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
