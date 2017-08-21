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

package com.liferay.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * <p>
 * See https://issues.liferay.com/browse/LPS-13878.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class TagSupport implements DirectTag, Tag {

	public static Tag findAncestorWithClass(Tag fromTag, Class<?> clazz) {
		if ((fromTag == null) || (clazz == null) ||
			(!Tag.class.isAssignableFrom(clazz) && !clazz.isInterface())) {

			return null;
		}

		while (true) {
			Tag parentTag = fromTag.getParent();

			if (parentTag == null) {
				return null;
			}

			if (clazz.isInstance(parentTag)) {
				return parentTag;
			}

			fromTag = parentTag;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	@Override
	public Tag getParent() {
		return _parent;
	}

	@Override
	public void release() {
		_parent = null;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public void setParent(Tag tag) {
		_parent = tag;
	}

	protected PageContext pageContext;

	private Tag _parent;

}