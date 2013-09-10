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

package com.liferay.util.portlet;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.InputStream;

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortletRequestUtil {

	public static List<DiskFileItem> testMultipartWithCommonsFileUpload(
			ActionRequest actionRequest)
		throws Exception {

		// Check if the given request is a multipart request

		boolean multiPartContent = PortletFileUpload.isMultipartContent(
			actionRequest);

		if (multiPartContent) {
			_log.info("The given request is a multipart request");
		}
		else {
			_log.info("The given request is NOT a multipart request");
		}

		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

		PortletFileUpload portletFileUpload = new PortletFileUpload(
			diskFileItemFactory);

		List<DiskFileItem> diskFileItems = portletFileUpload.parseRequest(
			actionRequest);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Apache commons upload was able to parse " +
					diskFileItems.size() + " items");
		}

		for (int i = 0; i < diskFileItems.size(); i++) {
			DiskFileItem diskFileItem = diskFileItems.get(i);

			if (_log.isInfoEnabled()) {
				_log.info("Item " + i + " " + diskFileItem);
			}
		}

		return diskFileItems;
	}

	public static int testMultipartWithPortletInputStream(
			ActionRequest actionRequest)
		throws Exception {

		InputStream inputStream = actionRequest.getPortletInputStream();

		if (inputStream != null) {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream);

			int size = unsyncByteArrayOutputStream.size();

			if (_log.isInfoEnabled()) {
				_log.info(
					"Byte array size from the raw input stream is " + size);
			}

			return size;
		}

		return -1;
	}

	public static String toXML(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Element requestElement = new Element("request");

		requestElement.addElement("container-type", "portlet");
		requestElement.addElement("container-type", "portlet");
		requestElement.addElement(
			"container-namespace", portletRequest.getContextPath());
		requestElement.addElement(
			"content-type", portletRequest.getResponseContentType());
		requestElement.addElement(
			"server-name", portletRequest.getServerName());
		requestElement.addElement(
			"server-port", portletRequest.getServerPort());
		requestElement.addElement("secure", portletRequest.isSecure());
		requestElement.addElement("auth-type", portletRequest.getAuthType());
		requestElement.addElement(
			"remote-user", portletRequest.getRemoteUser());
		requestElement.addElement(
			"context-path", portletRequest.getContextPath());
		requestElement.addElement("locale", portletRequest.getLocale());
		requestElement.addElement(
			"portlet-mode", portletRequest.getPortletMode());
		requestElement.addElement(
			"portlet-session-id", portletRequest.getRequestedSessionId());
		requestElement.addElement("scheme", portletRequest.getScheme());
		requestElement.addElement(
			"window-state", portletRequest.getWindowState());

		if (portletRequest instanceof ActionRequest) {
			requestElement.addElement("lifecycle", RenderRequest.ACTION_PHASE);
		}
		else if (portletRequest instanceof RenderRequest) {
			requestElement.addElement("lifecycle", RenderRequest.RENDER_PHASE);
		}
		else if (portletRequest instanceof ResourceRequest) {
			requestElement.addElement(
				"lifecycle", RenderRequest.RESOURCE_PHASE);
		}

		if (portletResponse instanceof MimeResponse) {
			_mimeResponseToXML((MimeResponse)portletResponse, requestElement);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			Element themeDisplayElement = requestElement.addElement(
				"theme-display");

			_themeDisplayToXML(themeDisplay, themeDisplayElement);
		}

		Element parametersElement = requestElement.addElement("parameters");

		Enumeration<String> enu = portletRequest.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			Element parameterElement = parametersElement.addElement(
				"parameter");

			parameterElement.addElement("name", name);

			String[] values = portletRequest.getParameterValues(name);

			for (int i = 0; i < values.length; i++) {
				parameterElement.addElement("value", values[i]);
			}
		}

		Element attributesElement = requestElement.addElement("attributes");

		enu = portletRequest.getAttributeNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (!_isValidAttributeName(name)) {
				continue;
			}

			Object value = portletRequest.getAttribute(name);

			if (!_isValidAttributeValue(value)) {
				continue;
			}

			Element attributeElement = attributesElement.addElement(
				"attribute");

			attributeElement.addElement("name", name);
			attributeElement.addElement("value", value);
		}

		Element portletSessionElement = requestElement.addElement(
			"portlet-session");

		attributesElement = portletSessionElement.addElement(
			"portlet-attributes");

		PortletSession portletSession = portletRequest.getPortletSession();

		try {
			enu = portletSession.getAttributeNames(
				PortletSession.PORTLET_SCOPE);

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!_isValidAttributeName(name)) {
					continue;
				}

				Object value = portletSession.getAttribute(
					name, PortletSession.PORTLET_SCOPE);

				if (!_isValidAttributeValue(value)) {
					continue;
				}

				Element attributeElement = attributesElement.addElement(
					"attribute");

				attributeElement.addElement("name", name);
				attributeElement.addElement("value", value);
			}

			attributesElement = portletSessionElement.addElement(
				"application-attributes");

			enu = portletSession.getAttributeNames(
				PortletSession.APPLICATION_SCOPE);

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				if (!_isValidAttributeName(name)) {
					continue;
				}

				Object value = portletSession.getAttribute(
					name, PortletSession.APPLICATION_SCOPE);

				if (!_isValidAttributeValue(value)) {
					continue;
				}

				Element attributeElement = attributesElement.addElement(
					"attribute");

				attributeElement.addElement("name", name);
				attributeElement.addElement("value", value);
			}
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}

		return requestElement.toXMLString();
	}

	private static boolean _isValidAttributeName(String name) {
		if (StringUtil.equalsIgnoreCase(name, "j_password") ||
			StringUtil.equalsIgnoreCase(name, "LAYOUT_CONTENT") ||
			StringUtil.equalsIgnoreCase(name, "LAYOUTS") ||
			StringUtil.equalsIgnoreCase(name, "PORTLET_RENDER_PARAMETERS") ||
			StringUtil.equalsIgnoreCase(name, "USER_PASSWORD") ||
			name.startsWith("javax.") ||
			name.startsWith("liferay-ui:")) {

			return false;
		}
		else {
			return true;
		}
	}

	private static boolean _isValidAttributeValue(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (obj instanceof Collection<?>) {
			Collection<?> col = (Collection<?>)obj;

			if (col.size() == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		else if (obj instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>)obj;

			if (map.size() == 0) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			String objString = String.valueOf(obj);

			if (Validator.isNull(objString)) {
				return false;
			}

			String hashCode = StringPool.AT.concat(
				StringUtil.toHexString(obj.hashCode()));

			if (objString.endsWith(hashCode)) {
				return false;
			}

			return true;
		}
	}

	private static void _mimeResponseToXML(
		MimeResponse mimeResponse, Element requestElement) {

		String namespace = mimeResponse.getNamespace();

		requestElement.addElement("portlet-namespace", namespace);

		try {
			PortletURL actionURL = mimeResponse.createActionURL();

			requestElement.addElement("action-url", actionURL);
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}

		try {
			PortletURL renderURL = mimeResponse.createRenderURL();

			requestElement.addElement("render-url", renderURL);

			try {
				renderURL.setWindowState(LiferayWindowState.EXCLUSIVE);

				requestElement.addElement("render-url-exclusive", renderURL);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderURL.setWindowState(LiferayWindowState.MAXIMIZED);

				requestElement.addElement("render-url-maximized", renderURL);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderURL.setWindowState(LiferayWindowState.MINIMIZED);

				requestElement.addElement("render-url-minimized", renderURL);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderURL.setWindowState(LiferayWindowState.NORMAL);

				requestElement.addElement("render-url-normal", renderURL);
			}
			catch (WindowStateException wse) {
			}

			try {
				renderURL.setWindowState(LiferayWindowState.POP_UP);

				requestElement.addElement("render-url-pop-up", renderURL);
			}
			catch (WindowStateException wse) {
			}
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}

		ResourceURL resourceURL = mimeResponse.createResourceURL();

		String resourceURLString = HttpUtil.removeParameter(
			resourceURL.toString(), namespace + "struts_action");

		resourceURLString = HttpUtil.removeParameter(
			resourceURLString, namespace + "redirect");

		requestElement.addElement("resource-url", resourceURLString);
	}

	private static void _portletDisplayToXML(
		PortletDisplay portletDisplay, Element portletDisplayElement) {

		portletDisplayElement.addElement("id", portletDisplay.getId());
		portletDisplayElement.addElement(
			"instance-id", portletDisplay.getInstanceId());
		portletDisplayElement.addElement(
			"portlet-name", portletDisplay.getPortletName());
		portletDisplayElement.addElement(
			"resource-pk", portletDisplay.getResourcePK());
		portletDisplayElement.addElement(
			"root-portlet-id", portletDisplay.getRootPortletId());
		portletDisplayElement.addElement("title", portletDisplay.getTitle());
	}

	private static void _themeDisplayToXML(
		ThemeDisplay themeDisplay, Element themeDisplayElement) {

		themeDisplayElement.addElement("cdn-host", themeDisplay.getCDNHost());
		themeDisplayElement.addElement(
			"company-id", themeDisplay.getCompanyId());
		themeDisplayElement.addElement(
			"do-as-user-id", themeDisplay.getDoAsUserId());
		themeDisplayElement.addElement(
			"i18n-language-id", themeDisplay.getI18nLanguageId());
		themeDisplayElement.addElement("i18n-path", themeDisplay.getI18nPath());
		themeDisplayElement.addElement(
			"language-id", themeDisplay.getLanguageId());
		themeDisplayElement.addElement("locale", themeDisplay.getLocale());
		themeDisplayElement.addElement(
			"path-context", themeDisplay.getPathContext());
		themeDisplayElement.addElement(
			"path-friendly-url-private-group",
			themeDisplay.getPathFriendlyURLPrivateGroup());
		themeDisplayElement.addElement(
			"path-friendly-url-private-user",
			themeDisplay.getPathFriendlyURLPrivateUser());
		themeDisplayElement.addElement(
			"path-friendly-url-public",
			themeDisplay.getPathFriendlyURLPublic());
		themeDisplayElement.addElement(
			"path-image", themeDisplay.getPathImage());
		themeDisplayElement.addElement("path-main", themeDisplay.getPathMain());
		themeDisplayElement.addElement(
			"path-theme-images", themeDisplay.getPathThemeImages());
		themeDisplayElement.addElement("plid", themeDisplay.getPlid());
		themeDisplayElement.addElement(
			"portal-url", HttpUtil.removeProtocol(themeDisplay.getPortalURL()));
		themeDisplayElement.addElement(
			"real-user-id", themeDisplay.getRealUserId());
		themeDisplayElement.addElement(
			"scope-group-id", themeDisplay.getScopeGroupId());
		themeDisplayElement.addElement("secure", themeDisplay.isSecure());
		themeDisplayElement.addElement(
			"server-name", themeDisplay.getServerName());
		themeDisplayElement.addElement(
			"server-port", themeDisplay.getServerPort());
		themeDisplayElement.addElement(
			"time-zone", themeDisplay.getTimeZone().getID());
		themeDisplayElement.addElement(
			"url-portal", HttpUtil.removeProtocol(themeDisplay.getURLPortal()));
		themeDisplayElement.addElement("user-id", themeDisplay.getUserId());

		if (themeDisplay.getPortletDisplay() != null) {
			Element portletDisplayElement = themeDisplayElement.addElement(
				"portlet-display");

			_portletDisplayToXML(
				themeDisplay.getPortletDisplay(), portletDisplayElement);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletRequestUtil.class);

}