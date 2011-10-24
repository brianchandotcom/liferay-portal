/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class IconTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
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

	@Override
	protected void cleanUp() {
		_cssClass = null;
		_data = null;
		_id = null;
		_image = null;
		_imageHover = null;
		_label = false;
		_lang = null;
		_message = null;
		_method = null;
		_onClick = null;
		_src = null;
		_srcHover = null;
		_target = null;
		_toolTip = false;
		_url = null;
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
		String id = _id;

		if (Validator.isNull(id)) {
			id = PortalUtil.generateRandomKey(
				request, IconTag.class.getName());
		}

		request.setAttribute("liferay-ui:icon:cssClass", _cssClass);
		request.setAttribute("liferay-ui:icon:data", _data);
		request.setAttribute("liferay-ui:icon:id", id);
		request.setAttribute("liferay-ui:icon:image", _image);
		request.setAttribute("liferay-ui:icon:imageHover", _imageHover);
		request.setAttribute("liferay-ui:icon:label", String.valueOf(_label));
		request.setAttribute("liferay-ui:icon:lang", _lang);
		request.setAttribute("liferay-ui:icon:message", _message);
		request.setAttribute("liferay-ui:icon:method", _method);
		request.setAttribute("liferay-ui:icon:onClick", _onClick);
		request.setAttribute("liferay-ui:icon:src", _src);
		request.setAttribute("liferay-ui:icon:srcHover", _srcHover);
		request.setAttribute("liferay-ui:icon:target", _target);
		request.setAttribute(
			"liferay-ui:icon:toolTip", String.valueOf(_toolTip));
		request.setAttribute("liferay-ui:icon:url", _url);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/html/taglib/ui/icon/page.jsp";

	private String _cssClass;
	private Map<String, Object> _data;
	private String _id;
	private String _image;
	private String _imageHover;
	private boolean _label;
	private String _lang;
	private String _message;
	private String _method;
	private String _onClick;
	private String _src;
	private String _srcHover;
	private String _target = "_self";
	private boolean _toolTip;
	private String _url;

}