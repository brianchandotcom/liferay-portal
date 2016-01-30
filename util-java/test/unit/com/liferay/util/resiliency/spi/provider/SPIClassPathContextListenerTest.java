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
import com.liferay.portal.kernel.process.ClassPathUtil;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.MockSPI;
import com.liferay.portal.kernel.resiliency.spi.MockSPIProvider;
import com.liferay.portal.kernel.resiliency.spi.SPIUtil;
import com.liferay.portal.kernel.resiliency.spi.provider.SPIProvider;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;

import java.lang.reflect.InvocationHandler;
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

import org.springframework.mock.web.MockServletContext;

/**
 * @author Shuyang Zhou
 */
public class SPIClassPathContextListenerTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

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

		// Global lib 1 directory for portal-service.jar

		File globalLib1Dir = new File(_CONTEXT_PATH, _GLOBAL_LIB_1_DIR_NAME);

		globalLib1Dir.mkdir();

		_portalServiceJarFile = new File(globalLib1Dir, "portal-service.jar");

		_portalServiceJarFile.createNewFile();

		_global1JarFile = new File(globalLib1Dir, "global1JarFile.jar");

		_global1JarFile.createNewFile();

		File global1NotJarFile = new File(
			globalLib1Dir, "global1NotJarFile.zip");

		global1NotJarFile.createNewFile();

		// Global lib 2 directory for JDBC driver

		File globalLib2Dir = new File(_CONTEXT_PATH, _GLOBAL_LIB_2_DIR_NAME);

		globalLib2Dir.mkdir();

		_jdbcDriverJarFile = new File(globalLib2Dir, "jdbcDriver.jar");

		_jdbcDriverJarFile.createNewFile();

		_global2JarFile = new File(globalLib2Dir, "global2JarFile.jar");

		_global2JarFile.createNewFile();

		File global2NotJarFile = new File(
			globalLib2Dir, "global2NotJarFile.zip");

		global2NotJarFile.createNewFile();

		// Mock lookup

		final Map<String, URL> resources = new HashMap<>();

		final String driverClassName = "TestDriver";

		putResource(resources, _jdbcDriverJarFile, driverClassName);
		putResource(
			resources, _portalServiceJarFile, PortalException.class.getName());

		final Method getMethod = Props.class.getMethod("get", String.class);

		PropsUtil.setProps(
			(Props)ProxyUtil.newProxyInstance(
				Props.class.getClassLoader(), new Class<?>[] {Props.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						if (getMethod.equals(method)) {
							if (args[0].equals(
									PropsKeys.JDBC_DEFAULT_DRIVER_CLASS_NAME)) {

								return driverClassName;
							}

							return StringPool.BLANK;
						}

						throw new UnsupportedOperationException();
					}

				}));

		PortalClassLoaderUtil.setClassLoader(
			new ClassLoader() {

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
		deleteFile(new File(_CONTEXT_PATH, _GLOBAL_LIB_1_DIR_NAME));
		deleteFile(new File(_CONTEXT_PATH, _GLOBAL_LIB_2_DIR_NAME));
	}

	@Test
	public void testClassPathGeneration() {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					SPIClassPathContextListener.class.getName(), Level.FINE)) {

			// With log

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			_mockServletContext.addInitParameter(
				"spiProviderClassName", "InvalidSPIProvider");

			SPIClassPathContextListener spiClassPathContextListener =
				new SPIClassPathContextListener();

			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));

			StringBundler sb = new StringBundler();

			sb.append(_jarFile.getAbsolutePath());
			sb.append(File.pathSeparator);
			sb.append(_global1JarFile.getAbsolutePath());
			sb.append(File.pathSeparator);
			sb.append(_portalServiceJarFile.getAbsolutePath());
			sb.append(File.pathSeparator);
			sb.append(_global2JarFile.getAbsolutePath());
			sb.append(File.pathSeparator);
			sb.append(_jdbcDriverJarFile.getAbsolutePath());
			sb.append(File.pathSeparator);
			sb.append(_extJarFile.getAbsolutePath());
			sb.append(File.pathSeparator);
			sb.append(_CONTEXT_PATH);
			sb.append("/WEB-INF/classes");

			String spiClassPath = sb.toString();

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

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			ReflectionTestUtil.setFieldValue(
				SPIUtil.class, "_spi", new MockSPI());

			try {
				spiClassPathContextListener.contextInitialized(
					new ServletContextEvent(_mockServletContext));
			}
			finally {
				ReflectionTestUtil.setFieldValue(SPIUtil.class, "_spi", null);
			}

			Assert.assertEquals(
				spiClassPath, SPIClassPathContextListener.SPI_CLASS_PATH);
			Assert.assertTrue(logRecords.isEmpty());
		}
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
		Assert.assertSame(
			clazz,
			ReflectionTestUtil.invoke(
				childClassLoader, "findLoadedClass",
				new Class<?>[] {String.class}, TestClass.class.getName()));
		Assert.assertNull(
			ReflectionTestUtil.invoke(
				parentClassLoader, "findLoadedClass",
				new Class<?>[] {String.class}, TestClass.class.getName()));
		Assert.assertSame(
			clazz,
			SPIClassPathContextListener.loadClassDirectly(
				childClassLoader, TestClass.class.getName()));
	}

	@Test
	public void testMissingGlobalLibDir1() throws IOException {
		testMissingDir(_GLOBAL_LIB_1_DIR_NAME);
	}

	@Test
	public void testMissingGlobalLibDir2() throws IOException {
		testMissingDir(_GLOBAL_LIB_2_DIR_NAME);
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
	public void testRegistration() {

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

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					SPIClassPathContextListener.class.getName(),
					Level.SEVERE)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Duplicate SPI provider " + spiProviderReference.get() +
					" is already registered in servlet context " +
						_mockServletContext.getContextPath(),
				logRecord.getMessage());
		}

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

		// Register from SPI

		_mockServletContext.addInitParameter(
			"spiProviderClassName", MockSPIProvider.class.getName());

		ReflectionTestUtil.setFieldValue(SPIUtil.class, "_spi", new MockSPI());

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));
		}
		finally {
			ReflectionTestUtil.setFieldValue(SPIUtil.class, "_spi", null);
		}

		spiProviderReference = SPIClassPathContextListener.spiProviderReference;

		Assert.assertNotNull(spiProviderReference.get());

		spiProviders = MPIHelperUtil.getSPIProviders();

		Assert.assertEquals(1, spiProviders.size());
		Assert.assertSame(spiProviderReference.get(), spiProviders.get(0));

		embeddedLibDir.delete();
	}

	protected void deleteFile(File file) {
		Queue<File> fileQueue = new LinkedList<>();

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

		File file = new File(_CONTEXT_PATH, dirName);

		deleteFile(file);

		SPIClassPathContextListener spiClassPathContextListener =
			new SPIClassPathContextListener();

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"Unable to find directory " + file.getAbsolutePath(),
				re.getMessage());
		}

		// Not a directory

		file.deleteOnExit();

		file.createNewFile();

		try {
			spiClassPathContextListener.contextInitialized(
				new ServletContextEvent(_mockServletContext));

			Assert.fail();
		}
		catch (RuntimeException re) {
			Assert.assertEquals(
				"Unable to find directory " + file.getAbsolutePath(),
				re.getMessage());
		}
		finally {
			file.delete();
		}
	}

	private static final String _CONTEXT_PATH = StringUtil.toLowerCase(
		System.getProperty("java.io.tmpdir"));

	private static final String _EMBEDDED_LIB_DIR_NAME = "/embeddedLib";

	private static final String _EMBEDDED_LIB_EXT_DIR_NAME = "/embeddedLib/ext";

	private static final String _GLOBAL_LIB_1_DIR_NAME = "/globalLib1";

	private static final String _GLOBAL_LIB_2_DIR_NAME = "/globalLib2";

	private File _extJarFile;
	private File _global1JarFile;
	private File _global2JarFile;
	private File _jarFile;
	private File _jdbcDriverJarFile;

	private final MockServletContext _mockServletContext =
		new MockServletContext() {

			{
				addInitParameter("spiEmbeddedLibDir", _EMBEDDED_LIB_DIR_NAME);
			}

			@Override
			public String getRealPath(String path) {
				return _CONTEXT_PATH;
			}

		};

	private File _portalServiceJarFile;

	private static class TestClass {
	}

}