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

import com.liferay.portal.kernel.dao.search.SearchEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class ResultRow
	implements com.liferay.portal.kernel.dao.search.ResultRow {

	public ResultRow(Object obj, long primaryKey, int pos) {
		this(obj, String.valueOf(primaryKey), pos);
	}

	public ResultRow(Object obj, long primaryKey, int pos, boolean bold) {
		this(obj, String.valueOf(primaryKey), pos, bold);
	}

	public ResultRow(Object obj, String primaryKey, int pos) {
		this(obj, primaryKey, pos, false);
	}

	public ResultRow(Object obj, String primaryKey, int pos, boolean bold) {
		this(String.valueOf(pos + 1), obj, primaryKey, pos, bold);
	}

	public ResultRow(
		String rowId, Object obj, String primaryKey, int pos, boolean bold) {

		_rowId = rowId;
		_obj = obj;
		_primaryKey = primaryKey;
		_pos = pos;
		_bold = bold;
		_searchEntries = new ArrayList<SearchEntry>();
	}

	@Override
	public void addButton(int index, String name, String href) {
		addButton(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, name, href);
	}

	@Override
	public void addButton(
		int index, String align, String valign, int colspan, String name,
		String href) {

		if (_restricted) {
			href = null;
		}

		ButtonSearchEntry buttonSearchEntry = new ButtonSearchEntry();

		buttonSearchEntry.setAlign(align);
		buttonSearchEntry.setColspan(colspan);
		buttonSearchEntry.setHref(href);
		buttonSearchEntry.setName(name);
		buttonSearchEntry.setValign(valign);

		_searchEntries.add(index, buttonSearchEntry);
	}

	@Override
	public void addButton(String name, String href) {
		addButton(_searchEntries.size(), name, href);
	}

	@Override
	public void addButton(
		String align, String valign, int colspan, String name, String href) {

		addButton(_searchEntries.size(), align, valign, colspan, name, href);
	}

	@Override
	public void addButton(
		String align, String valign, String name, String href) {

		addButton(
			_searchEntries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			name, href);
	}

	@Override
	public void addDate(Date date) {
		addDate(_searchEntries.size(), date, null);
	}

	@Override
	public void addDate(Date date, PortletURL portletURL) {
		if (portletURL != null) {
			addDate(_searchEntries.size(), date, portletURL.toString());
		}
		else {
			addDate(_searchEntries.size(), date, null);
		}
	}

	@Override
	public void addDate(Date date, String href) {
		addDate(_searchEntries.size(), date, null);
	}

	@Override
	public void addDate(int index, Date date, String href) {
		DateSearchEntry dateSearchEntry = new DateSearchEntry();

		dateSearchEntry.setAlign(SearchEntry.DEFAULT_ALIGN);
		dateSearchEntry.setColspan(SearchEntry.DEFAULT_COLSPAN);
		dateSearchEntry.setDate(date);
		dateSearchEntry.setHref(href);
		dateSearchEntry.setValign(SearchEntry.DEFAULT_VALIGN);

		_searchEntries.add(index, dateSearchEntry);
	}

	@Override
	public void addJSP(int index, String path) {
		addJSP(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, path);
	}

	@Override
	public void addJSP(
		int index, String path, ServletContext servletContext,
		HttpServletRequest request, HttpServletResponse response) {

		addJSP(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, path, servletContext, request,
			response);
	}

	@Override
	public void addJSP(
		int index, String align, String valign, int colspan, String path) {

		JSPSearchEntry jspSearchEntry = new JSPSearchEntry();

		jspSearchEntry.setAlign(align);
		jspSearchEntry.setColspan(colspan);
		jspSearchEntry.setPath(path);
		jspSearchEntry.setValign(valign);

		_searchEntries.add(index, jspSearchEntry);
	}

	@Override
	public void addJSP(
		int index, String align, String valign, int colspan, String path,
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response) {

		JSPSearchEntry jspSearchEntry = new JSPSearchEntry();

		jspSearchEntry.setAlign(align);
		jspSearchEntry.setColspan(colspan);
		jspSearchEntry.setPath(path);
		jspSearchEntry.setRequest(request);
		jspSearchEntry.setResponse(response);
		jspSearchEntry.setServletContext(servletContext);
		jspSearchEntry.setValign(valign);

		_searchEntries.add(index, jspSearchEntry);
	}

	@Override
	public void addJSP(String path) {
		addJSP(_searchEntries.size(), path);
	}

	@Override
	public void addJSP(
		String path, ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response) {

		addJSP(_searchEntries.size(), path, servletContext, request, response);
	}

	@Override
	public void addJSP(String path, String cssClass) {
		JSPSearchEntry jspSearchEntry = new JSPSearchEntry();

		jspSearchEntry.setAlign(SearchEntry.DEFAULT_ALIGN);
		jspSearchEntry.setColspan(SearchEntry.DEFAULT_COLSPAN);
		jspSearchEntry.setCssClass(cssClass);
		jspSearchEntry.setPath(path);
		jspSearchEntry.setValign(SearchEntry.DEFAULT_VALIGN);

		_searchEntries.add(_searchEntries.size(), jspSearchEntry);
	}

	@Override
	public void addJSP(String align, String valign, int colspan, String path) {
		addJSP(_searchEntries.size(), align, valign, colspan, path);
	}

	@Override
	public void addJSP(
		String align, String valign, int colspan, String path,
		ServletContext servletContext, HttpServletRequest request,
		HttpServletResponse response) {

		addJSP(
			_searchEntries.size(), align, valign, colspan, path, servletContext,
			request, response);
	}

	@Override
	public void addJSP(String align, String valign, String path) {
		addJSP(
			_searchEntries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			path);
	}

	@Override
	public void addJSP(
		String align, String valign, String path, ServletContext servletContext,
		HttpServletRequest request, HttpServletResponse response) {

		addJSP(
			_searchEntries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			path, servletContext, request, response);
	}

	@Override
	public void addSearchEntry(int index, SearchEntry searchEntry) {
		_searchEntries.add(index, searchEntry);
	}

	@Override
	public void addSearchEntry(SearchEntry searchEntry) {
		_searchEntries.add(searchEntry);
	}

	@Override
	public void addStatus(int status) {
		addStatus(_searchEntries.size(), status, 0, null, null);
	}

	@Override
	public void addStatus(
		int index, int status, long statusByUserId, Date statusDate,
		String href) {

		StatusSearchEntry statusSearchEntry = new StatusSearchEntry();

		statusSearchEntry.setAlign(SearchEntry.DEFAULT_ALIGN);
		statusSearchEntry.setColspan(SearchEntry.DEFAULT_COLSPAN);
		statusSearchEntry.setHref(href);
		statusSearchEntry.setStatus(status);
		statusSearchEntry.setStatusDate(statusDate);
		statusSearchEntry.setStatusByUserId(statusByUserId);
		statusSearchEntry.setValign(SearchEntry.DEFAULT_VALIGN);

		_searchEntries.add(index, statusSearchEntry);
	}

	@Override
	public void addStatus(
		int index, int status, String href, ServletContext servletContext,
		HttpServletRequest request, HttpServletResponse response) {

		StatusSearchEntry statusSearchEntry = new StatusSearchEntry();

		statusSearchEntry.setAlign(SearchEntry.DEFAULT_ALIGN);
		statusSearchEntry.setColspan(SearchEntry.DEFAULT_COLSPAN);
		statusSearchEntry.setHref(href);
		statusSearchEntry.setRequest(request);
		statusSearchEntry.setResponse(response);
		statusSearchEntry.setServletContext(servletContext);
		statusSearchEntry.setStatus(status);
		statusSearchEntry.setValign(SearchEntry.DEFAULT_VALIGN);

		_searchEntries.add(index, statusSearchEntry);
	}

	@Override
	public void addStatus(int status, long statusByUserId, Date statusDate) {
		addStatus(
			_searchEntries.size(), status, statusByUserId, statusDate, null);
	}

	@Override
	public void addStatus(
		int status, long statusByUserId, Date statusDate,
		PortletURL portletURL) {

		if (portletURL != null) {
			addStatus(
				_searchEntries.size(), status, statusByUserId, statusDate,
				portletURL.toString());
		}
		else {
			addStatus(
				_searchEntries.size(), status, statusByUserId, statusDate,
				null);
		}
	}

	@Override
	public void addStatus(
		int status, long statusByUserId, Date statusDate, String href) {

		addStatus(
			_searchEntries.size(), status, statusByUserId, statusDate, href);
	}

	@Override
	public void addStatus(int status, PortletURL portletURL) {
		if (portletURL != null) {
			addStatus(
				_searchEntries.size(), status, 0, null, portletURL.toString());
		}
		else {
			addStatus(_searchEntries.size(), status, 0, null, null);
		}
	}

	@Override
	public void addStatus(int status, String href) {
		addStatus(_searchEntries.size(), status, 0, null, href);
	}

	@Override
	public void addText(int index, String name) {
		addText(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, name);
	}

	@Override
	public void addText(int index, String name, PortletURL portletURL) {
		if (portletURL == null) {
			addText(index, name);
		}
		else {
			addText(index, name, portletURL.toString());
		}
	}

	@Override
	public void addText(int index, String name, String href) {
		addText(
			index, SearchEntry.DEFAULT_ALIGN, SearchEntry.DEFAULT_VALIGN,
			SearchEntry.DEFAULT_COLSPAN, name, href);
	}

	@Override
	public void addText(
		int index, String align, String valign, int colspan, String name) {

		TextSearchEntry textSearchEntry = new TextSearchEntry();

		textSearchEntry.setAlign(align);
		textSearchEntry.setColspan(colspan);
		textSearchEntry.setName(name);
		textSearchEntry.setValign(valign);

		_searchEntries.add(index, textSearchEntry);
	}

	@Override
	public void addText(
		int index, String align, String valign, int colspan, String name,
		PortletURL portletURL) {

		if (portletURL == null) {
			addText(index, align, valign, colspan, name);
		}
		else {
			addText(index, align, valign, colspan, name, portletURL.toString());
		}
	}

	@Override
	public void addText(
		int index, String align, String valign, int colspan, String name,
		String href) {

		if (_restricted) {
			href = null;
		}

		TextSearchEntry textSearchEntry = new TextSearchEntry();

		textSearchEntry.setAlign(align);
		textSearchEntry.setColspan(colspan);
		textSearchEntry.setHref(href);
		textSearchEntry.setName(name);
		textSearchEntry.setValign(valign);

		_searchEntries.add(index, textSearchEntry);
	}

	public void addText(int index, TextSearchEntry searchEntry) {
		if (_restricted) {
			searchEntry.setHref(null);
		}

		_searchEntries.add(index, searchEntry);
	}

	@Override
	public void addText(String name) {
		addText(_searchEntries.size(), name);
	}

	@Override
	public void addText(String name, PortletURL portletURL) {
		if (portletURL == null) {
			addText(name);
		}
		else {
			addText(name, portletURL.toString());
		}
	}

	@Override
	public void addText(String name, String href) {
		addText(_searchEntries.size(), name, href);
	}

	@Override
	public void addText(String align, String valign, int colspan, String name) {
		addText(_searchEntries.size(), align, valign, colspan, name);
	}

	@Override
	public void addText(
		String align, String valign, int colspan, String name,
		PortletURL portletURL) {

		if (portletURL == null) {
			addText(align, valign, colspan, name);
		}
		else {
			addText(align, valign, colspan, name, portletURL.toString());
		}
	}

	@Override
	public void addText(
		String align, String valign, int colspan, String name, String href) {

		addText(_searchEntries.size(), align, valign, colspan, name, href);
	}

	@Override
	public void addText(String align, String valign, String name) {
		addText(
			_searchEntries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			name);
	}

	@Override
	public void addText(
		String align, String valign, String name, PortletURL portletURL) {

		addText(align, valign, SearchEntry.DEFAULT_COLSPAN, name, portletURL);
	}

	@Override
	public void addText(String align, String valign, String name, String href) {
		addText(
			_searchEntries.size(), align, valign, SearchEntry.DEFAULT_COLSPAN,
			name, href);
	}

	public void addText(TextSearchEntry searchEntry) {
		if (_restricted) {
			searchEntry.setHref(null);
		}

		_searchEntries.add(_searchEntries.size(), searchEntry);
	}

	@Override
	public String getClassHoverName() {
		return _classHoverName;
	}

	@Override
	public String getClassName() {
		return _className;
	}

	@Override
	public Map<String, Object> getData() {
		return _data;
	}

	@Override
	public List<SearchEntry> getEntries() {
		return _searchEntries;
	}

	@Override
	public Object getObject() {
		return _obj;
	}

	@Override
	public Object getParameter(String param) {
		if (_params == null) {
			_params = new HashMap<String, Object>();
		}

		return _params.get(param);
	}

	@Override
	public int getPos() {
		return _pos;
	}

	@Override
	public String getPrimaryKey() {
		return _primaryKey;
	}

	@Override
	public String getRowId() {
		return _rowId;
	}

	@Override
	public boolean isBold() {
		return _bold;
	}

	@Override
	public boolean isRestricted() {
		return _restricted;
	}

	@Override
	public boolean isSkip() {
		return _skip;
	}

	@Override
	public void removeSearchEntry(int pos) {
		_searchEntries.remove(pos);
	}

	@Override
	public void setBold(boolean bold) {
		_bold = bold;
	}

	@Override
	public void setClassHoverName(String classHoverName) {
		_classHoverName = classHoverName;
	}

	@Override
	public void setClassName(String className) {
		_className = className;
	}

	@Override
	public void setData(Map<String, Object> data) {
		_data = data;
	}

	@Override
	public void setObject(Object obj) {
		_obj = obj;
	}

	@Override
	public void setParameter(String param, Object value) {
		if (_params == null) {
			_params = new HashMap<String, Object>();
		}

		_params.put(param, value);
	}

	@Override
	public void setPrimaryKey(String primaryKey) {
		_primaryKey = primaryKey;
	}

	@Override
	public void setRestricted(boolean restricted) {
		_restricted = restricted;
	}

	@Override
	public void setRowId(String rowId) {
		_rowId = rowId;
	}

	@Override
	public void setSkip(boolean skip) {
		_skip = skip;
	}

	private boolean _bold;
	private String _classHoverName;
	private String _className;
	private Map<String, Object> _data;
	private Object _obj;
	private Map<String, Object> _params;
	private final int _pos;
	private String _primaryKey;
	private boolean _restricted;
	private String _rowId;
	private final List<SearchEntry> _searchEntries;
	private boolean _skip;

}