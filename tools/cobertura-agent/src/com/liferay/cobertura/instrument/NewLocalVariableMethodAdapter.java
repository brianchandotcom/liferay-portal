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

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Shuyang Zhou
 */
public class NewLocalVariableMethodAdapter extends MethodVisitor {

	public NewLocalVariableMethodAdapter(
		MethodVisitor methodVisitor, int access, String desc,
		int addedStackWords) {

		super(Opcodes.ASM4, methodVisitor);

		if ((Opcodes.ACC_STATIC & access) == 0) {
			firstStackVariable = 1;
		}

		for (Type type : Type.getArgumentTypes(desc)) {
			firstStackVariable += type.getSize();
		}

		this.addedStackWords = addedStackWords;
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		if (var >= firstStackVariable) {
			var += addedStackWords;
		}

		mv.visitIincInsn(var, increment);
	}

	@Override
	public void visitLocalVariable(
		String name, String desc, String signature, Label start, Label end,
		int index) {

		if (index >= firstStackVariable) {
			index += addedStackWords;
		}

		mv.visitLocalVariable(name, desc, signature, start, end, index);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		if (var >= firstStackVariable) {
			var += addedStackWords;
		}

		mv.visitVarInsn(opcode, var);
	}

	protected int addedStackWords;
	protected int firstStackVariable;

}