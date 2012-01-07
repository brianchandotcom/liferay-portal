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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class GetUrlTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		try {
			WebCacheItem wci = new GetUrlWebCacheItem(_url, _expires);

			String content = (String)WebCachePoolUtil.get(
				GetUrlTag.class.getName() + StringPool.PERIOD + _url, wci);

			if (Validator.isNotNull(_var)) {
				pageContext.setAttribute(_var, content);
			}
			else {
				JspWriter jspWriter = pageContext.getOut();

				jspWriter.print(content);
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setExpires(long expires) {
		_expires = expires;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setVar(String var) {
		_var = var;
	}

	private String _url;
	private long _expires = Time.WEEK;
	private String _var;

}