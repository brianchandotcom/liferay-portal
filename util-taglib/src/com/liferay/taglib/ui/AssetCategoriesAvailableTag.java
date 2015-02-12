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

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;
import com.liferay.taglib.TagSupport;

import java.util.List;

import javax.servlet.jsp.JspException;

/**
 * @author Sergio González
 */
public class AssetCategoriesAvailableTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		try {
			List<AssetCategory> categories =
				AssetCategoryServiceUtil.getCategories(_className, _classPK);

			if (!categories.isEmpty()) {
				return EVAL_BODY_INCLUDE;
			}

			return SKIP_BODY;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_className = null;
				_classPK = 0;
			}
		}
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private String _className;
	private long _classPK;

}