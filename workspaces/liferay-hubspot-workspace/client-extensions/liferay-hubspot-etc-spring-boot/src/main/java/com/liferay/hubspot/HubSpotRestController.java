/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.hubspot.service.HubSpotService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Keven Leone
 * @author Ricardo Mariz
 */
@RequestMapping("/hubspot")
@RestController
public class HubSpotRestController extends BaseRestController {

	@PostMapping("/company")
	public void postCompany(@RequestBody String json) throws Exception {
		_patchObjectEntry(
			_hubSpotService.postCompany(_getObjectEntryValuesJSONObject(json)),
			"o/c/h1s4companies/" + _getClassPK(json));
	}

	@PostMapping("/contact")
	public void postContact(@RequestBody String json) throws Exception {
		_patchObjectEntry(
			_hubSpotService.postContact(_getObjectEntryValuesJSONObject(json)),
			"o/c/h1s4contacts/" + _getClassPK(json));
	}

	@PostMapping("/lead")
	public void postLead(@RequestBody String json) throws Exception {
		JSONObject jsonObject = _getObjectEntryValuesJSONObject(json);

		String contactId = null;

		if (jsonObject.optString(
				"r_h1s4ContactToH1S4Leads_c_h1s4ContactERC"
			).startsWith(
				_PREFIX_HUBSPOT_ID
			)) {

			contactId = StringUtil.replace(
				jsonObject.optString(
					"r_h1s4ContactToH1S4Leads_c_h1s4ContactERC"),
				_PREFIX_HUBSPOT_ID, null);
		}
		else {
			JSONObject contactJSONObject = _getH1S4ContactJSONObject(
				jsonObject.optLong("r_h1s4ContactToH1S4Leads_c_h1s4ContactId"));

			if (contactJSONObject == null) {
				return;
			}

			contactJSONObject = _hubSpotService.searchHubSpotResource(
				"contacts", "email", contactJSONObject.getString("email"));

			if (contactJSONObject == null) {
				return;
			}

			contactId = contactJSONObject.getString("id");
		}

		_patchObjectEntry(
			_hubSpotService.postLead(contactId, jsonObject),
			"o/c/h1s4leads/" + _getClassPK(json));
	}

	private long _getClassPK(String json) {
		JSONObject jsonObject = new JSONObject(json);

		return jsonObject.getLong("classPK");
	}

	private JSONObject _getH1S4ContactJSONObject(long id) {
		return new JSONObject(
			get(
				_liferayOAuth2AccessTokenManager.getAuthorization(
					"liferay-hubspot-etc-spring-boot-oahs"),
				UriComponentsBuilder.fromPath(
					"o/c/h1s4contacts/" + id
				).build(
				).toUri()));
	}

	private JSONObject _getObjectEntryValuesJSONObject(String json) {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		return objectEntryJSONObject.getJSONObject("values");
	}

	private void _patchObjectEntry(JSONObject jsonObject, String path)
		throws Exception {

		if (jsonObject == null) {
			return;
		}

		String body = new JSONObject(
		).put(
			"externalReferenceCode",
			_PREFIX_HUBSPOT_ID + jsonObject.getLong("id")
		).toString();

		patch(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-hubspot-etc-spring-boot-oahs"),
			body,
			UriComponentsBuilder.fromPath(
				path
			).build(
			).toUri());

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Object entry updated: ", body, ", ", path));
		}
	}

	private static final String _PREFIX_HUBSPOT_ID = "HSI-";

	private static final Log _log = LogFactory.getLog(
		HubSpotRestController.class);

	@Autowired
	private HubSpotService _hubSpotService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}