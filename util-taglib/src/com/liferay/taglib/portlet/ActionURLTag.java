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

package com.liferay.taglib.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.util.ParamAndPropertyAncestorTagImpl;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionURLTag extends ParamAndPropertyAncestorTagImpl {

	public static void doTag(
			String lifecycle, String windowState, String portletMode,
			String var, String varImpl, Boolean secure,
			Boolean copyCurrentRenderParameters, Boolean escapeXml, String name,
			String resourceID, String cacheability, long plid, long refererPlid,
			String portletName, Boolean anchor, Boolean encrypt,
			long doAsGroupId, long doAsUserId, Boolean portletConfiguration,
			Map<String, String[]> params, PageContext pageContext)
		throws Exception {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (portletName == null) {
			portletName = _getPortletName(request);
		}

		LiferayPortletURL liferayPortletURL = _getLiferayPortletURL(
			request, plid, portletName, lifecycle);

		if (liferayPortletURL == null) {
			_log.error(
				"Render response is null because this tag is not being " +
					"called within the context of a portlet");

			return;
		}

		if (Validator.isNotNull(windowState)) {
			liferayPortletURL.setWindowState(
				WindowStateFactory.getWindowState(windowState));
		}

		if (Validator.isNotNull(portletMode)) {
			liferayPortletURL.setPortletMode(
				PortletModeFactory.getPortletMode(portletMode));
		}

		if (secure != null) {
			liferayPortletURL.setSecure(secure.booleanValue());
		}
		else {
			liferayPortletURL.setSecure(PortalUtil.isSecure(request));
		}

		if (copyCurrentRenderParameters != null) {
			liferayPortletURL.setCopyCurrentRenderParameters(
				copyCurrentRenderParameters.booleanValue());
		}

		if (escapeXml != null) {
			liferayPortletURL.setEscapeXml(escapeXml.booleanValue());
		}

		if (lifecycle.equals(PortletRequest.ACTION_PHASE) &&
			Validator.isNotNull(name)) {

			liferayPortletURL.setParameter(ActionRequest.ACTION_NAME, name);
		}

		if (resourceID != null) {
			liferayPortletURL.setResourceID(resourceID);
		}

		if (cacheability != null) {
			liferayPortletURL.setCacheability(cacheability);
		}

		if (refererPlid > LayoutConstants.DEFAULT_PLID) {
			liferayPortletURL.setRefererPlid(refererPlid);
		}

		if (anchor != null) {
			liferayPortletURL.setAnchor(anchor.booleanValue());
		}

		if (encrypt != null) {
			liferayPortletURL.setEncrypt(encrypt.booleanValue());
		}

		if (doAsGroupId > 0) {
			liferayPortletURL.setDoAsGroupId(doAsGroupId);
		}

		if (doAsUserId > 0) {
			liferayPortletURL.setDoAsUserId(doAsUserId);
		}

		if ((portletConfiguration != null) &&
			portletConfiguration.booleanValue()) {

			String returnToFullPageURL = ParamUtil.getString(
				request, "returnToFullPageURL");
			String portletResource = ParamUtil.getString(
				request, "portletResource");
			String previewWidth = ParamUtil.getString(request, "previewWidth");

			liferayPortletURL.setParameter(
				"struts_action", "/portlet_configuration/edit_configuration");
			liferayPortletURL.setParameter(
				"returnToFullPageURL", returnToFullPageURL);
			liferayPortletURL.setParameter("portletResource", portletResource);
			liferayPortletURL.setParameter("previewWidth", previewWidth);
		}

		if (params != null) {
			MapUtil.merge(liferayPortletURL.getParameterMap(), params);

			liferayPortletURL.setParameters(params);
		}

		String portletURLToString = liferayPortletURL.toString();

		if (Validator.isNotNull(var)) {
			pageContext.setAttribute(var, portletURLToString);
		}
		else if (Validator.isNotNull(varImpl)) {
			pageContext.setAttribute(varImpl, liferayPortletURL);
		}
		else {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write(portletURLToString);
		}
	}

	private static LiferayPortletURL _getLiferayPortletURL(
		HttpServletRequest request, long plid, String portletName,
		String lifecycle) {

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest == null) {
			return null;
		}

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(portletResponse);

		return liferayPortletResponse.createLiferayPortletURL(
			plid, portletName, lifecycle);
	}

	private static String _getPortletName(HttpServletRequest request) {
		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (portletRequest == null) {
			return null;
		}

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);

		return liferayPortletConfig.getPortletId();
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			doTag(
				getLifecycle(), _windowState, _portletMode, _var, _varImpl,
				_secure, _copyCurrentRenderParameters, _escapeXml, _name,
				_resourceID, _cacheability, _plid, _refererPlid, _portletName,
				_anchor, _encrypt, _doAsGroupId, _doAsUserId,
				_portletConfiguration, getParams(), pageContext);

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			clearParams();
			clearProperties();

			_plid = LayoutConstants.DEFAULT_PLID;
		}
	}

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public String getLifecycle() {
		return PortletRequest.ACTION_PHASE;
	}

	public void setAnchor(boolean anchor) {
		_anchor = Boolean.valueOf(anchor);
	}

	public void setCacheability(String cacheability) {
		_cacheability = cacheability;
	}

	public void setCopyCurrentRenderParameters(
		boolean copyCurrentRenderParameters) {

		_copyCurrentRenderParameters = Boolean.valueOf(
			copyCurrentRenderParameters);
	}

	public void setDoAsGroupId(long doAsGroupId) {
		_doAsGroupId = doAsGroupId;
	}

	public void setDoAsUserId(long doAsUserId) {
		_doAsUserId = doAsUserId;
	}

	public void setEncrypt(boolean encrypt) {
		_encrypt = Boolean.valueOf(encrypt);
	}

	public void setEscapeXml(boolean escapeXml) {
		_escapeXml = Boolean.valueOf(escapeXml);
	}

	public void setId(String resourceID) {
		_resourceID = resourceID;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public void setPortletConfiguration(boolean portletConfiguration) {
		_portletConfiguration = Boolean.valueOf(portletConfiguration);
	}

	public void setPortletMode(String portletMode) {
		_portletMode = portletMode;
	}

	public void setPortletName(String portletName) {
		_portletName = portletName;
	}

	public void setRefererPlid(long refererPlid) {
		_refererPlid = refererPlid;
	}

	public void setSecure(boolean secure) {
		_secure = Boolean.valueOf(secure);
	}

	public void setVar(String var) {
		_var = var;
	}

	public void setVarImpl(String varImpl) {
		_varImpl = varImpl;
	}

	public void setWindowState(String windowState) {
		_windowState = windowState;
	}

	private static Log _log = LogFactoryUtil.getLog(ActionURLTag.class);

	private Boolean _anchor;
	private String _cacheability;
	private Boolean _copyCurrentRenderParameters;
	private long _doAsGroupId;
	private long _doAsUserId;
	private Boolean _encrypt;
	private Boolean _escapeXml;
	private String  _name;
	private long _plid = LayoutConstants.DEFAULT_PLID;
	private Boolean _portletConfiguration;
	private String _portletMode;
	private String _portletName;
	private long _refererPlid = LayoutConstants.DEFAULT_PLID;
	private String _resourceID;
	private Boolean _secure;
	private String _var;
	private String _varImpl;
	private String _windowState;

}