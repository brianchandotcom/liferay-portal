/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.event.jrp.JRPEventProcessor;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseEventHandler implements EventHandler {

	protected BaseEventHandler(JSONObject messageJSONObject) {
		_messageJSONObject = messageJSONObject;
	}

	protected JSONObject getMessageJSONObject() {
		return _messageJSONObject;
	}

	protected void updateJRPStatus(
		BuildRunEntity buildRunEntity, BuildEntity buildEntity,
		JobEntity jobEntity, String status) {

		if (buildEntity == null) {
			return;
		}

		String jenkinsBuildId = buildEntity.getBuildParameterValue(
			"JENKINS_BUILD_ID");

		if (StringUtil.isNullOrEmpty(jenkinsBuildId)) {
			return;
		}

		JRPEventProcessor jrpEventProcessor =
			Jethr0ContextUtil.getJRPEventProcessor();

		Map<String, String> messageProperties = HashMapBuilder.put(
			"jenkinsBuildId", jenkinsBuildId
		).put(
			"jethr0JobId",
			() -> {
				if (jobEntity == null) {
					return null;
				}

				return String.valueOf(jobEntity.getId());
			}
		).build();

		JSONObject jsonObject = new JSONObject();

		if (buildEntity != null) {
			jsonObject.put(
				"jethr0BuildId", String.valueOf(buildEntity.getId())
			).put(
				"jethr0BuildURL",
				StringUtil.combine(
					Jethr0ContextUtil.getLiferayPortalURL(), "/#/jobs/builds/",
					buildEntity.getId())
			);
		}

		if (buildRunEntity != null) {
			jsonObject.put(
				"jenkinsBuildURL",
				String.valueOf(buildRunEntity.getJenkinsBuildURL()));

			BuildRunEntity.Result buildRunResult = buildRunEntity.getResult();

			if (buildRunResult != null) {
				jsonObject.put("result", buildRunResult.getKey());
			}
		}

		jsonObject.put("status", status);

		jrpEventProcessor.sendMessage(jsonObject.toString(), messageProperties);
	}

	private final JSONObject _messageJSONObject;

}