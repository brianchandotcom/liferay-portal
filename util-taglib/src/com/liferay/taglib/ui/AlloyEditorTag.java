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

import com.liferay.portal.kernel.editor.EditorUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class AlloyEditorTag extends IncludeTag {

	public void setContents(String contents) {
		_contents = contents;
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

	public void setSkipEditorLoading(boolean skipEditorLoading) {
		_skipEditorLoading = skipEditorLoading;
	}

	@Override
	protected void cleanUp() {
		_contents = null;
		_contentsLanguageId = null;
		_cssClass = null;
		_editorImpl = null;
		_fileBrowserParams = null;
		_name = "editor";
		_onChangeMethod = null;
		_onBlurMethod = null;
		_onFocusMethod = null;
		_onInitMethod = null;
		_page = null;
		_placeholder = null;
		_skipEditorLoading = false;
	}

	@Override
	protected String getPage() {
		return _page;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if (_contentsLanguageId == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_contentsLanguageId = themeDisplay.getLanguageId();
		}

		String cssClasses = "portlet ";

		Portlet portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);

		if (portlet != null) {
			cssClasses += portlet.getCssClassWrapper();
		}

		String editorImpl = EditorUtil.getEditorValue(request, _editorImpl);

		_page = "/html/js/editor/" + editorImpl + ".jsp";

		request.setAttribute("liferay-ui:input-editor:contents", _contents);
		request.setAttribute(
			"liferay-ui:input-editor:contentsLanguageId", _contentsLanguageId);
		request.setAttribute("liferay-ui:input-editor:cssClass", _cssClass);
		request.setAttribute("liferay-ui:input-editor:cssClasses", cssClasses);
		request.setAttribute("liferay-ui:input-editor:editorImpl", editorImpl);
		request.setAttribute(
			"liferay-ui:input-editor:fileBrowserParams", _fileBrowserParams);
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
			"liferay-ui:input-editor:skipEditorLoading",
			String.valueOf(_skipEditorLoading));
	}

	private String _contents;
	private String _contentsLanguageId;
	private String _cssClass;
	private String _editorImpl;
	private Map<String, String> _fileBrowserParams;
	private String _name = "editor";
	private String _onBlurMethod;
	private String _onChangeMethod;
	private String _onFocusMethod;
	private String _onInitMethod;
	private String _page;
	private String _placeholder;
	private boolean _skipEditorLoading;

}