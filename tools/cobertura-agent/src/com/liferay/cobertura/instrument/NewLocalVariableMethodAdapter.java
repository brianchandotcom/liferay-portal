
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
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import org.objectweb.asm.Type;

/**
 *
 * @author Shuyang Zhou
 */
public class NewLocalVariableMethodAdapter extends MethodAdapter implements Opcodes
{
	protected int firstStackVariable;
	protected int addedStackWords;

	public NewLocalVariableMethodAdapter(MethodVisitor mv, int access, String desc, int addedStackWords)
	{
		super(mv);
		Type[] args = Type.getArgumentTypes(desc);
		firstStackVariable = ((ACC_STATIC & access) != 0) ? 0 : 1;
		for (int i = 0; i < args.length; i++) {
			firstStackVariable += args[i].getSize();
		}
		this.addedStackWords = addedStackWords;
	}
	
	public void visitVarInsn(int opcode, int var) 
	{
		mv.visitVarInsn(opcode, (var >= firstStackVariable) ? var + addedStackWords : var);
	}

	public void visitIincInsn(int var, int increment) {
		mv.visitIincInsn((var >= firstStackVariable) ? var + addedStackWords : var, increment);
	}

	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index)
	{
		mv.visitLocalVariable(name, desc, signature, start, end, (index >= firstStackVariable) ? index + addedStackWords : index);
	}

	public int getAddedStackWords()
	{
		return addedStackWords;
	}

	public int getFirstStackVariable()
	{
		return firstStackVariable;
	}

}