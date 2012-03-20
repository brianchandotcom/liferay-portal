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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchPaginatorTag<R> extends SearchFormTag<R> {

	public void setId(String id) {
		_id = id;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_type = "regular";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute("liferay-ui:search:id", _id);
		request.setAttribute("liferay-ui:search:type", _type);
	}

	private static final String _PAGE =
		"/html/taglib/ui/search_paginator/page.jsp";

	private String _id;
	private String _type = "regular";

}