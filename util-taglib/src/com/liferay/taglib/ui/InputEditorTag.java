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

import com.liferay.portal.kernel.editor.Editor;
import com.liferay.portal.kernel.editor.configuration.EditorConfiguration;
import com.liferay.portal.kernel.editor.configuration.EditorConfigurationFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.taglib.BaseValidatorTagSupport;
import com.liferay.taglib.aui.AUIUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.io.IOException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class InputEditorTag extends BaseValidatorTagSupport {

	public static Editor getEditor(
		HttpServletRequest request, String editorName) {

		if (!BrowserSnifferUtil.isRtf(request)) {
			return _serviceTrackerMap.getService("simple");
		}

		if (Validator.isNull(editorName)) {
			return _serviceTrackerMap.getService(_EDITOR_WYSIWYG_DEFAULT);
		}

		if (!_serviceTrackerMap.containsKey(editorName)) {
			return _serviceTrackerMap.getService(_EDITOR_WYSIWYG_DEFAULT);
		}

		return _serviceTrackerMap.getService(editorName);
	}

	@Override
	public String getInputName() {
		return getConfigKey();
	}

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

	public void setRequired(boolean required) {
		_required = required;
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
		_required = false;
		_showSource = true;
		_skipEditorLoading = false;
		_toolbarSet = _TOOLBAR_SET_DEFAULT;
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
		String portletId = (String)request.getAttribute(WebKeys.PORTLET_ID);

		if (portletId == null) {
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

		attributes.put("liferay-ui:input-editor:namespace", getNamespace());

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		EditorConfiguration editorConfiguration =
			EditorConfigurationFactoryUtil.getEditorConfiguration(
				PortletIdCodec.decodePortletName(portletId), getConfigKey(),
				getEditorName(request), attributes, themeDisplay,
				getRequestBackedPortletURLFactory());

		Map<String, Object> data = editorConfiguration.getData();

		if (MapUtil.isNotEmpty(_data)) {
			MapUtil.merge(_data, data);
		}

		return data;
	}

	protected Editor getEditor(HttpServletRequest request) {
		return getEditor(request, _editorName);
	}

	protected String getEditorName(HttpServletRequest request) {
		Editor editor = getEditor(request);

		return editor.getName();
	}

	protected String getEditorResourceType() {
		Editor editor = getEditor(request);

		return editor.getResourceType();
	}

	protected String getNamespace() {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletRequest == null) || (portletResponse == null)) {
			return AUIUtil.getNamespace(request);
		}

		return AUIUtil.getNamespace(portletRequest, portletResponse);
	}

	@Override
	protected String getPage() {
		Editor editor = getEditor(request);

		return editor.getJspPath();
	}

	protected RequestBackedPortletURLFactory
		getRequestBackedPortletURLFactory() {

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest == null) {
			return RequestBackedPortletURLFactoryUtil.create(request);
		}

		return RequestBackedPortletURLFactoryUtil.create(portletRequest);
	}

	protected String getToolbarSet() {
		if (Validator.isNotNull(_toolbarSet)) {
			return _toolbarSet;
		}

		return _TOOLBAR_SET_DEFAULT;
	}

	@Override
	protected void includePage(String page, HttpServletResponse response)
		throws IOException, ServletException {

		servletContext = PortalWebResourcesUtil.getServletContext(
			getEditorResourceType());

		super.includePage(page, response);
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

		ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(
			pageContext);

		if (Validator.isNull(_placeholder)) {
			_placeholder = LanguageUtil.get(
				resourceBundle, "write-your-content-here");
		}

		request.setAttribute(
			"liferay-ui:input-editor:placeholder", _placeholder);

		request.setAttribute(
			"liferay-ui:input-editor:required", String.valueOf(_required));
		request.setAttribute(
			"liferay-ui:input-editor:resizable", String.valueOf(_resizable));
		request.setAttribute(
			"liferay-ui:input-editor:showSource", String.valueOf(_showSource));
		request.setAttribute(
			"liferay-ui:input-editor:skipEditorLoading",
			String.valueOf(_skipEditorLoading));
		request.setAttribute(
			"liferay-ui:input-editor:toolbarSet", getToolbarSet());
		request.setAttribute("liferay-ui:input-editor:width", _width);

		request.setAttribute(
			"liferay-ui:input-editor:data",
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(), new Class<?>[] {Map.class},
				new LazyDataInvocationHandler()));
	}

	private static final String _EDITOR_WYSIWYG_DEFAULT = PropsUtil.get(
		PropsKeys.EDITOR_WYSIWYG_DEFAULT);

	private static final String _TOOLBAR_SET_DEFAULT = "liferay";

	private static final ServiceTrackerMap<String, Editor> _serviceTrackerMap =
		ServiceTrackerCollections.singleValueMap(
			Editor.class, null,
			new ServiceReferenceMapper<String, Editor>() {

				@Override
				public void map(
					ServiceReference<Editor> serviceReference,
					Emitter<String> emitter) {

					Registry registry = RegistryUtil.getRegistry();

					Editor editor = registry.getService(serviceReference);

					emitter.emit(editor.getName());
				}

			});

	static {
		_serviceTrackerMap.open();
	}

	private boolean _allowBrowseDocuments = true;
	private boolean _autoCreate = true;
	private String _configKey;
	private Map<String, String> _configParams;
	private String _contents;
	private String _contentsLanguageId;
	private String _cssClass;
	private Map<String, Object> _data;
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
	private boolean _required;
	private boolean _resizable = true;
	private boolean _showSource = true;
	private boolean _skipEditorLoading;
	private String _toolbarSet = _TOOLBAR_SET_DEFAULT;
	private String _width;

	private class LazyDataInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
			throws ReflectiveOperationException {

			if (_data == null) {
				_data = getData();
			}

			return method.invoke(_data, args);
		}

		private Map<String, Object> _data;

	}

}