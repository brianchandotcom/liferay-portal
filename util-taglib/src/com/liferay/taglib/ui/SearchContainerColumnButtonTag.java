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

import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.search.ButtonSearchEntry;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.List;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * @author     Raymond Augé
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class SearchContainerColumnButtonTag<R>
	extends SearchContainerColumnTag {

	@Override
	public int doEndTag() {
		try {
			SearchContainerRowTag<R> searchContainerRowTag =
				(SearchContainerRowTag<R>)findAncestorWithClass(
					this, SearchContainerRowTag.class);

			ResultRow resultRow = searchContainerRowTag.getRow();

			if (index <= -1) {
				List<SearchEntry> searchEntries = resultRow.getEntries();

				index = searchEntries.size();
			}

			ButtonSearchEntry buttonSearchEntry = new ButtonSearchEntry();

			buttonSearchEntry.setAlign(getAlign());
			buttonSearchEntry.setColspan(getColspan());
			buttonSearchEntry.setCssClass(getCssClass());
			buttonSearchEntry.setHref(String.valueOf(getHref()));

			ResourceBundle resourceBundle =
				TagResourceBundleUtil.getResourceBundle(pageContext);

			buttonSearchEntry.setName(
				LanguageUtil.get(resourceBundle, getName()));

			buttonSearchEntry.setValign(getValign());

			resultRow.addSearchEntry(index, buttonSearchEntry);

			return EVAL_PAGE;
		}
		finally {
			index = -1;

			if (!ServerDetector.isResin()) {
				align = SearchEntry.DEFAULT_ALIGN;
				colspan = SearchEntry.DEFAULT_COLSPAN;
				cssClass = SearchEntry.DEFAULT_CSS_CLASS;
				_href = null;
				name = StringPool.BLANK;
				valign = SearchEntry.DEFAULT_VALIGN;
			}
		}
	}

	@Override
	public int doStartTag() throws JspException {
		SearchContainerRowTag<R> searchContainerRowTag =
			(SearchContainerRowTag<R>)findAncestorWithClass(
				this, SearchContainerRowTag.class);

		if (searchContainerRowTag == null) {
			throw new JspTagException(
				"Requires liferay-ui:search-container-row");
		}

		if (!searchContainerRowTag.isHeaderNamesAssigned()) {
			List<String> headerNames = searchContainerRowTag.getHeaderNames();

			headerNames.add(StringPool.BLANK);
		}

		return EVAL_BODY_INCLUDE;
	}

	public Object getHref() {
		if (_href instanceof PortletURL) {
			_href = _href.toString();
		}

		return _href;
	}

	public void setHref(Object href) {
		_href = href;
	}

	private Object _href;

}