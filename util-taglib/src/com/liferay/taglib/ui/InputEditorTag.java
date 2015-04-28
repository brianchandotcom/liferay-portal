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

import com.liferay.portal.kernel.editor.config.EditorConfig;
import com.liferay.portal.kernel.editor.config.EditorConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class InputEditorTag extends IncludeTag {

	public void setAllowBrowseDocuments(boolean allowBrowseDocuments) {
		_allowBrowseDocuments = allowBrowseDocuments;
	}

	public void setAutoCreate(boolean autoCreate) {
		_autoCreate = autoCreate;
	}

	public void setConfigKey(String configKey) {
		_configKey = configKey;
	}

	public void setConfigParams(Map<String, String> configParams) {
		_configParams = configParams;
	}

	public void setContents(String contents) {
		_contents = contents;
	}

	public void setContentsLanguageId(String contentsLanguageId) {
		_contentsLanguageId = contentsLanguageId;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setEditorName(String)}
	 */
	@Deprecated
	public void setEditorImpl(String editorImpl) {
		_editorName = PropsUtil.get(editorImpl);
	}

	public void setEditorName(String editorName) {
		_editorName = editorName;
	}

	public void setFileBrowserParams(Map<String, String> fileBrowserParams) {
		_fileBrowserParams = fileBrowserParams;
	}

	public void setHeight(String height) {
		_height = height;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #setContents(String)}
	 */
	@Deprecated
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

	public void setOnBlurMethod(String onBlurMethod) {
		_onBlurMethod = onBlurMethod;
	}

	public void setOnChangeMethod(String onChangeMethod) {
		_onChangeMethod = onChangeMethod;
	}

	public void setOnFocusMethod(String onFocusMethod) {
		_onFocusMethod = onFocusMethod;
	}

	public void setOnInitMethod(String onInitMethod) {
		_onInitMethod = onInitMethod;
	}

	public void setPlaceholder(String placeholder) {
		_placeholder = placeholder;
	}

	public void setResizable(boolean resizable) {
		_resizable = resizable;
	}

	public void setShowSource(boolean showSource) {
		_showSource = showSource;
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
		_allowBrowseDocuments = true;
		_autoCreate = true;
		_configKey = null;
		_configParams = null;
		_contents = null;
		_contentsLanguageId = null;
		_cssClass = null;
		_data = null;
		_editorName = null;
		_fileBrowserParams = null;
		_height = null;
		_initMethod = "initEditor";
		_inlineEdit = false;
		_inlineEditSaveURL = null;
		_name = "editor";
		_onChangeMethod = null;
		_onBlurMethod = null;
		_onFocusMethod = null;
		_onInitMethod = null;
		_placeholder = null;
		_resizable = true;
		_showSource = true;
		_skipEditorLoading = false;
		_toolbarSet = "liferay";
		_width = null;
	}

	protected String getConfigKey() {
		String configKey = _configKey;

		if (Validator.isNull(configKey)) {
			configKey = _name;
		}

		return configKey;
	}

	protected String getContentsLanguageId() {
		if (_contentsLanguageId == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_contentsLanguageId = themeDisplay.getLanguageId();
		}

		return _contentsLanguageId;
	}

	protected String getCssClasses() {
		Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

		String cssClasses = "portlet ";

		if (portlet != null) {
			cssClasses += portlet.getCssClassWrapper();
		}

		return cssClasses;
	}

	protected Map<String, Object> getData() {
		Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

		if (portlet == null) {
			return _data;
		}

		Map<String, Object> attributes = new HashMap<>();

		Enumeration<String> enumeration = request.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String attributeName = enumeration.nextElement();

			if (attributeName.startsWith("liferay-ui:input-editor")) {
				attributes.put(
					attributeName, request.getAttribute(attributeName));
			}
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		LiferayPortletResponse portletResponse =
			(LiferayPortletResponse)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		EditorConfig editorConfig = EditorConfigFactoryUtil.getEditorConfig(
			portlet.getPortletId(), getConfigKey(), getEditorName(request),
			attributes, themeDisplay, portletResponse);

		Map<String, Object> data = editorConfig.getData();

		if (MapUtil.isNotEmpty(_data)) {
			MapUtil.merge(_data, data);
		}

		return data;
	}

	protected String getEditorName(HttpServletRequest request) {
		String editorName = _editorName;

		if (!BrowserSnifferUtil.isRtf(request)) {
			editorName = "simple";
		}

		if (Validator.isNull(editorName)) {
			editorName = _EDITOR_WYSIWYG_DEFAULT;
		}

		if (!FileAvailabilityUtil.isAvailable(
				servletContext, getPage(editorName))) {

			editorName = _EDITOR_WYSIWYG_DEFAULT;
		}

		return editorName;
	}

	@Override
	protected String getPage() {
		String editorName = getEditorName(request);

		return getPage(editorName);
	}

	protected String getPage(String editorName) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getPathJavaScript() + "/editor/" +
			editorName + ".jsp";
	}

	@Override
	protected RequestDispatcher getRequestDispatcher(String page) {
		return DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
			PortalWebResourcesUtil.getServletContext(), page);
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:input-editor:allowBrowseDocuments",
			String.valueOf(_allowBrowseDocuments));
		request.setAttribute(
			"liferay-ui:input-editor:autoCreate", String.valueOf(_autoCreate));
		request.setAttribute(
			"liferay-ui:input-editor:configParams", _configParams);
		request.setAttribute("liferay-ui:input-editor:contents", _contents);
		request.setAttribute(
			"liferay-ui:input-editor:contentsLanguageId",
			getContentsLanguageId());
		request.setAttribute("liferay-ui:input-editor:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:input-editor:cssClasses", getCssClasses());
		request.setAttribute(
			"liferay-ui:input-editor:editorName", getEditorName(request));
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
			"liferay-ui:input-editor:onBlurMethod", _onBlurMethod);
		request.setAttribute(
			"liferay-ui:input-editor:onChangeMethod", _onChangeMethod);
		request.setAttribute(
			"liferay-ui:input-editor:onFocusMethod", _onFocusMethod);
		request.setAttribute(
			"liferay-ui:input-editor:onInitMethod", _onInitMethod);
		request.setAttribute(
			"liferay-ui:input-editor:placeholder", _placeholder);
		request.setAttribute(
			"liferay-ui:input-editor:resizable", String.valueOf(_resizable));
		request.setAttribute(
			"liferay-ui:input-editor:showSource", String.valueOf(_showSource));
		request.setAttribute(
			"liferay-ui:input-editor:skipEditorLoading",
			String.valueOf(_skipEditorLoading));
		request.setAttribute("liferay-ui:input-editor:toolbarSet", _toolbarSet);
		request.setAttribute("liferay-ui:input-editor:width", _width);

		request.setAttribute("liferay-ui:input-editor:data", getData());
	}

	private static final String _EDITOR_WYSIWYG_DEFAULT = PropsUtil.get(
		PropsKeys.EDITOR_WYSIWYG_DEFAULT);

	private boolean _allowBrowseDocuments = true;
	private boolean _autoCreate = true;
	private String _configKey;
	private Map<String, String> _configParams;
	private String _contents;
	private String _contentsLanguageId;
	private String _cssClass;
	private Map<String, Object> _data = null;
	private String _editorName;
	private Map<String, String> _fileBrowserParams;
	private String _height;
	private String _initMethod = "initEditor";
	private boolean _inlineEdit;
	private String _inlineEditSaveURL;
	private String _name = "editor";
	private String _onBlurMethod;
	private String _onChangeMethod;
	private String _onFocusMethod;
	private String _onInitMethod;
	private String _placeholder;
	private boolean _resizable = true;
	private boolean _showSource = true;
	private boolean _skipEditorLoading;
	private String _toolbarSet = "liferay";
	private String _width;

}