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

package com.liferay.portal.ee.license;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.license.util.LicenseManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.http.HttpAuthManagerUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.license.LicenseConstants;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;
import com.liferay.portal.util.LicenseUtil;

import java.security.MessageDigest;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tina Tian
 */
public class UpdateLicenseAction
	extends com.liferay.portal.action.UpdateLicenseAction {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		String command = ParamUtil.getString(httpServletRequest, Constants.CMD);

		if (Objects.equals(command, "resetState")) {
			LCSLicenseManager.resetState(
				httpServletRequest, httpServletResponse);
		}
		else if (Objects.equals(command, "validateState")) {
			_writeKey(httpServletRequest, httpServletResponse);

			return null;
		}

		int[] lcsStates = LCSLicenseManager.getLCSStates(httpServletRequest);

		if ((lcsStates[0] != LCSPortletState.GOOD.intValue()) &&
			(lcsStates[1] != LicenseConstants.STATE_GOOD)) {

			LicenseUtil.registerOrder(httpServletRequest);

			lcsStates = LCSLicenseManager.getLCSStates(httpServletRequest);
		}

		if ((lcsStates[0] != LCSPortletState.GOOD.intValue()) &&
			(lcsStates[1] != LicenseConstants.STATE_GOOD)) {

			String clusterNodeId = ParamUtil.getString(
				httpServletRequest, "clusterNodeId");

			if (command.equals("licenseProperties")) {
				String licenseProperties = _getLicenseProperties(clusterNodeId);

				httpServletResponse.setContentType(
					ContentTypes.APPLICATION_JSON);

				ServletResponseUtil.write(
					httpServletResponse, licenseProperties);

				return null;
			}
			else if (command.equals("serverInfo")) {
				String serverInfo = _getServerInfo(clusterNodeId);

				httpServletResponse.setContentType(
					ContentTypes.APPLICATION_JSON);

				ServletResponseUtil.write(httpServletResponse, serverInfo);

				return null;
			}

			return actionMapping.getActionForward("portal.license");
		}

		return super.execute(
			actionMapping, httpServletRequest, httpServletResponse);
	}

	private String _digest(MessageDigest messageDigest, String text) {
		messageDigest.update(text.getBytes());

		byte[] bytes = messageDigest.digest();

		StringBuilder sb = new StringBuilder(bytes.length << 1);

		for (int i = 0; i < bytes.length; i++) {
			int byte_ = bytes[i] & 0xff;

			sb.append(_HEX_CHARACTERS[byte_ >> 4]);
			sb.append(_HEX_CHARACTERS[byte_ & 0xf]);
		}

		return sb.toString();
	}

	private String _digest(String productId, String uuid, int licenseState)
		throws Exception {

		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		String digest = _digest(messageDigest, uuid + productId);

		int length = digest.length();

		StringBuilder sb = new StringBuilder(length + (length / 4));

		for (int i = 0; i < (length / 2); i++) {
			if ((i % 2) == 0) {
				sb.append(licenseState);
			}

			sb.append(digest.charAt(i));
			sb.append(digest.charAt(length - i - 1));
		}

		return _digest(messageDigest, sb.toString());
	}

	private String _getLicenseProperties(String clusterNodeId) {
		List<Map<String, String>> licenseProperties =
			LicenseManagerUtil.getClusterLicenseProperties(clusterNodeId);

		if (licenseProperties == null) {
			return StringPool.BLANK;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Map<String, String> propertiesMap : licenseProperties) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			for (Map.Entry<String, String> entry : propertiesMap.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	private String _getServerInfo(String clusterNodeId) throws Exception {
		Map<String, String> serverInfo = LicenseUtil.getClusterServerInfo(
			clusterNodeId);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (serverInfo != null) {
			for (Map.Entry<String, String> entry : serverInfo.entrySet()) {
				jsonObject.put(entry.getKey(), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	private void _writeKey(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			long userId = HttpAuthManagerUtil.getBasicUserId(
				httpServletRequest);

			if (userId <= 0) {
				return;
			}
		}
		catch (Exception e) {
			return;
		}

		String productId = ParamUtil.getString(httpServletRequest, "productId");
		String uuid = ParamUtil.getString(httpServletRequest, "uuid");

		if (Validator.isNull(productId) || Validator.isNull(uuid)) {
			return;
		}

		int licenseState = LCSLicenseManager.getLicenseState(null);

		try {
			String digest = _digest(productId, uuid, licenseState);

			httpServletResponse.setContentType(ContentTypes.TEXT);

			ServletResponseUtil.write(httpServletResponse, digest);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static final char[] _HEX_CHARACTERS = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
		'e', 'f'
	};

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateLicenseAction.class);

}