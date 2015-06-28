/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slabs.collaborate.core.db;

/**
 *
 * @author Shyam
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.varia.ReloadingPropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shyam
 */
public class ConnectionFactory {

	private static final Logger L = LoggerFactory.getLogger(ConnectionFactory.class);

	public static SqlSessionFactory getSession(String configFile) throws IOException {

		L.info("Loading Database configuration from {}", configFile);
		return new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(configFile));

	}

}
