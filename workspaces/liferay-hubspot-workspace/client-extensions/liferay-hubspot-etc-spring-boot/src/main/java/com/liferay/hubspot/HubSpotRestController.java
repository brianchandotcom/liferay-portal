/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.hubspot.service.HubSpotService;
import com.liferay.hubspot.service.LiferayService;
import com.liferay.petra.string.StringUtil;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricardo Mariz
 */
@RequestMapping("/hubspot")
@RestController
public class HubSpotRestController extends BaseRestController {

	@PostMapping("/company")
	public void postCompany(@RequestBody String json) throws Exception {
		JSONObject companyJSONObject = _hubSpotService.createCompany(
			_getObjectEntryValuesJSONObject(json));

		if (companyJSONObject == null) {
			return;
		}

		_liferayService.patchHeadlessEntry(
			new JSONObject(
			).put(
				"externalReferenceCode",
				"HSI-" + companyJSONObject.getLong("id")
			).toString(),
			"o/c/h1s4companies/" + _getClassPK(json));
	}

	@PostMapping("/contact")
	public void postContact(@RequestBody String json) throws Exception {
		JSONObject contactJSONObject = _hubSpotService.createContact(
			_getObjectEntryValuesJSONObject(json));

		if (contactJSONObject == null) {
			return;
		}

		_liferayService.patchHeadlessEntry(
			new JSONObject(
			).put(
				"externalReferenceCode",
				"HSI-" + contactJSONObject.getLong("id")
			).toString(),
			"o/c/h1s4contacts/" + _getClassPK(json));
	}

	@PostMapping("/lead")
	public void postLead(@RequestBody String json) throws Exception {
		JSONObject jsonObject = _getObjectEntryValuesJSONObject(json);

		String contactId;

		if (jsonObject.optString(
				"r_h1s4ContactToH1S4Leads_c_h1s4ContactERC"
			).startsWith(
				"HSI-"
			)) {

			contactId = StringUtil.replace(
				jsonObject.optString(
					"r_h1s4ContactToH1S4Leads_c_h1s4ContactERC"),
				"HSI-", null);
		}
		else {
			JSONObject contactJSONObject = _liferayService.getHubSpotContact(
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

		JSONObject leadJSONObject = _hubSpotService.createLead(
			contactId, jsonObject);

		if (leadJSONObject == null) {
			return;
		}

		_liferayService.patchHeadlessEntry(
			new JSONObject(
			).put(
				"externalReferenceCode", "HSI-" + leadJSONObject.getLong("id")
			).toString(),
			"o/c/h1s4leads/" + _getClassPK(json));
	}

	private long _getClassPK(String json) {
		JSONObject jsonObject = new JSONObject(json);

		return jsonObject.getLong("classPK");
	}

	private JSONObject _getObjectEntryValuesJSONObject(String json) {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		return objectEntryJSONObject.getJSONObject("values");
	}

	@Autowired
	private HubSpotService _hubSpotService;

	@Autowired
	private LiferayService _liferayService;

}