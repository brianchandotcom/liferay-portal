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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class InputPermissionsParamsTag extends TagSupport {

	public static String doTag(String modelName, HttpServletRequest request)
		throws Exception {

		try {
			RenderResponse renderResponse =
				(RenderResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			Layout layout = themeDisplay.getLayout();

			Group layoutGroup = layout.getGroup();

			Group group = themeDisplay.getScopeGroup();

			List<String> supportedActions =
				ResourceActionsUtil.getModelResourceActions(modelName);
			List<String> groupDefaultActions =
				ResourceActionsUtil.getModelResourceGroupDefaultActions(
					modelName);
			List<String> guestDefaultActions =
				ResourceActionsUtil.getModelResourceGuestDefaultActions(
					modelName);
			List<String> guestUnsupportedActions =
				ResourceActionsUtil.getModelResourceGuestUnsupportedActions(
					modelName);

			StringBundler sb = new StringBundler();

			for (int i = 0; i < supportedActions.size(); i++) {
				String action = supportedActions.get(i);

				boolean groupChecked = groupDefaultActions.contains(action);

				boolean guestChecked = false;

				if (layoutGroup.isControlPanel()) {
					if (guestDefaultActions.contains(action)) {
						guestChecked = true;
					}
				}
				else if (layout.isPublicLayout() &&
						 guestDefaultActions.contains(action)) {

					guestChecked = true;
				}

				boolean guestDisabled = guestUnsupportedActions.contains(
					action);

				if (guestDisabled) {
					guestChecked = false;
				}

				if (group.isOrganization() || group.isRegularSite()) {
					if (groupChecked) {
						sb.append(StringPool.AMPERSAND);
						sb.append(renderResponse.getNamespace());
						sb.append("groupPermissions=");
						sb.append(HttpUtil.encodeURL(action));
					}
				}

				if (guestChecked) {
					sb.append(StringPool.AMPERSAND);
					sb.append(renderResponse.getNamespace());
					sb.append("guestPermissions=");
					sb.append(HttpUtil.encodeURL(action));
				}
			}

			String inputPermissionsViewRole = getDefaultViewRole(
				modelName, themeDisplay);

			sb.append(StringPool.AMPERSAND);
			sb.append(renderResponse.getNamespace());
			sb.append("inputPermissionsViewRole=");
			sb.append(HttpUtil.encodeURL(inputPermissionsViewRole));

			return sb.toString();
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public static String getDefaultViewRole(
			String modelName, ThemeDisplay themeDisplay)
		throws PortalException {

		Layout layout = themeDisplay.getLayout();

		Group layoutGroup = layout.getGroup();

		List<String> guestDefaultActions =
			ResourceActionsUtil.getModelResourceGuestDefaultActions(modelName);

		if (layoutGroup.isControlPanel()) {
			if (guestDefaultActions.contains(ActionKeys.VIEW)) {
				return RoleConstants.GUEST;
			}
		}
		else if (layout.isPublicLayout() &&
				 guestDefaultActions.contains(ActionKeys.VIEW)) {

			return RoleConstants.GUEST;
		}

		List<String> groupDefaultActions =
			ResourceActionsUtil.getModelResourceGroupDefaultActions(modelName);

		if (groupDefaultActions.contains(ActionKeys.VIEW)) {
			Group siteGroup = GroupLocalServiceUtil.getGroup(
				themeDisplay.getSiteGroupId());

			Role defaultGroupRole = RoleLocalServiceUtil.getDefaultGroupRole(
				siteGroup.getGroupId());

			return defaultGroupRole.getName();
		}

		return RoleConstants.OWNER;
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(_modelName);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setModelName(String modelName) {
		_modelName = modelName;
	}

	private String _modelName;

}