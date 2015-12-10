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

import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Sergio González
 */
public class FormNavigatorTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setBackURL(String backURL) {
		_backURL = backURL;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setCategoryNames(String[] categoryNames) {
		_categoryNames = categoryNames;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setCategorySections(String[][] categorySections) {
		_categorySections = categorySections;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFormModelBean(Object formModelBean) {
		_formModelBean = formModelBean;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setHtmlBottom(String htmlBottom) {
		_htmlBottom = htmlBottom;
	}

	public void setHtmlTop(String htmlTop) {
		_htmlTop = htmlTop;
	}

	public void setId(String id) {
		_id = id;
	}

	/**
	 * @deprecated As of 7.0.0
	 */
	@Deprecated
	public void setJspPath(String jspPath) {
		_jspPath = jspPath;
	}

	public void setMarkupView(String markupView) {
		_markupView = markupView;
	}

	public void setShowButtons(boolean showButtons) {
		_showButtons = showButtons;
	}

	@Override
	protected void cleanUp() {
		_backURL = null;
		_categoryNames = null;
		_categorySections = null;
		_displayStyle = "form";
		_formModelBean = null;
		_formName = "fm";
		_htmlBottom = null;
		_htmlTop = null;
		_id = null;
		_jspPath = null;
		_markupView = null;
		_showButtons = true;
	}

	protected String[] getCategoryKeys() {
		if (_categoryNames != null) {
			return _categoryNames;
		}

		return FormNavigatorCategoryUtil.getKeys(_id);
	}

	protected String[] getCategoryLabels() {
		if (_categoryNames != null) {
			return _categoryNames;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return FormNavigatorCategoryUtil.getLabels(
			_id, themeDisplay.getLocale());
	}

	protected String[][] getCategorySectionKeys() {
		if (_categorySections != null) {
			return _categorySections;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] categoryKeys = getCategoryKeys();

		String[][] categorySectionKeys = new String[0][];

		for (int i = 0; i < categoryKeys.length; i++) {
			String categoryKey = categoryKeys[i];

			categorySectionKeys = ArrayUtil.append(
				categorySectionKeys,
				FormNavigatorEntryUtil.getKeys(
					_id, categoryKey, themeDisplay.getUser(), _formModelBean));
		}

		return categorySectionKeys;
	}

	protected String[][] getCategorySectionLabels() {
		if (_categorySections != null) {
			return _categorySections;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] categoryKeys = getCategoryKeys();

		String[][] categorySectionLabels = new String[0][];

		for (int i = 0; i < categoryKeys.length; i++) {
			String categoryKey = categoryKeys[i];

			categorySectionLabels = ArrayUtil.append(
				categorySectionLabels,
				FormNavigatorEntryUtil.getLabels(
					_id, categoryKey, themeDisplay.getUser(), _formModelBean,
					themeDisplay.getLocale()));
		}

		return categorySectionLabels;
	}

	protected String[] getDeprecatedCategorySections() {
		if (_categorySections == null) {
			return new String[0];
		}

		String[] deprecatedCategorySections = new String[0];

		for (String[] categorySection : _categorySections) {
			deprecatedCategorySections = ArrayUtil.append(
				deprecatedCategorySections, categorySection);
		}

		return deprecatedCategorySections;
	}

	@Override
	protected String getPage() {
		if (Validator.isNotNull(_markupView)) {
			return
				"/html/taglib/ui/form_navigator/" + _markupView + "/page.jsp";
		}

		return "/html/taglib/ui/form_navigator/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:form-navigator:backURL", _backURL);
		request.setAttribute(
			"liferay-ui:form-navigator:categoryKeys", getCategoryKeys());
		request.setAttribute(
			"liferay-ui:form-navigator:categoryLabels", getCategoryLabels());
		request.setAttribute(
			"liferay-ui:form-navigator:categorySectionKeys",
			getCategorySectionKeys());
		request.setAttribute(
			"liferay-ui:form-navigator:categorySectionLabels",
			getCategorySectionLabels());
		request.setAttribute(
			"liferay-ui:form-navigator:deprecatedCategorySections",
			getDeprecatedCategorySections());
		request.setAttribute(
			"liferay-ui:form-navigator:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:form-navigator:formModelBean", _formModelBean);
		request.setAttribute("liferay-ui:form-navigator:formName", _formName);
		request.setAttribute("liferay-ui:form-navigator:id", _id);
		request.setAttribute(
			"liferay-ui:form-navigator:htmlBottom", _htmlBottom);
		request.setAttribute("liferay-ui:form-navigator:htmlTop", _htmlTop);
		request.setAttribute("liferay-ui:form-navigator:jspPath", _jspPath);
		request.setAttribute(
			"liferay-ui:form-navigator:showButtons",
			String.valueOf(_showButtons));
	}

	private String _backURL;
	private String[] _categoryNames;
	private String[][] _categorySections;
	private String _displayStyle = "form";
	private Object _formModelBean;
	private String _formName = "fm";
	private String _htmlBottom;
	private String _htmlTop;
	private String _id;
	private String _jspPath;
	private String _markupView;
	private boolean _showButtons = true;

}