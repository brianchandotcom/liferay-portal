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

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.search.StatusSearchEntry;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * @author Eudaldo Alonso
 */
public class SearchContainerColumnStatusTag<R>
	extends SearchContainerColumnTag {

	@Override
	public int doEndTag() {
		try {
			SearchContainerRowTag<R> searchContainerRowTag =
				(SearchContainerRowTag<R>)findAncestorWithClass(
					this, SearchContainerRowTag.class);

			ResultRow resultRow = searchContainerRowTag.getRow();

			if ((_status == -1) && (resultRow.getObject() != null)) {
				if (Validator.isNull(_property)) {
					_status = (Integer)BeanPropertiesUtil.getObject(
						resultRow.getObject(), "status");
					_statusByUserId = (Long)BeanPropertiesUtil.getObject(
						resultRow.getObject(), "statusByUserId");
					_statusDate = (Date)BeanPropertiesUtil.getObject(
						resultRow.getObject(), "statusDate");
				}
				else {
					_status = (Integer)BeanPropertiesUtil.getObject(
						resultRow.getObject(), _property);
				}
			}

			if (index <= -1) {
				List<SearchEntry> searchEntries = resultRow.getEntries();

				index = searchEntries.size();
			}

			if (resultRow.isRestricted()) {
				_href = null;
			}

			StatusSearchEntry statusSearchEntry = new StatusSearchEntry();

			statusSearchEntry.setAlign(getAlign());
			statusSearchEntry.setColspan(getColspan());
			statusSearchEntry.setCssClass(getCssClass());
			statusSearchEntry.setRequest(
				(HttpServletRequest)pageContext.getRequest());
			statusSearchEntry.setResponse(
				(HttpServletResponse)pageContext.getResponse());

			ServletContext servletContext = ServletContextPool.get(
				PortalUtil.getServletContextName());

			statusSearchEntry.setServletContext(servletContext);

			statusSearchEntry.setStatus(_status);
			statusSearchEntry.setStatusByUserId(_statusByUserId);
			statusSearchEntry.setStatusDate(_statusDate);
			statusSearchEntry.setValign(getValign());

			resultRow.addSearchEntry(index, statusSearchEntry);

			return EVAL_PAGE;
		}
		finally {
			index = -1;
			_status = -1;
			_statusByUserId = -1;
			_statusDate = null;

			align = SearchEntry.DEFAULT_ALIGN;
			colspan = SearchEntry.DEFAULT_COLSPAN;
			cssClass = SearchEntry.DEFAULT_CSS_CLASS;
			_href = null;
			name = null;
			_orderable = false;
			_orderableProperty = null;
			_property = null;
			valign = SearchEntry.DEFAULT_VALIGN;
		}
	}

	@Override
	public int doStartTag() throws JspException {
		if (_orderable && Validator.isNull(_orderableProperty)) {
			_orderableProperty = name;
		}

		SearchContainerRowTag<R> searchContainerRowTag =
			(SearchContainerRowTag<R>)findAncestorWithClass(
				this, SearchContainerRowTag.class);

		if (searchContainerRowTag == null) {
			throw new JspTagException(
				"Requires liferay-ui:search-container-row");
		}

		if (!searchContainerRowTag.isHeaderNamesAssigned()) {
			List<String> headerNames = searchContainerRowTag.getHeaderNames();

			String name = getName();

			if (Validator.isNull(name) && Validator.isNotNull(_property)) {
				name = _property;
			}

			headerNames.add(name);

			if (_orderable) {
				Map<String, String> orderableHeaders =
					searchContainerRowTag.getOrderableHeaders();

				if (Validator.isNotNull(_orderableProperty)) {
					orderableHeaders.put(name, _orderableProperty);
				}
				else if (Validator.isNotNull(_property)) {
					orderableHeaders.put(name, _property);
				}
				else if (Validator.isNotNull(name)) {
					orderableHeaders.put(name, name);
				}
			}
		}

		return EVAL_BODY_INCLUDE;
	}

	public Object getHref() {
		if (_href instanceof PortletURL) {
			_href = _href.toString();
		}

		return _href;
	}

	public String getOrderableProperty() {
		return _orderableProperty;
	}

	public String getProperty() {
		return _property;
	}

	public int getStatus() {
		return _status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public boolean isOrderable() {
		return _orderable;
	}

	public void setHref(Object href) {
		_href = href;
	}

	public void setOrderable(boolean orderable) {
		_orderable = orderable;
	}

	public void setOrderableProperty(String orderableProperty) {
		_orderableProperty = orderableProperty;
	}

	public void setProperty(String property) {
		_property = property;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private Object _href;
	private boolean _orderable;
	private String _orderableProperty;
	private String _property;
	private int _status = -1;
	private long _statusByUserId;
	private Date _statusDate;

}