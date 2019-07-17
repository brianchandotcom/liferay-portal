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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.QuickAccessEntry;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.BaseBodyTagSupport;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eudaldo Alonso
 */
public class QuickAccessEntryTag extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		try {
			return processEndTag();
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_label = null;
			_onClick = null;
			_url = null;
		}
	}

	public void setLabel(String label) {
		_label = LanguageUtil.get(
			TagResourceBundleUtil.getResourceBundle(pageContext), label);
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setUrl(String url) {
		_url = url;
	}

	protected String getEndPage() {
		return _END_PAGE;
	}

	protected String getStartPage() {
		return _START_PAGE;
	}

	protected int processEndTag() throws Exception {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		List<QuickAccessEntry> quickAccessEntries =
			(List<QuickAccessEntry>)httpServletRequest.getAttribute(
				WebKeys.PORTLET_QUICK_ACCESS_ENTRIES);

		if (quickAccessEntries == null) {
			quickAccessEntries = new ArrayList<>();

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_QUICK_ACCESS_ENTRIES, quickAccessEntries);
		}

		QuickAccessEntry quickAccessEntry = new QuickAccessEntry();

		quickAccessEntry.setBody(getBodyContentAsStringBundler());
		quickAccessEntry.setId(StringUtil.randomId());
		quickAccessEntry.setLabel(_label);
		quickAccessEntry.setOnClick(_onClick);
		quickAccessEntry.setURL(_url);

		quickAccessEntries.add(quickAccessEntry);

		return EVAL_PAGE;
	}

	private static final String _END_PAGE =
		"/html/taglib/ui/quick_access_entry/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/quick_access_entry/start.jsp";

	private String _label;
	private String _onClick;
	private String _url;

}