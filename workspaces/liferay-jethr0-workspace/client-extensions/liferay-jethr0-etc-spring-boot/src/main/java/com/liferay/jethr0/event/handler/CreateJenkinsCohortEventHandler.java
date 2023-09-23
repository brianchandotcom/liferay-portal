/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.handler;

import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateJenkinsCohortEventHandler extends BaseObjectEventHandler {

	@Override
	public String process() throws Exception {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject jenkinsCohortJSONObject = validateJenkinsCohortJSONObject(
			messageJSONObject.optJSONObject("jenkinsCohort"));

		JenkinsCohortEntityRepository jenkinsCohortEntityRepository =
			getJenkinsCohortEntityRepository();

		JenkinsCohortEntity jenkinsCohortEntity =
			jenkinsCohortEntityRepository.create(jenkinsCohortJSONObject);

		JSONArray jenkinsServersJSONArray =
			jenkinsCohortJSONObject.optJSONArray("jenkinsServers");

		if ((jenkinsServersJSONArray != null) &&
			!jenkinsServersJSONArray.isEmpty()) {

			JenkinsServerEntityRepository jenkinsServerEntityRepository =
				getJenkinsServerEntityRepository();
			JenkinsNodeEntityRepository jenkinsNodeEntityRepository =
				getJenkinsNodeEntityRepository();

			for (int i = 0; i < jenkinsServersJSONArray.length(); i++) {
				JSONObject jenkinsServerJSONObject =
					jenkinsServersJSONArray.getJSONObject(i);

				JenkinsServerEntity jenkinsServerEntity =
					jenkinsServerEntityRepository.create(
						jenkinsCohortEntity, jenkinsServerJSONObject);

				jenkinsNodeEntityRepository.createAll(jenkinsServerEntity);

				jenkinsServerEntityRepository.update(jenkinsServerEntity);
			}
		}

		jenkinsCohortEntityRepository.update(jenkinsCohortEntity);

		return jenkinsCohortEntity.toString();
	}

	protected CreateJenkinsCohortEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

}