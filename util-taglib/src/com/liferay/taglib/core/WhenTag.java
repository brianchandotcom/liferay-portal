/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.core;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

/**
 * @author Shuyang Zhou
 */
public class WhenTag extends ConditionalTagSupport {

	@Override
	public int doStartTag() throws JspTagException {
		Tag parentTag = getParent();

		if (!(parentTag instanceof ChooseTag)) {
			throw new JspTagException(
				"The when tag must exist under a choose tag");
		}

		ChooseTag chooseTag = (ChooseTag)parentTag;

		if (!chooseTag.canRun()) {
			return SKIP_BODY;
		}

		if (condition()) {
			chooseTag.markRan();

			return EVAL_BODY_INCLUDE;
		}

		return SKIP_BODY;
	}

	@Override
	public void release() {
		super.release();

		_test = false;
	}

	public void setTest(boolean test) {
		_test = test;
	}

	@Override
	protected boolean condition() {
		return _test;
	}

	private boolean _test;

}