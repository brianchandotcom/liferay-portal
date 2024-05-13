/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.dalo.JenkinsCohortEntityDALO;
import com.liferay.jethr0.jenkins.dalo.JenkinsCohortToJenkinsServersEntityRelationshipDALO;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
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
	extends BaseEntityRepository<JenkinsCohortEntity> {

	public JenkinsCohortEntity create(String name) {
		if (StringUtil.isNullOrEmpty(name)) {
			throw new RuntimeException("Invalid Jenkins cohort name: " + name);
		}

		JenkinsCohortEntity jenkinsCohortEntity = getByName(name);

		if (jenkinsCohortEntity != null) {
			return jenkinsCohortEntity;
		}

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("name", name);

		return create(jsonObject);
	}

	public JenkinsCohortEntity getByName(String name) {
		for (JenkinsCohortEntity jenkinsCohortEntity : getAll()) {
			if (!Objects.equals(name, jenkinsCohortEntity.getName())) {
				continue;
			}

			return jenkinsCohortEntity;
		}

		return null;
	}

	@Override
	public JenkinsCohortEntityDALO getEntityDALO() {
		return _jenkinsCohortEntityDALO;
	}

	@Override
	public void initialize() {
		addAll(_jenkinsCohortEntityDALO.getAll());

		for (JenkinsCohortEntity jenkinsCohortEntity : getAll()) {
			jenkinsCohortEntity.update();

			update(jenkinsCohortEntity);
		}
	}

	@Override
	public void initializeRelationships() {
	}

	public void relateJenkinsCohortToJenkinsServer(
		JenkinsCohortEntity jenkinsCohortEntity,
		JenkinsServerEntity jenkinsServerEntity) {

		jenkinsCohortEntity.addJenkinsServerEntity(jenkinsServerEntity);

		jenkinsServerEntity.setJenkinsCohortEntity(jenkinsCohortEntity);
	}

	public void setJenkinsServerEntityRepository(
		JenkinsServerEntityRepository jenkinsServerEntityRepository) {

		_jenkinsServerEntityRepository = jenkinsServerEntityRepository;
	}

	@Override
	protected JenkinsCohortEntity updateRelationshipsFromDALO(
		JenkinsCohortEntity jenkinsCohortEntity) {

		return _updateJenkinsCohortToJenkinsServerRelationshipsFromDALO(
			jenkinsCohortEntity);
	}

	@Override
	protected JenkinsCohortEntity updateRelationshipsToDALO(
		JenkinsCohortEntity jenkinsCohortEntity) {

		_jenkinsCohortToJenkinsServersEntityRelationshipDALO.
			updateChildEntities(jenkinsCohortEntity);

		return jenkinsCohortEntity;
	}

	private JenkinsCohortEntity
		_updateJenkinsCohortToJenkinsServerRelationshipsFromDALO(
			JenkinsCohortEntity parentJenkinsCohortEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentJenkinsCohortEntity,
			_jenkinsCohortToJenkinsServersEntityRelationshipDALO,
			_jenkinsServerEntityRepository,
			(jenkinsCohortEntity, jenkinsServerEntity) ->
				relateJenkinsCohortToJenkinsServer(
					jenkinsCohortEntity, jenkinsServerEntity),
			jenkinsCohortEntity ->
				jenkinsCohortEntity.getJenkinsServerEntities(),
			(jenkinsCohortEntity, jenkinsServerEntity) ->
				jenkinsCohortEntity.removeJenkinsServerEntity(
					jenkinsServerEntity));
	}

	@Autowired
	private JenkinsCohortEntityDALO _jenkinsCohortEntityDALO;

	@Autowired
	private JenkinsCohortToJenkinsServersEntityRelationshipDALO
		_jenkinsCohortToJenkinsServersEntityRelationshipDALO;

	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

}