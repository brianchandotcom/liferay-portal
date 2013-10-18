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

import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.util.PropsImpl;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.servlet.ServletContextEvent;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockServletContext;

/**
 * @author Shuyang Zhou
 */
@RunWith(NewClassLoaderJUnitTestRunner.class)
public class SPIClassPathContextListenerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {

		// embeddedLibDir

		File spiEmbeddedLibDir = new File(
			_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME);

		spiEmbeddedLibDir.mkdir();

		_jarFile = new File(spiEmbeddedLibDir, "jarfile.jar");

		_jarFile.createNewFile();

		_notJarFile = new File(spiEmbeddedLibDir, "notjarfile.zip");

		_notJarFile.createNewFile();

		// embeddedLibDir/ext

		File spiEmbeddedLibExtDir = new File(
			_CONTEXT_PATH, _EMBEDDED_LIB_EXT_DIR_NAME);

		spiEmbeddedLibExtDir.mkdir();

		_extJarFile = new File(spiEmbeddedLibExtDir, "extJarFile.jar");

		_extJarFile.createNewFile();

		File extNotJarFile = new File(
			spiEmbeddedLibExtDir, "extNotJarFile.zip");

		extNotJarFile.createNewFile();

		// portal-service.jar

		SystemProperties.set(
			PropsKeys.LIFERAY_LIB_GLOBAL_DIR,
			spiEmbeddedLibExtDir.getAbsolutePath());

		_portalServiceJarFile = new File(
			spiEmbeddedLibExtDir, "portal-service.jar");

		_portalServiceJarFile.createNewFile();

		// JDBC driver

		_jdbcDriverJarFile = new File(spiEmbeddedLibExtDir, "jdbcDriver.jar");

		_jdbcDriverJarFile.createNewFile();

		final String driverClassName = "TestDriver";

		final URL resourceURL = new URL(
			"file://" + _jdbcDriverJarFile.getAbsolutePath() + "/" +
			driverClassName + ".class");

		PropsUtil.setProps(new PropsImpl() {

			@Override
			public String get(String key) {
				if (key.equals(PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME)) {
					return driverClassName;
				}

				return super.get(key);
			}

		});

		PortalClassLoaderUtil.setClassLoader(new ClassLoader() {

			@Override
			public URL getResource(String name) {
				if (name.equals(driverClassName.concat(".class"))) {
					return resourceURL;
				}

				return super.getResource(name);
			}

		});

		setTomcat(false);
	}

	@After
	public void tearDown() {
		deleteFile(new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME));
	}

	@Test
	public void testLoadClassDirectly() throws Exception {

		String jvmClassPath = ClassPathUtil.getJVMClassPath(false);

		URL[] urls = ClassPathUtil.getClassPathURLs(jvmClassPath);

		ClassLoader parentClassLoader = new URLClassLoader(urls, null);

		ClassLoader childClassLoader = new URLClassLoader(
			urls, parentClassLoader);

		Class<?> clazz = SPIClassPathContextListener.loadClassDirectly(
			childClassLoader, TestClass.class.getName());

		Assert.assertNotSame(TestClass.class, clazz);
		Assert.assertEquals(TestClass.class.getName(), clazz.getName());
		Assert.assertSame(childClassLoader, clazz.getClassLoader());

		Method findLoadedClassMethod = ReflectionUtil.getDeclaredMethod(
			ClassLoader.class, "findLoadedClass", String.class);

		Assert.assertSame(
			clazz,
			findLoadedClassMethod.invoke(
				childClassLoader, TestClass.class.getName()));
		Assert.assertNull(
			findLoadedClassMethod.invoke(
				parentClassLoader, TestClass.class.getName()));
		Assert.assertSame(
			clazz,
			SPIClassPathContextListener.loadClassDirectly(
				childClassLoader, TestClass.class.getName()));
	}

	@Test
	public void testMissingSPIEmbeddedLibDir() throws IOException {

		// SPI embedded lib directory does not exist

		deleteFile(new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME));

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		Assert.assertSame(
			StringPool.BLANK, SPIClassPathContextListener.SPI_CLASS_PATH);

		AtomicReference<SPIProvider> spiProviderReference =
			SPIClassPathContextListener.spiProviderReference;

		Assert.assertNull(spiProviderReference.get());
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to find SPI embedded lib directory " + _CONTEXT_PATH +
				_EMBEDDED_LIB_DIR_NAME,
			logRecord.getMessage());

		// SPI embedded lib directory is not a directory

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		File file = new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME);

		file.deleteOnExit();

		file.createNewFile();

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));
		}
		finally {
			file.delete();
		}

		Assert.assertSame(
			StringPool.BLANK, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertNull(spiProviderReference.get());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to find SPI embedded lib directory " + _CONTEXT_PATH +
				_EMBEDDED_LIB_DIR_NAME,
			logRecord.getMessage());
	}

	@Test
	public void testNonTomcatClassPathGeneration() throws Exception {
		_mockServletContext.addInitParameter(
			"spiProviderClassName", "InvalidSPIProvider");

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		String spiClassPath =
			_jarFile.getAbsolutePath() + File.pathSeparator +
				_extJarFile.getAbsolutePath() + File.pathSeparator +
					_portalServiceJarFile.getAbsolutePath() +
						File.pathSeparator +
							_jdbcDriverJarFile.getAbsolutePath() +
								File.pathSeparator + _CONTEXT_PATH +
									"/WEB-INF/classes";

		Assert.assertEquals(
			spiClassPath, SPIClassPathContextListener.SPI_CLASS_PATH);
	}

	@Test
	public void testNonTomcatMissingPortalServiceJar() throws IOException {
		Assert.assertTrue(_portalServiceJarFile.delete());

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		Assert.assertSame(
			StringPool.BLANK, SPIClassPathContextListener.SPI_CLASS_PATH);

		AtomicReference<SPIProvider> spiProviderReference =
			SPIClassPathContextListener.spiProviderReference;

		Assert.assertNull(spiProviderReference.get());
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to find portal-service.jar file " +
				_portalServiceJarFile.getAbsolutePath(),
			logRecord.getMessage());
	}

	@Test
	public void testNonTomcatMissingSPIEmbeddedLibExtDir() throws IOException {

		// SPI embedded lib ext directory does not exist

		deleteFile(new File(_CONTEXT_PATH, _EMBEDDED_LIB_EXT_DIR_NAME));

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		Assert.assertSame(
			StringPool.BLANK, SPIClassPathContextListener.SPI_CLASS_PATH);

		AtomicReference<SPIProvider> spiProviderReference =
			SPIClassPathContextListener.spiProviderReference;

		Assert.assertNull(spiProviderReference.get());
		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to find SPI embedded lib ext directory " + _CONTEXT_PATH +
				_EMBEDDED_LIB_EXT_DIR_NAME,
			logRecord.getMessage());

		// SPI embedded lib ext directory is not a directory

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		File file = new File(_CONTEXT_PATH, _EMBEDDED_LIB_EXT_DIR_NAME);

		file.deleteOnExit();

		file.createNewFile();

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));
		}
		finally {
			file.delete();
		}

		Assert.assertSame(
			StringPool.BLANK, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertNull(spiProviderReference.get());
		Assert.assertEquals(1, logRecords.size());

		logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Unable to find SPI embedded lib ext directory " + _CONTEXT_PATH +
				_EMBEDDED_LIB_EXT_DIR_NAME,
			logRecord.getMessage());
	}

	@Test
	public void testRegistration() throws Exception {

		// Register

		setTomcat(true);

		File embeddedLibDir = new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME);

		embeddedLibDir.mkdir();

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		_mockServletContext.addInitParameter(
			"spiProviderClassName", MockSPIProvider.class.getName());

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		AtomicReference<SPIProvider> spiProviderReference =
			SPIClassPathContextListener.spiProviderReference;

		Assert.assertNotNull(spiProviderReference.get());

		List<SPIProvider> spiProviders = MPIHelperUtil.getSPIProviders();

		Assert.assertEquals(1, spiProviders.size());
		Assert.assertSame(spiProviderReference.get(), spiProviders.get(0));

		// Duplicate register

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.SEVERE);

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"Duplicate SPI provider " + spiProviderReference.get() +
				" is already registered in servlet context " +
					_mockServletContext.getContextPath(),
			logRecord.getMessage());

		// Unregister

		spiClassPathContextListener.contextDestroyed(
			new ServletContextEvent(_mockServletContext));

		Assert.assertNull(spiProviderReference.get());

		spiProviders = MPIHelperUtil.getSPIProviders();

		Assert.assertTrue(spiProviders.isEmpty());

		// Duplicate unregister

		spiClassPathContextListener.contextDestroyed(
			new ServletContextEvent(_mockServletContext));

		Assert.assertNull(spiProviderReference.get());

		spiProviders = MPIHelperUtil.getSPIProviders();

		Assert.assertTrue(spiProviders.isEmpty());

		embeddedLibDir.delete();
	}

	@Test
	public void testTomcatClassPathGeneration() throws Exception {

		// With log

		setTomcat(true);

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.FINE);

		_mockServletContext.addInitParameter(
			"spiProviderClassName", "InvalidSPIProvider");

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		String spiClassPath =
			_jarFile.getAbsolutePath() + File.pathSeparator + _CONTEXT_PATH +
				"/WEB-INF/classes" + File.pathSeparator +
					ClassPathUtil.getGlobalClassPath();

		Assert.assertEquals(
			spiClassPath, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertEquals(3, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"SPI embedded lib class path " + _jarFile.getAbsolutePath() +
				File.pathSeparator, logRecord.getMessage());

		logRecord = logRecords.get(1);

		Assert.assertEquals(
			"SPI class path " + spiClassPath, logRecord.getMessage());

		logRecord = logRecords.get(2);

		Assert.assertEquals(
			"Unable to create SPI provider with name InvalidSPIProvider",
			logRecord.getMessage());

		// Without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.OFF);

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		Assert.assertEquals(
			spiClassPath, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertTrue(logRecords.isEmpty());
	}

	public static class MockSPIProvider implements SPIProvider {

		@Override
		public SPI createSPI(SPIConfiguration spiConfiguration) {
			throw new UnsupportedOperationException();
		}

		@Override
		public String getName() {
			return MockSPIProvider.class.getName();
		}

		@Override
		public String toString() {
			return MockSPIProvider.class.getName();
		}

	}

	protected void deleteFile(File file) {
		Queue<File> fileQueue = new LinkedList<File>();

		fileQueue.offer(file);

		while ((file = fileQueue.poll()) != null) {
			if (file.isFile()) {
				file.delete();
			}
			else if (file.isDirectory()) {
				File[] files = file.listFiles();

				if (files.length == 0) {
					file.delete();
				}
				else {
					fileQueue.addAll(Arrays.asList(files));
					fileQueue.add(file);
				}
			}
		}
	}

	protected void setTomcat(boolean tomcat) throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			ServerDetector.class, "_tomcat");

		field.set(ServerDetector.getInstance(), tomcat);
	}

	private static String _CONTEXT_PATH = System.getProperty("java.io.tmpdir");

	private static String _EMBEDDED_LIB_DIR_NAME = "/embeddedLibDir";

	private static String _EMBEDDED_LIB_EXT_DIR_NAME = "/embeddedLibDir/ext";

	private File _extJarFile;
	private File _jarFile;
	private File _jdbcDriverJarFile;

	private MockServletContext _mockServletContext = new MockServletContext() {

		{
			addInitParameter("spiEmbeddedLibDir", _EMBEDDED_LIB_DIR_NAME);
		}

		@Override
		public String getRealPath(String path) {
			return _CONTEXT_PATH;
		}

	};

	private File _notJarFile;
	private File _portalServiceJarFile;

	private class TestClass {
	}

}