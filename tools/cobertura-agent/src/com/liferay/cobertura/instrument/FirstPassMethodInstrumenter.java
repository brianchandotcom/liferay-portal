
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sourceforge.cobertura.coveragedata.ClassData;
import net.sourceforge.cobertura.instrument.JumpHolder;
import net.sourceforge.cobertura.instrument.SwitchHolder;
import net.sourceforge.cobertura.util.RegexUtil;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import static org.objectweb.asm.Opcodes.GOTO;
import static org.objectweb.asm.Opcodes.JSR;
import org.objectweb.asm.tree.MethodNode;

/**
 * @author Shuyang Zhou
 */
public class FirstPassMethodInstrumenter extends MethodAdapter implements Opcodes
{

	private final String ownerClass;

	private String myName;

	private String myDescriptor;

	private int myAccess;
   
	private Collection ignoreRegexs;
   
	private Collection ignoreBranchesRegexs;

	private ClassData classData;

	private int currentLine;
   
	private int currentJump;
   
	private int currentSwitch;
	
	private Map jumpTargetLabels;

	private Map switchTargetLabels;
   
	private Map lineLabels;
   
	private MethodVisitor writerMethodVisitor;
   
	private MethodNode methodNode;

	public FirstPassMethodInstrumenter(ClassData classData, final MethodVisitor mv,
			final String owner, final int access, final String name, final String desc, 
			final String signature, final String[] exceptions, final Collection ignoreRegexs,
			final Collection ignoreBranchesRegexs)
	{
		super(new MethodNode(access, name, desc, signature, exceptions));
		writerMethodVisitor = mv;
		this.ownerClass = owner;
		this.methodNode = (MethodNode) this.mv;
		this.classData = classData;
		this.myAccess = access;
		this.myName = name;
		this.myDescriptor = desc;
		this.ignoreRegexs = ignoreRegexs;
		this.ignoreBranchesRegexs = ignoreBranchesRegexs;
		this.jumpTargetLabels = new HashMap();
		this.switchTargetLabels = new HashMap();
		this.lineLabels = new HashMap();
		this.currentLine = 0;
	}

	public void visitEnd() {
		super.visitEnd();

		methodNode.accept(lineLabels.isEmpty() ? writerMethodVisitor : new SecondPassMethodInstrumenter(this)); //when there is no line number info -> no instrumentation
	}

	public void visitJumpInsn(int opcode, Label label)
	{
		// Ignore any jump instructions in the "class init" method.
		// When initializing static variables, the JVM first checks
		// that the variable is null before attempting to set it.
		// This check contains an IFNONNULL jump instruction which
		// would confuse people if it showed up in the reports.
		if ((opcode != GOTO) && (opcode != JSR) && (currentLine != 0)
				&& (!this.myName.equals("<clinit>")))
		{
			classData.addLineJump(currentLine, currentJump);
			jumpTargetLabels.put(label, new JumpHolder(currentLine, currentJump++));
		}
		
		super.visitJumpInsn(opcode, label);
	}

	public void visitLineNumber(int line, Label start)
	{
		// Record initial information about this line of code
		currentLine = line;
		classData.addLine(currentLine, myName, myDescriptor);
		currentJump = 0;
		currentSwitch = 0;
      
		lineLabels.put(start, new Integer(line));

		//removed because the MethodNode doesn't reproduce visitLineNumber where they are but at the end of the file :-(( 
		//therefore we don't need them
		//We can directly instrument the visit line number here, but it is better to leave all instrumentation in the second pass
		//therefore we just collects what label is the line ...
		//super.visitLineNumber(line, start);
	}

	public void visitMethodInsn(int opcode, String owner, String name,
			String desc)
	{
		super.visitMethodInsn(opcode, owner, name, desc);

		// If any of the ignore patterns match this line
		// then remove it from our data
		if (RegexUtil.matches(ignoreRegexs, owner)) 
		{
			classData.removeLine(currentLine);
		}
	}

	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels)
	{
		super.visitLookupSwitchInsn(dflt, keys, labels);
      
		if (currentLine != 0)
		{
			switchTargetLabels.put(dflt, new SwitchHolder(currentLine, currentSwitch, -1)); 
			for (int i = labels.length -1; i >=0; i--)
				switchTargetLabels.put(labels[i], new SwitchHolder(currentLine, currentSwitch, i));
			classData.addLineSwitch(currentLine, currentSwitch++, keys);
		}
	}

	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels)
	{
		super.visitTableSwitchInsn(min, max, dflt, labels);
      
		if (currentLine != 0)
		{
			switchTargetLabels.put(dflt, new SwitchHolder(currentLine, currentSwitch, -1)); 
			for (int i = labels.length -1; i >=0; i--)
				switchTargetLabels.put(labels[i], new SwitchHolder(currentLine, currentSwitch, i));
			classData.addLineSwitch(currentLine, currentSwitch++, min, max);
		}
	}

	protected void removeLine(int lineNumber) 
	{
		classData.removeLine(lineNumber);
	}
   
	protected MethodVisitor getWriterMethodVisitor() 
	{
		return writerMethodVisitor;
	}

	protected Collection getIgnoreRegexs() 
	{
		return ignoreRegexs;
	}

	protected Map getJumpTargetLabels() 
	{
		return jumpTargetLabels;
	}

	protected Map getSwitchTargetLabels() 
	{
		return switchTargetLabels;
	}

	protected int getMyAccess() 
	{
		return myAccess;
	}

	protected String getMyDescriptor() 
	{
		return myDescriptor;
	}

	protected String getMyName() 
	{
		return myName;
	}

	protected String getOwnerClass() 
	{
		return ownerClass;
	}

	protected Map getLineLabels() 
	{
		return lineLabels;
	}

}
