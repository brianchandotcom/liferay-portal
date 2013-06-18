/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eduardo Lundgren
 */
public class PositionTagSupport extends BaseBodyTagSupport implements BodyTag {

	public String getPosition() {
		return getPositionValue();
	}

	public boolean isPositionAuto() {
		String position = getPosition();

		if (position.equals(_POSITION_AUTO)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isPositionInLine() {
		String position = getPosition();

		if (position.equals(_POSITION_INLINE)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setPosition(String position) {
		_position = position;
	}

	protected void cleanUp() {
		_position = null;
	}

	protected String getPositionValue() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		String position = _position;

		String fragmentId = ParamUtil.getString(request, "p_f_id");

		if (Validator.isNotNull(fragmentId)) {
			position = _POSITION_INLINE;
		}

		if (Validator.isNull(position)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (themeDisplay.isIsolated() || themeDisplay.isStateExclusive()) {
				position = _POSITION_INLINE;
			}
			else {
				position = _POSITION_AUTO;
			}
		}

		return position;
	}

	private static final String _POSITION_AUTO = "auto";

	private static final String _POSITION_INLINE = "inline";

	private String _position;

}