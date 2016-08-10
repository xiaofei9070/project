package com.star.sse;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlets.EventSource;
import org.eclipse.jetty.servlets.EventSourceServlet;

public class MovementServlet extends EventSourceServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected EventSource newEventSource(HttpServletRequest request) {
		return new MovementEventSource(800, 600, 20); 
	}

}
