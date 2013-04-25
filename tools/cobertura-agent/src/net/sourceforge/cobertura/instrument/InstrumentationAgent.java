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

package net.sourceforge.cobertura.instrument;

import java.io.File;
import java.io.IOException;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.CoverageDataFileHandler;
import net.sourceforge.cobertura.coveragedata.ProjectData;

/**
 * @author Shuyang Zhou
 */
public class InstrumentationAgent {

	public static synchronized void assertCoverage(
		boolean includeInnerClasses, Class<?>... classes) {

		if (!_dynamicallyInstrumented) {
			return;
		}

		try {
			File dataFile = CoverageDataFileHandler.getDefaultDataFile();

			ProjectData projectData = ProjectDataUtil.captureProjectData(
				dataFile, _lockFile);

			for (Class<?> clazz : classes) {
				ClassData classData = projectData.getClassData(clazz.getName());

				_assertClassDataCoverage(clazz, classData);

				Class<?>[] declaredClasses = clazz.getDeclaredClasses();

				for (Class<?> declaredClass : declaredClasses) {
					classData = projectData.getClassData(
						declaredClass.getName());

					_assertClassDataCoverage(declaredClass, classData);
				}
			}
		}
		finally {
			System.clearProperty("cobertura.parent.dynamically.instrumented");
			System.clearProperty("junit.code.coverage");

			_dynamicallyInstrumented = false;

			_instrumentation.removeTransformer(_coberturaClassFileTransformer);

			_coberturaClassFileTransformer = null;

			if (_originalClassDefinitions == null) {
				return;
			}

			try {
				List<ClassDefinition> classDefinitions =
					new ArrayList<ClassDefinition>(
						_originalClassDefinitions.size());

				for (int i = 0; i < _originalClassDefinitions.size(); i++) {
					OriginalClassDefinition originalClassDefinition =
						_originalClassDefinitions.get(i);

					ClassDefinition classDefinition =
						originalClassDefinition.toClassDefinition();

					if (classDefinition != null) {
						classDefinitions.add(classDefinition);
					}
				}

				_originalClassDefinitions = null;

				_instrumentation.redefineClasses(
					classDefinitions.toArray(
						new ClassDefinition[classDefinitions.size()]));
			}
			catch (Exception e) {
				throw new RuntimeException("Unable to uninstrument classes", e);
			}
		}
	}

	public static synchronized void dynamicallyInstrument(
			String[] includes, String[] excludes)
		throws UnmodifiableClassException {

		if ((_instrumentation == null) || _dynamicallyInstrumented) {
			return;
		}

		if (includes == null) {
			includes = _includes;
		}

		if (excludes == null) {
			excludes = _excludes;
		}

		if (_coberturaClassFileTransformer == null) {
			_coberturaClassFileTransformer = new CoberturaClassFileTransformer(
				includes, excludes, _lockFile);
		}

		_instrumentation.addTransformer(_coberturaClassFileTransformer, true);

		Class<?>[] allLoadedClasses =_instrumentation.getAllLoadedClasses();

		List<Class<?>> modifiableClasses = new ArrayList<Class<?>>();

		for (Class<?> loadedClass : allLoadedClasses) {
			if (_instrumentation.isModifiableClass(loadedClass)) {
				String className = loadedClass.getName();

				className = className.replace('.', '/');

				if (_coberturaClassFileTransformer.matches(className)) {
					modifiableClasses.add(loadedClass);
				}
			}
		}

		if (!modifiableClasses.isEmpty()) {
			_instrumentation.retransformClasses(
				modifiableClasses.toArray(
					new Class<?>[modifiableClasses.size()]));
		}

		_dynamicallyInstrumented = true;
		_originalClassDefinitions = null;

		System.setProperty("cobertura.parent.dynamically.instrumented", "true");
		System.setProperty("junit.code.coverage", "true");
	}

	public static void initialize() {
		ProjectDataUtil.addShutdownHook(
			new Runnable() {

				public void run() {
					File dataFile =
						CoverageDataFileHandler.getDefaultDataFile();

					ProjectData projectData =
						ProjectDataUtil.collectProjectData();

					ProjectDataUtil.mergeSave(dataFile, _lockFile, projectData);
				}

			}
		);
	}

	public static boolean isStaticallyInstrumented() {
		return _staticallyInstrumented;
	}

	public static synchronized void premain(
		String agentArguments, Instrumentation instrumentation) {

		String[] arguments = agentArguments.split(";");

		String[] includes = arguments[0].split(",");
		String[] excludes = arguments[1].split(",");

		boolean coberturaParentDynamicallyInstrumented = Boolean.getBoolean(
			"cobertura.parent.dynamically.instrumented");
		boolean junitCodeCoverage = Boolean.getBoolean("junit.code.coverage");

		// A subprocess is only considered as statically instrumented when it is
		// configured as such and its parent is not dynamically instrumented

		_staticallyInstrumented =
			!coberturaParentDynamicallyInstrumented && junitCodeCoverage;

		if (junitCodeCoverage) {
			CoberturaClassFileTransformer coberturaClassFileTransformer =
				new CoberturaClassFileTransformer(
					includes, excludes, _lockFile);

			instrumentation.addTransformer(coberturaClassFileTransformer);
		}
		else if (instrumentation.isRedefineClassesSupported() &&
				 instrumentation.isRetransformClassesSupported()) {

			_instrumentation = instrumentation;
			_includes = includes;
			_excludes = excludes;

			// Forcibly clear the data file to make sure that the coverage
			// assert is based on the current test

			File dataFile = CoverageDataFileHandler.getDefaultDataFile();

			dataFile.delete();
		}
		else {
			StringBuilder sb = new StringBuilder();

			sb.append("Current JVM is not capable for dynamic ");
			sb.append("instrumententation. Instrumentation ");

			if (instrumentation.isRetransformClassesSupported()) {
				sb.append("supports ");
			}
			else {
				sb.append("does not support ");
			}

			sb.append("restranforming classes. Instrumentation ");

			if (instrumentation.isRedefineClassesSupported()) {
				sb.append("supports ");
			}
			else {
				sb.append("does not support ");
			}

			sb.append("redefining classes. Dynamic instrumententation is ");
			sb.append("disabled.");

			System.out.println(sb.toString());
		}
	}

	public static synchronized void recordInstrumentation(
		ClassLoader classLoader, String name, byte[] bytes) {

		if (!_dynamicallyInstrumented) {
			return;
		}

		if (_originalClassDefinitions == null) {
			_originalClassDefinitions =
				new ArrayList<OriginalClassDefinition>();
		}

		OriginalClassDefinition originalClassDefinition =
			new OriginalClassDefinition(classLoader, name, bytes);

		_originalClassDefinitions.add(originalClassDefinition);
	}

	private static void _assertClassDataCoverage(
		Class<?> clazz, ClassData classData) {

		if (classData == null) {
			throw new RuntimeException(
				"Class " + clazz.getName() + " has no coverage data");
		}

		if ((classData.getBranchCoverageRate() != 1.0) ||
			(classData.getLineCoverageRate() != 1.0)) {

			System.out.printf(
				"%n[Cobertura] %s is not fully covered.%n[Cobertura]Branch " +
					"coverage rate : %.2f, line coverage rate : %.2f.%n" +
						"[Cobertura]Please rerun test with -Djunit.code." +
							"coverage=true to see coverage report.%n",
				classData.getName(), classData.getBranchCoverageRate(),
				classData.getLineCoverageRate());

			throw new RuntimeException(
				classData.getName() + " is not fully covered");
		}

		System.out.printf(
			"[Cobertura] %s is fully covered.%n", classData.getName());
	}

	private static CoberturaClassFileTransformer _coberturaClassFileTransformer;
	private static boolean _dynamicallyInstrumented;
	private static String[] _excludes;

	private static String[] _includes;
	private static Instrumentation _instrumentation;
	private static File _lockFile;

	static {
		File dataFile = CoverageDataFileHandler.getDefaultDataFile();

		File parentFolder = dataFile.getParentFile();

		if (!parentFolder.exists()) {
			parentFolder.mkdirs();
		}

		// OS wide lock file is created by the first started process where we
		// know for sure that there is no race condition. Acquiring an exclusive
		// lock on this lock file prevents losing updates on the data file.

		_lockFile = new File(parentFolder, "lock");

		try {
			_lockFile.createNewFile();
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

	private static List<OriginalClassDefinition> _originalClassDefinitions;
	private static boolean _staticallyInstrumented;

	private static class OriginalClassDefinition {

		public OriginalClassDefinition(
			ClassLoader classLoader, String className, byte[] bytes) {

			_classLoader = classLoader;
			_className = className.replace('/', '.');
			_bytes = bytes;
		}

		public ClassDefinition toClassDefinition()
			throws ClassNotFoundException {

			try {
				Class<?> clazz = Class.forName(_className, true, _classLoader);

				return new ClassDefinition(clazz, _bytes);
			}
			catch (NoClassDefFoundError ncdf) {
				return null;
			}
		}

		private final byte[] _bytes;
		private final ClassLoader _classLoader;
		private final String _className;

	}

}