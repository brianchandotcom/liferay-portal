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
 * @author Sergio González
 */
public class ImageSelectorTag extends IncludeTag {

	public void setDraggableImage(String draggableImage) {
		_draggableImage = draggableImage;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public void setParamName(String paramName) {
		_paramName = paramName;
	}

	public void setValidExtensions(String validExtensions) {
		_validExtensions = validExtensions;
	}

	@Override
	protected void cleanUp() {
		_draggableImage = "none";
		_fileEntryId = 0;
		_paramName = "imageSelectorFileEntryId";
		_validExtensions = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:image-selector:draggableImage", _draggableImage);
		request.setAttribute(
			"liferay-ui:image-selector:fileEntryId", _fileEntryId);
		request.setAttribute("liferay-ui:image-selector:paramName", _paramName);
		request.setAttribute(
			"liferay-ui:image-selector:validExtensions", _validExtensions);
	}

	private static final String _PAGE =
		"/html/taglib/ui/image_selector/page.jsp";

	private String _draggableImage = "none";
	private long _fileEntryId;
	private String _paramName = "imageSelectorFileEntry";
	private String _validExtensions;

}