/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.job.JobEntity;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsTestGitHubCommentEventHandler
	extends BaseTestGitHubCommentEventHandler {

	protected PluginsTestGitHubCommentEventHandler(
		JSONObject messageJSONObject) {

		super(messageJSONObject);
	}

	@Override
	protected JobEntity.Type getJobEntityType() {
		return JobEntity.Type.PLUGINS_PULL_REQUEST;
	}

	@Override
	protected int getJobPriority() {
		return 5;
	}

}