/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub;

import com.liferay.ai.hub.service.KubernetesJobService;
import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;

import java.net.URI;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author José Abelenda
 */
@RequestMapping("/object/action/crawler")
@RestController
public class ObjectActionCrawlerRestController extends BaseRestController {

	public ObjectActionCrawlerRestController(
		KubernetesJobService kubernetesJobService) {

		_kubernetesJobService = kubernetesJobService;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> post(@RequestBody String json) {
		if (_log.isDebugEnabled()) {
			_log.debug(json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		try {
			String executionId = _kubernetesJobService.createJob(
				valuesJSONObject.getString("indexName"),
				valuesJSONObject.getString("url"));

			String body = post(
				_liferayOAuth2AccessTokenManager.getAuthorization(
					"liferay-aihub-etc-spring-boot-oahs"),
				new JSONObject(
				).put(
					"crawlerJobStatus", "dispatched"
				).put(
					"executionId", executionId
				).put(
					"r_accountToAIHubCrawlerJobs_accountEntryId",
					valuesJSONObject.getLong(
						"r_accountToAIHubContentRetrievers_accountEntryId")
				).put(
					"r_contentRetrieverToCrawlerJobs_aiHubContentRetrieverId",
					objectEntryJSONObject.getLong("objectEntryId")
				).toString(),
				URI.create("/o/ai-hub/crawler-jobs"));

			return ResponseEntity.accepted(
			).body(
				Map.of(
					"executionId", executionId, "externalReferenceCode",
					new JSONObject(
						body
					).getString(
						"externalReferenceCode"
					))
			);
		}
		catch (Exception exception) {
			_log.error("Crawler dispatch failed", exception);

			return new ResponseEntity<>(
				Map.of("error", String.valueOf(exception.getMessage())),
				HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionCrawlerRestController.class);

	private final KubernetesJobService _kubernetesJobService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}