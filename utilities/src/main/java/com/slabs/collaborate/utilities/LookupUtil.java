package com.slabs.collaborate.utilities;

import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LookupUtil {

	private static final Logger L = LoggerFactory.getLogger(LookupUtil.class);

	private static InitialContext ctx;

	public static void initialize(Properties... properties) throws NamingException {

		L.info("Initializing LookupUtil with properties {}", properties);
		if (properties.length == 1) {
			ctx = new InitialContext(properties[0]);
		} else {
			ctx = new InitialContext();
		}

	}

	public static <T extends Object> T lookup(String jndiString, Class<T> className) throws NamingException {

		return (T) ctx.lookup(jndiString);
	}

}
