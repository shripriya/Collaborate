/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slabs.collaborate.core.db;

import java.io.IOException;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Shyam
 */
public class DatabaseHelper {

	private static final Logger L = LoggerFactory.getLogger(DatabaseHelper.class);

	private static SqlSessionFactory factory;

	public static void initialize() throws IOException {

		L.info("Initializing SQL Session factory....");
		factory = ConnectionFactory.getSession("dbconfig.xml");
		L.info("SQL Session factory initialized....");
	}

	public static SqlSession openSession() throws IOException {

		if (factory == null) {
			initialize();
		}
		L.info("Opening SQL Session....");
		return factory.openSession();
	}

	public static <T extends Object> T getMapper(Class<T> cls, SqlSession session) {

		return (T) session.getMapper(cls);
	}

}
