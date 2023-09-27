/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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