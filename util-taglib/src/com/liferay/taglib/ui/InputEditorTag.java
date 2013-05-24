/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.editor.EditorUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InputEditorTag extends IncludeTag {

	public void setConfigParams(Map<String, String> configParams) {
		_configParams = configParams;
	}

	public void setContentsLanguageId(String contentsLanguageId) {
		_contentsLanguageId = contentsLanguageId;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setEditorImpl(String editorImpl) {
		_editorImpl = editorImpl;
	}

	public void setFileBrowserParams(Map<String, String> fileBrowserParams) {
		_fileBrowserParams = fileBrowserParams;
	}

	public void setHeight(String height) {
		_height = height;
	}

	public void setInitMethod(String initMethod) {
		_initMethod = initMethod;
	}

	public void setInlineEdit(boolean inlineEdit) {
		_inlineEdit = inlineEdit;
	}

	public void setInlineEditSaveURL(String inlineEditSaveURL) {
		_inlineEditSaveURL = inlineEditSaveURL;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOnChangeMethod(String onChangeMethod) {
		_onChangeMethod = onChangeMethod;
	}

	public void setResizable(boolean resizable) {
		_resizable = resizable;
	}

	public void setSkipEditorLoading(boolean skipEditorLoading) {
		_skipEditorLoading = skipEditorLoading;
	}

	public void setToolbarSet(String toolbarSet) {
		_toolbarSet = toolbarSet;
	}

	public void setWidth(String width) {
		_width = width;
	}

	@Override
	protected void cleanUp() {
		_configParams = null;
		_contentsLanguageId = null;
		_cssClass = null;
		_editorImpl = null;
		_fileBrowserParams = null;
		_height = null;
		_initMethod = "initEditor";
		_inlineEdit = false;
		_inlineEditSaveURL = null;
		_name = "editor";
		_onChangeMethod = null;
		_page = null;
		_resizable = true;
		_skipEditorLoading = false;
		_toolbarSet = "liferay";
		_width = null;
	}

	@Override
	protected String getPage() {
		return _page;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String cssClasses = "portlet ";

		Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

		if (portlet != null) {
			cssClasses += portlet.getCssClassWrapper();
		}

		String editorImpl = EditorUtil.getEditorValue(request, _editorImpl);

		_page = "/html/js/editor/" + editorImpl + ".jsp";

		request.setAttribute(
			"liferay-ui:input-editor:configParams", _configParams);
		request.setAttribute(
			"liferay-ui:input-editor:contentsLanguageId", _contentsLanguageId);
		request.setAttribute("liferay-ui:input-editor:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-editor:cssClasses", cssClasses);
		request.setAttribute("liferay-ui:input-editor:editorImpl", editorImpl);
		request.setAttribute(
			"liferay-ui:input-editor:fileBrowserParams", _fileBrowserParams);
		request.setAttribute("liferay-ui:input-editor:height", _height);
		request.setAttribute("liferay-ui:input-editor:initMethod", _initMethod);
		request.setAttribute(
			"liferay-ui:input-editor:inlineEdit", String.valueOf(_inlineEdit));
		request.setAttribute(
			"liferay-ui:input-editor:inlineEditSaveURL", _inlineEditSaveURL);
		request.setAttribute("liferay-ui:input-editor:name", _name);
		request.setAttribute(
			"liferay-ui:input-editor:onChangeMethod", _onChangeMethod);
		request.setAttribute(
			"liferay-ui:input-editor:resizable", String.valueOf(_resizable));
		request.setAttribute(
			"liferay-ui:input-editor:skipEditorLoading",
			String.valueOf(_skipEditorLoading));
		request.setAttribute("liferay-ui:input-editor:toolbarSet", _toolbarSet);
		request.setAttribute("liferay-ui:input-editor:width", _width);
	}

	private Map<String, String> _configParams;
	private String _cssClass;
	private String _contentsLanguageId;
	private String _editorImpl;
	private Map<String, String> _fileBrowserParams;
	private String _height;
	private String _initMethod = "initEditor";
	private boolean _inlineEdit;
	private String _inlineEditSaveURL;
	private String _name = "editor";
	private String _onChangeMethod;
	private String _page;
	private boolean _resizable = true;
	private boolean _skipEditorLoading;
	private String _toolbarSet = "liferay";
	private String _width;

}