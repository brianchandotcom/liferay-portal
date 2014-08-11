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

package com.liferay.taglib.util;

import com.liferay.kernel.servlet.taglib.ViewExtension;
import com.liferay.kernel.servlet.taglib.ViewExtensionUtil;
import com.liferay.taglib.TagSupport;

import java.util.List;

import javax.servlet.jsp.JspException;

/**
 * @author Carlos Sierra Andrés
 */
public class ExtensionTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		List<ViewExtension> viewExtensions =
			ViewExtensionUtil.getViewExtensions(getExtensionId());

		if (viewExtensions == null || viewExtensions.isEmpty()) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	public String getExtensionId() {
		return _extensionId;
	}

	public void setExtensionId(String extensionId) {
		_extensionId = extensionId;
	}

	private String _extensionId;

}