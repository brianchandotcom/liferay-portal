/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class AUIUtil {

	public static final String BUTTON_INPUT_PREFIX = "aui-button-input";

	public static final String BUTTON_PREFIX = "aui-button";

	public static final String FIELD_PREFIX = "aui-field";

	public static final String INPUT_PREFIX = "aui-field-input";

	public static final String LABEL_CHOICE_PREFIX = "aui-choice-label";

	public static final String LABEL_FIELD_PREFIX = "aui-field-label";

	public static String buildCss(
		String prefix, String baseTypeCss, boolean inlineField,
		boolean disabled, boolean choiceField, boolean first, boolean last,
		String cssClass) {

		StringBundler sb = new StringBundler();

		sb.append(prefix);

		if (choiceField) {
			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-choice");
		}
		else if (baseTypeCss.equals("button")) {
		}
		else if (baseTypeCss.equals("password") ||
				 baseTypeCss.equals("string") ||
				 baseTypeCss.equals("textarea")) {

			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-text");
		}
		else if (baseTypeCss.equals("select")) {
			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-select");
			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-menu");
		}
		else {
			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-");
			sb.append(baseTypeCss);
		}

		if (inlineField) {
			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-inline");
		}

		if (disabled) {
			sb.append(StringPool.SPACE);
			sb.append(prefix);
			sb.append("-disabled");
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
			sb.append(dataValue);
			sb.append("\" ");
		}

		return sb.toString();
	}

	/**
	 * @deprecated {@link #buildLabel(String, boolean, String, boolean)}
	 */
	public static String buildLabel(
		String inlineLabel, boolean showForLabel, String forLabel) {

		return buildLabel(inlineLabel, showForLabel, forLabel, false);
	}

	public static String buildLabel(
		String inlineLabel, boolean showForLabel, String forLabel,
		boolean choiceField) {

		StringBundler sb = new StringBundler(4);

		if (choiceField) {
			sb.append("class=\"" + LABEL_CHOICE_PREFIX);
		}
		else {
			sb.append("class=\"" + LABEL_FIELD_PREFIX);

			if (Validator.isNotNull(inlineLabel)) {
				sb.append("-inline-label");
			}
		}

		sb.append("\"");

		if (showForLabel) {
			sb.append(" for=\"" + forLabel + "\"");
		}

		return sb.toString();
	}

}