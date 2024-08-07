/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.dalo.JenkinsNodeEntityDALO;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.jenkins.server.JenkinsServerEntity;
import com.liferay.jethr0.util.StringUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsNodeEntityRepository
	extends BaseEntityRepository<JenkinsNodeEntity> {

	public JenkinsNodeEntity create(
		JenkinsServerEntity jenkinsServerEntity, JSONObject nodeJSONObject) {

		nodeJSONObject.put(
			"r_jenkinsServerToJenkinsNodes_c_jenkinsServerId",
			jenkinsServerEntity.getId());

		JenkinsNodeEntity jenkinsNodeEntity = getByName(
			jenkinsServerEntity, nodeJSONObject.getString("name"));

		if (jenkinsNodeEntity != null) {
			update(jenkinsNodeEntity);

			return jenkinsNodeEntity;
		}

		return create(nodeJSONObject);
	}

	public void createAll(JenkinsServerEntity jenkinsServerEntity) {
		JSONObject jsonObject = jenkinsServerEntity.getComputerJSONObject();

		if (jsonObject == null) {
			return;
		}

		JSONArray computerJSONArray = jsonObject.optJSONArray("computer");

		if (computerJSONArray == null) {
			return;
		}

		for (int i = 0; i < computerJSONArray.length(); i++) {
			JSONObject computerJSONObject = computerJSONArray.getJSONObject(i);

			JSONObject nodeJSONObject = new JSONObject();

			String name = computerJSONObject.getString("displayName");

			String primaryLabel = name;

			String url = StringUtil.combine(
				jenkinsServerEntity.getURL(), "/computer/", name);

			JenkinsNodeEntity.Type type = JenkinsNodeEntity.Type.SLAVE;

			int nodeCount = 2;
			int nodeRAM = 12;

			if (Objects.equals(
					computerJSONObject.getString("_class"),
					"hudson.model.Hudson$MasterComputer")) {

				name = "master";
				nodeCount = 1;
				primaryLabel = "master";
				type = JenkinsNodeEntity.Type.MASTER;
				url = StringUtil.combine(
					jenkinsServerEntity.getURL(), "/computer/(master)");
			}

			Matcher nodeNameMatcher = _nodeNamePattern.matcher(name);

			if (nodeNameMatcher.find()) {
				nodeRAM = 24;
			}

			nodeJSONObject.put(
				"goodBattery", true
			).put(
				"name", name
			).put(
				"nodeCount", nodeCount
			).put(
				"nodeRAM", nodeRAM
			).put(
				"r_jenkinsServerToJenkinsNodes_c_jenkinsServerId",
				jenkinsServerEntity.getId()
			).put(
				"type", type.getJSONObject()
			).put(
				"url", url
			);

			JSONArray assignedLabelsJSONArray = computerJSONObject.getJSONArray(
				"assignedLabels");

			boolean primaryLabelFound = false;

			for (int j = 0; j < assignedLabelsJSONArray.length(); j++) {
				JSONObject assignedLabelJSONObject =
					assignedLabelsJSONArray.getJSONObject(j);

				String assignedLabel = assignedLabelJSONObject.getString(
					"name");

				Matcher goodBatteryMatcher = _goodBatteryPattern.matcher(
					assignedLabel);

				if (goodBatteryMatcher.find()) {
					nodeJSONObject.put(
						"goodBattery",
						Boolean.valueOf(goodBatteryMatcher.group(1)));
				}

				Matcher nodeCountMatcher = _nodeCountPattern.matcher(
					assignedLabel);

				if (nodeCountMatcher.find()) {
					nodeJSONObject.put(
						"nodeCount",
						Integer.valueOf(nodeCountMatcher.group(1)));
				}

				Matcher nodeRAMMatcher = _nodeRAMPattern.matcher(assignedLabel);

				if (nodeRAMMatcher.find()) {
					nodeJSONObject.put(
						"nodeRAM", Integer.valueOf(nodeRAMMatcher.group(1)));
				}

				if (name.equals(assignedLabel)) {
					primaryLabelFound = true;
				}
			}

			if ((type == JenkinsNodeEntity.Type.MASTER) && !primaryLabelFound) {
				primaryLabel = "built-in";
			}

			nodeJSONObject.put("primaryLabel", primaryLabel);

			create(jenkinsServerEntity, nodeJSONObject);
		}
	}

	public Set<JenkinsNodeEntity> getAll(
		JenkinsServerEntity jenkinsServerEntity) {

		Set<JenkinsNodeEntity> jenkinsNodeEntities = new HashSet<>();

		for (JenkinsNodeEntity jenkinsNodeEntity : getAll()) {
			if (Objects.equals(
					jenkinsServerEntity.getId(),
					jenkinsNodeEntity.getJenkinsServerEntityId())) {

				jenkinsNodeEntities.add(jenkinsNodeEntity);
			}
		}

		return jenkinsNodeEntities;
	}

	public JenkinsNodeEntity getByName(
		JenkinsServerEntity jenkinsServerEntity, String name) {

		for (JenkinsNodeEntity jenkinsNodeEntity :
				getAll(jenkinsServerEntity)) {

			if (Objects.equals(name, jenkinsNodeEntity.getName())) {
				return jenkinsNodeEntity;
			}
		}

		JenkinsNodeEntity jenkinsNodeEntity = _jenkinsNodeEntityDALO.getByName(
			jenkinsServerEntity, name);

		if (jenkinsNodeEntity != null) {
			add(jenkinsNodeEntity);
		}

		return jenkinsNodeEntity;
	}

	@Override
	public JenkinsNodeEntityDALO getEntityDALO() {
		return _jenkinsNodeEntityDALO;
	}

	@Override
	public void initialize() {
		for (JenkinsServerEntity jenkinsServerEntity :
				_jenkinsServerEntityRepository.getAll()) {

			createAll(jenkinsServerEntity);
		}
	}

	@Override
	public void initializeRelationships() {
	}

	public void setJenkinsServerEntityRepository(
		JenkinsServerEntityRepository jenkinsServerEntityRepository) {

		_jenkinsServerEntityRepository = jenkinsServerEntityRepository;
	}

	@Override
	protected JenkinsNodeEntity updateRelationshipsFromDALO(
		JenkinsNodeEntity jenkinsNodeEntity) {

		_jenkinsServerEntityRepository.relateJenkinsServerToJenkinsNode(
			_jenkinsServerEntityRepository.getById(
				jenkinsNodeEntity.getJenkinsServerEntityId()),
			jenkinsNodeEntity);

		return jenkinsNodeEntity;
	}

	private static final Pattern _goodBatteryPattern = Pattern.compile(
		"goodBattery=(true|false)");
	private static final Pattern _nodeCountPattern = Pattern.compile(
		"nodeCount=(\\d+)");
	private static final Pattern _nodeNamePattern = Pattern.compile(
		"test-\\d+-\\d+");
	private static final Pattern _nodeRAMPattern = Pattern.compile(
		"nodeRAM=(\\d+)");

	@Autowired
	private JenkinsNodeEntityDALO _jenkinsNodeEntityDALO;

	private JenkinsServerEntityRepository _jenkinsServerEntityRepository;

}