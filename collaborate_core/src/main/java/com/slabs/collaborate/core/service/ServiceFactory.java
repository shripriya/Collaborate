package com.slabs.collaborate.core.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceFactory {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext("classpath:com/slabs/collaborate/spring/config/collaborate-core-config.xml");

	public static ApplicationContext getBeanContext() {

		return context;
	}

}
