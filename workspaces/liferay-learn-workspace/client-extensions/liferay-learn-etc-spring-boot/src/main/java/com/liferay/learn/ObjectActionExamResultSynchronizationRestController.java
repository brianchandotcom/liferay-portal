/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.learn;

import com.liferay.client.extension.util.spring.boot.BaseRestController;
import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.petra.string.StringBundler;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Nilton Vieira
 */
@RequestMapping("/object/action/exam/result/synchronization")
@RestController
public class ObjectActionExamResultSynchronizationRestController
	extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		if (_log.isInfoEnabled()) {
			_log.info("Exam result synchronization started");
		}

		int examResultAmount = 0;
		long startTime = System.currentTimeMillis();
		String status = "Failed";

		try {
			OffsetDateTime offsetDateTime = _getLatestSuccessfulExecutionDate(
				jwt);

			while (offsetDateTime.isBefore(
						OffsetDateTime.now(ZoneOffset.UTC))) {

				examResultAmount += _importExamResults(jwt, offsetDateTime);

				offsetDateTime = offsetDateTime.plusDays(7);
			}

			status = "Successful";
		}
		catch (Exception exception) {
			_log.error(exception);
		}
		finally {
			_updateExamResultSynchronizationEntry(
				new JSONObject(
					json
				).getLong(
					"classPK"
				),
				examResultAmount, System.currentTimeMillis() - startTime, jwt,
				status);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Exam result synchronization finished");
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	@Override
	protected String getWebClientBaseURL() {
		return "";
	}

	private OffsetDateTime _getLatestSuccessfulExecutionDate(Jwt jwt) {
		JSONObject responseJSONObject = new JSONObject(
			get(
				"Bearer " + jwt.getTokenValue(),
				StringBundler.concat(
					lxcDXPServerProtocol, "://", lxcDXPMainDomain,
					"/o/c/p2s3examresultsynchronizations/scopes/", _siteGroupId,
					"?fields=dateCreated",
					"&filter=synchronizationStatus eq 'Successful'&pageSize=1",
					"&sort=dateCreated:desc")));

		JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

		if (itemsJSONArray.isEmpty()) {
			return OffsetDateTime.of(2017, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
		}

		JSONObject jsonObject = itemsJSONArray.getJSONObject(0);

		return OffsetDateTime.parse(jsonObject.getString("dateCreated"));
	}

	private String _getPayload(JSONObject responseJSONObject) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"date",
			OffsetDateTime.parse(
				responseJSONObject.getString("date")
			).atZoneSameInstant(
				ZoneOffset.UTC
			).format(
				DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX")
			)
		).put(
			"email",
			responseJSONObject.getJSONObject(
				"simpleRegistration"
			).getJSONObject(
				"candidate"
			).getString(
				"email"
			)
		).put(
			"examName", responseJSONObject.getString("examName")
		).put(
			"externalReferenceCode", responseJSONObject.getLong("id")
		).put(
			"firstName",
			responseJSONObject.getJSONObject(
				"simpleRegistration"
			).getJSONObject(
				"candidate"
			).getString(
				"firstName"
			)
		).put(
			"lastName",
			responseJSONObject.getJSONObject(
				"simpleRegistration"
			).getJSONObject(
				"candidate"
			).getString(
				"lastName"
			)
		).put(
			"result", responseJSONObject.getString("passFail")
		).put(
			"testName",
			responseJSONObject.getJSONObject(
				"simpleRegistration"
			).getString(
				"testName"
			)
		);

		return jsonObject.toString();
	}

	private int _importExamResults(Jwt jwt, OffsetDateTime startDate) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"endDate",
			startDate.plusDays(
				7
			).format(
				DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")
			)
		).put(
			"requestType", "GET TRANSCRIPTS BY DATE RANGE"
		).put(
			"returnFormat", "JSON"
		).put(
			"securityToken", _webassessorSecurityToken
		).put(
			"startDate",
			startDate.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"))
		);

		JSONArray responseJSONArray = new JSONArray(
			post(
				null, jsonObject.toString(),
				"https://webassessor.com/WebAssessorWebServices/jaxrs" +
					"/wawebservices/processRequest"));

		if (responseJSONArray.get(0) instanceof String) {
			return 0;
		}

		for (int i = 0; i < responseJSONArray.length(); i++) {
			JSONObject responseJSONObject = responseJSONArray.getJSONObject(i);

			put(
				"Bearer " + jwt.getTokenValue(),
				_getPayload(responseJSONObject),
				StringBundler.concat(
					lxcDXPServerProtocol, "://", lxcDXPMainDomain,
					"/o/c/p2s3examresults/scopes/", _siteGroupId,
					"/by-external-reference-code/",
					responseJSONObject.getLong("id")));
		}

		return responseJSONArray.length();
	}

	private void _updateExamResultSynchronizationEntry(
		Long classPK, int examResultAmount, long executionTime, Jwt jwt,
		String synchronizationStatus) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"examResultAmount", examResultAmount
		).put(
			"executionTime", executionTime
		).put(
			"synchronizationStatus", synchronizationStatus
		);

		patch(
			"Bearer " + jwt.getTokenValue(), jsonObject.toString(),
			StringBundler.concat(
				lxcDXPServerProtocol, "://", lxcDXPMainDomain,
				"/o/c/p2s3examresultsynchronizations/", classPK));
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionExamResultSynchronizationRestController.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

	@Value("${liferay.learn.dxp.site.group.id}")
	private Long _siteGroupId;

	@Value("${liferay.learn.webassessor.security.token}")
	private String _webassessorSecurityToken;

}