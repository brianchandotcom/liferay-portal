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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.PortalIncludeUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.aui.ScriptTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconMenuTag extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doAfterBody() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper iconCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:icon-menu:icon-count");

		Boolean singleIcon = (Boolean)request.getAttribute(
			"liferay-ui:icon-menu:single-icon");

		if ((iconCount != null) && (iconCount.getValue() == 1) &&
			(singleIcon == null)) {

			bodyContent.clearBody();

			ScriptData scriptData = (ScriptData)request.getAttribute(
				WebKeys.AUI_SCRIPT_DATA);

			if (scriptData != null) {
				scriptData.reset();
			}

			request.setAttribute(
				"liferay-ui:icon-menu:single-icon", Boolean.TRUE);

			return EVAL_BODY_AGAIN;
		}
		else {
			return SKIP_BODY;
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			return processEndTag();
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			if (!ServerDetector.isResin()) {
				_cssClass = null;
				_direction = "left";
				_endPage = null;
				_extended = true;
				_icon = null;
				_id = null;
				_localizeMessage = true;
				_maxDisplayItems = _DEFAULT_MAX_DISPLAY_ITEMS;
				_message = "actions";
				_showArrow = true;
				_showExpanded = false;
				_showWhenSingleIcon = false;
				_startPage = null;
			}
		}
	}

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ScriptData scriptData = (ScriptData)request.getAttribute(
			WebKeys.AUI_SCRIPT_DATA);

		if (scriptData != null) {
			scriptData.mark();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_direction == null) {
			_direction = "left";
		}

		if (_icon == null) {
			_icon = themeDisplay.getPathThemeImages() + "/common/tool.png";
		}

		if (Validator.isNull(_id)) {
			_id = (String)request.getAttribute(
				"liferay-ui:search-container-row:rowId");

			if (Validator.isNull(_id)) {
				_id = PortalUtil.generateRandomKey(
					request, IconMenuTag.class.getName());
			}

			_id = _id.concat("_menu");
		}

		request.setAttribute("liferay-ui:icon-menu:id", _id);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String namespace = portletDisplay.getNamespace();

		_id = namespace.concat(_id);

		request.setAttribute(
			"liferay-ui:icon-menu:icon-count", new IntegerWrapper());
		request.setAttribute(
			"liferay-ui:icon-menu:showWhenSingleIcon",
			String.valueOf(_showWhenSingleIcon));

		return EVAL_BODY_BUFFERED;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDirection(String direction) {
		_direction = direction;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setEndPage(String endPage) {
		_endPage = endPage;
	}

	public void setExtended(boolean extended) {
		_extended = extended;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLocalizeMessage(boolean localizeMessage) {
		_localizeMessage = localizeMessage;
	}

	public void setMaxDisplayItems(int maxDisplayItems) {
		if (maxDisplayItems <= 0) {
			maxDisplayItems = _DEFAULT_MAX_DISPLAY_ITEMS;
		}

		_maxDisplayItems = maxDisplayItems;
	}

	public void setMessage(String message) {
		if (message != null) {
			_message = message;
		}
	}

	public void setShowArrow(boolean showArrow) {
		_showArrow = showArrow;
	}

	public void setShowExpanded(boolean showExpanded) {
		_showExpanded = showExpanded;
	}

	public void setShowWhenSingleIcon(boolean showWhenSingleIcon) {
		_showWhenSingleIcon = showWhenSingleIcon;
	}

	public void setStartPage(String startPage) {
		_startPage = startPage;
	}

	protected String getEndPage() {
		if (Validator.isNull(_endPage)) {
			return _END_PAGE;
		}
		else {
			return _endPage;
		}
	}

	protected String getStartPage() {
		if (Validator.isNull(_startPage)) {
			return _START_PAGE;
		}
		else {
			return _startPage;
		}
	}

	protected int processEndTag() throws Exception {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		IntegerWrapper iconCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:icon-menu:icon-count");

		request.removeAttribute("liferay-ui:icon-menu:icon-count");
		request.removeAttribute("liferay-ui:icon-menu:id");

		Boolean singleIcon = (Boolean)request.getAttribute(
			"liferay-ui:icon-menu:single-icon");

		request.removeAttribute("liferay-ui:icon-menu:single-icon");

		JspWriter jspWriter = pageContext.getOut();

		if ((iconCount != null) && (iconCount.getValue() >= 1) &&
			((singleIcon == null) || _showWhenSingleIcon)) {

			if (!FileAvailabilityUtil.isAvailable(
					pageContext.getServletContext(), getStartPage())) {

				if (_showExpanded) {
					jspWriter.write("<ul class=\"dropdown-menu lfr-menu-list");
					jspWriter.write(" lfr-menu-expanded ");
					jspWriter.print(_cssClass);
					jspWriter.write("\" id=\"");
					jspWriter.write(_id);
					jspWriter.write("\">");
				}
				else {
					jspWriter.write("<div class=\"btn-group");

					if (Validator.isNotNull(_cssClass)) {
						jspWriter.write(StringPool.SPACE);
						jspWriter.print(_cssClass);
					}

					if (_direction.equals("up")) {
						jspWriter.write(" dropup");
					}

					jspWriter.write("\"><a class=\"dropdown-toggle direction-");
					jspWriter.write(_direction);
					jspWriter.write(" max-display-items-");
					jspWriter.write(String.valueOf(_maxDisplayItems));

					if (_disabled) {
						jspWriter.write(" disabled");
					}

					if (_extended) {
						jspWriter.write(" btn");
					}

					String message = _message;

					if (_localizeMessage) {
						message = LanguageUtil.get(pageContext, _message);
					}

					jspWriter.write("\" href=\"javascript:;\" id=\"");
					jspWriter.write(_id);
					jspWriter.write("\" title=\"");
					jspWriter.write(message);
					jspWriter.write("\">");

					if (_showArrow && _direction.equals("left")) {
						jspWriter.write("<i class=\"caret\"></i>&nbsp;");
					}

					if (Validator.isNotNull(_icon)) {
						jspWriter.write("<img alt=\"\" src=\"");
						jspWriter.write(_icon);
						jspWriter.write("\" />&nbsp;");
					}

					jspWriter.write(message);

					if (_showArrow && !_direction.equals("left")) {
						jspWriter.write("&nbsp;<i class=\"caret\"></i>");
					}

					jspWriter.write("</a>");

					ScriptTag.doTag(
						null, "liferay-menu",
						"Liferay.Menu.register('" + _id + "');", bodyContent,
						pageContext);

					jspWriter.write("<ul class=\"dropdown-menu lfr-menu-list");
					jspWriter.write(" direction-");
					jspWriter.write(_direction);
					jspWriter.write("\">");
				}
			}
			else {
				PortalIncludeUtil.include(pageContext, getStartPage());
			}
		}

		writeBodyContent(jspWriter);

		if ((iconCount != null) && (iconCount.getValue() >= 1) &&
			((singleIcon == null) || _showWhenSingleIcon)) {

			if (!FileAvailabilityUtil.isAvailable(
					pageContext.getServletContext(), getEndPage())) {

				jspWriter.write("</ul>");

				if (_showExpanded) {
					ScriptTag.doTag(
						null, "liferay-menu",
						"Liferay.Menu.handleFocus('#" + _id + "menu');",
						bodyContent, pageContext);
				}
				else {
					jspWriter.write("</div>");
				}
			}
			else {
				PortalIncludeUtil.include(pageContext, getEndPage());
			}
		}

		request.removeAttribute("liferay-ui:icon-menu:showWhenSingleIcon");

		return EVAL_PAGE;
	}

	private static final int _DEFAULT_MAX_DISPLAY_ITEMS = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.ICON_MENU_MAX_DISPLAY_ITEMS));

	private static final String _END_PAGE = "/html/taglib/ui/icon_menu/end.jsp";

	private static final String _START_PAGE =
		"/html/taglib/ui/icon_menu/start.jsp";

	private String _cssClass;
	private String _direction = "left";
	private boolean _disabled;
	private String _endPage;
	private boolean _extended = true;
	private String _icon;
	private String _id;
	private boolean _localizeMessage = true;
	private int _maxDisplayItems = _DEFAULT_MAX_DISPLAY_ITEMS;
	private String _message = "actions";
	private boolean _showArrow = true;
	private boolean _showExpanded;
	private boolean _showWhenSingleIcon;
	private String _startPage;

}