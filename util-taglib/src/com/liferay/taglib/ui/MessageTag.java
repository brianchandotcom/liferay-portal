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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			String value = StringPool.BLANK;

			HttpServletRequest request =
				(HttpServletRequest)pageContext.getRequest();

			boolean unicode = GetterUtil.getBoolean(
				request.getAttribute(WebKeys.JAVASCRIPT_CONTEXT));

			if (unicode) {
				_unicode = unicode;
			}

			if (_arguments == null) {
				if (!_localizeKey) {
					value = _key;
				}
				else if (_unicode) {
					value = UnicodeLanguageUtil.get(pageContext, _key);
				}
				else {
					value = LanguageUtil.get(pageContext, _key);
				}
			}
			else {
				if (_unicode) {
					value = UnicodeLanguageUtil.format(
						pageContext, _key, _arguments, _translateArguments);
				}
				else {
					value = LanguageUtil.format(
						pageContext, _key, _arguments, _translateArguments);
				}
			}

			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(value);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_arguments = null;
				_key = null;
				_localizeKey = true;
				_translateArguments = true;
				_unicode = false;
			}
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
	private String _key;
	private boolean _localizeKey = true;
	private boolean _translateArguments = true;
	private boolean _unicode;

}