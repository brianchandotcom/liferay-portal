/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jrp;

import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsNodeEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsServerEntityRepository;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CreateJenkinsCohortEventHandler extends BaseJRPEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Creating jenkins cohort from JRP at " +
					StringUtil.toString(new Date()));
		}

		JSONObject jenkinsCohortJSONObject = getJenkinsCohortJSONObject();

		JenkinsCohortEntityRepository jenkinsCohortEntityRepository =
			Jethr0ContextUtil.getJenkinsCohortEntityRepository();

		JenkinsCohortEntity jenkinsCohortEntity =
			jenkinsCohortEntityRepository.create(jenkinsCohortJSONObject);

		JSONArray jenkinsServersJSONArray =
			jenkinsCohortJSONObject.optJSONArray("jenkinsServers");

		if ((jenkinsServersJSONArray != null) &&
			!jenkinsServersJSONArray.isEmpty()) {

			JenkinsServerEntityRepository jenkinsServerEntityRepository =
				Jethr0ContextUtil.getJenkinsServerEntityRepository();
			JenkinsNodeEntityRepository jenkinsNodeEntityRepository =
				Jethr0ContextUtil.getJenkinsNodeEntityRepository();

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

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Created jenkins cohort ",
					jenkinsCohortEntity.getEntityURL(), " from JRP at ",
					StringUtil.toString(new Date())));
		}

		return jenkinsCohortEntity.toString();
	}

	protected CreateJenkinsCohortEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		CreateJenkinsCohortEventHandler.class);

}