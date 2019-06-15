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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.DirectTag;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageTag extends TagSupport implements DirectTag {

	public static void doTag(
			Object[] arguments, boolean escape, boolean escapeAttribute,
			String key, boolean localizeKey, boolean translateArguments,
			boolean unicode, PageContext pageContext)
		throws Exception {

		String value = StringPool.BLANK;

		if (!unicode) {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			unicode = GetterUtil.getBoolean(
				httpServletRequest.getAttribute(WebKeys.JAVASCRIPT_CONTEXT));
		}

		if (arguments == null) {
			if (localizeKey) {
				ResourceBundle resourceBundle =
					TagResourceBundleUtil.getResourceBundle(pageContext);

				if (escape) {
					value = HtmlUtil.escape(
						LanguageUtil.get(resourceBundle, key));
				}
				else if (escapeAttribute) {
					value = HtmlUtil.escapeAttribute(
						LanguageUtil.get(resourceBundle, key));
				}
				else if (unicode) {
					value = UnicodeLanguageUtil.get(resourceBundle, key);
				}
				else {
					value = LanguageUtil.get(resourceBundle, key);
				}
			}
			else {
				value = key;
			}
		}
		else {
			ResourceBundle resourceBundle =
				TagResourceBundleUtil.getResourceBundle(pageContext);

			if (unicode) {
				value = UnicodeLanguageUtil.format(
					resourceBundle, key, arguments, translateArguments);
			}
			else {
				value = LanguageUtil.format(
					resourceBundle, key, arguments, translateArguments);
			}
		}

		if (Validator.isNotNull(value)) {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(value);
		}
	}

	public static void doTag(String key, PageContext pageContext)
		throws Exception {

		doTag(null, false, false, key, true, true, false, pageContext);
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			doTag(
				_arguments, _escape, _escapeAttribute, _key, _localizeKey,
				_translateArguments, _unicode, pageContext);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_arguments = null;
			_escape = false;
			_escapeAttribute = false;
			_key = null;
			_localizeKey = true;
			_translateArguments = true;
			_unicode = false;
		}
	}

	public void setArguments(Object argument) {
		if (argument == null) {
			_arguments = null;

			return;
		}

		Class<?> clazz = argument.getClass();

		if (clazz.isArray()) {
			_arguments = (Object[])argument;
		}
		else {
			_arguments = new Object[] {argument};
		}
	}

	public void setEscape(boolean escape) {
		_escape = escape;
	}

	public void setEscapeAttribute(boolean escapeAttribute) {
		_escapeAttribute = escapeAttribute;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setLocalizeKey(boolean localizeKey) {
		_localizeKey = localizeKey;
	}

	public void setTranslateArguments(boolean translateArguments) {
		_translateArguments = translateArguments;
	}

	public void setUnicode(boolean unicode) {
		_unicode = unicode;
	}

	private Object[] _arguments;
	private boolean _escape;
	private boolean _escapeAttribute;
	private String _key;
	private boolean _localizeKey = true;
	private boolean _translateArguments = true;
	private boolean _unicode;

}