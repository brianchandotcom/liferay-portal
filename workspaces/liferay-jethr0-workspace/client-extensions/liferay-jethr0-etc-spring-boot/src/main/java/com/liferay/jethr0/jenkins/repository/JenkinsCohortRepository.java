/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohort;
import com.liferay.jethr0.jenkins.dalo.JenkinsCohortDALO;
import com.liferay.jethr0.jenkins.dalo.JenkinsCohortToJenkinsServersDALO;
import com.liferay.jethr0.util.StringUtil;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsCohortRepository
	extends BaseEntityRepository<JenkinsCohort> {

	public JenkinsCohort add(String name) {
		if (StringUtil.isNullOrEmpty(name)) {
			throw new RuntimeException("Invalid Jenkins cohort name: " + name);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return add(jsonObject);
	}

	@Override
	public JenkinsCohortDALO getEntityDALO() {
		return _jenkinsCohortDALO;
	}

	@Override
	public void initializeRelationships() {
		for (JenkinsCohort jenkinsCohort : getAll()) {
			for (long jenkinsServerId :
					_jenkinsCohortToJenkinsServersDALO.getChildEntityIds(
						jenkinsCohort)) {

				if (jenkinsServerId == 0) {
					continue;
				}

				jenkinsCohort.addJenkinsServer(
					_jenkinsServerRepository.getById(jenkinsServerId));
			}
		}
	}

	public void setJenkinsServerRepository(
		JenkinsServerRepository jenkinsServerRepository) {

		_jenkinsServerRepository = jenkinsServerRepository;
	}

	@Autowired
	private JenkinsCohortDALO _jenkinsCohortDALO;

	@Autowired
	private JenkinsCohortToJenkinsServersDALO
		_jenkinsCohortToJenkinsServersDALO;

	private JenkinsServerRepository _jenkinsServerRepository;

}