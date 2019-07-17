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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Scott Lee
 * @author Shuyang Zhou
 */
public class IconHelpTag extends IconTag {

	@Override
	protected String getPage() {
		return super.getPage();
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		setCssClass("taglib-icon-help");
		setIcon("question-circle-full");
		setId(StringUtil.randomId());
		setLocalizeMessage(false);
		setMarkupView("lexicon");

		setMessage(
			LanguageUtil.get(
				TagResourceBundleUtil.getResourceBundle(pageContext),
				getMessage()));

		setToolTip(true);

		super.setAttributes(httpServletRequest);
	}

}