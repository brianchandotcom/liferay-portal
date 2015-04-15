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

package com.liferay.taglib.staging;

import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Levente Hudák
 */
public class DefineObjectsTag extends IncludeTag {

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(request, "groupId");

		Group group = GroupLocalServiceUtil.fetchGroup(groupId);

		if (group == null) {
			group = (Group)request.getAttribute(WebKeys.GROUP);
		}

		if (group == null) {
			group = themeDisplay.getScopeGroup();
		}

		if (group == null) {
			return SKIP_BODY;
		}

		pageContext.setAttribute("group", group);
		pageContext.setAttribute("groupId", group.getGroupId());
		pageContext.setAttribute("liveGroup", null);
		pageContext.setAttribute("liveGroupId", 0L);

		Layout layout = themeDisplay.getLayout();

		pageContext.setAttribute("stagingGroup", null);
		pageContext.setAttribute("stagingGroupId", 0L);

		if (!group.isStaged() && !group.isStagedRemotely() &&
			!group.hasLocalOrRemoteStagingGroup()) {

			return SKIP_BODY;
		}

		Group liveGroup = StagingUtil.getLiveGroup(group.getGroupId());
		Group stagingGroup = StagingUtil.getStagingGroup(group.getGroupId());

		pageContext.setAttribute("liveGroup", liveGroup);
		pageContext.setAttribute("liveGroupId", liveGroup.getGroupId());
		pageContext.setAttribute("stagingGroup", stagingGroup);
		pageContext.setAttribute("stagingGroupId", stagingGroup.getGroupId());

		if (Validator.isNotNull(_portletId)) {
			boolean stagedPortlet = liveGroup.isStagedPortlet(_portletId);

			if (group.isStagedRemotely()) {
				stagedPortlet = stagingGroup.isStagedPortlet(_portletId);
			}

			if (stagedPortlet) {
				pageContext.setAttribute("group", stagingGroup);
				pageContext.setAttribute("groupId", stagingGroup.getGroupId());
				pageContext.setAttribute("scopeGroup", stagingGroup);
				pageContext.setAttribute(
					"scopeGroupId", stagingGroup.getGroupId());
			}
		}

		return SKIP_BODY;
	}

	@Override
	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	@Override
	protected void cleanUp() {
		_portletId = null;
	}

	private String _portletId;

}