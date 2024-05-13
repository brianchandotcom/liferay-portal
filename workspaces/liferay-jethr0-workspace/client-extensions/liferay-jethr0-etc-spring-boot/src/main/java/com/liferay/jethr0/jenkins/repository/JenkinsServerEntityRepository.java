/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.dalo.JenkinsServerEntityDALO;
import com.liferay.jethr0.jenkins.dalo.JenkinsServerToJenkinsNodesEntityRelationshipDALO;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsServerEntityRepository
	extends BaseEntityRepository<JenkinsServerEntity> {

	public JenkinsServerEntity create(
		JenkinsCohortEntity jenkinsCohortEntity, JSONObject jsonObject) {

		jsonObject.put(
			"r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId",
			jenkinsCohortEntity.getId());

		return create(jsonObject);
	}

	public JenkinsServerEntity create(
		JenkinsCohortEntity jenkinsCohortEntity, String jenkinsUserName,
		String jenkinsUserPassword, String name, URL url) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsUserName", jenkinsUserName
		).put(
			"jenkinsUserPassword", jenkinsUserPassword
		).put(
			"name", name
		).put(
			"r_jenkinsCohortToJenkinsServers_c_jenkinsCohortId",
			jenkinsCohortEntity.getId()
		).put(
			"url", String.valueOf(url)
		);

		return create(jsonObject);
	}

	@Override
	public JenkinsServerEntity create(JSONObject jsonObject) {
		URL url = StringUtil.toURL(jsonObject.getString("url"));

		Matcher jenkinsURLMatcher = _jenkinsURLPattern.matcher(
			String.valueOf(url));

		if (!jenkinsURLMatcher.find()) {
			throw new RuntimeException("Invalid Jenkins URL: " + url);
		}

		String name = jsonObject.optString("name");

		if (StringUtil.isNullOrEmpty(name)) {
			jsonObject.put("name", jenkinsURLMatcher.group("serverName"));
		}

		return super.create(jsonObject);
	}

	public JenkinsServerEntity createByURL(URL url) {
		JenkinsServerEntity jenkinsServerEntity = getByURL(url);

		if (jenkinsServerEntity != null) {
			return jenkinsServerEntity;
		}

		Matcher jenkinsURLMatcher = _jenkinsURLPattern.matcher(
			String.valueOf(url));

		if (!jenkinsURLMatcher.find()) {
			throw new RuntimeException("Invalid Jenkins URL: " + url);
		}

		JenkinsCohortEntity jenkinsCohortEntity =
			_jenkinsCohortEntityRepository.create(
				jenkinsURLMatcher.group("cohortName"));

		return create(
			jenkinsCohortEntity, _jenkinsUserName, _jenkinsUserPassword,
			jenkinsURLMatcher.group("serverName"), url);
	}

	public JenkinsServerEntity getByURL(URL url) {
		for (JenkinsServerEntity jenkinsServerEntity : getAll()) {
			if (!StringUtil.equals(jenkinsServerEntity.getURL(), url)) {
				continue;
			}

			return jenkinsServerEntity;
		}

		return null;
	}

	@Override
	public JenkinsServerEntityDALO getEntityDALO() {
		return _jenkinsServerEntityDALO;
	}

	@Override
	public void initialize() {
		addAll(_jenkinsServerEntityDALO.getAll());

		for (JenkinsServerEntity jenkinsServerEntity : getAll()) {
			jenkinsServerEntity.update();

			update(jenkinsServerEntity);
		}
	}

	@Override
	public void initializeRelationships() {
	}

	public void relateJenkinsServerToJenkinsNode(
		JenkinsServerEntity jenkinsServerEntity,
		JenkinsNodeEntity jenkinsNodeEntity) {

		jenkinsServerEntity.addJenkinsNodeEntity(jenkinsNodeEntity);

		jenkinsNodeEntity.setJenkinsServerEntity(jenkinsServerEntity);
	}

	public void setJenkinsCohortEntityRepository(
		JenkinsCohortEntityRepository jenkinsCohortEntityRepository) {

		_jenkinsCohortEntityRepository = jenkinsCohortEntityRepository;
	}

	public void setJenkinsNodeEntityRepository(
		JenkinsNodeEntityRepository jenkinsNodeEntityRepository) {

		_jenkinsNodeEntityRepository = jenkinsNodeEntityRepository;
	}

	@Override
	protected JenkinsServerEntity updateRelationshipsFromDALO(
		JenkinsServerEntity jenkinsServerEntity) {

		_jenkinsCohortEntityRepository.relateJenkinsCohortToJenkinsServer(
			_jenkinsCohortEntityRepository.getById(
				jenkinsServerEntity.getJenkinsCohortEntityId()),
			jenkinsServerEntity);

		return _updateJenkinsCohortToJenkinsServersRelationshipsFromDALO(
			jenkinsServerEntity);
	}

	@Override
	protected JenkinsServerEntity updateRelationshipsToDALO(
		JenkinsServerEntity jenkinsServerEntity) {

		_jenkinsServerToJenkinsNodesEntityRelationshipDALO.updateChildEntities(
			jenkinsServerEntity);

		return jenkinsServerEntity;
	}

	private JenkinsServerEntity
		_updateJenkinsCohortToJenkinsServersRelationshipsFromDALO(
			JenkinsServerEntity parentJenkinsServerEntity) {

		return updateParentToChildRelationshipsFromDALO(
			parentJenkinsServerEntity,
			_jenkinsServerToJenkinsNodesEntityRelationshipDALO,
			_jenkinsNodeEntityRepository,
			(jenkinsServerEntity, jenkinsNodeEntity) ->
				relateJenkinsServerToJenkinsNode(
					jenkinsServerEntity, jenkinsNodeEntity),
			jenkinsServerEntity -> jenkinsServerEntity.getJenkinsNodeEntities(),
			(jenkinsServerEntity, jenkinsNodeEntity) ->
				jenkinsServerEntity.removeJenkinsNode(jenkinsNodeEntity));
	}

	private static final Pattern _jenkinsURLPattern = Pattern.compile(
		"https?://(?<serverName>(?<cohortName>test-\\d+)-\\d+)" +
			"(\\.liferay\\.com)?(/.*)?");

	@Autowired
	private JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;

	@Autowired
	private JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;

	@Autowired
	private JenkinsServerEntityDALO _jenkinsServerEntityDALO;

	@Autowired
	private JenkinsServerToJenkinsNodesEntityRelationshipDALO
		_jenkinsServerToJenkinsNodesEntityRelationshipDALO;

	@Value("${JETHR0_JENKINS_USER_NAME:test@liferay.com}")
	private String _jenkinsUserName;

	@Value("${JETHR0_JENKINS_USER_PASSWORD:password}")
	private String _jenkinsUserPassword;

}