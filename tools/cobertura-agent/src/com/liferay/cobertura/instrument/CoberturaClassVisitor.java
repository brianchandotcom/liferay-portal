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

import net.sourceforge.cobertura.coveragedata.ClassData;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shuyang Zhou
 */
public class CoberturaClassVisitor extends ClassVisitor {

	public CoberturaClassVisitor(
		ClassData classData, ClassVisitor classVisitor) {

		super(Opcodes.ASM4, classVisitor);

		_classData = classData;
	}

	@Override
	public void visit(
		int version, int access, String name, String signature,
		String superName, String[] interfaces) {

		_classData.setContainsInstrumentationInfo();

		// Don't instrument interfaces

		if ((access & Opcodes.ACC_INTERFACE) == 0) {
			_instrument = true;
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public void visitEnd() {
		if (_instrument && (_classData.getNumberOfValidLines() == 0) &&
			_logger.isWarnEnabled()) {

			_logger.warn(
				"No line number information found for class " +
					_classData.getName() +
						". Please recompile with debug info.");
		}
	}

	@Override
	public MethodVisitor visitMethod(
		int access, final String name, String desc, final String signature,
		String[] exceptions) {

		MethodVisitor methodVisitor = cv.visitMethod(
			access, name, desc, signature, exceptions);

		if (!_instrument || (methodVisitor == null)) {
			return methodVisitor;
		}

		return new FirstPassMethodInstrumenter(
			_classData, methodVisitor, _classData.getName(), access, name, desc,
			signature, exceptions);
	}

	@Override
	public void visitSource(String source, String debug) {
		super.visitSource(source, debug);

		_classData.setSourceFileName(source);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		CoberturaClassVisitor.class);

	private final ClassData _classData;;
	private boolean _instrument;

}