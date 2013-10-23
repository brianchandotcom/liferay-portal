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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.resiliency.spi.SPIConfiguration;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewClassLoaderJUnitTestRunner;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsImpl;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

		// Embedded lib directory

		File spiEmbeddedLibDir = new File(
			_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME);

		spiEmbeddedLibDir.mkdir();

		_jarFile = new File(spiEmbeddedLibDir, "jarFile.jar");

		_jarFile.createNewFile();

		File notJarFile = new File(spiEmbeddedLibDir, "notJarFile.zip");

		notJarFile.createNewFile();

		// Embedded lib ext directory

		File spiEmbeddedLibExtDir = new File(
			_CONTEXT_PATH, _EMBEDDED_LIB_EXT_DIR_NAME);

		spiEmbeddedLibExtDir.mkdir();

		_extJarFile = new File(spiEmbeddedLibExtDir, "extJarFile.jar");

		_extJarFile.createNewFile();

		File extNotJarFile = new File(
			spiEmbeddedLibExtDir, "extNotJarFile.zip");

		extNotJarFile.createNewFile();

		// Global lib dir1 for portal-service.jar

		File globalLibDir1 = new File(_CONTEXT_PATH, _GLOBAL_LIB_DIR1_NAME);

		globalLibDir1.mkdir();

		_portalServiceJarFile = new File(globalLibDir1, "portal-service.jar");

		_portalServiceJarFile.createNewFile();

		_global1JarFile = new File(globalLibDir1, "global1JarFile.jar");

		_global1JarFile.createNewFile();

		File global1NotJarFile = new File(
			globalLibDir1, "global1NotJarFile.zip");

		global1NotJarFile.createNewFile();

		// Global lib dir2 for JDBC driver

		File globalLibDir2 = new File(_CONTEXT_PATH, _GLOBAL_LIB_DIR2_NAME);

		globalLibDir2.mkdir();

		_jdbcDriverJarFile = new File(globalLibDir2, "jdbcDriver.jar");

		_jdbcDriverJarFile.createNewFile();

		_global2JarFile = new File(globalLibDir2, "global2JarFile.jar");

		_global2JarFile.createNewFile();

		File global2NotJarFile = new File(
			globalLibDir2, "global2NotJarFile.zip");

		global2NotJarFile.createNewFile();

		// Mock lookup

		final Map<String, URL> resources = new HashMap<String, URL>();

		final String driverClassName = "TestDriver";

		putResource(resources, _jdbcDriverJarFile, driverClassName);
		putResource(
			resources, _portalServiceJarFile, PortalException.class.getName());

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
				URL url = resources.get(name);

				if (url != null) {
					return url;
				}

				return super.getResource(name);
			}

		});
	}

	@After
	public void tearDown() {
		deleteFile(new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME));
		deleteFile(new File(_CONTEXT_PATH, _GLOBAL_LIB_DIR1_NAME));
		deleteFile(new File(_CONTEXT_PATH, _GLOBAL_LIB_DIR2_NAME));
	}

	@Test
	public void testClassPathGeneration() throws Exception {

		// With log

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.FINE);

		_mockServletContext.addInitParameter(
			"spiProviderClassName", "InvalidSPIProvider");

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		spiClassPathContextListener.contextInitialized(
			new ServletContextEvent(_mockServletContext));

		String spiClassPath =
			_jarFile.getAbsolutePath() + File.pathSeparator +
			_global1JarFile.getAbsolutePath() + File.pathSeparator +
			_portalServiceJarFile.getAbsolutePath() + File.pathSeparator +
			_global2JarFile.getAbsolutePath() + File.pathSeparator +
			_jdbcDriverJarFile.getAbsolutePath() + File.pathSeparator +
			_extJarFile.getAbsolutePath() + File.pathSeparator +
			_CONTEXT_PATH + "/WEB-INF/classes";

		Assert.assertEquals(
			spiClassPath, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertEquals(2, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals(
			"SPI class path " + spiClassPath, logRecord.getMessage());

		logRecord = logRecords.get(1);

		Assert.assertEquals(
			"Unable to create SPI provider with name InvalidSPIProvider",
			logRecord.getMessage());

		// Without log

		logRecords = JDKLoggerTestUtil.configureJDKLogger(
			SPIClassPathContextListener.class.getName(), Level.OFF);

		Field field = ReflectionUtil.getDeclaredField(SPIUtil.class, "_spi");

		field.set(null, new MockSPI());

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));
		}
		finally {
			field.set(null, null);
		}

		Assert.assertEquals(
			spiClassPath, SPIClassPathContextListener.SPI_CLASS_PATH);
		Assert.assertTrue(logRecords.isEmpty());
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
	public void testMissingGlobalLibDir1() throws IOException {
		testMissingDir(_GLOBAL_LIB_DIR1_NAME);
	}

	@Test
	public void testMissingGlobalLibDir2() throws IOException {
		testMissingDir(_GLOBAL_LIB_DIR2_NAME);
	}

	@Test
	public void testMissingSPIEmbeddedLibDir() throws IOException {
		testMissingDir(_EMBEDDED_LIB_DIR_NAME);
	}

	@Test
	public void testMissingSPIEmbeddedLibExtDir() throws IOException {
		testMissingDir(_EMBEDDED_LIB_EXT_DIR_NAME);
	}

	@Test
	public void testRegistration() throws Exception {

		// Register

		File embeddedLibDir = new File(_CONTEXT_PATH, _EMBEDDED_LIB_DIR_NAME);

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

	protected void putResource(
			Map<String, URL> resources, File jarFile, String className)
		throws MalformedURLException {

		String resourceName = className.replace(
			CharPool.PERIOD, CharPool.SLASH);

		resourceName = resourceName.concat(".class");

		URL url = new URL(
			"file://" + jarFile.getAbsolutePath() + "!/" + resourceName);

		resources.put(resourceName, url);
	}

	protected void testMissingDir(String dirName) throws IOException {

		// Does not exist

		deleteFile(new File(_CONTEXT_PATH, dirName));

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"Unable to find directory " + _CONTEXT_PATH +
					dirName, re.getMessage());
		}

		// Not a directory

		File file = new File(_CONTEXT_PATH, dirName);

		file.deleteOnExit();

		file.createNewFile();

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"Unable to find directory " + _CONTEXT_PATH +
					dirName, re.getMessage());
		}
		finally {
			file.delete();
		}
	}

	private static String _CONTEXT_PATH = System.getProperty("java.io.tmpdir");

	private static String _EMBEDDED_LIB_DIR_NAME = "/embeddedLibDir";

	private static String _EMBEDDED_LIB_EXT_DIR_NAME = "/embeddedLibDir/ext";

	private static String _GLOBAL_LIB_DIR1_NAME = "/globalLibDir1";

	private static String _GLOBAL_LIB_DIR2_NAME = "/globalLibDir2";

	private File _extJarFile;
	private File _global1JarFile;
	private File _global2JarFile;
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

	private File _portalServiceJarFile;

	private class TestClass {
	}

}