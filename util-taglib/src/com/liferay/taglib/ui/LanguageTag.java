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
import com.liferay.portal.kernel.servlet.taglib.ui.LanguageEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplate;
import com.liferay.taglib.aui.AUIUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class LanguageTag extends IncludeTag {

	public void setDdmTemplateGroupId(long ddmTemplateGroupId) {
		_ddmTemplateGroupId = ddmTemplateGroupId;
	}

	public void setDdmTemplateKey(String ddmTemplateKey) {
		_ddmTemplateKey = ddmTemplateKey;
	}

	public void setDisplayCurrentLocale(boolean displayCurrentLocale) {
		_displayCurrentLocale = displayCurrentLocale;
	}

	public void setFormAction(String formAction) {
		_formAction = formAction;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setLanguageIds(String[] languageIds) {
		_languageIds = languageIds;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	protected void cleanUp() {
		_ddmTemplateGroupId = 0;
		_ddmTemplateKey = null;
		_displayCurrentLocale = true;
		_formAction = null;
		_formName = "fm";
		_languageId = null;
		_languageIds = null;
		_name = "languageId";
	}

	protected String getDisplayStyle() {
		if (Validator.isNotNull(_ddmTemplateKey)) {
			return PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				_ddmTemplateKey;
		}

		return null;
	}

	protected String getFormAction() {
		String formAction = _formAction;

		if (Validator.isNotNull(formAction)) {
			return formAction;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		formAction =
			themeDisplay.getPathMain() +
				"/portal/update_language?p_l_id=" + themeDisplay.getPlid();
		formAction = HttpUtil.setParameter(
			formAction, "redirect", PortalUtil.getCurrentURL(request));

		return formAction;
	}

	protected List<LanguageEntry> getLanguageEntries(
		Collection<Locale> locales, boolean displayCurrentLocale,
		String formAction, String parameterName) {

		List<LanguageEntry> languageEntries = new ArrayList<>();

		Map<String, Integer> counts = new HashMap<>();

		for (Locale locale : locales) {
			Integer count = counts.get(locale.getLanguage());

			if (count == null) {
				count = new Integer(1);
			}
			else {
				count = new Integer(count.intValue() + 1);
			}

			counts.put(locale.getLanguage(), count);
		}

		Set<String> duplicateLanguages = new HashSet<>();

		for (Locale locale : locales) {
			Integer count = counts.get(locale.getLanguage());

			if (count.intValue() != 1) {
				duplicateLanguages.add(locale.getLanguage());
			}
		}

		Locale currentLocale = null;

		if (Validator.isNotNull(_languageId)) {
			currentLocale = LocaleUtil.fromLanguageId(_languageId);
		}
		else {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			currentLocale = themeDisplay.getLocale();
		}

		for (Locale locale : locales) {
			String url = null;

			if (!LocaleUtil.equals(locale, currentLocale)) {
				url = HttpUtil.setParameter(
					formAction, parameterName, LocaleUtil.toLanguageId(locale));
			}
			else if (!displayCurrentLocale) {
				continue;
			}

			languageEntries.add(
				new LanguageEntry(
					duplicateLanguages, currentLocale, locale, url));
		}

		return languageEntries;
	}

	protected Collection<Locale> getLocales() {
		if (ArrayUtil.isNotEmpty(_languageIds)) {
			return Arrays.asList(LocaleUtil.fromLanguageIds(_languageIds));
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return LanguageUtil.getAvailableLocales(themeDisplay.getSiteGroupId());
	}

	protected String getNamespacedName() {
		String name = _name;

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		String namespace = AUIUtil.getNamespace(
			portletRequest, portletResponse);

		if (Validator.isNotNull(namespace)) {
			name = namespace.concat(name);
		}

		return name;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:language:displayCurrentLocale",
			String.valueOf(_displayCurrentLocale));
		request.setAttribute(
			"liferay-ui:language:displayStyle", getDisplayStyle());
		request.setAttribute(
			"liferay-ui:language:displayStyleGroupId", _ddmTemplateGroupId);
		request.setAttribute("liferay-ui:language:formAction", getFormAction());
		request.setAttribute("liferay-ui:language:formName", _formName);
		request.setAttribute(
			"liferay-ui:language:languageEntries",
			getLanguageEntries(
				getLocales(), _displayCurrentLocale, getFormAction(),
				getNamespacedName()));
		request.setAttribute("liferay-ui:language:languageId", _languageId);
		request.setAttribute("liferay-ui:language:name", _name);
	}

	private static final String _PAGE = "/html/taglib/ui/language/page.jsp";

	private long _ddmTemplateGroupId;
	private String _ddmTemplateKey;
	private boolean _displayCurrentLocale = true;
	private String _formAction;
	private String _formName = "fm";
	private String _languageId;
	private String[] _languageIds;
	private String _name = "languageId";

}