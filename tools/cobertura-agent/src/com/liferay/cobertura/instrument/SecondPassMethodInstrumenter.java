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

import java.util.Map;

import net.sourceforge.cobertura.instrument.JumpHolder;
import net.sourceforge.cobertura.instrument.SwitchHolder;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Shuyang Zhou
 */
public class SecondPassMethodInstrumenter extends MethodVisitor {

	public SecondPassMethodInstrumenter(
		FirstPassMethodInstrumenter firstPassMethodInstrumenter) {

		super(Opcodes.ASM4, firstPassMethodInstrumenter.methodVisitor);

		int stackVariableCount = 0;

		if ((Opcodes.ACC_STATIC & firstPassMethodInstrumenter.access) == 0) {
			stackVariableCount++;
		}

		for (Type type : Type.getArgumentTypes(
				firstPassMethodInstrumenter.desc)) {

			stackVariableCount += type.getSize();
		}

		_stackVariableCount = stackVariableCount;

		_firstPassMethodInstrumenter = firstPassMethodInstrumenter;
	}

	@Override
	public void visitCode() {
		_methodStarted = true;

		super.visitCode();
	}

	@Override
	public void visitFieldInsn(
		int opcode, String owner, String name, String desc) {

		touchBranchFalse();

		super.visitFieldInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		touchBranchFalse();

		if (var >= _stackVariableCount) {
			var += 2;
		}

		mv.visitIincInsn(var, increment);
	}

	@Override
	public void visitInsn(int opcode) {
		touchBranchFalse();

		super.visitInsn(opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		touchBranchFalse();

		super.visitIntInsn(opcode, operand);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		touchBranchFalse();

		// Ignore any jump instructions in the "class init" method. When
		// initializing static variables, the JVM first checks that the variable
		// is null before attempting to set it. This check contains an IFNONNULL
		// jump instruction which would confuse people if it showed up in the
		// reports.

		if (!"<clinit>".equals(_firstPassMethodInstrumenter.name) &&
			(opcode != Opcodes.GOTO) && (opcode != Opcodes.JSR) &&
			(_currentLine != 0)) {

			_lastJump = new JumpHolder(_currentLine, _currentJump++);

			mv.visitIntInsn(Opcodes.SIPUSH, _currentLine);
			mv.visitVarInsn(Opcodes.ISTORE, _variableIndex);
			mv.visitIntInsn(Opcodes.SIPUSH, _lastJump.getJumpNumber());
			mv.visitVarInsn(Opcodes.ISTORE, _variableIndex + 1);
		}

		super.visitJumpInsn(opcode, label);
	}

	@Override
	public void visitLabel(Label label) {
		if (_methodStarted) {
			_methodStarted = false;
			_variableIndex = _stackVariableCount;

			mv.visitInsn(Opcodes.ICONST_0);
			mv.visitVarInsn(Opcodes.ISTORE, _variableIndex);
			mv.visitIntInsn(Opcodes.SIPUSH, -1);
			mv.visitVarInsn(Opcodes.ISTORE, _variableIndex + 1);

			_startLabel = label;
		}

		_endLabel = label;

		super.visitLabel(label);

		Map<Label, JumpHolder> jumpLables =
			_firstPassMethodInstrumenter.jumpLabels;

		if (jumpLables.containsKey(label)) {
			if (_lastJump != null) {
				Label label1 = instrumentIsLastJump();

				instrumentOwnerClass();
				instrumentPutLineAndBranchNumbers();

				mv.visitInsn(_BOOLEAN_FALSE);

				instrumentInvokeTouchJump();

				Label label2 = new Label();

				mv.visitJumpInsn(Opcodes.GOTO, label2);
				mv.visitLabel(label1);
				mv.visitVarInsn(Opcodes.ILOAD, _variableIndex + 1);
				mv.visitJumpInsn(Opcodes.IFLT, label2);

				instrumentOwnerClass();
				instrumentPutLineAndBranchNumbers();

				mv.visitInsn(_BOOLEAN_TRUE);

				instrumentInvokeTouchJump();

				mv.visitLabel(label2);
			}
			else {
				mv.visitVarInsn(Opcodes.ILOAD, _variableIndex + 1);

				Label label1 = new Label();

				mv.visitJumpInsn(Opcodes.IFLT, label1);

				instrumentJumpHit(true);

				mv.visitLabel(label1);
			}
		}
		else if (_lastJump != null) {
			Label label1 = instrumentIsLastJump();

			instrumentJumpHit(false);

			mv.visitLabel(label1);
		}

		_lastJump = null;

		Map<Label, SwitchHolder> switchLabels =
			_firstPassMethodInstrumenter.switchLabels;

		SwitchHolder switchHolder = switchLabels.get(label);

		if (switchHolder != null) {
			instrumentSwitchHit(
				switchHolder.getLineNumber(), switchHolder.getSwitchNumber(),
				switchHolder.getBranch());
		}

		Map<Label, Integer> lineLabels =
			_firstPassMethodInstrumenter.lineLabels;

		Integer line = lineLabels.get(label);

		if (line != null) {
			visitLineNumber(line.intValue(), label);
		}
	}

	@Override
	public void visitLdcInsn(Object cst) {
		touchBranchFalse();

		super.visitLdcInsn(cst);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		_currentLine = line;
		_currentJump = 0;

		instrumentOwnerClass();

		mv.visitIntInsn(Opcodes.SIPUSH, line);
		mv.visitMethodInsn(
			Opcodes.INVOKESTATIC, _TOUCH_COLLECTOR_CLASS, "touch",
			"(Ljava/lang/String;I)V");

		super.visitLineNumber(line, start);
	}

	@Override
	public void visitLocalVariable(
		String name, String desc, String signature, Label start, Label end,
		int index) {

		if (index >= _stackVariableCount) {
			index += 2;
		}

		mv.visitLocalVariable(name, desc, signature, start, end, index);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		touchBranchFalse();

		super.visitLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		mv.visitLocalVariable(
			"__cobertura__line__number__", "I", null, _startLabel, _endLabel,
			_variableIndex);

		mv.visitLocalVariable(
			"__cobertura__branch__number__", "I", null, _startLabel, _endLabel,
			_variableIndex + 1);

		super.visitMaxs(maxStack, maxLocals);
	}

	@Override
	public void visitMethodInsn(
		int opcode, String owner, String name, String desc) {

		touchBranchFalse();

		super.visitMethodInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitMultiANewArrayInsn(String desc, int dims) {
		touchBranchFalse();

		super.visitMultiANewArrayInsn(desc, dims);
	}

	@Override
	public void visitTableSwitchInsn(
		int min, int max, Label dflt, Label... labels) {

		touchBranchFalse();

		super.visitTableSwitchInsn(min, max, dflt, labels);
	}

	@Override
	public void visitTryCatchBlock(
		Label start, Label end, Label handler, String type) {

		touchBranchFalse();

		super.visitTryCatchBlock(start, end, handler, type);
	}

	@Override
	public void visitTypeInsn(int opcode, String desc) {
		touchBranchFalse();

		super.visitTypeInsn(opcode, desc);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		touchBranchFalse();

		if (var >= _stackVariableCount) {
			var += 2;
		}

		mv.visitVarInsn(opcode, var);
	}

	private void instrumentInvokeTouchJump() {
		mv.visitMethodInsn(
			Opcodes.INVOKESTATIC, _TOUCH_COLLECTOR_CLASS, "touchJump",
			"(Ljava/lang/String;IIZ)V");

		mv.visitIntInsn(Opcodes.SIPUSH, -1);
		mv.visitVarInsn(Opcodes.ISTORE, _variableIndex + 1);
	}

	private void instrumentInvokeTouchSwitch() {
		mv.visitMethodInsn(
			Opcodes.INVOKESTATIC, _TOUCH_COLLECTOR_CLASS, "touchSwitch",
			"(Ljava/lang/String;III)V");
	}

	private Label instrumentIsLastJump() {
		mv.visitVarInsn(Opcodes.ILOAD, _variableIndex);
		mv.visitIntInsn(Opcodes.SIPUSH, _lastJump.getLineNumber());

		Label label = new Label();

		mv.visitJumpInsn(Opcodes.IF_ICMPNE, label);
		mv.visitVarInsn(Opcodes.ILOAD, _variableIndex + 1);
		mv.visitIntInsn(Opcodes.SIPUSH, _lastJump.getJumpNumber());
		mv.visitJumpInsn(Opcodes.IF_ICMPNE, label);

		return label;
	}

	private void instrumentJumpHit(boolean branch) {
		instrumentOwnerClass();

		instrumentPutLineAndBranchNumbers();

		mv.visitInsn(branch ? _BOOLEAN_TRUE : _BOOLEAN_FALSE);

		instrumentInvokeTouchJump();
	}

	private void instrumentOwnerClass() {
		mv.visitLdcInsn(_firstPassMethodInstrumenter.owner);
	}

	private void instrumentPutLineAndBranchNumbers() {
		mv.visitVarInsn(Opcodes.ILOAD, _variableIndex);
		mv.visitVarInsn(Opcodes.ILOAD, _variableIndex + 1);
	}

	private void instrumentSwitchHit(
		int lineNumber, int switchNumber, int branch) {

		instrumentOwnerClass();

		mv.visitIntInsn(Opcodes.SIPUSH, lineNumber);
		mv.visitIntInsn(Opcodes.SIPUSH, switchNumber);
		mv.visitIntInsn(Opcodes.SIPUSH, branch);

		instrumentInvokeTouchSwitch();
	}

	private void touchBranchFalse() {
		if (_lastJump != null) {
			_lastJump = null;
			instrumentJumpHit(false);
		}
	}

	private static final int _BOOLEAN_FALSE = Opcodes.ICONST_1;

	private static final int _BOOLEAN_TRUE = Opcodes.ICONST_0;

	private static final String _TOUCH_COLLECTOR_CLASS =
		"net/sourceforge/cobertura/coveragedata/TouchCollector";

	private int _currentJump;
	private int _currentLine;
	private Label _endLabel;
	private final FirstPassMethodInstrumenter _firstPassMethodInstrumenter;
	private JumpHolder _lastJump;
	private boolean _methodStarted;
	private final int _stackVariableCount;
	private Label _startLabel;
	private int _variableIndex;

}