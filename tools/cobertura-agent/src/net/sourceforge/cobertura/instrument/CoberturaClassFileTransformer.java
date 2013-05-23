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

import java.lang.instrument.ClassFileTransformer;

import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.cobertura.coveragedata.CoverageDataFileHandler;
import net.sourceforge.cobertura.coveragedata.ProjectData;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Shuyang Zhou
 */
public class CoberturaClassFileTransformer implements ClassFileTransformer {

	public CoberturaClassFileTransformer(
		String[] includes, String[] excludes, final File lockFile) {

		_includePatterns = new Pattern[includes.length];

		for (int i = 0; i < includes.length; i++) {
			Pattern pattern = Pattern.compile(includes[i]);

			_includePatterns[i] = pattern;
		}

		_excludePatterns = new Pattern[excludes.length];

		for (int i = 0; i < excludes.length; i++) {
			Pattern pattern = Pattern.compile(excludes[i]);

			_excludePatterns[i] = pattern;
		}

		ProjectDataUtil.addShutdownHook(
			new Runnable() {

				public void run() {
					File dataFile =
						CoverageDataFileHandler.getDefaultDataFile();

					Collection<ProjectData> projectDatas =
						_projectDatas.values();

					ProjectDataUtil.mergeSave(
						dataFile, lockFile,
						projectDatas.toArray(
							new ProjectData[projectDatas.size()]));

					_projectDatas.clear();
				}

			}

		);
	}

	public boolean matches(String className) {
		if (_excludePatterns.length != 0) {
			for (Pattern excludePattern : _excludePatterns) {
				Matcher matcher = excludePattern.matcher(className);

				if (matcher.matches()) {
					return false;
				}
			}
		}

		if (_includePatterns.length != 0) {
			for (Pattern includePattern : _includePatterns) {
				Matcher matcher = includePattern.matcher(className);

				if (matcher.matches()) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public byte[] transform(
		ClassLoader classLoader, String className, Class<?> refinedClass,
		ProtectionDomain protectionDomain, byte[] classfileBuffer) {

		try {
			if (matches(className)) {
				InstrumentationAgent.recordInstrumentation(
					classLoader, className, classfileBuffer);

				ProjectData projectData = _projectDatas.get(classLoader);

				if (projectData == null) {
					projectData = new ProjectData();

					ProjectData previousProjectData = _projectDatas.putIfAbsent(
						classLoader, projectData);

					if (previousProjectData != null) {
						projectData = previousProjectData;
					}
				}

				ClassWriter classWriter = new ClassWriter(
					ClassWriter.COMPUTE_MAXS);

				ClassVisitor classVisitor = classWriter;

				if (!InstrumentationAgent.isStaticallyInstrumented()) {
					classVisitor = new RemoveHasBeenInstrumentedClassVisitor(
						classVisitor);
				}

				classVisitor = new ClassInstrumenter(
					projectData, classVisitor, Collections.emptyList(),
					Collections.emptyList());

				ClassReader classReader = new ClassReader(classfileBuffer);

				synchronized (projectData) {
					classReader.accept(classVisitor, 0);
				}

				return classWriter.toByteArray();
			}

			// Modify TouchCollector's static initialization block by
			// redirecting ProjectData#initialize to
			// InstrumentationAgent#initialize

			if (className.equals(
					"net/sourceforge/cobertura/coveragedata/TouchCollector")) {

				ClassWriter classWriter = new ClassWriter(
					ClassWriter.COMPUTE_MAXS);

				ClassVisitor classVisitor = new TouchCollectorClassVisitor(
					classWriter);

				ClassReader classReader = new ClassReader(classfileBuffer);

				classReader.accept(classVisitor, 0);

				return classWriter.toByteArray();
			}
		}
		catch (Throwable t) {
			t.printStackTrace();

			throw new RuntimeException(t);
		}

		return null;
	}

	private static final String _HAS_BEEN_INSTRUMENTED_CLASS_NAME =
		"net/sourceforge/cobertura/coveragedata/HasBeenInstrumented";

	private Pattern[] _excludePatterns;
	private Pattern[] _includePatterns;
	private ConcurrentMap<ClassLoader, ProjectData> _projectDatas =
		new ConcurrentHashMap<ClassLoader, ProjectData>();

	private static class RemoveHasBeenInstrumentedClassVisitor
		extends ClassAdapter {

		public RemoveHasBeenInstrumentedClassVisitor(
			ClassVisitor classVisitor) {

			super(classVisitor);
		}

		@Override
		public void visit(
			int version, int access, String name, String signature,
			String superName, String[] interfaces) {

			if ((access & Opcodes.ACC_SUPER) != 0) {
				List<String> interfacesList = new ArrayList<String>(
					Arrays.asList(interfaces));

				if (interfacesList.remove(_HAS_BEEN_INSTRUMENTED_CLASS_NAME)) {
					interfaces = interfacesList.toArray(
						new String[interfacesList.size()]);

					super.visit(
						version, access, name, signature, superName,
						interfaces);

					return;
				}
			}

			super.visit(
				version, access, name, signature, superName, interfaces);
		}
	}

	private static class TouchCollectorClassVisitor extends ClassAdapter {

		public TouchCollectorClassVisitor(ClassVisitor classVisitor) {
			super(classVisitor);
		}

		@Override
		public MethodVisitor visitMethod(
			int access, String name, String desc, String signature,
			String[] exceptions) {

			MethodVisitor methodVisitor = cv.visitMethod(
				access, name, desc, signature, exceptions);

			if ((methodVisitor != null) && name.equals("<clinit>")) {
				methodVisitor = new TouchCollectorCLINITVisitor(methodVisitor);
			}

			return methodVisitor;
		}

	}

	private static class TouchCollectorCLINITVisitor extends MethodAdapter {

		public TouchCollectorCLINITVisitor(MethodVisitor methodVisitor) {
			super(methodVisitor);
		}

		@Override
		public void visitMethodInsn(
			int opcode, String owner, String name, String desc) {

			if ((opcode == Opcodes.INVOKESTATIC) &&
				owner.equals(
					"net/sourceforge/cobertura/coveragedata/ProjectData") &&
				name.equals("initialize") && desc.equals("()V")) {

				owner =
					"net/sourceforge/cobertura/instrument/InstrumentationAgent";
			}

			super.visitMethodInsn(opcode, owner, name, desc);
		}

	}

}