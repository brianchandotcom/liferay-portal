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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
import com.liferay.portal.kernel.model.SpriteImage;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class IconTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getIcon() {
		return _icon;
	}

	public void setAlt(String alt) {
		_alt = alt;
	}

	public void setAriaRole(String ariaRole) {
		_ariaRole = ariaRole;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setIconCssClass(String iconCssClass) {
		_iconCssClass = iconCssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setImageHover(String imageHover) {
		_imageHover = imageHover;
	}

	public void setLabel(boolean label) {
		_label = label;
	}

	public void setLang(String lang) {
		_lang = lang;
	}

	public void setLinkCssClass(String linkCssClass) {
		_linkCssClass = linkCssClass;
	}

	public void setLocalizeMessage(boolean localizeMessage) {
		_localizeMessage = localizeMessage;
	}

	public void setMarkupView(String markupView) {
		_markupView = markupView;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setMethod(String method) {
		_method = method;
	}

	public void setOnClick(String onClick) {
		_onClick = onClick;
	}

	public void setSrc(String src) {
		_src = src;
	}

	public void setSrcHover(String srcHover) {
		_srcHover = srcHover;
	}

	public void setTarget(String target) {
		_target = target;
	}

	public void setToolTip(boolean toolTip) {
		_toolTip = toolTip;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setUseDialog(boolean useDialog) {
		_useDialog = useDialog;
	}

	@Override
	protected void cleanUp() {
		_alt = null;
		_ariaRole = null;
		_cssClass = null;
		_data = null;
		_icon = null;
		_iconCssClass = null;
		_id = null;
		_image = null;
		_imageHover = null;
		_label = null;
		_lang = null;
		_linkCssClass = null;
		_localizeMessage = true;
		_markupView = null;
		_message = null;
		_method = null;
		_onClick = null;
		_src = null;
		_srcHover = null;
		_target = "_self";
		_toolTip = null;
		_url = null;
		_useDialog = false;
	}

	protected Map<String, Object> getData() {
		Map<String, Object> data = null;

		if (_data != null) {
			data = new HashMap<>(_data);
		}
		else {
			data = new HashMap<>(1);
		}

		if (_useDialog && Validator.isNull(data.get("title"))) {
			String message = getProcessedMessage();

			ResourceBundle resourceBundle =
				TagResourceBundleUtil.getResourceBundle(pageContext);

			if (_localizeMessage) {
				message = LanguageUtil.get(resourceBundle, message);
			}

			data.put("title", HtmlUtil.stripHtml(message));
		}

		return data;
	}

	protected String getDetails() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = TagResourceBundleUtil.getResourceBundle(
			pageContext);

		String details = null;

		if (_alt != null) {
			details = " alt=\"" + LanguageUtil.get(resourceBundle, _alt) + "\"";
		}
		else if (isLabel()) {
			details = " alt=\"\"";
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append(" alt=\"");
			sb.append(LanguageUtil.get(resourceBundle, getProcessedMessage()));
			sb.append("\" title=\"");
			sb.append(LanguageUtil.get(resourceBundle, getProcessedMessage()));
			sb.append("\"");

			details = sb.toString();
		}

		if (Validator.isNull(_src) || !themeDisplay.isThemeImagesFastLoad() ||
			isAUIImage()) {

			return details;
		}

		SpriteImage spriteImage = null;
		String spriteFileName = null;
		String spriteFileURL = null;

		String imageFileName = StringUtil.replace(_src, "common/../", "");

		if (imageFileName.contains(Http.PROTOCOL_DELIMITER)) {
			String portalURL = PortalUtil.getPortalURL(request);

			if (imageFileName.startsWith(portalURL)) {
				imageFileName = imageFileName.substring(portalURL.length());
			}
			else {
				URL imageURL = null;

				try {
					imageURL = new URL(imageFileName);

					imageFileName = imageURL.getPath();
				}
				catch (MalformedURLException murle) {
				}
			}
		}

		Theme theme = themeDisplay.getTheme();

		String contextPath = theme.getContextPath();

		String imagesPath = contextPath.concat(theme.getImagesPath());

		if (imageFileName.startsWith(imagesPath)) {
			spriteImage = theme.getSpriteImage(imageFileName);

			if (spriteImage != null) {
				spriteFileName = spriteImage.getSpriteFileName();

				if (BrowserSnifferUtil.isIe(request) &&
					(BrowserSnifferUtil.getMajorVersion(request) < 7)) {

					spriteFileName = StringUtil.replace(
						spriteFileName, ".png", ".gif");
				}

				String cdnBaseURL = themeDisplay.getCDNBaseURL();

				spriteFileURL = cdnBaseURL.concat(spriteFileName);
			}
		}

		if (spriteImage == null) {
			Portlet portlet = (Portlet)request.getAttribute(
				"liferay-portlet:icon_portlet:portlet");

			if (portlet == null) {
				portlet = (Portlet)request.getAttribute(WebKeys.RENDER_PORTLET);
			}

			if (portlet != null) {
				PortletApp portletApp = portlet.getPortletApp();

				spriteImage = portletApp.getSpriteImage(imageFileName);

				if (spriteImage != null) {
					spriteFileName = spriteImage.getSpriteFileName();

					if (BrowserSnifferUtil.isIe(request) &&
						(BrowserSnifferUtil.getMajorVersion(request) < 7)) {

						spriteFileName = StringUtil.replace(
							spriteFileName, ".png", ".gif");
					}

					String cdnBaseURL = themeDisplay.getCDNBaseURL();

					spriteFileURL = cdnBaseURL.concat(spriteFileName);
				}
			}
		}

		if (spriteImage != null) {
			String themeImagesPath = themeDisplay.getPathThemeImages();

			_src = themeImagesPath.concat("/spacer.png");

			StringBundler sb = new StringBundler(10);

			sb.append(details);
			sb.append(" style=\"background-image: url('");
			sb.append(spriteFileURL);
			sb.append("'); background-position: 50% -");
			sb.append(spriteImage.getOffset());
			sb.append("px; background-repeat: no-repeat; height: ");
			sb.append(spriteImage.getHeight());
			sb.append("px; width: ");
			sb.append(spriteImage.getWidth());
			sb.append("px;\"");

			details = sb.toString();
		}

		return details;
	}

	protected String getId() {
		if (Validator.isNotNull(_id)) {
			return _id;
		}

		String id = (String)request.getAttribute("liferay-ui:icon-menu:id");

		String message = _message;

		if (Validator.isNull(message)) {
			message = _image;
		}

		if (Validator.isNotNull(id) && Validator.isNotNull(message)) {
			id = id.concat(StringPool.UNDERLINE).concat(
				FriendlyURLNormalizerUtil.normalize(message));

			PortletResponse portletResponse =
				(PortletResponse)request.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			String namespace = StringPool.BLANK;

			if (portletResponse != null) {
				namespace = portletResponse.getNamespace();
			}

			id = PortalUtil.getUniqueElementId(
				getOriginalServletRequest(), namespace, id);
		}
		else {
			id = PortalUtil.generateRandomKey(request, IconTag.class.getName());
		}

		id = HtmlUtil.getAUICompatibleId(id);

		return id;
	}

	protected String getImage() {
		return _image;
	}

	protected String getMessage() {
		return _message;
	}

	protected String getMethod() {
		if (Validator.isNotNull(_method)) {
			return _method;
		}

		String url = getUrl();

		if (url.contains("p_p_lifecycle=0")) {
			return "get";
		}

		return "post";
	}

	protected String getOnClick() {
		String onClick = StringPool.BLANK;

		if (Validator.isNotNull(_onClick)) {
			onClick = _onClick;

			if (!onClick.endsWith(StringPool.SEMICOLON)) {
				onClick = onClick + StringPool.SEMICOLON;
			}
		}

		if (isForcePost()) {
			StringBundler sb = new StringBundler(5);

			sb.append("event.preventDefault();");
			sb.append(onClick);
			sb.append("submitForm(document.hrefFm, '");
			sb.append(getUrl());
			sb.append("')");

			onClick = sb.toString();
		}

		return onClick;
	}

	@Override
	protected String getPage() {
		return "/html/taglib/ui/icon/page.jsp";
	}

	protected String getProcessedMessage() {
		if (_message != null) {
			return _message;
		}

		return StringUtil.replace(
			_image, new String[] {StringPool.UNDERLINE, _AUI_PATH},
			new String[] {StringPool.DASH, StringPool.BLANK});
	}

	protected String getProcessedUrl() {
		if (isForcePost()) {
			return "javascript:;";
		}

		return _url;
	}

	protected String getSrc() {
		if (Validator.isNotNull(_src)) {
			return _src;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (isAUIImage()) {
			return themeDisplay.getPathThemeImages().concat("/spacer.png");
		}
		else if (Validator.isNotNull(_image)) {
			StringBundler sb = new StringBundler(4);

			sb.append(themeDisplay.getPathThemeImages());
			sb.append("/common/");
			sb.append(_image);
			sb.append(".png");

			return StringUtil.replace(sb.toString(), "common/../", "");
		}

		return StringPool.BLANK;
	}

	protected String getSrcHover() {
		if (Validator.isNotNull(_srcHover) || Validator.isNull(_imageHover)) {
			return _srcHover;
		}

		StringBundler sb = new StringBundler(4);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPathThemeImages());

		sb.append("/common/");
		sb.append(_imageHover);
		sb.append(".png");

		return sb.toString();
	}

	protected String getUrl() {
		return GetterUtil.getString(_url);
	}

	protected boolean isAUIImage() {
		if ((_image != null) && _image.startsWith(_AUI_PATH)) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected boolean isForcePost() {
		if (StringUtil.equalsIgnoreCase(_target, "_blank")) {
			return false;
		}

		String method = getMethod();

		if (method.equals("post")) {
			String url = getUrl();

			if (url.startsWith(Http.HTTP_WITH_SLASH) ||
				url.startsWith(Http.HTTPS_WITH_SLASH)) {

				return true;
			}
		}

		return false;
	}

	protected boolean isLabel() {
		if (_label != null) {
			return _label;
		}

		IntegerWrapper iconListIconCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:icon-list:icon-count");

		Boolean iconListSingleIcon = (Boolean)request.getAttribute(
			"liferay-ui:icon-list:single-icon");

		if ((iconListIconCount != null) || (iconListSingleIcon != null)) {
			_label = true;

			return _label;
		}

		IntegerWrapper iconMenuIconCount = (IntegerWrapper)request.getAttribute(
			"liferay-ui:icon-menu:icon-count");

		Boolean iconMenuSingleIcon = (Boolean)request.getAttribute(
			"liferay-ui:icon-menu:single-icon");

		if ((iconMenuIconCount != null) || (iconMenuSingleIcon != null)) {
			_label = true;

			return _label;
		}

		_label = false;

		return _label;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:icon:alt", _alt);
		request.setAttribute("liferay-ui:icon:ariaRole", _ariaRole);
		request.setAttribute(
			"liferay-ui:icon:auiImage", String.valueOf(isAUIImage()));
		request.setAttribute("liferay-ui:icon:cssClass", _cssClass);
		request.setAttribute("liferay-ui:icon:data", getData());
		request.setAttribute("liferay-ui:icon:details", getDetails());
		request.setAttribute(
			"liferay-ui:icon:forcePost", String.valueOf(isForcePost()));
		request.setAttribute("liferay-ui:icon:icon", _icon);
		request.setAttribute("liferay-ui:icon:iconCssClass", _iconCssClass);
		request.setAttribute("liferay-ui:icon:id", getId());
		request.setAttribute("liferay-ui:icon:image", _image);
		request.setAttribute("liferay-ui:icon:imageHover", _imageHover);
		request.setAttribute(
			"liferay-ui:icon:label", String.valueOf(isLabel()));
		request.setAttribute("liferay-ui:icon:lang", _lang);
		request.setAttribute("liferay-ui:icon:linkCssClass", _linkCssClass);
		request.setAttribute(
			"liferay-ui:icon:localizeMessage",
			String.valueOf(_localizeMessage));
		request.setAttribute("liferay-ui:icon:markupView", _markupView);
		request.setAttribute("liferay-ui:icon:message", getProcessedMessage());
		request.setAttribute("liferay-ui:icon:method", getMethod());
		request.setAttribute("liferay-ui:icon:onClick", getOnClick());
		request.setAttribute("liferay-ui:icon:src", getSrc());
		request.setAttribute("liferay-ui:icon:srcHover", getSrcHover());
		request.setAttribute("liferay-ui:icon:target", _target);

		boolean toolTip = false;

		if (_toolTip != null) {
			toolTip = _toolTip.booleanValue();
		}
		else if (!isLabel() && Validator.isNotNull(getProcessedMessage())) {
			toolTip = true;
		}

		request.setAttribute(
			"liferay-ui:icon:toolTip", String.valueOf(toolTip));

		request.setAttribute("liferay-ui:icon:url", getProcessedUrl());
		request.setAttribute(
			"liferay-ui:icon:useDialog", String.valueOf(_useDialog));
	}

	private static final String _AUI_PATH = "../aui/";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private String _alt;
	private String _ariaRole;
	private String _cssClass;
	private Map<String, Object> _data;
	private String _icon;
	private String _iconCssClass;
	private String _id;
	private String _image;
	private String _imageHover;
	private Boolean _label;
	private String _lang;
	private String _linkCssClass;
	private boolean _localizeMessage = true;
	private String _markupView;
	private String _message;
	private String _method;
	private String _onClick;
	private String _src;
	private String _srcHover;
	private String _target = "_self";
	private Boolean _toolTip;
	private String _url;
	private boolean _useDialog;

}