package com.slabs.collaborate.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slabs.collaborate.core.server.CollaborateServer;

/**
 * Servlet implementation class CollaborateServlet
 */
public class CollaborateServlet extends HttpServlet {

	private static final Logger L = LoggerFactory.getLogger(CollaborateServlet.class);

	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {

		// TODO Auto-generated method stub
		super.init();

		CollaborateServer.getInstance().initialize();

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CollaborateServlet() {

		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {

		super.destroy();
	}

}
