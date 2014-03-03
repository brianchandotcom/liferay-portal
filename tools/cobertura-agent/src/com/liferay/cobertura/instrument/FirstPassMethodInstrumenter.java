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

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.instrument.JumpHolder;
import net.sourceforge.cobertura.instrument.SwitchHolder;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Shuyang Zhou
 */
public class FirstPassMethodInstrumenter extends MethodVisitor {

	public FirstPassMethodInstrumenter(
		ClassData classData, MethodVisitor methodVisitor, String owner,
		int access, String name, String desc, String signature,
		String[] exceptions) {

		super(
			Opcodes.ASM4,
			new MethodNode(access, name, desc, signature, exceptions));

		_classData = classData;

		this.methodVisitor = methodVisitor;
		this.owner = owner;
		this.access = access;
		this.name = name;
		this.desc = desc;

		_methodNode = (MethodNode)mv;
	}

	@Override
	public void visitEnd() {
		super.visitEnd();

		MethodVisitor methodVisitor = this.methodVisitor;

		if (!lineLabels.isEmpty()) {
			methodVisitor = new SecondPassMethodInstrumenter(this);
		}

		_methodNode.accept(methodVisitor);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {

		// Ignore any jump instructions in the "class init" method. When
		// initializing static variables, the JVM first checks that the variable
		// is null before attempting to set it. This check contains an IFNONNULL
		// jump instruction which would confuse people if it showed up in the
		// reports.

		if (!name.equals("<clinit>") && (opcode != Opcodes.GOTO) &&
			(opcode != Opcodes.JSR) && (_currentLine != 0)) {

			_classData.addLineJump(_currentLine, _currentJump);

			jumpLabels.put(label, new JumpHolder(_currentLine, _currentJump++));
		}

		super.visitJumpInsn(opcode, label);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		_currentLine = line;

		_classData.addLine(_currentLine, name, desc);

		_currentJump = 0;
		_currentSwitch = 0;

		lineLabels.put(start, new Integer(line));
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		super.visitLookupSwitchInsn(dflt, keys, labels);

		if (_currentLine != 0) {
			switchLabels.put(
				dflt, new SwitchHolder(_currentLine, _currentSwitch, -1));

			for (int i = labels.length -1; i >= 0; i--) {
				switchLabels.put(
					labels[i],
					new SwitchHolder(_currentLine, _currentSwitch, i));
			}

			_classData.addLineSwitch(_currentLine, _currentSwitch++, keys);
		}
	}

	@Override
	public void visitTableSwitchInsn(
		int min, int max, Label dflt, Label... labels) {

		super.visitTableSwitchInsn(min, max, dflt, labels);

		if (_currentLine != 0) {
			switchLabels.put(
				dflt, new SwitchHolder(_currentLine, _currentSwitch, -1));

			for (int i = labels.length -1; i >= 0; i--) {
				switchLabels.put(
					labels[i],
					new SwitchHolder(_currentLine, _currentSwitch, i));
			}

			_classData.addLineSwitch(_currentLine, _currentSwitch++, min, max);
		}
	}

	protected int access;
	protected String desc;
	protected Map<Label, JumpHolder> jumpLabels =
		new HashMap<Label, JumpHolder>();
	protected Map<Label, Integer> lineLabels = new HashMap<Label, Integer>();
	protected MethodVisitor methodVisitor;
	protected String name;
	protected final String owner;
	protected Map<Label, SwitchHolder> switchLabels =
		new HashMap<Label, SwitchHolder>();

	private final ClassData _classData;
	private int _currentJump;
	private int _currentLine;
	private int _currentSwitch;
	private final MethodNode _methodNode;

}