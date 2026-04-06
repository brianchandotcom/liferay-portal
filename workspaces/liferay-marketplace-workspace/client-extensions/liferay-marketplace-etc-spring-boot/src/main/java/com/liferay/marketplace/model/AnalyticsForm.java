/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

/**
 * @author Caleb Hall
 */
public class AnalyticsForm {

	public static AnalyticsForm fromJSONObject(JSONObject jsonObject) {
		JSONArray jsonArray = jsonObject.getJSONArray(
			"incidentReportEmailAddresses");

		List<String> emailAddresses = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			emailAddresses.add(jsonArray.getString(i));
		}

		return new AnalyticsForm(
			jsonObject.getString("corpProjectName"),
			jsonObject.getString("corpProjectUuid"),
			emailAddresses.toArray(new String[0]), jsonObject.getString("name"),
			jsonObject.optString("serverLocation"),
			jsonObject.getString("ownerEmailAddress"));
	}

	public AnalyticsForm(
		String corpProjectName, String corpProjectUuid,
		String[] incidentReportEmailAddresses, String name,
		String serverLocation, String ownerEmailAddress) {

		_corpProjectName = corpProjectName;
		_corpProjectUuid = corpProjectUuid;
		_incidentReportEmailAddresses = incidentReportEmailAddresses;
		_name = name;
		_serverLocation = serverLocation;
		_ownerEmailAddress = ownerEmailAddress;
	}

	public String getCorpProjectName() {
		return _corpProjectName;
	}

	public String getCorpProjectUuid() {
		return _corpProjectUuid;
	}

	public String[] getIncidentReportEmailAddresses() {
		return _incidentReportEmailAddresses;
	}

	public String getName() {
		return _name;
	}

	public String getOwnerEmailAddress() {
		return _ownerEmailAddress;
	}

	public String getServerLocation() {
		if (Validator.isBlank(_serverLocation)) {
			return _SERVER_LOCATION;
		}

		return _serverLocation;
	}

	public String getSharedCluster() {
		return _SHARED_CLUSTER;
	}

	public String getTrial() {
		return _TRIAL;
	}

	public BodyInserter<MultiValueMap<String, String>, ClientHttpRequest>
		toFormData() {

		return BodyInserters.fromFormData(
			"corpProjectName", getCorpProjectName()
		).with(
			"corpProjectUuid", getCorpProjectUuid()
		).with(
			"incidentReportEmailAddresses",
			new JSONArray(
				getIncidentReportEmailAddresses()
			).toString()
		).with(
			"name", getName()
		).with(
			"serverLocation", getServerLocation()
		).with(
			"sharedCluster", getSharedCluster()
		).with(
			"trial", getTrial()
		).with(
			"ownerEmailAddress", getOwnerEmailAddress()
		);
	}

	private static final String _SERVER_LOCATION = "us-west1-ac-uat-c1";

	private static final String _SHARED_CLUSTER = "false";

	private static final String _TRIAL = "true";

	private final String _corpProjectName;
	private final String _corpProjectUuid;
	private final String[] _incidentReportEmailAddresses;
	private final String _name;
	private final String _ownerEmailAddress;
	private final String _serverLocation;

}