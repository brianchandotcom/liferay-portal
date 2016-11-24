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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class PngImageTag extends IncludeTag {

	public void setHeight(String height) {
		_height = height;
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setWidth(String width) {
		_width = width;
	}

	@Override
	protected void cleanUp() {
		_height = null;
		_image = null;
		_width = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:png_image:height", _height);
		request.setAttribute("liferay-ui:png_image:image", _image);
		request.setAttribute("liferay-ui:png_image:width", _width);
	}

	private static final String _PAGE = "/html/taglib/ui/png_image/page.jsp";

	private String _height;
	private String _image;
	private String _width;

}