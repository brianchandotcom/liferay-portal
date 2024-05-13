/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jrp;

import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.jenkins.repository.JenkinsCohortEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
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
public class CreateJobEventHandler extends BaseJRPEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Creating job from JRP at " + StringUtil.toString(new Date()));
		}

		JSONObject jobJSONObject = getJobJSONObject();

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(jobJSONObject);

		JSONArray buildsJSONArray = jobJSONObject.optJSONArray("builds");

		if ((buildsJSONArray != null) && !buildsJSONArray.isEmpty()) {
			BuildEntityRepository buildEntityRepository =
				Jethr0ContextUtil.getBuildEntityRepository();

			for (int i = 0; i < buildsJSONArray.length(); i++) {
				JSONObject buildJSONObject = buildsJSONArray.getJSONObject(i);

				buildEntityRepository.create(jobEntity, buildJSONObject);
			}
		}

		JSONArray jenkinsCohortsJSONArray = jobJSONObject.optJSONArray(
			"jenkinsCohorts");

		if ((jenkinsCohortsJSONArray != null) &&
			!jenkinsCohortsJSONArray.isEmpty()) {

			JenkinsCohortEntityRepository jenkinsCohortEntityRepository =
				Jethr0ContextUtil.getJenkinsCohortEntityRepository();

			for (int i = 0; i < jenkinsCohortsJSONArray.length(); i++) {
				JSONObject jenkinsCohortJSONObject =
					jenkinsCohortsJSONArray.getJSONObject(i);

				long jenkinsCohortId = jenkinsCohortJSONObject.optLong("id");

				if (jenkinsCohortId != 0) {
					jobEntity.addJenkinsCohortEntity(
						jenkinsCohortEntityRepository.getById(jenkinsCohortId));

					continue;
				}

				String jenkinsCohortName = jenkinsCohortJSONObject.optString(
					"name");

				if (StringUtil.isNullOrEmpty(jenkinsCohortName)) {
					continue;
				}

				jobEntity.addJenkinsCohortEntity(
					jenkinsCohortEntityRepository.getByName(jenkinsCohortName));
			}
		}

		jobEntityRepository.update(jobEntity);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Created job ", jobEntity.getEntityURL(), " from JRP at ",
					StringUtil.toString(new Date())));
		}

		return jobEntity.toString();
	}

	protected CreateJobEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		CreateJobEventHandler.class);

}