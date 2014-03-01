
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

import net.sourceforge.cobertura.instrument.JumpHolder;
import net.sourceforge.cobertura.instrument.SwitchHolder;
import net.sourceforge.cobertura.util.RegexUtil;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFLT;
import static org.objectweb.asm.Opcodes.IF_ICMPNE;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.JSR;
import static org.objectweb.asm.Opcodes.SIPUSH;

/**
 *
 * @author Shuyang Zhou
 */
public class SecondPassMethodInstrumenter extends NewLocalVariableMethodAdapter implements Opcodes
{
	private String TOUCH_COLLECTOR_CLASS="net/sourceforge/cobertura/coveragedata/TouchCollector";
	
	private int currentLine;
   
	private int currentJump;
	
	private boolean methodStarted;
	
	private int myVariableIndex;

	private Label startLabel;
	
	private Label endLabel;
	
	private JumpHolder lastJump;
   
	private FirstPassMethodInstrumenter firstPass;
	
	private static final int BOOLEAN_TRUE = ICONST_0;
	private static final int BOOLEAN_FALSE = ICONST_1;

	public SecondPassMethodInstrumenter(FirstPassMethodInstrumenter firstPass)
	{
		super(firstPass.getWriterMethodVisitor(), firstPass.getMyAccess(), firstPass.getMyDescriptor(), 2);
		this.firstPass = firstPass;
		this.currentLine = 0;
	}

	public void visitJumpInsn(int opcode, Label label)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		// Ignore any jump instructions in the "class init" method.
		// When initializing static variables, the JVM first checks
		// that the variable is null before attempting to set it.
		// This check contains an IFNONNULL jump instruction which
		// would confuse people if it showed up in the reports.
		if ((opcode != GOTO) && (opcode != JSR) && (currentLine != 0)
				&& (!this.firstPass.getMyName().equals("<clinit>")))
		{
			lastJump = new JumpHolder(currentLine, currentJump++); 
			mv.visitIntInsn(SIPUSH, currentLine);
			mv.visitVarInsn(ISTORE, myVariableIndex);
			mv.visitIntInsn(SIPUSH, lastJump.getJumpNumber());
			mv.visitVarInsn(ISTORE, myVariableIndex + 1);
		}
		
		super.visitJumpInsn(opcode, label);
	}

	public void visitLineNumber(int line, Label start)
	{
		// Record initial information about this line of code
		currentLine = line;
		currentJump = 0;

		instrumentOwnerClass();

		// Mark the current line number as covered:
		// classData.touch(line)
		mv.visitIntInsn(SIPUSH, line);
		mv.visitMethodInsn(INVOKESTATIC,
				TOUCH_COLLECTOR_CLASS, "touch",
				"(Ljava/lang/String;I)V");

		super.visitLineNumber(line, start);
	}

	public void visitMethodInsn(int opcode, String owner, String name,
			String desc)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitMethodInsn(opcode, owner, name, desc);

		// If any of the ignore patterns match this line
		// then remove it from our data
		if (RegexUtil.matches(firstPass.getIgnoreRegexs(), owner)) 
		{
			firstPass.removeLine(currentLine);
		}
	}

	public void visitFieldInsn(int opcode, String owner, String name, String desc)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitFieldInsn(opcode, owner, name, desc);
	}

	public void visitIincInsn(int var, int increment)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitIincInsn(var, increment);
	}

	public void visitInsn(int opcode)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitInsn(opcode);
	}

	public void visitIntInsn(int opcode, int operand)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitIntInsn(opcode, operand);
	}

	public void visitLabel(Label label)
	{
		//When this is the first method's label ... create the 2 new local variables (lineNumber and branchNumber)
		if (methodStarted) 
		{
			methodStarted = false;
			myVariableIndex = getFirstStackVariable();
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, myVariableIndex);
			mv.visitIntInsn(SIPUSH, -1); 
			mv.visitVarInsn(ISTORE, myVariableIndex + 1);
			startLabel = label;
		}
		//to have the last label for visitLocalVariable
		endLabel = label;
		
		super.visitLabel(label);
		
		//instrument the branch coverage collection
		if (firstPass.getJumpTargetLabels().keySet().contains(label)) 
		{ //this label is the true branch label
			if (lastJump != null) 
			{ //this is also label after jump - we have to check the branch number whether this is the true or false branch
				Label newLabelX = instrumentIsLastJump();
				instrumentOwnerClass();
				instrumentPutLineAndBranchNumbers();
				mv.visitInsn(BOOLEAN_FALSE);
				instrumentInvokeTouchJump();
				Label newLabelY = new Label();
				mv.visitJumpInsn(GOTO, newLabelY);
				mv.visitLabel(newLabelX);
				mv.visitVarInsn(ILOAD, myVariableIndex + 1);
				mv.visitJumpInsn(IFLT, newLabelY);
				instrumentOwnerClass();
				instrumentPutLineAndBranchNumbers();
				mv.visitInsn(BOOLEAN_TRUE);
				instrumentInvokeTouchJump();
				mv.visitLabel(newLabelY);
			}
			else
			{ //just hit te true branch
				//just check whether the jump has been invoked or the label has been touched other way 
				mv.visitVarInsn(ILOAD, myVariableIndex + 1);
				Label newLabelX = new Label();
				mv.visitJumpInsn(IFLT, newLabelX);
				instrumentJumpHit(true);
				mv.visitLabel(newLabelX);
			}
		} 
		else if (lastJump != null) 
		{ //this is "only" after jump label, hit the false branch only if the lastJump is same as stored stack lineNumber and jumpNumber
			Label newLabelX = instrumentIsLastJump();
			instrumentJumpHit(false); 
			mv.visitLabel(newLabelX);
		}
		lastJump = null;
		
		SwitchHolder sh = (SwitchHolder) firstPass.getSwitchTargetLabels().get(label);
		if (sh != null)
		{
			instrumentSwitchHit(sh.getLineNumber(), sh.getSwitchNumber(), sh.getBranch());
		}
		
		//we have to manually invoke the visitLineNumber because of not correct MedthodNode's handling
		Integer line = (Integer) firstPass.getLineLabels().get(label);
		if (line != null) {
			visitLineNumber(line.intValue(), label);
		}
	}

	public void visitLdcInsn(Object cst)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitLdcInsn(cst);
	}

	public void visitMultiANewArrayInsn(String desc, int dims)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitMultiANewArrayInsn(desc, dims);
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitLookupSwitchInsn(dflt, keys, labels);
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitTableSwitchInsn(min, max, dflt, labels);
	}

	public void visitTryCatchBlock(Label start, Label end, Label handler, String type)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitTryCatchBlock(start, end, handler, type);
	}

	public void visitTypeInsn(int opcode, String desc)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		super.visitTypeInsn(opcode, desc);
	}

	public void visitVarInsn(int opcode, int var)
	{
		//to touch the previous branch (when there is such)
		touchBranchFalse();
		
		//this is to change the variable instructions to conform to 2 new variables
		super.visitVarInsn(opcode, var);
	}

	public void visitCode()
	{
		methodStarted = true;
		super.visitCode();
	}
	
	private void touchBranchFalse() {
		if (lastJump != null) {
			lastJump = null;
			instrumentJumpHit(false);
		}
	}

	private void instrumentOwnerClass()
	{
		// OwnerClass is the name of the class being instrumented
		mv.visitLdcInsn(firstPass.getOwnerClass());
	}
	
	private void instrumentSwitchHit(int lineNumber, int switchNumber, int branch)
	{
		instrumentOwnerClass();
		
		//Invoke the touchSwitch(lineNumber, switchNumber, branch)
		mv.visitIntInsn(SIPUSH, lineNumber);
		mv.visitIntInsn(SIPUSH, switchNumber);
		mv.visitIntInsn(SIPUSH, branch);
		instrumentInvokeTouchSwitch();
	}
	
	private void instrumentJumpHit(boolean branch)
	{
		instrumentOwnerClass();
		
		//Invoke the touchJump(lineNumber, branchNumber, branch)
		instrumentPutLineAndBranchNumbers();
		mv.visitInsn(branch ? BOOLEAN_TRUE : BOOLEAN_FALSE);
		instrumentInvokeTouchJump();
	}

	private void instrumentInvokeTouchJump()
	{
		mv.visitMethodInsn(INVOKESTATIC, TOUCH_COLLECTOR_CLASS, "touchJump", "(Ljava/lang/String;IIZ)V");
		mv.visitIntInsn(SIPUSH, -1); //is important to reset current branch, because we have to know that the branch info on stack has already been used and can't be used
		mv.visitVarInsn(ISTORE, myVariableIndex + 1);
	}

	private void instrumentInvokeTouchSwitch()
	{
		mv.visitMethodInsn(INVOKESTATIC, TOUCH_COLLECTOR_CLASS, "touchSwitch", "(Ljava/lang/String;III)V");
	}

	private void instrumentPutLineAndBranchNumbers()
	{
		mv.visitVarInsn(ILOAD, myVariableIndex);
		mv.visitVarInsn(ILOAD, myVariableIndex + 1);
	}

	private Label instrumentIsLastJump() {
		mv.visitVarInsn(ILOAD, myVariableIndex);
		mv.visitIntInsn(SIPUSH, lastJump.getLineNumber());
		Label newLabelX = new Label();
		mv.visitJumpInsn(IF_ICMPNE, newLabelX);
		mv.visitVarInsn(ILOAD, myVariableIndex + 1);
		mv.visitIntInsn(SIPUSH, lastJump.getJumpNumber());
		mv.visitJumpInsn(IF_ICMPNE, newLabelX);
		return newLabelX;
	}

	public void visitMaxs(int maxStack, int maxLocals)
	{
		mv.visitLocalVariable("__cobertura__line__number__", "I", null, startLabel, endLabel, myVariableIndex);
		mv.visitLocalVariable("__cobertura__branch__number__", "I", null, startLabel, endLabel, myVariableIndex + 1);
		super.visitMaxs(maxStack, maxLocals);
	}

}