/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub;

import com.liferay.ai.hub.service.KubernetesJobService;
import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;

import io.fabric8.kubernetes.api.model.batch.v1.Job;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<String> post(@RequestBody String json)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(json);
		}

		JSONObject jsonObject = new JSONObject(json);

		JSONObject objectEntryJSONObject = jsonObject.getJSONObject(
			"objectEntry");

		JSONObject valuesJSONObject = objectEntryJSONObject.getJSONObject(
			"values");

		Job job = _kubernetesJobService.createJob(
			valuesJSONObject.getString("indexName"),
			valuesJSONObject.getString("url"));

		return ResponseEntity.ok(
			post(
				_liferayOAuth2AccessTokenManager.getAuthorization(
					"liferay-aihub-etc-spring-boot-oahs"),
				new JSONObject(
				).put(
					"crawlerJobStatus", "dispatched"
				).put(
					"executionId",
					job.getMetadata(
					).getName()
				).put(
					"r_accountToAIHubCrawlerJobs_accountEntryId",
					valuesJSONObject.getLong(
						"r_accountToAIHubContentRetrievers_accountEntryId")
				).put(
					"r_contentRetrieverToCrawlerJobs_aiHubContentRetrieverId",
					objectEntryJSONObject.getLong("objectEntryId")
				).toString(),
				URI.create("/o/ai-hub/crawler-jobs")));
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionCrawlerRestController.class);

	private final KubernetesJobService _kubernetesJobService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}