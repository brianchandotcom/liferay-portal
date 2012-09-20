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
				_align = "right";
				_cssClass = null;
				_direction = null;
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
			if (_align.equals("left")) {
				_direction = "right";
			}
			else {
				_direction = "left";
			}
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

	public void setAlign(String align) {
		_align = align;
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
					jspWriter.write("<div class=\"lfr-component ");
					jspWriter.write("lfr-menu-list lfr-menu-expanded align-");
					jspWriter.write(_align);
					jspWriter.write(" ");
					jspWriter.print(_cssClass);
					jspWriter.write("\" id=\"");
					jspWriter.write(_id);
					jspWriter.write("\">");
				}
				else {
					String message = _message;

					if (_localizeMessage) {
						message = LanguageUtil.get(pageContext, _message);
					}

					jspWriter.write("<span title=\"");
					jspWriter.write(message);
					jspWriter.write("\"><ul class='lfr-component lfr-actions ");
					jspWriter.write("align-");
					jspWriter.write(_align);
					jspWriter.write(" direction-");
					jspWriter.write(_direction);
					jspWriter.write(" max-display-items-");
					jspWriter.write(String.valueOf(_maxDisplayItems));
					jspWriter.write(" ");

					if (Validator.isNotNull(_cssClass)) {
						jspWriter.print(_cssClass);
					}

					if (_disabled) {
						jspWriter.write(" disabled");
					}

					if (_extended) {
						jspWriter.write(" lfr-extended");
					}

					if (_showArrow) {
						jspWriter.write(" show-arrow");
					}

					jspWriter.write("\' id=\"");
					jspWriter.write(_id);
					jspWriter.write("\">");
					jspWriter.write("<li class=\"lfr-trigger\"><strong>");
					jspWriter.write(
						"<a class=\"nobr\" href=\"javascript:;\" id=\"");
					jspWriter.write(_id);
					jspWriter.write("Button\">");

					if (Validator.isNotNull(_icon)) {
						jspWriter.write("<img alt=\"\" src=\"");
						jspWriter.write(_icon);
						jspWriter.write("\" />");
					}

					jspWriter.write("<span class=\"taglib-text\">");
					jspWriter.write(message);
					jspWriter.write("</span></a></strong>");

					ScriptTag.doTag(
						null, "liferay-menu",
						"Liferay.Menu.register('" + _id + "');", bodyContent,
						pageContext);
				}

				jspWriter.write("<ul>");
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
					jspWriter.write("</div>");

					ScriptTag.doTag(
						null, "liferay-menu",
						"Liferay.Menu.handleFocus('#" + _id + "menu');",
						bodyContent, pageContext);
				}
				else {
					jspWriter.write("</li></ul></span>");
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

	private String _align = "right";
	private String _cssClass;
	private String _direction;
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