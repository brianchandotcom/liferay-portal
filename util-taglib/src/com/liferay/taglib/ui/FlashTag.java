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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class FlashTag extends IncludeTag {

	public String getAlign() {
		return _align;
	}

	public String getAllowFullScreen() {
		return _allowFullScreen;
	}

	public String getAllowScriptAccess() {
		return _allowScriptAccess;
	}

	public String getBase() {
		return _base;
	}

	public String getBgcolor() {
		return _bgcolor;
	}

	public String getDevicefont() {
		return _devicefont;
	}

	public String getFlashvars() {
		return _flashvars;
	}

	public String getHeight() {
		return _height;
	}

	public String getId() {
		return _id;
	}

	public String getLoop() {
		return _loop;
	}

	public String getMenu() {
		return _menu;
	}

	public String getMovie() {
		return _movie;
	}

	public String getPlay() {
		return _play;
	}

	public String getQuality() {
		return _quality;
	}

	public String getSalign() {
		return _salign;
	}

	public String getScale() {
		return _scale;
	}

	public String getSwliveconnect() {
		return _swliveconnect;
	}

	public String getVersion() {
		return _version;
	}

	public String getWidth() {
		return _width;
	}

	public String getWmode() {
		return _wmode;
	}

	public void setAlign(String align) {
		if (Validator.isNotNull(align)) {
			_align = align;
		}
	}

	public void setAllowFullScreen(String allowFullScreen) {
		if (Validator.isNotNull(allowFullScreen)) {
			_allowFullScreen = allowFullScreen;
		}
	}

	public void setAllowScriptAccess(String allowScriptAccess) {
		if (Validator.isNotNull(allowScriptAccess)) {
			_allowScriptAccess = allowScriptAccess;
		}
	}

	public void setBase(String base) {
		if (Validator.isNotNull(base)) {
			_base = base;
		}
	}

	public void setBgcolor(String bgcolor) {
		if (Validator.isNotNull(bgcolor)) {
			_bgcolor = bgcolor;
		}
	}

	public void setDevicefont(String devicefont) {
		if (Validator.isNotNull(devicefont)) {
			_devicefont = devicefont;
		}
	}

	public void setFlashvars(String flashvars) {
		if (Validator.isNotNull(flashvars)) {
			_flashvars = flashvars;
		}
	}

	public void setHeight(String height) {
		if (Validator.isNotNull(height)) {
			_height = height;
		}
	}

	public void setId(String id) {
		if (Validator.isNotNull(id)) {
			_id = id;
		}
	}

	public void setLoop(String loop) {
		if (Validator.isNotNull(loop)) {
			_loop = loop;
		}
	}

	public void setMenu(String menu) {
		if (Validator.isNotNull(menu)) {
			_menu = menu;
		}
	}

	public void setMovie(String movie) {
		_movie = movie;
	}

	public void setPlay(String play) {
		if (Validator.isNotNull(play)) {
			_play = play;
		}
	}

	public void setQuality(String quality) {
		if (Validator.isNotNull(quality)) {
			_quality = quality;
		}
	}

	public void setSalign(String salign) {
		if (Validator.isNotNull(salign)) {
			_salign = salign;
		}
	}

	public void setScale(String scale) {
		if (Validator.isNotNull(scale)) {
			_scale = scale;
		}
	}

	public void setSwliveconnect(String swliveconnect) {
		if (Validator.isNotNull(swliveconnect)) {
			_swliveconnect = swliveconnect;
		}
	}

	public void setVersion(String version) {
		if (Validator.isNotNull(version)) {
			_version = version;
		}
	}

	public void setWidth(String width) {
		if (Validator.isNotNull(width)) {
			_width = width;
		}
	}

	public void setWmode(String wmode) {
		if (Validator.isNotNull(wmode)) {
			_wmode = wmode;
		}
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_align = "left";
		_allowFullScreen = Boolean.FALSE.toString();
		_allowScriptAccess = "sameDomain";
		_base = StringPool.PERIOD;
		_bgcolor = "#FFFFFF";
		_devicefont = Boolean.TRUE.toString();
		_flashvars = StringPool.BLANK;
		_height = "500";
		_id = StringPool.BLANK;
		_loop = Boolean.TRUE.toString();
		_menu = Boolean.FALSE.toString();
		_movie = StringPool.BLANK;
		_play = Boolean.FALSE.toString();
		_quality = "best";
		_salign = StringPool.BLANK;
		_scale = "showall";
		_swliveconnect = Boolean.FALSE.toString();
		_version = "7";
		_width = "100%";
		_wmode = "opaque";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("liferay-ui:flash:align", _align);
		httpServletRequest.setAttribute(
			"liferay-ui:flash:allowFullScreen", _allowFullScreen);
		httpServletRequest.setAttribute(
			"liferay-ui:flash:allowScriptAccess", _allowScriptAccess);
		httpServletRequest.setAttribute("liferay-ui:flash:base", _base);
		httpServletRequest.setAttribute("liferay-ui:flash:bgcolor", _bgcolor);
		httpServletRequest.setAttribute(
			"liferay-ui:flash:devicefont", _devicefont);
		httpServletRequest.setAttribute(
			"liferay-ui:flash:flashvars", _flashvars);
		httpServletRequest.setAttribute("liferay-ui:flash:height", _height);
		httpServletRequest.setAttribute("liferay-ui:flash:id", _id);
		httpServletRequest.setAttribute("liferay-ui:flash:loop", _loop);
		httpServletRequest.setAttribute("liferay-ui:flash:menu", _menu);
		httpServletRequest.setAttribute("liferay-ui:flash:movie", _movie);
		httpServletRequest.setAttribute("liferay-ui:flash:play", _play);
		httpServletRequest.setAttribute("liferay-ui:flash:quality", _quality);
		httpServletRequest.setAttribute("liferay-ui:flash:salign", _salign);
		httpServletRequest.setAttribute("liferay-ui:flash:scale", _scale);
		httpServletRequest.setAttribute(
			"liferay-ui:flash:swliveconnect", _swliveconnect);
		httpServletRequest.setAttribute("liferay-ui:flash:version", _version);
		httpServletRequest.setAttribute("liferay-ui:flash:width", _width);
		httpServletRequest.setAttribute("liferay-ui:flash:wmode", _wmode);
	}

	private static final String _PAGE = "/html/taglib/ui/flash/page.jsp";

	private String _align = "left";
	private String _allowFullScreen = Boolean.FALSE.toString();
	private String _allowScriptAccess = "sameDomain";
	private String _base = StringPool.PERIOD;
	private String _bgcolor = "#FFFFFF";
	private String _devicefont = Boolean.TRUE.toString();
	private String _flashvars = StringPool.BLANK;
	private String _height = "500";
	private String _id = StringPool.BLANK;
	private String _loop = Boolean.TRUE.toString();
	private String _menu = Boolean.FALSE.toString();
	private String _movie = StringPool.BLANK;
	private String _play = Boolean.FALSE.toString();
	private String _quality = "best";
	private String _salign = StringPool.BLANK;
	private String _scale = "showall";
	private String _swliveconnect = Boolean.FALSE.toString();
	private String _version = "7";
	private String _width = "100%";
	private String _wmode = "opaque";

}