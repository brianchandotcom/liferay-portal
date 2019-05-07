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

package com.liferay.taglib.search;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class SearchEntry
	implements Cloneable, com.liferay.portal.kernel.dao.search.SearchEntry {

	@Override
	public String getAlign() {
		return _align;
	}

	@Override
	public int getColspan() {
		return _colspan;
	}

	@Override
	public String getCssClass() {
		return _cssClass;
	}

	@Override
	public int getIndex() {
		return _index;
	}

	@Override
	public String getValign() {
		return _valign;
	}

	@Override
	public boolean isTruncate() {
		return _truncate;
	}

	@Override
	public abstract void print(
			Writer writer, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception;

	@Override
	public void setAlign(String align) {
		_align = align;
	}

	@Override
	public void setColspan(int colspan) {
		_colspan = colspan;
	}

	@Override
	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	@Override
	public void setIndex(int index) {
		_index = index;
	}

	@Override
	public void setTruncate(boolean truncate) {
		_truncate = truncate;
	}

	@Override
	public void setValign(String valign) {
		_valign = valign;
	}

	private String _align = DEFAULT_ALIGN;
	private int _colspan = DEFAULT_COLSPAN;
	private String _cssClass = DEFAULT_CSS_CLASS;
	private int _index;
	private boolean _truncate;
	private String _valign = DEFAULT_VALIGN;

}