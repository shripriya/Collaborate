package com.slabs.collaborate.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MarkerEngine {

	private static final Logger L = LoggerFactory.getLogger(MarkerEngine.class);

	private static Configuration configuration;

	public static void initialize() {

		MarkerEngine.configuration = createDefaultConfig();
	}

	public static String process(String templateName, Map<String, ? extends Object> model) throws CollaborateUtilityException {

		StringWriter writer = null;
		if (configuration == null) {
			initialize();
		}

		try {
			Template t = configuration.getTemplate(templateName);
			writer = new StringWriter();

			t.process(model, writer);
			return writer.toString();

		} catch (IOException ex) {
			throw new CollaborateUtilityException("Exception occurred while processing template", ex, false);
		} catch (TemplateException ex) {
			throw new CollaborateUtilityException("Exception occurred while processing template", ex, false);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {
					throw new CollaborateUtilityException("Exception occurred while processing template", ex, false);
				}
			}
		}

	}

	public static void process(String templateName, Map<String, Object> model, File fileToWrite) throws CollaborateUtilityException {

		FileWriter writer = null;
		if (configuration == null) {
			initialize();
		}

		Template t;
		try {
			writer = new FileWriter(fileToWrite);
			t = configuration.getTemplate(templateName);
			t.process(model, writer);
		} catch (IOException ex) {
			throw new CollaborateUtilityException("Exception occurred while processing template", ex, false);
		} catch (TemplateException ex) {
			throw new CollaborateUtilityException("Exception occurred while processing template", ex, false);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException ex) {
					throw new CollaborateUtilityException("Exception occurred while processing template", ex, false);
				}
			}
		}

	}

	private static Configuration createDefaultConfig() {

		Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
		configuration.setClassLoaderForTemplateLoading(MarkerEngine.class.getClassLoader(), "");
		return configuration;
	}

	public static Configuration getConfiguration() {

		return configuration;
	}

	public static void setConfiguration(Configuration configuration) {

		MarkerEngine.configuration = configuration;
	}

}
