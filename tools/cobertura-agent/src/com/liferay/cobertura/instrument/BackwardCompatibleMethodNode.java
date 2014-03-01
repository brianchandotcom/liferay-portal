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
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * At ASM SVN rev1641, a change was made to fix an infinite loops bug during
 * MethodNode clone. However it raised another issue made the
 * {@link MethodNode#getLabelNode(Label)} returned LabelNode lost the track of
 * the orginal input {@link Label}. This breaks cobertura's instrumentment.
 * <p>
 * {@link BackwardCompatibleMethodNode} class extends {@link MethodNode} and
 * override the {@link MethodNode#getLabelNode(Label)} to restore the old
 * behavior to overcome this issue.
 * <p>
 * Since cobertura does not do clone on MethodNode, this won't cause the
 * infinite loops problem.
 * <p>
 * For more detail see :
 * <p>
 * <a href="http://forge.ow2.org/tracker/?group_id=23&atid=100023&func=detail&aid=306920">
 * [ #306920 ] MethodNode.labelNode(label) smashes "user" data Label.info</a>
 * <p>
 * <a href="http://websvn.ow2.org/comp.php?repname=asm&compare[]=%2F@1640&compare[]=%2F@1641">
 * ASM SVN rev1641</a>
 *
 * @author Shuyang Zhou
 */
public class BackwardCompatibleMethodNode extends MethodNode {

	public BackwardCompatibleMethodNode(
		int access, String name, String desc, String signature,
		String[] exceptions) {

		super(access, name, desc, signature, exceptions);
	}

	@Override
	protected LabelNode getLabelNode(Label label) {
		if (label.info instanceof LabelNode) {
			return (LabelNode)label.info;
		}

		return new LabelNode(label);
	}

}