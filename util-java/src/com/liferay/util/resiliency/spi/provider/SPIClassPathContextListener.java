/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;

import java.lang.reflect.Method;

import java.util.Arrays;
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

		Set<File> jarFiles = new LinkedHashSet<File>();

		// Load embedded Tomcat

		File spiEmbeddedLibDir = new File(contextPath, spiEmbeddedLibDirName);

		addJarFiles(jarFiles, spiEmbeddedLibDir);

		// Load portal-service.jar from MPI

		addJarFiles(
			jarFiles, PortalClassLoaderUtil.getClassLoader(),
			PortalException.class.getName());

		// Load JDBC driver jars from MPI

		addJarFiles(
			jarFiles, PortalClassLoaderUtil.getClassLoader(),
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME));

		// Load ext jars

		addJarFiles(jarFiles, new File(spiEmbeddedLibDir, "ext"));

		StringBundler sb = new StringBundler(jarFiles.size() * 2 + 4);

		for (File file : jarFiles) {
			sb.append(file.getAbsolutePath());
			sb.append(File.pathSeparator);
		}

		sb.append(contextPath);
		sb.append("/WEB-INF/classes");

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

	protected void addJarFiles(
		Set<File> jarFiles, ClassLoader classLoader, String className) {

		String path = ClassUtil.getParentPath(classLoader, className);

		int pos = path.lastIndexOf(".jar");

		pos = path.lastIndexOf(CharPool.SLASH, pos);

		path = path.substring(0, pos);

		addJarFiles(jarFiles, new File(path));
	}

	protected void addJarFiles(Set<File> jarFiles, File dir) {
		if (!dir.exists() || !dir.isDirectory()) {
			throw new RuntimeException(
				"Unable to find directory " + dir.getAbsolutePath());
		}

		File[] files = dir.listFiles();

		Arrays.sort(files);

		for (File file : files) {
			String fileName = file.getName();

			if (fileName.endsWith(".jar")) {
				jarFiles.add(file);
			}
		}
	}

	protected static final AtomicReference<SPIProvider> spiProviderReference =
		new AtomicReference<SPIProvider>();

	private static final Log _log = LogFactoryUtil.getLog(
		SPIClassPathContextListener.class);

}