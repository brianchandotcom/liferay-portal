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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author David Truong
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class SocialBookmarkTag extends IncludeTag {

	public void setContentId(String contentId) {
		_contentId = contentId;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setType(String type) {
		_type = type;

		_postURL = PropsUtil.get(
			PropsKeys.SOCIAL_BOOKMARK_POST_URL, new Filter(_type));
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		_contentId = null;
		_target = null;
		_title = null;
		_type = null;
		_url = null;
	}

	protected String getDisplayStyle() {
		String displayStyle = _displayStyle;

		if (Validator.isNull(displayStyle)) {
			String[] displayStyles = PropsUtil.getArray(
				PropsKeys.SOCIAL_BOOKMARK_DISPLAY_STYLES);

			displayStyle = displayStyles[0];
		}

		return displayStyle;
	}

	@Override
	protected String getPage() {
		String[] socialTypes = PropsUtil.getArray(
			PropsKeys.SOCIAL_BOOKMARK_TYPES);

		if (ArrayUtil.contains(socialTypes, _type)) {
			String displayStyle = getDisplayStyle();

			if (!displayStyle.equals("menu") && Validator.isNotNull(_jspPath)) {
				return _jspPath;
			}
			else {
				return _PAGE;
			}
		}
		else {
			return null;
		}
	}

	protected String getPostUrl() {
		return StringUtil.replace(
			_postURL,
			new String[] {
				"${liferay:social-bookmark:title}",
				"${liferay:social-bookmark:url}"
			},
			new String[] {URLCodec.encodeURL(_title), _url});
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String jspPath = _jspPaths.get(_type);

		if (jspPath == null) {
			jspPath = PropsUtil.get(
				PropsKeys.SOCIAL_BOOKMARK_JSP, new Filter(_type));

			_jspPaths.put(_type, jspPath);
		}

		_jspPath = jspPath;

		String icon = _icon;

		String displayStyle = getDisplayStyle();

		if (displayStyle.equals("menu") || Validator.isNull(_jspPath)) {
			if (Validator.isNull(icon)) {
				icon = PropsUtil.get(
					PropsKeys.SOCIAL_BOOKMARK_ICON, new Filter(_type));

				if (Validator.isNull(icon)) {
					icon = "../aui/share-sign";
				}
			}

			request.setAttribute("liferay-ui:social-bookmark:icon", icon);
			request.setAttribute(
				"liferay-ui:social-bookmark:postUrl", getPostUrl());
		}

		request.setAttribute(
			"liferay-ui:social-bookmark:contentId", _contentId);
		request.setAttribute(
			"liferay-ui:social-bookmark:displayStyle", _displayStyle);
		request.setAttribute("liferay-ui:social-bookmark:target", _target);
		request.setAttribute("liferay-ui:social-bookmark:title", _title);
		request.setAttribute("liferay-ui:social-bookmark:type", _type);
		request.setAttribute("liferay-ui:social-bookmark:url", _url);
	}

	private static final String _PAGE =
		"/html/taglib/ui/social_bookmark/page.jsp";

	private static final Map<String, String> _jspPaths = new HashMap<>();

	private String _contentId;
	private String _displayStyle;
	private String _icon;
	private String _jspPath;
	private String _postURL;
	private String _target;
	private String _title;
	private String _type;
	private String _url;

}