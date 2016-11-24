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

package com.liferay.taglib.security;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.security.Key;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class EncryptTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write("</a>");

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			StringBundler sb = new StringBundler();

			// Open anchor

			sb.append("<a ");

			// Class

			if (Validator.isNotNull(_className)) {
				sb.append("class=\"");
				sb.append(_className);
				sb.append("\" ");
			}

			// HREF

			sb.append("href=\"");
			sb.append(_protocol);
			sb.append(Http.PROTOCOL_DELIMITER);

			int pos = _url.indexOf(CharPool.QUESTION);

			if (pos == -1) {
				sb.append(_url);
			}
			else {
				sb.append(_url.substring(0, pos));
				sb.append(StringPool.QUESTION);

				Company company = PortalUtil.getCompany(
					(HttpServletRequest)pageContext.getRequest());

				Key key = company.getKeyObj();

				StringTokenizer st = new StringTokenizer(
					_url.substring(pos + 1, _url.length()),
					StringPool.AMPERSAND);

				while (st.hasMoreTokens()) {
					String paramAndValue = st.nextToken();

					int x = paramAndValue.indexOf(CharPool.EQUAL);

					String param = paramAndValue.substring(0, x);
					String value = paramAndValue.substring(x + 1);

					sb.append(param).append(StringPool.EQUAL);

					if (_unencryptedParamsSet.contains(param)) {
						sb.append(HttpUtil.encodeURL(value));
					}
					else {
						try {
							sb.append(
								HttpUtil.encodeURL(
									Encryptor.encrypt(key, value)));
						}
						catch (EncryptorException ee) {
							_log.error(ee.getMessage());
						}

						if (st.hasMoreTokens()) {
							sb.append(StringPool.AMPERSAND);
						}
					}
				}

				sb.append("&shuo=1");
			}

			sb.append("\" ");

			// Style

			if (Validator.isNotNull(_style)) {
				sb.append("style=\"");
				sb.append(_style);
				sb.append("\" ");
			}

			// Target

			if (Validator.isNotNull(_target)) {
				sb.append("target=\"");
				sb.append(_target);
				sb.append("\"");
			}

			// Close anchor

			sb.append(">");

			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(sb.toString());

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setProtocol(String protocol) {
		_protocol = protocol;
	}

	public void setStyle(String style) {
		_style = style;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setUnencryptedParams(String unencryptedParams) {
		_unencryptedParamsSet.clear();

		String[] unencryptedParamsArray = StringUtil.split(unencryptedParams);

		for (int i = 0; i < unencryptedParamsArray.length; i++) {
			_unencryptedParamsSet.add(unencryptedParamsArray[i]);
		}
	}

	public void setUrl(String url) {
		_url = url;
	}

	private static final Log _log = LogFactoryUtil.getLog(EncryptTag.class);

	private String _className;
	private String _protocol;
	private String _style;
	private String _target;
	private final Set<String> _unencryptedParamsSet = new HashSet<>();
	private String _url;

}