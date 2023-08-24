/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohort;
import com.liferay.jethr0.jenkins.dalo.JenkinsCohortEntityDALO;
import com.liferay.jethr0.jenkins.dalo.JenkinsCohortToJenkinsServersEntityRelationshipDALO;
import com.liferay.jethr0.util.StringUtil;

import java.util.Objects;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsCohortEntityRepository
	extends BaseEntityRepository<JenkinsCohort> {

	public JenkinsCohort add(String name) {
		if (StringUtil.isNullOrEmpty(name)) {
			throw new RuntimeException("Invalid Jenkins cohort name: " + name);
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return add(jsonObject);
	}

	public JenkinsCohort getByName(String name) {
		for (JenkinsCohort jenkinsCohort : getAll()) {
			if (!Objects.equals(name, jenkinsCohort.getName())) {
				continue;
			}

			return jenkinsCohort;
		}

		return null;
	}

	@Override
	public JenkinsCohortEntityDALO getEntityDALO() {
		return _jenkinsCohortEntityDALO;
	}

	@Override
	public void initializeRelationships() {
		for (JenkinsCohort jenkinsCohort : getAll()) {
			for (long jenkinsServerId :
					_jenkinsCohortToJenkinsServersEntityRelationshipDALO.
						getChildEntityIds(jenkinsCohort)) {

				if (jenkinsServerId == 0) {
					continue;
				}

				jenkinsCohort.addJenkinsServer(
					_jenkinsServerEntityRepository.getById(jenkinsServerId));
			}
		}
	}

	public void setJenkinsServerRepository(
		JenkinsServerEntityRepository jenkinsServerEntityRepository) {

		_jenkinsServerEntityRepository = jenkinsServerEntityRepository;
	}

	@Autowired
	private JenkinsCohortEntityDALO _jenkinsCohortEntityDALO;

	@Autowired
	private JenkinsCohortToJenkinsServersEntityRelationshipDALO
		_jenkinsCohortToJenkinsServersEntityRelationshipDALO;

	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

}