/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.taglib.ui;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Keith R. Davis
 * @author Iliyan Peychev
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *             com.liferay.document.library.taglib.servlet.taglib.UploadProgressTag}
 */
@Deprecated
public class UploadProgressTag extends IncludeTag {

	public int getHeight() {
		return _height;
	}

	public String getId() {
		return _id;
	}

	public String getMessage() {
		return _message;
	}

	public Integer getUpdatePeriod() {
		return _updatePeriod;
	}

	public void setHeight(int height) {
		_height = height;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setUpdatePeriod(Integer updatePeriod) {
		_updatePeriod = updatePeriod;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_height = 25;
		_id = null;
		_message = null;
		_updatePeriod = 1000;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("liferay-ui:progress:height", _height);
		httpServletRequest.setAttribute("liferay-ui:progress:id", _id);
		httpServletRequest.setAttribute(
			"liferay-ui:progress:message", _message);
		httpServletRequest.setAttribute(
			"liferay-ui:progress:updatePeriod", _updatePeriod);
	}

	private static final String _PAGE = "/html/taglib/ui/progress/page.jsp";

	private Integer _height = 25;
	private String _id;
	private String _message;
	private Integer _updatePeriod = 1000;

}