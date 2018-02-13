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

package com.liferay.taglib.ui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import javax.servlet.jsp.JspException;

/**
 * @author Raymond Augé
 */
public abstract class SearchContainerColumnTag
	extends ParamAndPropertyAncestorTagImpl {

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	public String getAlign() {
		return align;
	}

	public int getColspan() {
		return colspan;
	}

	public String getCssClass() {
		return cssClass;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public boolean getTruncate() {
		return truncate;
	}

	public String getValign() {
		return valign;
	}

	public void setAlign(String align) {
		this.align = align;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTruncate(boolean truncate) {
		this.truncate = truncate;
	}

	public void setValign(String valign) {
		this.valign = valign;
	}

	protected String align = SearchEntry.DEFAULT_ALIGN;
	protected int colspan = SearchEntry.DEFAULT_COLSPAN;
	protected String cssClass = SearchEntry.DEFAULT_CSS_CLASS;
	protected int index = -1;
	protected String name = StringPool.BLANK;
	protected boolean truncate;
	protected String valign = SearchEntry.DEFAULT_VALIGN;

}