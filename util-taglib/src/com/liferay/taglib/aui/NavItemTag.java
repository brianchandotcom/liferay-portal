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

package com.liferay.taglib.aui;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.aui.base.BaseNavItemTag;
import com.liferay.taglib.util.TagResourceBundleUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 * @author Carlos Sierra Andrés
 */
public class NavItemTag extends BaseNavItemTag implements BodyTag {

	@Override
	public int doStartTag() throws JspException {
		NavBarTag navBarTag = (NavBarTag)findAncestorWithClass(
			this, NavBarTag.class);

		if ((navBarTag != null) && getSelected()) {
			String title = getTitle();

			if (Validator.isNull(title)) {
				title = getLabel();
			}

			navBarTag.setSelectedItemName(title);
		}

		super.doStartTag();

		return BodyTag.EVAL_BODY_BUFFERED;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		if (!getUseDialog() && AUIUtil.isOpensNewWindow(getTarget())) {
			String title = getTitle();

			if (title == null) {
				title = StringPool.BLANK;
			}

			title = title.concat(
				LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					"opens-new-window"));

			setNamespacedAttribute(
				httpServletRequest, "title", String.valueOf(title));
		}
	}

}