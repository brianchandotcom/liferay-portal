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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
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

	public String getAlt() {
		return _alt;
	}

	public String getAriaRole() {
		return _ariaRole;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getIcon() {
		return _icon;
	}

	public String getIconCssClass() {
		return _iconCssClass;
	}

	public String getImageHover() {
		return _imageHover;
	}

	public String getLang() {
		return _lang;
	}

	public String getLinkCssClass() {
		return _linkCssClass;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public String getLinkTitle() {
		return _linkTitle;
	}

	public String getMarkupView() {
		return _markupView;
	}

	public String getTarget() {
		return _target;
	}

	public boolean isLocalizeMessage() {
		return _localizeMessage;
	}

	public boolean isToolTip() {
		return _toolTip;
	}

	public boolean isUseDialog() {
		return _useDialog;
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

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setLinkTitle(String linkTitle) {
		_linkTitle = linkTitle;
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
		super.cleanUp();

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
		_linkTitle = null;
		_localizeMessage = true;
		_markupView = null;
		_message = null;
		_method = null;
		_onClick = null;
		_resourceBundle = null;
		_src = null;
		_srcHover = null;
		_target = "_self";
		_toolTip = null;
		_url = null;
		_useDialog = false;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected Map<String, Object> getData() {
		return _getData();
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected String getDetails() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _getDetails(themeDisplay);
	}

	protected String getId() {
		if (Validator.isNotNull(_id)) {
			return _id;
		}

		HttpServletRequest httpServletRequest = getRequest();

		String id = (String)httpServletRequest.getAttribute(
			"liferay-ui:icon-menu:id");

		String message = _message;

		if (Validator.isNull(message)) {
			message = _image;
		}

		if (Validator.isNotNull(id) && Validator.isNotNull(message)) {
			id = id.concat(
				StringPool.UNDERLINE
			).concat(
				FriendlyURLNormalizerUtil.normalize(message)
			);

			PortletResponse portletResponse =
				(PortletResponse)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE);

			String namespace = StringPool.BLANK;

			if (portletResponse != null) {
				namespace = portletResponse.getNamespace();
			}

			id = PortalUtil.getUniqueElementId(
				getOriginalServletRequest(), namespace, id);
		}
		else {
			id = PortalUtil.generateRandomKey(
				httpServletRequest, IconTag.class.getName());
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

		if (_url == null) {
			return "post";
		}

		if (_url.contains("p_p_lifecycle=0")) {
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

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected String getSrc() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _getSrc(themeDisplay);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected String getSrcHover() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return _getSrcHover(themeDisplay);
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

		if (_url == null) {
			return false;
		}

		String method = getMethod();

		if (method.equals("post") &&
			(_url.startsWith(Http.HTTP_WITH_SLASH) ||
			 _url.startsWith(Http.HTTPS_WITH_SLASH))) {

			return true;
		}

		return false;
	}

	protected boolean isLabel() {
		if (_label != null) {
			return _label;
		}

		HttpServletRequest httpServletRequest = getRequest();

		IntegerWrapper iconListIconCount =
			(IntegerWrapper)httpServletRequest.getAttribute(
				"liferay-ui:icon-list:icon-count");

		if (iconListIconCount != null) {
			_label = true;

			return true;
		}

		IntegerWrapper iconMenuIconCount =
			(IntegerWrapper)httpServletRequest.getAttribute(
				"liferay-ui:icon-menu:icon-count");

		if (iconMenuIconCount != null) {
			_label = true;

			return true;
		}

		Boolean iconListSingleIcon = (Boolean)httpServletRequest.getAttribute(
			"liferay-ui:icon-list:single-icon");

		if (iconListSingleIcon != null) {
			_label = true;

			return true;
		}

		Boolean iconMenuSingleIcon = (Boolean)httpServletRequest.getAttribute(
			"liferay-ui:icon-menu:single-icon");

		if (iconMenuSingleIcon != null) {
			_label = true;

			return true;
		}

		_label = false;

		return false;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		httpServletRequest.setAttribute("liferay-ui:icon:alt", _alt);
		httpServletRequest.setAttribute("liferay-ui:icon:ariaRole", _ariaRole);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:auiImage", String.valueOf(isAUIImage()));
		httpServletRequest.setAttribute("liferay-ui:icon:cssClass", _cssClass);
		httpServletRequest.setAttribute("liferay-ui:icon:data", _getData());
		httpServletRequest.setAttribute(
			"liferay-ui:icon:details", _getDetails(themeDisplay));
		httpServletRequest.setAttribute(
			"liferay-ui:icon:forcePost", String.valueOf(isForcePost()));
		httpServletRequest.setAttribute("liferay-ui:icon:icon", _icon);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:iconCssClass", _iconCssClass);
		httpServletRequest.setAttribute("liferay-ui:icon:id", getId());
		httpServletRequest.setAttribute("liferay-ui:icon:image", _image);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:imageHover", _imageHover);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:label", String.valueOf(isLabel()));
		httpServletRequest.setAttribute("liferay-ui:icon:lang", _lang);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:linkCssClass", _linkCssClass);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:linkTitle", _linkTitle);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:localizeMessage",
			String.valueOf(_localizeMessage));
		httpServletRequest.setAttribute(
			"liferay-ui:icon:markupView", _markupView);
		httpServletRequest.setAttribute(
			"liferay-ui:icon:message", getProcessedMessage());
		httpServletRequest.setAttribute("liferay-ui:icon:method", getMethod());
		httpServletRequest.setAttribute(
			"liferay-ui:icon:onClick", getOnClick());
		httpServletRequest.setAttribute(
			"liferay-ui:icon:src", _getSrc(themeDisplay));
		httpServletRequest.setAttribute(
			"liferay-ui:icon:srcHover", _getSrcHover(themeDisplay));
		httpServletRequest.setAttribute("liferay-ui:icon:target", _target);

		boolean toolTip = false;

		if (_toolTip != null) {
			toolTip = _toolTip.booleanValue();
		}
		else if (!isLabel() && Validator.isNotNull(getProcessedMessage())) {
			toolTip = true;
		}

		httpServletRequest.setAttribute(
			"liferay-ui:icon:toolTip", String.valueOf(toolTip));

		httpServletRequest.setAttribute(
			"liferay-ui:icon:url", getProcessedUrl());
		httpServletRequest.setAttribute(
			"liferay-ui:icon:useDialog", String.valueOf(_useDialog));
	}

	private Map<String, Object> _getData() {
		Map<String, Object> data = null;

		if (_data != null) {
			data = new HashMap<>(_data);
		}
		else {
			data = new HashMap<>();
		}

		if (_useDialog && Validator.isNull(data.get("title"))) {
			String message = getProcessedMessage();

			if (_localizeMessage) {
				message = LanguageUtil.get(_getResourceBundle(), message);
			}

			data.put("title", HtmlUtil.stripHtml(message));
		}

		return data;
	}

	private String _getDetails(ThemeDisplay themeDisplay) {
		String details = null;

		if (_alt != null) {
			details =
				" alt=\"" + LanguageUtil.get(_getResourceBundle(), _alt) + "\"";
		}
		else if (isLabel()) {
			details = " alt=\"\"";
		}
		else {
			StringBundler sb = new StringBundler(5);

			sb.append(" alt=\"");

			String localizedProcessedMessage = StringPool.BLANK;

			String processedMessage = getProcessedMessage();

			if (processedMessage != null) {
				localizedProcessedMessage = LanguageUtil.get(
					_getResourceBundle(), processedMessage);
			}

			sb.append(localizedProcessedMessage);

			sb.append("\" title=\"");
			sb.append(localizedProcessedMessage);
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

		HttpServletRequest httpServletRequest = getRequest();

		if (imageFileName.contains(Http.PROTOCOL_DELIMITER)) {
			String portalURL = PortalUtil.getPortalURL(httpServletRequest);

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

				if (BrowserSnifferUtil.isIe(httpServletRequest) &&
					(BrowserSnifferUtil.getMajorVersion(httpServletRequest) <
						7)) {

					spriteFileName = StringUtil.replace(
						spriteFileName, ".png", ".gif");
				}

				String cdnBaseURL = themeDisplay.getCDNBaseURL();

				spriteFileURL = cdnBaseURL.concat(spriteFileName);
			}
		}

		if (spriteImage == null) {
			Portlet portlet = (Portlet)httpServletRequest.getAttribute(
				"liferay-portlet:icon_portlet:portlet");

			if (portlet == null) {
				portlet = (Portlet)httpServletRequest.getAttribute(
					WebKeys.RENDER_PORTLET);
			}

			if (portlet != null) {
				PortletApp portletApp = portlet.getPortletApp();

				spriteImage = portletApp.getSpriteImage(imageFileName);

				if (spriteImage != null) {
					spriteFileName = spriteImage.getSpriteFileName();

					float majorVersion = BrowserSnifferUtil.getMajorVersion(
						httpServletRequest);

					if (BrowserSnifferUtil.isIe(httpServletRequest) &&
						(majorVersion < 7)) {

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

	private ResourceBundle _getResourceBundle() {
		if (_resourceBundle == null) {
			_resourceBundle = TagResourceBundleUtil.getResourceBundle(
				pageContext);
		}

		return _resourceBundle;
	}

	private String _getSrc(ThemeDisplay themeDisplay) {
		if (Validator.isNotNull(_src)) {
			return _src;
		}

		if (isAUIImage()) {
			String pathThemeImages = themeDisplay.getPathThemeImages();

			return pathThemeImages.concat("/spacer.png");
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

	private String _getSrcHover(ThemeDisplay themeDisplay) {
		if (Validator.isNotNull(_srcHover) || Validator.isNull(_imageHover)) {
			return _srcHover;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(themeDisplay.getPathThemeImages());

		sb.append("/common/");
		sb.append(_imageHover);
		sb.append(".png");

		return sb.toString();
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
	private String _linkTitle;
	private boolean _localizeMessage = true;
	private String _markupView;
	private String _message;
	private String _method;
	private String _onClick;
	private ResourceBundle _resourceBundle;
	private String _src;
	private String _srcHover;
	private String _target = "_self";
	private Boolean _toolTip;
	private String _url;
	private boolean _useDialog;

}