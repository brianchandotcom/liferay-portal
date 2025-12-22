/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.hubspot.service.HubSpotService;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ricardo Mariz
 */
@RequestMapping("/")
@RestController
public class HubSpotRestController extends BaseRestController {

	@PostMapping("/company")
	public void createCompany(@RequestBody String json) throws Exception {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		_hubSpotService.createCompany(
			valuesJSONObject.getString("companyName"),
			String.valueOf(valuesJSONObject.getInt("numberOfEmployees")),
			valuesJSONObject.getString("phone"),
			valuesJSONObject.getString("websiteURL"));
	}

	@PostMapping("/contact")
	public void createContact(@RequestBody String json) throws Exception {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		_hubSpotService.createContact(
			valuesJSONObject.getString("email"),
			valuesJSONObject.getString("firstName"),
			valuesJSONObject.getString("lastName"),
			valuesJSONObject.getString("phone"));
	}

	@PostMapping("/lead")
	public void createLead(@RequestBody String json) throws Exception {
		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		_hubSpotService.createLead(
			valuesJSONObject.getString("email"),
			valuesJSONObject.getString("firstName"),
			valuesJSONObject.getString("lastName"),
			valuesJSONObject.getString("phone"),
			valuesJSONObject.getString("companyName"),
			String.valueOf(valuesJSONObject.getInt("numberOfEmployees")),
			valuesJSONObject.getString("websiteURL"));
	}

	@Autowired
	private HubSpotService _hubSpotService;

}