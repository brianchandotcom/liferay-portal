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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Shuyang Zhou
 */
public class OutputTag extends PositionTagSupport {

	public OutputTag(String stringBundlerKey) {
		_webKey = stringBundlerKey;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			if (_output) {
				HttpServletRequest request =
					(HttpServletRequest)pageContext.getRequest();

				StringBundler sb = (StringBundler)request.getAttribute(_webKey);

				if (sb == null) {
					sb = new StringBundler();

					request.setAttribute(_webKey, sb);
				}

				sb.append(getBodyContentAsStringBundler());
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				cleanUp();
			}
		}
	}

	@Override
	public int doStartTag() {
		if (Validator.isNotNull(_outputKey)) {
			Set<String> outputKeys = getOutputKeys();

			if (!outputKeys.add(_outputKey)) {
				_output = false;

				return SKIP_BODY;
			}
		}

		if (isPositionInLine()) {
			_output = false;

			return EVAL_BODY_INCLUDE;
		}
		else {
			_output = true;

			return EVAL_BODY_BUFFERED;
		}
	}

	public void setOutputKey(String outputKey) {
		_outputKey = outputKey;
	}

	protected Set<String> getOutputKeys() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		Set<String> outputKeys = (Set<String>)request.getAttribute(
			OutputTag.class.getName());

		if (outputKeys == null) {
			outputKeys = new HashSet<String>();

			request.setAttribute(OutputTag.class.getName(), outputKeys);
		}

		return outputKeys;
	}

	private boolean _output;
	private String _outputKey;
	private String _webKey;

}