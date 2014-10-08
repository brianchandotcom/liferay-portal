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

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

/**
 * @author Brian Wing Shun Chan
 */
public class SectionTei extends TagExtraInfo {

	@Override
	public VariableInfo[] getVariableInfo(TagData tagData) {
		return _variableInfo;
	}

	private static final VariableInfo[] _variableInfo = new VariableInfo[] {
		new VariableInfo(
			"sectionParam", String.class.getName(), true, VariableInfo.NESTED),
		new VariableInfo(
			"sectionName", String.class.getName(), true, VariableInfo.NESTED),
		new VariableInfo(
			"sectionSelected", Boolean.class.getName(), true,
			VariableInfo.NESTED),
		new VariableInfo(
			"sectionScroll", String.class.getName(), true, VariableInfo.NESTED),
		new VariableInfo(
			"sectionRedirectParams", String.class.getName(), true,
			VariableInfo.NESTED)
	};

}