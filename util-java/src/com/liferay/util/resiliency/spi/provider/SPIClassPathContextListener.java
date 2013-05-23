
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.util.resiliency.spi.provider;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Shuyang Zhou
 */
public class SPIClassPathContextListener implements ServletContextListener {

	public static volatile String SPI_CLASSPATH = StringPool.BLANK;

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		SPIProvider spiProvider = spiProviderReference.getAndSet(null);

		if (spiProvider != null) {
			MPIHelperUtil.unregisterSPIProvider(spiProvider);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		// Generate classpath

		ServletContext servletContext = servletContextEvent.getServletContext();

		String contextPath = servletContext.getRealPath(StringPool.BLANK);
		String embeddedLibsDir = servletContext.getInitParameter(
			"embeddedLibsDir");

		File embeddedLibsFolder = new File(contextPath, embeddedLibsDir);

		if (!embeddedLibsFolder.exists() || !embeddedLibsFolder.isDirectory()) {
			_log.error(
				"Can not find embedded libs folder : " +
					embeddedLibsFolder.getAbsolutePath());

			return;
		}

		List<File> jarFiles = new ArrayList<File>();

		for (File file : embeddedLibsFolder.listFiles()) {
			String fileName = file.getName();

			if (fileName.endsWith(".jar")) {
				jarFiles.add(file);
			}
		}

		StringBundler sb = new StringBundler(jarFiles.size() * 2 + 4);

		for (File file : jarFiles) {
			sb.append(file.getAbsolutePath());
			sb.append(File.pathSeparator);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Generated embedded libs classpath : " + sb.toString());
		}

		sb.append(contextPath);
		sb.append("/WEB-INF/classes");
		sb.append(File.pathSeparator);

		sb.append(ClassPathUtil.getGlobalClassPath());

		SPI_CLASSPATH = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Generated SPI classpath : " + SPI_CLASSPATH);
		}

		// Generate SPIProvider instance

		String spiProviderClassName = servletContext.getInitParameter(
			"spiProviderClassName");

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<SPIProvider> spiProviderClass =
				(Class<SPIProvider>)loadClassDirectly(
					contextClassLoader, spiProviderClassName);

			SPIProvider spiProvider = spiProviderClass.newInstance();

			boolean result = spiProviderReference.compareAndSet(
				null, spiProvider);

			if (!result) {
				_log.error(
					"Unable to register generated SPIProvider " + spiProvider +
						". A SPIProvider already registered in this " +
							"ServletContext " + servletContext);
			}
			else {
				MPIHelperUtil.registerSPIProvider(spiProvider);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to create SPIProvide with name " + spiProviderClassName,
				e);
		}
	}

	/**
	 * This is an important hack to support process family tree.
	 * It allows a SPI to create its own SPI instances.
	 * It may seem naturally that SPI should able to create new SPI, as long as
	 * it has the spi provider web.
	 *
	 * But there is a tricky classloading issue.
	 * Let's call the original MPI's global classpath mcp1, the original spi
	 * provider web's private classpath scp1.
	 *
	 * Because SPI1 is forked from spi provider web, so its global classpath is
	 * mcp1 + scp1. In order to let SPI1 forks its own SPI, it needs the same
	 * spi provider web whose private classpath is still scp1.
	 *
	 * However the new SPI2 forked from SPI1 will automatically load the spi
	 * provider web classes from global classpath mcp1 + scp1, not the priavte
	 * scp1.
	 * So later on if the class in scp1 refers other private class like those in
	 * util-java.jar or do a cast to a real private Class type in spi provider
	 * web. We will see class cast failure.
	 *
	 * This method allow you to directly load class from the given
	 * ClassLoader(the plugin ClassLoader), to bypass the parent ClassLoader(the
	 * global ClassLoader). Because ClassLoader will automatically cache up the
	 * loaded classes, after we load them once, they will always be used.
	 */
	protected static Class<?> loadClassDirectly(
			ClassLoader classLoader, String className)
		throws Exception {

		synchronized (classLoader) {

			// Load from cache first

			Method findLoadedClassMethod = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "findLoadedClass", String.class);

			Class<?> clazz = (Class<?>)findLoadedClassMethod.invoke(
				classLoader, className);

			if (clazz == null) {

				// Miss cache, skip parent, directly load from given ClassLoader

				Method findClassMethod = ReflectionUtil.getDeclaredMethod(
					ClassLoader.class, "findClass", String.class);

				clazz = (Class<?>)findClassMethod.invoke(
					classLoader, className);
			}

			Method resolveClassMethod = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "resolveClass", Class.class);

			resolveClassMethod.invoke(classLoader, clazz);

			return clazz;
		}
	}

	protected static final AtomicReference<SPIProvider> spiProviderReference =
		new AtomicReference<SPIProvider>();

	private static Log _log = LogFactoryUtil.getLog(
		SPIClassPathContextListener.class);

}