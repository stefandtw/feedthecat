package org.tigris.noodle.filters;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.tigris.noodle.NoodleConstants;
import org.tigris.noodle.NoodleData;
import org.tigris.noodle.NoodleRequestFilter;

public class ChangeRemoteUrl implements NoodleRequestFilter {

	public static final String PROXY_PREFIX = "proxyPrefix";

	@Override
	public void filter(NoodleData noodleData) throws Exception {
		HttpServletRequest clientRequest = noodleData.getClientRequest();
		String remoteUrl = clientRequest.getParameter(NoodleConstants.PAGE_VAR);
		String pathInfo = clientRequest.getPathInfo();
		String contextPath = clientRequest.getContextPath();
		String serverName = clientRequest.getServerName();
		String servletPath = clientRequest.getServletPath();
		String requestURI = clientRequest.getRequestURI();
		String requestURL = clientRequest.getRequestURL().toString();
		String proxyPrefix = requestURL + "?page=";

		System.out.println("starting...");
		System.out.println(pathInfo);
		System.out.println(contextPath);
		System.out.println(serverName);
		System.out.println(servletPath);
		System.out.println(requestURI);
		System.out.println(requestURL);
		System.out.println(proxyPrefix);
		System.out.println("done");

		// starting...
		// /Noodle
		//
		// localhost
		// /noodle
		// /noodle/Noodle
		// http://localhost:8080/noodle/Noodle
		// http://localhost:8080/noodle/Noodle?page=
		// done

		// .getContextPath()
		noodleData.setURL(new URL(remoteUrl));
		// proxyPrefix = "http://localhost:8080/noodle/Noodle?page=";
		noodleData.getClientRequest().setAttribute(PROXY_PREFIX, proxyPrefix);
	}

}
