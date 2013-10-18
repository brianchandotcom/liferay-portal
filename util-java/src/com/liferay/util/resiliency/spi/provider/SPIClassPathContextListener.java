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
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;

import java.io.File;

import java.lang.reflect.Method;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Shuyang Zhou
 */
public class SPIClassPathContextListener implements ServletContextListener {

	public static volatile String SPI_CLASS_PATH = StringPool.BLANK;

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		SPIProvider spiProvider = spiProviderReference.getAndSet(null);

		if (spiProvider != null) {
			MPIHelperUtil.unregisterSPIProvider(spiProvider);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();

		String contextPath = servletContext.getRealPath(StringPool.BLANK);
		String spiEmbeddedLibDirName = servletContext.getInitParameter(
			"spiEmbeddedLibDir");

		File spiEmbeddedLibDir = new File(contextPath, spiEmbeddedLibDirName);

		if (!spiEmbeddedLibDir.exists() || !spiEmbeddedLibDir.isDirectory()) {
			_log.error(
				"Unable to find SPI embedded lib directory " +
					spiEmbeddedLibDir.getAbsolutePath());

			return;
		}

		Set<File> jarFiles = new LinkedHashSet<File>();

		for (File file : spiEmbeddedLibDir.listFiles()) {
			String fileName = file.getName();

			if (fileName.endsWith(".jar")) {
				jarFiles.add(file);
			}
		}

		if (!ServerDetector.isTomcat()) {
			File spiEmbeddedLibExtDir = new File(spiEmbeddedLibDir, "ext");

			if (!spiEmbeddedLibExtDir.exists() ||
				!spiEmbeddedLibExtDir.isDirectory()) {

				_log.error(
					"Unable to find SPI embedded lib ext directory " +
						spiEmbeddedLibExtDir.getAbsolutePath());

				return;
			}

			for (File file : spiEmbeddedLibExtDir.listFiles()) {
				String fileName = file.getName();

				if (fileName.endsWith(".jar")) {
					jarFiles.add(file);
				}
			}

			// Load portal-service.jar from MPI

			String liferayLibGlobalDir = SystemProperties.get(
				PropsKeys.LIFERAY_LIB_GLOBAL_DIR);

			File portalServiceJarFile = new File(
				liferayLibGlobalDir, "portal-service.jar");

			if (!portalServiceJarFile.exists()) {
				_log.error(
					"Unable to find portal-service.jar file " +
						portalServiceJarFile.getAbsolutePath());

				return;
			}

			jarFiles.add(portalServiceJarFile);

			// Load JDBC driver jars from MPI

			String jdbcDriverJarDirName = ClassUtil.getParentPath(
				PortalClassLoaderUtil.getClassLoader(),
				PropsUtil.get(PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME));

			int pos = jdbcDriverJarDirName.lastIndexOf(".jar");

			pos = jdbcDriverJarDirName.lastIndexOf(CharPool.SLASH, pos);

			jdbcDriverJarDirName = jdbcDriverJarDirName.substring(0, pos + 1);

			File jdbcDriverJarDir = new File(jdbcDriverJarDirName);

			for (File file : jdbcDriverJarDir.listFiles()) {
				String fileName = file.getName();

				if (fileName.endsWith(".jar")) {
					jarFiles.add(file);
				}
			}
		}

		StringBundler sb = new StringBundler(jarFiles.size() * 2 + 4);

		for (File file : jarFiles) {
			sb.append(file.getAbsolutePath());
			sb.append(File.pathSeparator);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("SPI embedded lib class path " + sb.toString());
		}

		sb.append(contextPath);
		sb.append("/WEB-INF/classes");

		if (ServerDetector.isTomcat()) {
			sb.append(File.pathSeparator);
			sb.append(ClassPathUtil.getGlobalClassPath());
		}

		SPI_CLASS_PATH = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("SPI class path " + SPI_CLASS_PATH);
		}

		String spiProviderClassName = servletContext.getInitParameter(
			"spiProviderClassName");

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			Class<SPIProvider> spiProviderClass = null;

			if (SPIUtil.isSPI()) {
				spiProviderClass = (Class<SPIProvider>)loadClassDirectly(
					contextClassLoader, spiProviderClassName);
			}
			else {
				spiProviderClass =
					(Class<SPIProvider>)contextClassLoader.loadClass(
						spiProviderClassName);
			}

			SPIProvider spiProvider = spiProviderClass.newInstance();

			boolean result = spiProviderReference.compareAndSet(
				null, spiProvider);

			if (!result) {
				_log.error(
					"Duplicate SPI provider " + spiProvider +
						" is already registered in servlet context " +
							servletContext.getContextPath());
			}
			else {
				MPIHelperUtil.registerSPIProvider(spiProvider);
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to create SPI provider with name " +
					spiProviderClassName,
				e);
		}
	}

	protected static Class<?> loadClassDirectly(
			ClassLoader classLoader, String className)
		throws Exception {

		synchronized (classLoader) {
			Method findLoadedClassMethod = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "findLoadedClass", String.class);

			Class<?> clazz = (Class<?>)findLoadedClassMethod.invoke(
				classLoader, className);

			if (clazz == null) {
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