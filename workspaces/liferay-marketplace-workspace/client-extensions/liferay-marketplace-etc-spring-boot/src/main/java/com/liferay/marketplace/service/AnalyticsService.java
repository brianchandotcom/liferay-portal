/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;

import java.util.Base64;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Caleb Hall
 */
@Component
public class AnalyticsService extends BaseService {

	public JSONObject getAnalyticsContextJSONObject(String environmentName) {
		if (Objects.equals(environmentName, "internal")) {
			return new JSONObject(
			).put(
				"emailAddress", _analyticsInternalAuthEmailAddress
			).put(
				"password", _analyticsInternalAuthPassword
			).put(
				"url", _analyticsInternalAuthUrl
			);
		}

		return new JSONObject(
		).put(
			"emailAddress", _analyticsAuthEmailAddress
		).put(
			"password", _analyticsAuthPassword
		).put(
			"url", _analyticsAuthUrl
		);
	}

	public String getAuthorization() {
		return getAuthorization(getAnalyticsContextJSONObject(null));
	}

	public String getAuthorization(JSONObject analyticsContextJSONObject) {
		Base64.Encoder encoder = Base64.getEncoder();

		String emailAddress = analyticsContextJSONObject.getString(
			"emailAddress");

		String authorization =
			emailAddress + ":" +
				analyticsContextJSONObject.getString("password");

		return "Basic " + encoder.encodeToString(authorization.getBytes());
	}

	public JSONObject getCorpProjectUuidJSONObject(
		JSONObject analyticsContextJSONObject, String corpProjectUuid) {

		try {
			JSONObject jsonObject = new JSONObject(
				get(
					getAuthorization(analyticsContextJSONObject),
					UriComponentsBuilder.fromUriString(
						analyticsContextJSONObject.getString("url")
					).path(
						"/o/faro/main/project/corpProjectUuid/" +
							corpProjectUuid
					).build(
					).toUri()));

			if (jsonObject.optInt("groupId") == 0) {
				return null;
			}

			return jsonObject;
		}
		catch (WebClientResponseException webClientResponseException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to get Analytics Cloud project: ",
						corpProjectUuid, " \n",
						webClientResponseException.getResponseBodyAsString()));
			}

			return null;
		}
	}

	public String provision(JSONObject jsonObject) throws Exception {
		return provision(getAnalyticsContextJSONObject(null), jsonObject);
	}

	public String provision(
			JSONObject analyticsContextJSONObject, JSONObject jsonObject)
		throws Exception {

		try {
			String response = WebClient.builder(
			).baseUrl(
				analyticsContextJSONObject.getString("url")
			).defaultHeader(
				HttpHeaders.AUTHORIZATION,
				getAuthorization(analyticsContextJSONObject)
			).build(
			).post(
			).uri(
				"/o/faro/main/project/provisioned"
			).contentType(
				MediaType.APPLICATION_FORM_URLENCODED
			).body(
				BodyInserters.fromFormData(
					"corpProjectName", jsonObject.optString("corpProjectName")
				).with(
					"corpProjectUuid", jsonObject.optString("corpProjectUuid")
				).with(
					"enableAutoConfiguration",
					String.valueOf(
						jsonObject.optBoolean("enableAutoConfiguration", true))
				).with(
					"friendlyURL", jsonObject.optString("friendlyURL")
				).with(
					"incidentReportEmailAddresses",
					jsonObject.getJSONArray(
						"incidentReportEmailAddresses"
					).toString()
				).with(
					"name", jsonObject.getString("name")
				).with(
					"serverLocation", jsonObject.getString("serverLocation")
				).with(
					"sharedCluster", "false"
				).with(
					"trial", "false"
				).with(
					"ownerEmailAddress",
					jsonObject.getString("ownerEmailAddress")
				)
			).retrieve(
			).bodyToMono(
				String.class
			).block();

			if (_log.isInfoEnabled()) {
				_log.info("Analytics project created " + response);
			}

			return response;
		}
		catch (WebClientResponseException webClientResponseException) {
			_log.error(
				StringBundler.concat(
					"Unable to provision Analytics Cloud project: ", jsonObject,
					"\n",
					webClientResponseException.getResponseBodyAsString()));

			throw webClientResponseException;
		}
	}

	public JSONObject provisionAnalyticsProject(
			JSONObject analyticsFormJSONObject, String analyticsEnvironment,
			String corpProjectUuid)
		throws Exception {

		JSONObject analyticsContextJSONObject = getAnalyticsContextJSONObject(
			analyticsEnvironment);

		JSONObject analyticsProjectJSONObject = getCorpProjectUuidJSONObject(
			analyticsContextJSONObject, corpProjectUuid);

		if (analyticsProjectJSONObject == null) {
			if (Objects.equals(analyticsEnvironment, "internal")) {
				analyticsFormJSONObject.put(
					"serverLocation", "us-west1-ac-uat-c1");
			}

			analyticsFormJSONObject.put("corpProjectUuid", corpProjectUuid);

			analyticsProjectJSONObject = new JSONObject(
				provision(analyticsContextJSONObject, analyticsFormJSONObject));
		}

		return analyticsProjectJSONObject;
	}

	private static final Log _log = LogFactory.getLog(AnalyticsService.class);

	@Value("${liferay.marketplace.analytics.auth.email.address}")
	private String _analyticsAuthEmailAddress;

	@Value("${liferay.marketplace.analytics.auth.password}")
	private String _analyticsAuthPassword;

	@Value("${liferay.marketplace.analytics.auth.url}")
	private String _analyticsAuthUrl;

	@Value("${liferay.marketplace.analytics.internal.auth.email.address}")
	private String _analyticsInternalAuthEmailAddress;

	@Value("${liferay.marketplace.analytics.internal.auth.password}")
	private String _analyticsInternalAuthPassword;

	@Value("${liferay.marketplace.analytics.internal.auth.url}")
	private String _analyticsInternalAuthUrl;

}