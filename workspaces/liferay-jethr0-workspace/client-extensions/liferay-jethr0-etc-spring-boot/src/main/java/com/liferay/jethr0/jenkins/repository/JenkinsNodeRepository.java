/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.jenkins.repository;

import com.liferay.jethr0.entity.repository.BaseEntityRepository;
import com.liferay.jethr0.jenkins.dalo.JenkinsNodeDALO;
import com.liferay.jethr0.jenkins.dalo.JenkinsServerDALO;
import com.liferay.jethr0.jenkins.dalo.JenkinsServerToJenkinsNodesDALO;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.jenkins.server.JenkinsServer;
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
public class JenkinsNodeRepository extends BaseEntityRepository<JenkinsNode> {

	public void addAll(JenkinsServer jenkinsServer) {
		JSONObject jsonObject = jenkinsServer.getComputerJSONObject();

		JSONArray computerJSONArray = jsonObject.getJSONArray("computer");

		for (int i = 0; i < computerJSONArray.length(); i++) {
			JSONObject computerJSONObject = computerJSONArray.getJSONObject(i);

			JSONObject nodeJSONObject = new JSONObject();

			String name = computerJSONObject.getString("displayName");

			String url = StringUtil.combine(
				jenkinsServer.getURL(), "/computer/", name);

			JenkinsNode.Type type = JenkinsNode.Type.SLAVE;

			int nodeCount = 2;

			if (Objects.equals(
					computerJSONObject.getString("_class"),
					"hudson.model.Hudson$MasterComputer")) {

				nodeCount = 1;
				type = JenkinsNode.Type.MASTER;
				url = StringUtil.combine(
					jenkinsServer.getURL(), "/computer/(master)");
			}

			nodeJSONObject.put(
				"goodBattery", true
			).put(
				"name", name
			).put(
				"nodeCount", nodeCount
			).put(
				"nodeRAM", 12
			).put(
				"r_jenkinsServerToJenkinsNodes_c_jenkinsServerId",
				jenkinsServer.getId()
			).put(
				"type", type.getJSONObject()
			).put(
				"url", url
			);

			JSONArray assignedLabelsJSONArray = computerJSONObject.getJSONArray(
				"assignedLabels");

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
			}

			JenkinsNode jenkinsNode = _jenkinsNodeDALO.create(nodeJSONObject);

			jenkinsNode.setJenkinsServer(jenkinsServer);

			jenkinsServer.addJenkinsNode(jenkinsNode);

			add(jenkinsNode);
		}
	}

	public JenkinsNode get(String jenkinsNodeName) {
		for (JenkinsNode jenkinsNode : getAll()) {
			if (Objects.equals(jenkinsNodeName, jenkinsNode.getName())) {
				return jenkinsNode;
			}
		}

		return null;
	}

	public Set<JenkinsNode> getAll(JenkinsServer jenkinsServer) {
		Set<JenkinsNode> jenkinsNodes = new HashSet<>();

		Set<Long> jenkinsNodeIds =
			_jenkinsServerToJenkinsNodesDALO.getChildEntityIds(jenkinsServer);

		for (JenkinsNode jenkinsNode : getAll()) {
			if (!jenkinsNodeIds.contains(jenkinsNode.getId())) {
				continue;
			}

			jenkinsNode.setJenkinsServer(jenkinsServer);

			jenkinsServer.addJenkinsNode(jenkinsNode);

			jenkinsNodes.add(jenkinsNode);
		}

		return jenkinsNodes;
	}

	@Override
	public JenkinsNodeDALO getEntityDALO() {
		return _jenkinsNodeDALO;
	}

	@Override
	public void initializeRelationships() {
		for (JenkinsNode jenkinsNode : getAll()) {
			JenkinsServer jenkinsServer = null;

			long jenkinsServerId = jenkinsNode.getJenkinsServerId();

			if (jenkinsServerId != 0) {
				jenkinsServer = _jenkinsServerRepository.getById(
					jenkinsServerId);
			}

			jenkinsNode.setJenkinsServer(jenkinsServer);
		}
	}

	public void setJenkinsServerRepository(
		JenkinsServerRepository jenkinsServerRepository) {

		_jenkinsServerRepository = jenkinsServerRepository;
	}

	private static final Pattern _goodBatteryPattern = Pattern.compile(
		"goodBattery=(true|false)");
	private static final Pattern _nodeCountPattern = Pattern.compile(
		"nodeCount=(\\d+)");
	private static final Pattern _nodeRAMPattern = Pattern.compile(
		"nodeRAM=(\\d+)");

	@Autowired
	private JenkinsNodeDALO _jenkinsNodeDALO;

	@Autowired
	private JenkinsServerDALO _jenkinsServerDALO;

	private JenkinsServerRepository _jenkinsServerRepository;

	@Autowired
	private JenkinsServerToJenkinsNodesDALO _jenkinsServerToJenkinsNodesDALO;

}