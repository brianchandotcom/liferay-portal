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

package com.liferay.taglib.portletext;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconTracker;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletConfigurationIconComparator;
import com.liferay.taglib.servlet.PipingServletResponse;
import com.liferay.taglib.ui.IconMenuTag;
import com.liferay.taglib.ui.IconTag;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class IconOptionsTag extends IconTag {

	public String getDirection() {
		return _direction;
	}

	public List<PortletConfigurationIcon> getPortletConfigurationIcons() {
		if (_portletConfigurationIcons != null) {
			return _portletConfigurationIcons;
		}

		_portletConfigurationIcons =
			PortletConfigurationIconTracker.getPortletConfigurationIcons(
				getPortletId(), getPortletRequest(),
				PortletConfigurationIconComparator.INSTANCE);

		return _portletConfigurationIcons;
	}

	public boolean isShowArrow() {
		return _showArrow;
	}

	public void setDirection(String direction) {
		_direction = direction;
	}

	public void setPortletConfigurationIcons(
		List<PortletConfigurationIcon> portletConfigurationIcons) {

		_portletConfigurationIcons = portletConfigurationIcons;
	}

	public void setShowArrow(boolean showArrow) {
		_showArrow = showArrow;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_direction = "right";
		_portletConfigurationIcons = null;
		_showArrow = true;
	}

	@Override
	protected String getPage() {
		return "/html/taglib/portlet/icon_options/page.jsp";
	}

	protected String getPortletId() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getRootPortletId();
	}

	protected PortletRequest getPortletRequest() {
		HttpServletRequest httpServletRequest = getRequest();

		return (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	protected PortletResponse getPortletResponse() {
		HttpServletRequest httpServletRequest = getRequest();

		return (PortletResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	@Override
	protected int processEndTag() throws Exception {
		IconMenuTag iconMenuTag = new IconMenuTag();

		iconMenuTag.setCssClass("portlet-options");
		iconMenuTag.setDirection(_direction);
		iconMenuTag.setExtended(false);
		iconMenuTag.setIcon("ellipsis-v");
		iconMenuTag.setMarkupView("lexicon");
		iconMenuTag.setMessage("options");
		iconMenuTag.setShowArrow(false);
		iconMenuTag.setShowWhenSingleIcon(true);
		iconMenuTag.setTriggerCssClass("icon-monospaced");

		iconMenuTag.doBodyTag(
			pageContext, this::_processPortletConfigurationIcons);

		return EVAL_PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-ui:icon-options:portletConfigurationIcons",
			getPortletConfigurationIcons());
		httpServletRequest.setAttribute(
			"liferay-ui:icon:direction", _direction);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:showArrow", String.valueOf(_showArrow));
	}

	private void _processPortletConfigurationIcons(PageContext pageContext) {
		try {
			HttpServletRequest httpServletRequest =
				(HttpServletRequest)pageContext.getRequest();

			PortletRequest portletRequest =
				(PortletRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			PortletResponse portletResponse =
				(PortletResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			for (PortletConfigurationIcon portletConfigurationIcon :
					_portletConfigurationIcons) {

				boolean include = portletConfigurationIcon.include(
					httpServletRequest,
					PipingServletResponse.createPipingServletResponse(
						pageContext));

				if (!include) {
					IconTag iconTag = new IconTag();

					iconTag.setAlt(portletConfigurationIcon.getAlt());
					iconTag.setAriaRole(portletConfigurationIcon.getAriaRole());
					iconTag.setCssClass(portletConfigurationIcon.getCssClass());
					iconTag.setData(portletConfigurationIcon.getData());
					iconTag.setIconCssClass(
						portletConfigurationIcon.getIconCssClass());
					iconTag.setId(portletConfigurationIcon.getId());
					iconTag.setImage(portletConfigurationIcon.getImage());
					iconTag.setImageHover(
						portletConfigurationIcon.getImageHover());
					iconTag.setLabel(portletConfigurationIcon.isLabel());
					iconTag.setLang(portletConfigurationIcon.getLang());
					iconTag.setLinkCssClass(
						"dropdown-item " +
							portletConfigurationIcon.getLinkCssClass());
					iconTag.setLocalizeMessage(false);
					iconTag.setMessage(
						portletConfigurationIcon.getMessage(portletRequest));
					iconTag.setMethod(portletConfigurationIcon.getMethod());
					iconTag.setOnClick(
						portletConfigurationIcon.getOnClick(
							portletRequest, portletResponse));
					iconTag.setSrc(portletConfigurationIcon.getSrc());
					iconTag.setSrcHover(portletConfigurationIcon.getSrcHover());
					iconTag.setTarget(portletConfigurationIcon.getTarget());
					iconTag.setToolTip(portletConfigurationIcon.isToolTip());
					iconTag.setUrl(
						portletConfigurationIcon.getURL(
							portletRequest, portletResponse));
					iconTag.setUseDialog(
						portletConfigurationIcon.isUseDialog());

					iconTag.doTag(pageContext);
				}
			}
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
	}

	private String _direction = "right";
	private List<PortletConfigurationIcon> _portletConfigurationIcons;
	private boolean _showArrow = true;

}