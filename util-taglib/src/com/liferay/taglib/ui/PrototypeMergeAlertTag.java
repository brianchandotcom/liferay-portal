/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class PrototypeMergeAlertTag extends IncludeTag {

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setLayoutPrototype(LayoutPrototype layoutPrototype) {
		_layoutPrototype = layoutPrototype;
	}

	public void setLayoutSetPrototype(LayoutSetPrototype layoutSetPrototype) {
		_layoutSetPrototype = layoutSetPrototype;
	}

	public void setPrivateLayoutSet(boolean privateLayoutSet) {
		_privateLayoutSet = privateLayoutSet;
	}

	public void setRedirect(String redirect) {
		_redirect = redirect;
	}

	public void setSelPlid(long selPlid) {
		_selPlid = selPlid;
	}

	@Override
	protected void cleanUp() {
		_groupId = 0;
		_layoutPrototype = null;
		_layoutSetPrototype = null;
		_privateLayoutSet = false;
		_redirect = StringPool.BLANK;
		_selPlid = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		boolean merge = false;
		int mergeFailCount = 0;
		PortletURL portletURL = null;
		String type = StringPool.BLANK;

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		if (_layoutPrototype != null) {
			try {
				mergeFailCount = SitesUtil.getMergeFailCount(_layoutPrototype);

				type = "page-template";

				if (mergeFailCount > GetterUtil.getInteger(
						PropsUtil.get(
							PropsKeys.LAYOUT_PROTOTYPE_MERGE_FAIL_THRESHOLD))) {

					portletURL = liferayPortletResponse.createActionURL();

					portletURL.setParameter("redirect", _redirect);
					portletURL.setParameter(
						"layoutPrototypeId",
						String.valueOf(
							_layoutPrototype.getLayoutPrototypeId()));

					if (_selPlid > 0) {
						portletURL.setParameter(
							"struts_action", "/layouts_admin/edit_layouts");
						portletURL.setParameter(
							Constants.CMD,
							Constants.RESET_MERGE_FAIL_COUNT_AND_MERGE);
						portletURL.setParameter(
							"selPlid", String.valueOf(_selPlid));

						merge = true;
					}
					else {
						portletURL.setParameter(
							"struts_action",
							"/layout_prototypes/edit_layout_prototype");
						portletURL.setParameter(
							Constants.CMD, Constants.RESET_MERGE_FAIL_COUNT);
					}
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to get attributes for layoutPrototype " +
							_layoutPrototype.getLayoutPrototypeId());
				}
			}
		}
		else if (_layoutSetPrototype != null) {
			try {
				mergeFailCount = SitesUtil.getMergeFailCount(
					_layoutSetPrototype);

				type = "site-template";

				if (mergeFailCount > GetterUtil.getInteger(
						PropsUtil.get(
							PropsKeys.
								LAYOUT_SET_PROTOTYPE_MERGE_FAIL_THRESHOLD))) {

					portletURL = liferayPortletResponse.createActionURL();

					portletURL.setParameter("redirect", _redirect);
					portletURL.setParameter(
						"layoutSetPrototypeId",
						String.valueOf(
							_layoutSetPrototype.getLayoutSetPrototypeId()));

					if (_groupId > 0) {
						portletURL.setParameter(
							"struts_action", "/sites_admin/edit_site");
						portletURL.setParameter(
							Constants.CMD,
							Constants.RESET_MERGE_FAIL_COUNT_AND_MERGE);
						portletURL.setParameter(
							"groupId", String.valueOf(_groupId));
						portletURL.setParameter(
							"privateLayoutSet",
							String.valueOf(_privateLayoutSet));

						merge = true;
					}
					else {
						portletURL.setParameter(
							"struts_action",
							"/layout_set_prototypes/edit_layout_set_prototype");
						portletURL.setParameter(
							Constants.CMD, Constants.RESET_MERGE_FAIL_COUNT);
					}
				}
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to get attributes for layoutSetPrototype " +
							_layoutSetPrototype.getLayoutSetPrototypeId());
				}
			}
		}

		request.setAttribute(
			"liferay-ui:prototype-merge-alert:merge", String.valueOf(merge));
		request.setAttribute(
			"liferay-ui:prototype-merge-alert:mergeFailCount",
			String.valueOf(mergeFailCount));
		request.setAttribute(
			"liferay-ui:prototype-merge-alert:portletURL", portletURL);
		request.setAttribute("liferay-ui:prototype-merge-alert:type", type);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/ui/prototype_merge_alert/page.jsp";

	private static Log _log = LogFactoryUtil.getLog(
		PrototypeMergeAlertTag.class);

	private long _groupId;
	private LayoutPrototype _layoutPrototype;
	private LayoutSetPrototype _layoutSetPrototype;
	private boolean _privateLayoutSet;
	private String _redirect;
	private long _selPlid;

}