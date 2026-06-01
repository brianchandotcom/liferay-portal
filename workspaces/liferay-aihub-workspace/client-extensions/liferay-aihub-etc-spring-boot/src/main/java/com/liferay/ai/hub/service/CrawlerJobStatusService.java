/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.batch.v1.Job;
import io.fabric8.kubernetes.api.model.batch.v1.JobCondition;
import io.fabric8.kubernetes.api.model.batch.v1.JobStatus;

import java.net.URI;
import java.net.URLEncoder;

import java.nio.charset.StandardCharsets;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author José Abelenda
 */
@Service
public class CrawlerJobStatusService extends BaseService {

	public CrawlerJobStatusService(KubernetesJobService kubernetesJobService) {
		_kubernetesJobService = kubernetesJobService;
	}

	@Scheduled(fixedDelay = 60000)
	public void updateStatuses() {
		JSONArray jsonArray = _getActiveCrawlerJobsJSONArray();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			try {
				String executionId = jsonObject.optString("executionId");

				if (executionId.isEmpty()) {
					continue;
				}

				Job job = _kubernetesJobService.getJob(executionId);

				String crawlerJobStatus = _getCrawlerJobStatus(job);

				if ((crawlerJobStatus == null) ||
					crawlerJobStatus.equals(
						jsonObject.optString("crawlerJobStatus"))) {

					continue;
				}

				JobStatus jobStatus = null;
				String startDate = null;

				if (job != null) {
					jobStatus = job.getStatus();

					startDate = jobStatus.getStartTime();
				}

				JSONObject patchJSONObject = new JSONObject(
				).put(
					"crawlerJobStatus", crawlerJobStatus
				).put(
					"startDate", startDate
				);

				if (crawlerJobStatus.equals("abandoned")) {
					patchJSONObject.put(
						"endDate", _getCurrentDateTime()
					).put(
						"errorMessage", "Kubernetes job not found"
					);
				}
				else if (crawlerJobStatus.equals("failed")) {
					JobCondition jobCondition = _getJobCondition(
						jobStatus, "Failed");

					if (jobCondition == null) {
						continue;
					}

					String endDate = jobCondition.getLastTransitionTime();

					if (endDate == null) {
						endDate = _getCurrentDateTime();
					}

					String errorMessage = jobCondition.getMessage();

					if (errorMessage == null) {
						errorMessage = "Kubernetes job failed";
					}

					patchJSONObject.put(
						"endDate", endDate
					).put(
						"errorMessage", errorMessage
					);
				}
				else if (crawlerJobStatus.equals("succeeded")) {
					String endDate = jobStatus.getCompletionTime();

					if (endDate == null) {
						endDate = _getCurrentDateTime();
					}

					patchJSONObject.put("endDate", endDate);

					ObjectMeta objectMeta = job.getMetadata();

					_putIndexedDocumentStats(
						patchJSONObject, objectMeta.getName());
				}

				long id = jsonObject.getLong("id");

				patch(
					_liferayOAuth2AccessTokenManager.getAuthorization(
						"liferay-aihub-etc-spring-boot-oahs"),
					patchJSONObject.toString(),
					URI.create("/o/ai-hub/crawler-jobs/" + id));

				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Crawler job ", id, " status updated to ",
							patchJSONObject.getString("crawlerJobStatus")));
				}
			}
			catch (Exception exception) {
				_log.error(
					"Unable to update status of crawler job " +
						jsonObject.optLong("id"),
					exception);
			}
		}
	}

	private JSONArray _getActiveCrawlerJobsJSONArray() {
		String response = get(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-aihub-etc-spring-boot-oahs"),
			URI.create(
				StringBundler.concat(
					"/o/ai-hub/crawler-jobs?filter=",
					URLEncoder.encode(
						"crawlerJobStatus in ('dispatched','queued','running')",
						StandardCharsets.UTF_8),
					"&pageSize=100")));

		JSONObject jsonObject = new JSONObject(response);

		return jsonObject.optJSONArray("items");
	}

	private String _getCrawlerJobStatus(Job job) {
		if (job == null) {
			return "abandoned";
		}

		JobStatus jobStatus = job.getStatus();

		if (jobStatus == null) {
			return null;
		}

		Integer succeeded = jobStatus.getSucceeded();

		if ((succeeded != null) && (succeeded > 0)) {
			return "succeeded";
		}

		Integer failed = jobStatus.getFailed();

		if ((failed != null) && (failed > 0)) {
			return "failed";
		}

		Integer active = jobStatus.getActive();

		if ((active != null) && (active > 0)) {
			return "running";
		}

		return null;
	}

	private String _getCurrentDateTime() {
		return Instant.now(
		).truncatedTo(
			ChronoUnit.SECONDS
		).toString();
	}

	private JobCondition _getJobCondition(JobStatus jobStatus, String type) {
		List<JobCondition> jobConditions = jobStatus.getConditions();

		if (jobConditions == null) {
			return null;
		}

		for (JobCondition jobCondition : jobConditions) {
			if (type.equals(jobCondition.getType()) &&
				Objects.equals(jobCondition.getStatus(), "True")) {

				return jobCondition;
			}
		}

		return null;
	}

	private void _putIndexedDocumentStats(JSONObject jsonObject, String name) {
		try {
			String jobLog = _kubernetesJobService.getJobLog(name, 50);

			if (jobLog == null) {
				return;
			}

			for (String line : jobLog.split("\n")) {
				if (!line.contains("crawler_final_report")) {
					continue;
				}

				JSONObject lineJSONObject = new JSONObject(line);

				JSONObject jsonPayloadJSONObject = lineJSONObject.optJSONObject(
					"jsonPayload");

				if (jsonPayloadJSONObject == null) {
					jsonPayloadJSONObject = lineJSONObject;
				}

				JSONObject crawlerJSONObject =
					jsonPayloadJSONObject.optJSONObject("crawler");

				if (crawlerJSONObject == null) {
					return;
				}

				jsonObject.put(
					"indexedDocumentBytes",
					crawlerJSONObject.getLong("docs_upserted_bytes")
				).put(
					"indexedDocumentCount",
					crawlerJSONObject.getLong("docs_upserted")
				);

				return;
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to read indexed document stats for job " + name,
					exception);
			}
		}
	}

	private static final Log _log = LogFactory.getLog(
		CrawlerJobStatusService.class);

	private final KubernetesJobService _kubernetesJobService;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}