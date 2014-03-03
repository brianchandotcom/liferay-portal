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

package com.liferay.cobertura.instrument;

import com.liferay.portal.kernel.util.CharPool;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.coveragedata.ProjectData;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shuyang Zhou
 */
public class ClassInstrumenter extends ClassVisitor {

	public ClassInstrumenter(
		ProjectData projectData, ClassVisitor classVisitor) {

		super(Opcodes.ASM4, classVisitor);

		_projectData = projectData;
	}

	@Override
	public void visit(
		int version, int access, String name, String signature,
		String superName, String[] interfaces) {

		_className = name.replace(CharPool.SLASH, CharPool.PERIOD);

		_classData = _projectData.getOrCreateClassData(_className);

		_classData.setContainsInstrumentationInfo();

		// Don't instrument interfaces or classes that have already been
		// instrumented.

		if (((access & Opcodes.ACC_INTERFACE) == 0) &&
			!_hasBeenInstrumented(interfaces)) {

			String[] newInterfaces = new String[interfaces.length + 1];

			System.arraycopy(
				interfaces, 0, newInterfaces, 0, interfaces.length);

			newInterfaces[newInterfaces.length - 1] = _HAS_BEEN_INSTRUMENTED;

			interfaces = newInterfaces;

			_instrument = true;
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public void visitEnd() {
		if (_instrument && (_classData.getNumberOfValidLines() == 0) &&
			_logger.isWarnEnabled()) {

			_logger.warn(
				"No line number information found for class " + _className +
					". Please recompile with debug info.");
		}
	}

	@Override
	public MethodVisitor visitMethod(
		int access, final String name, String desc, final String signature,
		String[] exceptions) {

		MethodVisitor methodVisitor = cv.visitMethod(
			access, name, desc, signature, exceptions);

		if (!_instrument) {
			return methodVisitor;
		}

		if (methodVisitor != null) {
			methodVisitor = new FirstPassMethodInstrumenter(
				_classData, methodVisitor, _className, access, name, desc,
				signature, exceptions);
		}

		return methodVisitor;
	}

	@Override
	public void visitSource(String source, String debug) {
		super.visitSource(source, debug);

		_classData.setSourceFileName(source);
	}

	private static boolean _hasBeenInstrumented(String[] interfaces) {
		for (String interfaceName : interfaces) {
			if (interfaceName.equals(_HAS_BEEN_INSTRUMENTED)) {
				return true;
			}
		}

		return false;
	}

	private static final String _HAS_BEEN_INSTRUMENTED =
		"net/sourceforge/cobertura/coveragedata/HasBeenInstrumented";

	private static final Logger _logger = LoggerFactory.getLogger(
		ClassInstrumenter.class);

	private ClassData _classData;
	private String _className;
	private boolean _instrument;
	private final ProjectData _projectData;

}