/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slabs.collaborate.utilities;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shyam
 */
public class JAXBUtil {

	private static final Logger L = LoggerFactory.getLogger(JAXBUtil.class);

	public static Object getObject(String xml, Class className) {

		try {
			JAXBContext jaxb = JAXBContext.newInstance(className);
			Unmarshaller u = jaxb.createUnmarshaller();
			Object o = u.unmarshal(new StringReader(xml));
			return o;
		} catch (JAXBException ex) {
			L.error("Error converting XML string to object {}", ex);
		}
		return null;

	}

	public static String getXMLString(Object o, Class className) {

		try {
			StringWriter w = new StringWriter();
			JAXBContext jaxb = JAXBContext.newInstance(className);
			Marshaller m = jaxb.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(o, w);
			return w.toString();
		} catch (JAXBException ex) {
			// TODO Auto-generated catch block
			L.error("Error converting object to XML {}", ex);
		}
		return null;
	}

	public static String writeXMLStringToFile(Object o, Class className, File directory, String fileName) {

		File file = null;
		Writer writer = null;
		try {
			file = FileUtil.loadFile(directory, fileName);
			JAXBContext jaxb = JAXBContext.newInstance(className);
			Marshaller m = jaxb.createMarshaller();
			writer = new FileWriter(file);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(o, writer);
			return FileUtil.readFile(file);
		} catch (IOException ex) {
			L.error("Error reading file {}, exception {}", file.getName(), ex);
		} catch (JAXBException ex) {
			L.error("Error converting object to XML {}", ex);
		} finally {
			try {
				writer.close();
			} catch (IOException ex) {
				L.error("Error closing writer {}", ex);
			}

		}
		return null;
	}

	public static void writeXMLStringToConsole(Object o, Class className) {

		try {
			JAXBContext jaxb = JAXBContext.newInstance(className);
			Marshaller m = jaxb.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(o, System.out);
		} catch (JAXBException ex) {
			// TODO Auto-generated catch block
			L.error("Error converting object to XML {}", ex);
		}
	}
}
