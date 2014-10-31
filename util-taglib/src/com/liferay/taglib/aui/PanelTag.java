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

import com.liferay.portal.kernel.servlet.taglib.aui.ToolTag;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.aui.base.BasePanelTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 * @author Brian Wing Shun Chan
 */
public class PanelTag extends BasePanelTag {

	public void addToolTag(ToolTag toolTag) {
		if (_toolTags == null) {
			_toolTags = new ArrayList<ToolTag>();
		}

		_toolTags.add(toolTag);
	}

	public List<ToolTag> getToolTags() {
		return _toolTags;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		if (_toolTags != null) {
			for (ToolTag toolTag : _toolTags) {
				toolTag.cleanUp();
			}

			_toolTags = null;
		}
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		String id = getId();

		if (Validator.isNull(id)) {
			id = PortalUtil.getUniqueElementId(
				request, StringPool.BLANK, AUIUtil.normalizeId("panel"));
		}

		setNamespacedAttribute(request, "id", id);
		setNamespacedAttribute(request, "toolTags", _toolTags);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private List<ToolTag> _toolTags;

}