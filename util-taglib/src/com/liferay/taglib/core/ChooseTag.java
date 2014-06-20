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

import com.liferay.taglib.TagSupport;

/**
 * @author Shuyang Zhou
 */
public class ChooseTag extends TagSupport {

	public boolean canRun() {
		return !_ran;
	}

	@Override
	public int doStartTag() {
		_ran = false;

		return EVAL_BODY_INCLUDE;
	}

	public void markRan() {
		if (_ran) {
			throw new IllegalStateException("Another subtag has already run");
		}

		_ran = true;
	}

	@Override
	public void release() {
		super.release();

		_ran = false;
	}

	private boolean _ran;

}