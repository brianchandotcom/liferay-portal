/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 *
 */

package com.liferay.util.appserver.tomcat.exploded;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.appserver.EmbeddedApplicationServer;
import com.liferay.util.appserver.tomcat.TomcatServer;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.startup.Tomcat;

/**
 * Runs the portal without war packaging; using the classes present in the
 * development environment
 *
 * @author Miguel Pastor
 *
 */
public class ExplodedTomcatServer extends TomcatServer {

	public static void main(String[] args) {

		if (args.length < 3) {
			System.err.println(
				"Expected args: <Tomcat base dir> <Webapp base dir>" +
					" [start|stop]");

			System.exit(-1);
		}

		EmbeddedApplicationServer tomcatInstance = new ExplodedTomcatServer(
				args[0], args[1], null);

		String command = args[2];

		if ("start".equals(command)) {
			tomcatInstance.start();
		} else {
			if ("stop".equals(command)) {
				tomcatInstance.stop();
			}
		}
	}

	public ExplodedTomcatServer(
		String tomcatBaseDir, String webappBaseDir, ClassLoader classLoader) {

		_tomcat.setBaseDir(tomcatBaseDir);
		_tomcat.getServer().addLifecycleListener(new AprLifecycleListener());

		try {
			Context liferayContext = _tomcat.addWebapp(
				EmbeddedApplicationServer.LIFERAY_DEFAULT_CONTEXT,
				webappBaseDir);

			// configure the context

			liferayContext.setReloadable(true);

			WebappLoader loader = new WebappLoader(classLoader);
			liferayContext.setLoader(loader);

		} catch (ServletException e) {
			_log.error(
				"An error has occurred while configuring Liferay app context");
		}

	}

	@Override
	public void stop() {
		try {
			_tomcat.stop();
		} catch (LifecycleException e) {
			_log.error(
				"An error has occurred while stopping the tomcat instance");
		}

	}

	@Override
	@SuppressWarnings("all")
	protected void doStart(String hostName, int port) {

		try {
			if (hostName != "localhost") {
				_tomcat.getConnector().setAttribute("address", hostName);
			}

			_tomcat.setPort(port);

			_tomcat.start();

			_tomcat.getServer().await();

		} catch (LifecycleException e) {
			e.printStackTrace();
		}

	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExplodedTomcatServer.class);

	private final Tomcat _tomcat = new Tomcat();

}