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

package com.liferay.taglib.aui;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class AUIUtil {

	public static final String BUTTON_PREFIX = "btn";

	public static final String FIELD_PREFIX = "field";

	public static String buildControlGroupCss(
		boolean inlineField, String inlineLabel, String wrapperCssClass,
		String baseType) {

		StringBundler sb = new StringBundler(8);

		sb.append("form-group");

		if (inlineField) {
			sb.append(" form-group-inline");
		}

		if (Validator.isNotNull(inlineLabel)) {
			sb.append(" form-inline");
		}

		if (Validator.isNotNull(wrapperCssClass)) {
			sb.append(StringPool.SPACE);
			sb.append(wrapperCssClass);
		}

		if (Validator.isNotNull(baseType)) {
			sb.append(" input-");
			sb.append(baseType);
			sb.append("-wrapper");
		}

		return sb.toString();
	}

	public static String buildCss(
		String prefix, boolean disabled, boolean first, boolean last,
		String cssClass) {

		StringBundler sb = new StringBundler(7);

		sb.append(prefix);

		if (disabled) {
			sb.append(" disabled");
		}

		if (first) {
			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-first");
		}
		else if (last) {
			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-last");
		}

		if (Validator.isNotNull(cssClass)) {
			sb.append(StringPool.SPACE);
			sb.append(cssClass);
		}

		return sb.toString();
	}

	public static String buildData(Map<String, Object> data) {
		return HtmlUtil.buildData(data);
	}

	public static String buildLabel(
		String baseType, boolean inlineField, boolean showForLabel,
		String forLabel) {

		return buildLabel(baseType, inlineField, showForLabel, forLabel, false);
	}

	public static String buildLabel(
		String baseType, boolean inlineField, boolean showForLabel,
		String forLabel, boolean disabled) {

		StringBundler sb = new StringBundler(6);

		if (baseType.equals("boolean")) {
			baseType = "checkbox";
		}

		if (baseType.equals("checkbox") || baseType.equals("radio")) {
			if (inlineField) {
				sb.append("class=\"");
				sb.append(baseType);
				sb.append("-inline\"");
			}
		}
		else {
			sb.append("class=\"control-label");

			if (disabled) {
				sb.append(" disabled");
			}

			sb.append("\"");
		}

		if (showForLabel) {
			sb.append(" for=\"");
			sb.append(HtmlUtil.escapeAttribute(forLabel));
			sb.append("\"");
		}

		return sb.toString();
	}

	public static Object getAttribute(
		HttpServletRequest request, String namespace, String key) {

		Map<String, Object> dynamicAttributes =
			(Map<String, Object>)request.getAttribute(
				namespace.concat("dynamicAttributes"));

		if ((dynamicAttributes != null) && dynamicAttributes.containsKey(key)) {
			return request.getAttribute(namespace.concat(key));
		}

		return null;
	}

	public static String getNamespace(HttpServletRequest request) {
		return GetterUtil.getString(
			request.getAttribute("aui:form:portletNamespace"));
	}

	public static String getNamespace(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String namespace = StringPool.BLANK;

		if (portletRequest == null) {
			return namespace;
		}

		boolean auiFormUseNamespace = GetterUtil.getBoolean(
			(String)portletRequest.getAttribute("aui:form:useNamespace"), true);

		if ((portletResponse != null) && auiFormUseNamespace) {
			namespace = GetterUtil.getString(
				portletRequest.getAttribute("aui:form:portletNamespace"),
				portletResponse.getNamespace());
		}

		return namespace;
	}

	public static boolean isOpensNewWindow(String target) {
		if ((target != null) &&
			(target.equals("_blank") || target.equals("_new"))) {

			return true;
		}

		return false;
	}

	public static String normalizeId(String name) {
		char[] chars = null;

		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);

			if ((_VALID_CHARS.length <= c) || !_VALID_CHARS[c]) {
				if (chars == null) {
					chars = new char[name.length()];

					name.getChars(0, chars.length, chars, 0);
				}

				chars[i] = CharPool.DASH;
			}
		}

		if (chars == null) {
			return name;
		}

		return new String(chars);
	}

	private static final boolean[] _VALID_CHARS = new boolean[128];

	static {
		for (int i = 'a'; i <= 'z'; i++) {
			_VALID_CHARS[i] = true;
		}

		for (int i = 'A'; i <= 'Z'; i++) {
			_VALID_CHARS[i] = true;
		}

		for (int i = '0'; i <= '9'; i++) {
			_VALID_CHARS[i] = true;
		}

		_VALID_CHARS['-'] = true;
		_VALID_CHARS['_'] = true;
	}

}