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
import com.liferay.portal.kernel.language.UnicodeLanguageUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.SpriteImage;
import com.liferay.portal.model.Theme;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;

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
		_iconCssClass = null;
		_id = null;
		_image = null;
		_imageHover = null;
		_label = false;
		_lang = null;
		_linkCssClass = null;
		_localizeMessage = true;
		_message = null;
		_method = null;
		_onClick = null;
		_src = null;
		_srcHover = null;
		_target = null;
		_toolTip = false;
		_url = null;
		_useDialog = false;
	}

	protected String getDetails() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String details = null;

		if (_alt != null) {
			details = " alt=\"" + LanguageUtil.get(pageContext, _alt) + "\"";
		}
		else if (_label) {
			details = " alt=\"\"";
		}
		else {
			StringBundler sb = new StringBundler(6);

			sb.append(" alt=\"");
			sb.append(LanguageUtil.get(pageContext, _message));
			sb.append("\"");

			if (_toolTip) {
				sb.append(" onmouseover=\"Liferay.Portal.ToolTip.show(this, '");
				sb.append(UnicodeLanguageUtil.get(pageContext, _message));
				sb.append("')\"");
			}
			else {
				sb.append(" title=\"");
				sb.append(LanguageUtil.get(pageContext, _message));
				sb.append("\"");
			}

			details = sb.toString();
		}

		boolean auiImage = (_image != null) && _image.startsWith(_AUI_PATH);

		if (Validator.isNotNull(_src) && themeDisplay.isThemeImagesFastLoad() &&
			!auiImage) {

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
					catch (MalformedURLException e) {
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
					portlet = (Portlet)request.getAttribute(
						WebKeys.RENDER_PORTLET);
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
		}

		return details;
	}

	protected String getImage() {
		return _image;
	}

	protected String getMessage() {
		return _message;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected String getUrl() {
		return _url;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (_data == null) {
			_data = new HashMap<String, Object>(1);
		}

		String id = _id;

		if (Validator.isNull(id)) {
			id = (String)request.getAttribute("liferay-ui:icon-menu:id");

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
				id = PortalUtil.generateRandomKey(
					request, IconTag.class.getName());
			}
		}

		if (_message == null) {
			_message = StringUtil.replace(
				_image, StringPool.UNDERLINE, StringPool.DASH);
			_message = StringUtil.replace(
				_message, _AUI_PATH, StringPool.BLANK);
		}

		if (_useDialog && Validator.isNull(_data.get("title"))) {
			String message = _message;

			if (_localizeMessage) {
				message = LanguageUtil.get(pageContext, _message);
			}

			_data.put("title", HtmlUtil.stripHtml(message));
		}

		if (Validator.isNull(_method)) {
			if (_url == null) {
				_url = StringPool.BLANK;
			}

			int pos = _url.indexOf("p_p_lifecycle=0");

			if (pos != -1) {
				_method = "get";
			}
			else {
				_method = "post";
			}
		}

		boolean forcePost =
			_method.equals("post") &&
			(_url.startsWith(Http.HTTP_WITH_SLASH) ||
				_url.startsWith(Http.HTTPS_WITH_SLASH));

		String onClick = StringPool.BLANK;

		if (Validator.isNotNull(_onClick)) {
			onClick = _onClick + StringPool.SEMICOLON;
		}

		if (forcePost) {
			StringBundler sb = new StringBundler(8);

			sb.append("event.preventDefault();");
			sb.append(onClick);
			sb.append("submitForm(document.hrefFm, '");
			sb.append(_url);
			sb.append("')");

			onClick = sb.toString();
		}

		boolean auiImage = (_image != null) && _image.startsWith(_AUI_PATH);

		if (Validator.isNull(_src)) {
			if (auiImage) {
				_src = themeDisplay.getPathThemeImages().concat("/spacer.png");
			}
			else if (Validator.isNotNull(_image)) {
				StringBundler sb = new StringBundler(4);

				sb.append(themeDisplay.getPathThemeImages());
				sb.append("/common/");
				sb.append(_image);
				sb.append(".png");

				_src = StringUtil.replace(sb.toString(), "common/../", "");
			}
		}

		if (Validator.isNull(_srcHover) && Validator.isNotNull(_imageHover)) {
			StringBundler sb = new StringBundler(4);

			sb.append(themeDisplay.getPathThemeImages());
			sb.append("/common/");
			sb.append(_imageHover);
			sb.append(".png");

			_srcHover = sb.toString();
		}

		request.setAttribute("liferay-ui:icon:alt", _alt);
		request.setAttribute("liferay-ui:icon:ariaRole", _ariaRole);
		request.setAttribute(
			"liferay-ui:icon:auiImage", String.valueOf(auiImage));
		request.setAttribute("liferay-ui:icon:cssClass", _cssClass);
		request.setAttribute("liferay-ui:icon:data", _data);
		request.setAttribute("liferay-ui:icon:details", getDetails());
		request.setAttribute("liferay-ui:icon:iconCssClass", _iconCssClass);
		request.setAttribute("liferay-ui:icon:id", id);
		request.setAttribute("liferay-ui:icon:image", _image);
		request.setAttribute("liferay-ui:icon:imageHover", _imageHover);
		request.setAttribute(
			"liferay-ui:icon:forcePost", String.valueOf(forcePost));
		request.setAttribute("liferay-ui:icon:label", String.valueOf(_label));
		request.setAttribute("liferay-ui:icon:lang", _lang);
		request.setAttribute("liferay-ui:icon:linkCssClass", _linkCssClass);
		request.setAttribute(
			"liferay-ui:icon:localizeMessage",
			String.valueOf(_localizeMessage));
		request.setAttribute("liferay-ui:icon:message", _message);
		request.setAttribute("liferay-ui:icon:method", _method);
		request.setAttribute("liferay-ui:icon:onClick", onClick);
		request.setAttribute("liferay-ui:icon:src", _src);
		request.setAttribute("liferay-ui:icon:srcHover", _srcHover);
		request.setAttribute("liferay-ui:icon:target", _target);
		request.setAttribute(
			"liferay-ui:icon:toolTip", String.valueOf(_toolTip));
		request.setAttribute("liferay-ui:icon:url", _url);
		request.setAttribute(
			"liferay-ui:icon:useDialog", String.valueOf(_useDialog));
	}

	private static final String _AUI_PATH = "../aui/";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/ui/icon/page.jsp";

	private String _alt;
	private String _ariaRole;
	private String _cssClass;
	private Map<String, Object> _data;
	private String _iconCssClass;
	private String _id;
	private String _image;
	private String _imageHover;
	private boolean _label;
	private String _lang;
	private String _linkCssClass;
	private boolean _localizeMessage = true;
	private String _message;
	private String _method;
	private String _onClick;
	private String _src;
	private String _srcHover;
	private String _target = "_self";
	private boolean _toolTip;
	private String _url;
	private boolean _useDialog = false;

}