/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.hubspot.constants.HubSpotConstants;
import com.liferay.hubspot.service.HubSpotService;
import com.liferay.hubspot.service.LiferayService;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Keven Leone
 * @author Ricardo Mariz
 */
@RestController
public class ObjectActionLeadRestController extends BaseRestController {

	@PostMapping("/object/action/lead")
	public void post(@RequestBody String json) throws Exception {
		String contactId = null;

		JSONObject jsonObject = _liferayService.getObjectEntryValuesJSONObject(
			json);

		String externalReferenceCode = jsonObject.optString(
			"r_hs10ContactToHS10Leads_c_hs10ContactERC");

		if (externalReferenceCode.startsWith(
				HubSpotConstants.PREFIX_HUBSPOT_ID)) {

			contactId = externalReferenceCode.substring(
				HubSpotConstants.PREFIX_HUBSPOT_ID.length());
		}
		else {
			long hs10ContactId = jsonObject.optLong(
				"r_hs10ContactToHS10Leads_c_hs10ContactId");

			JSONObject contactJSONObject = new JSONObject(
				get(
					_liferayOAuth2AccessTokenManager.getAuthorization(
						"liferay-hubspot-etc-spring-boot-oahs"),
					UriComponentsBuilder.fromPath(
						"o/c/hs10contacts/" + hs10ContactId
					).build(
					).toUri()));

			if (contactJSONObject == null) {
				return;
			}

			contactJSONObject = _hubSpotService.search(
				"contacts", "email", contactJSONObject.getString("email"));

			if (contactJSONObject == null) {
				return;
			}

			contactId = contactJSONObject.getString("id");
		}

		_liferayService.patchObjectEntry(
			_hubSpotService.postLead(contactId, jsonObject),
			"o/c/hs10leads/" + _liferayService.getClassPK(json));
	}

	@Autowired
	private HubSpotService _hubSpotService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Autowired
	private LiferayService _liferayService;

}