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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Shuyang Zhou
 */
public class AUIUtil {

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public static final String BUTTON_INPUT_PREFIX = "btn-input";

	public static final String BUTTON_PREFIX = "btn";

	public static final String FIELD_PREFIX = "field";

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public static final String INPUT_PREFIX = "field-input";

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public static final String LABEL_CHOICE_PREFIX = "choice-label";

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public static final String LABEL_FIELD_PREFIX = "field-label";

	public static String buildControlGroupCss(
		boolean inlineField, String inlineLabel, String wrapperCssClass,
		String baseType) {

		StringBundler sb = new StringBundler(9);

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
			sb.append(StringPool.SPACE);
			sb.append("input-");
			sb.append(baseType);
			sb.append("-wrapper");
		}

		return sb.toString();
	}

	public static String buildCss(
		String prefix, boolean disabled, boolean first, boolean last,
		String cssClass) {

		StringBundler sb = new StringBundler(8);

		sb.append(prefix);

		if (disabled) {
			sb.append(StringPool.SPACE);
			sb.append("disabled");
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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #buildCss(String, boolean,
	 *             boolean, boolean, String)}
	 */
	@Deprecated
	public static String buildCss(
		String prefix, String baseTypeCss, boolean disabled, boolean first,
		boolean last, String cssClass) {

		return buildCss(prefix, disabled, first, last, cssClass);
	}

	public static String buildData(Map<String, Object> data) {
		if ((data == null) || data.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(data.size() * 5);

		for (Map.Entry<String, Object> entry : data.entrySet()) {
			String dataKey = entry.getKey();
			String dataValue = String.valueOf(entry.getValue());

			sb.append("data-");
			sb.append(dataKey);
			sb.append("=\"");
			sb.append(HtmlUtil.escapeAttribute(dataValue));
			sb.append("\" ");
		}

		return sb.toString();
	}

	public static String buildLabel(
		String baseType, boolean inlineField, boolean showForLabel,
		String forLabel) {

		StringBundler sb = new StringBundler(7);

		if (baseType.equals("boolean")) {
			baseType = "checkbox";
		}

		if (baseType.equals("checkbox") || baseType.equals("radio")) {
			if (inlineField) {
				sb.append("class=\"");
				sb.append(baseType);
				sb.append("-inline");
				sb.append("\" ");
			}
		}
		else {
			sb.append("class=\"control-label\" ");
		}

		if (showForLabel) {
			sb.append("for=\"");
			sb.append(HtmlUtil.escapeAttribute(forLabel));
			sb.append("\"");
		}

		return sb.toString();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #buildLabel(String, boolean,
	 *             boolean, String)}
	 */
	@Deprecated
	public static String buildLabel(
		String inlineLabel, boolean showForLabel, String forLabel,
		boolean choiceField) {

		return buildLabel(StringPool.BLANK, false, showForLabel, forLabel);
	}

	public static Object getAttribute(
		HttpServletRequest request, String namespace, String key) {

		Map<String, Object> dynamicAttributes =
			(Map<String, Object>)request.getAttribute(
				namespace.concat("dynamicAttributes"));
		Map<String, Object> scopedAttributes =
			(Map<String, Object>)request.getAttribute(
				namespace.concat("scopedAttributes"));

		if (((dynamicAttributes != null) &&
			 dynamicAttributes.containsKey(key)) ||
			((scopedAttributes != null) &&
			 scopedAttributes.containsKey(key))) {

			return request.getAttribute(namespace.concat(key));
		}

		return null;
	}

	public static String normalizeId(String name) {
		Matcher matcher = _friendlyURLPattern.matcher(name);

		return matcher.replaceAll(StringPool.DASH);
	}

	private static final Pattern _friendlyURLPattern = Pattern.compile(
		"[^A-Za-z0-9/_-]");

}