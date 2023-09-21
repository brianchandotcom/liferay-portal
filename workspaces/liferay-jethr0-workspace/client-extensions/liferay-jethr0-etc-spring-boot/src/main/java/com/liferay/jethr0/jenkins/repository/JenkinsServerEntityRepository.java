/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.jenkins.dalo.JenkinsServerEntityDALO;
import com.liferay.jethr0.jenkins.dalo.JenkinsServerToJenkinsNodesEntityRelationshipDALO;
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

		JenkinsServerEntity jenkinsServerEntity = create(jsonObject);

		jenkinsServerEntity.setJenkinsCohortEntity(jenkinsCohortEntity);

		jenkinsCohortEntity.addJenkinsServerEntity(jenkinsServerEntity);

		return jenkinsServerEntity;
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
			jsonObject.put("name", jenkinsURLMatcher.group("name"));
		}

		return super.create(jsonObject);
	}

	public JenkinsServerEntity create(
		String jenkinsUserName, String jenkinsUserPassword, String name,
		URL url) {

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsUserName", jenkinsUserName
		).put(
			"jenkinsUserPassword", jenkinsUserPassword
		).put(
			"name", name
		).put(
			"url", String.valueOf(url)
		);

		return create(jsonObject);
	}

	public JenkinsServerEntity create(URL url) {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"jenkinsUserName", _jenkinsUserName
		).put(
			"jenkinsUserPassword", _jenkinsUserPassword
		).put(
			"url", String.valueOf(url)
		);

		return create(jsonObject);
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
	public void initializeRelationships() {
		for (JenkinsServerEntity jenkinsServerEntity : getAll()) {
			JenkinsCohortEntity jenkinsCohortEntity = null;

			long jenkinsCohortId =
				jenkinsServerEntity.getJenkinsCohortEntityId();

			if (jenkinsCohortId != 0) {
				jenkinsCohortEntity = _jenkinsCohortEntityRepository.getById(
					jenkinsCohortId);
			}

			jenkinsServerEntity.setJenkinsCohortEntity(jenkinsCohortEntity);

			for (long jenkinsNodeId :
					_jenkinsServerToJenkinsNodesEntityRelationshipDALO.
						getChildEntityIds(jenkinsServerEntity)) {

				if (jenkinsNodeId == 0) {
					continue;
				}

				jenkinsServerEntity.addJenkinsNodeEntity(
					_jenkinsNodeEntityRepository.getById(jenkinsNodeId));
			}
		}
	}

	public void setJenkinsCohortEntityRepository(
		JenkinsCohortEntityRepository jenkinsCohortEntityRepository) {

		_jenkinsCohortEntityRepository = jenkinsCohortEntityRepository;
	}

	public void setJenkinsNodeEntityRepository(
		JenkinsNodeEntityRepository jenkinsNodeEntityRepository) {

		_jenkinsNodeEntityRepository = jenkinsNodeEntityRepository;
	}

	private static final Pattern _jenkinsURLPattern = Pattern.compile(
		"https?://(?<name>[^/]+)(\\.liferay\\.com)?(/.*)?");

	@Autowired
	private JenkinsCohortEntityRepository _jenkinsCohortEntityRepository;

	@Autowired
	private JenkinsNodeEntityRepository _jenkinsNodeEntityRepository;

	@Autowired
	private JenkinsServerEntityDALO _jenkinsServerEntityDALO;

	@Autowired
	private JenkinsServerToJenkinsNodesEntityRelationshipDALO
		_jenkinsServerToJenkinsNodesEntityRelationshipDALO;

	@Value("${jenkins.user.name}")
	private String _jenkinsUserName;

	@Value("${jenkins.user.password}")
	private String _jenkinsUserPassword;

}