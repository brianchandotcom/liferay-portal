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

package com.liferay.taglib.search;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;

import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ButtonSearchEntry extends TextSearchEntry {

	@Override
	public Object clone() {
		ButtonSearchEntry buttonSearchEntry = new ButtonSearchEntry();

		BeanPropertiesUtil.copyProperties(this, buttonSearchEntry);

		return buttonSearchEntry;
	}

	@Override
	public void print(Object object) throws Exception {
		if (!(object instanceof PageContext)) {
			return;
		}

		PageContext pageContext = (PageContext)object;

		StringBuilder sb = new StringBuilder();

		sb.append("<input type=\"button\" ");
		sb.append("value=\"");
		sb.append(getName());
		sb.append("\" onClick=\"");
		sb.append(getHref());
		sb.append("\">");

		pageContext.getOut().print(sb.toString());
	}

}