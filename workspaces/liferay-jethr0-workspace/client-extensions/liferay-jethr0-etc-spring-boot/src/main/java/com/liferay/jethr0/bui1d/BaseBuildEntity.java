/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d;

import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.environment.EnvironmentEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildEntity
	extends BaseEntity implements BuildEntity {

	@Override
	public void addBuildRunEntities(Set<BuildRunEntity> buildRunEntities) {
		addRelatedEntities(buildRunEntities);
	}

	@Override
	public void addBuildRunEntity(BuildRunEntity buildRunEntity) {
		addRelatedEntity(buildRunEntity);
	}

	@Override
	public void addEnvironmentEntities(
		Set<EnvironmentEntity> environmentEntities) {

		addRelatedEntities(environmentEntities);
	}

	@Override
	public void addEnvironmentEntity(EnvironmentEntity environmentEntity) {
		addRelatedEntity(environmentEntity);
	}

	@Override
	public void addTaskEntities(Set<TaskEntity> taskEntities) {
		addRelatedEntities(taskEntities);
	}

	@Override
	public void addTaskEntity(TaskEntity taskEntity) {
		addRelatedEntity(taskEntity);
	}

	@Override
	public Map<String, String> getBuildParameters() {
		return _parameters;
	}

	@Override
	public String getBuildParameterValue(String name) {
		return _parameters.get(name);
	}

	@Override
	public Set<BuildRunEntity> getBuildRunEntities() {
		return getRelatedEntities(BuildRunEntity.class);
	}

	@Override
	public Set<BuildEntity> getChildBuildEntities() {
		return _childBuildEntities;
	}

	@Override
	public URL getEntityURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				Jethr0ContextUtil.getLiferayPortalURL(), "/#/builds/",
				getId()));
	}

	@Override
	public Set<EnvironmentEntity> getEnvironmentEntities() {
		return getRelatedEntities(EnvironmentEntity.class);
	}

	@Override
	public List<BuildRunEntity> getHistoryBuildRunEntities() {
		List<BuildRunEntity> historyBuildRunEntities = new ArrayList<>(
			getBuildRunEntities());

		Collections.sort(
			historyBuildRunEntities,
			Comparator.comparing(BuildRunEntity::getCreatedDate));

		return historyBuildRunEntities;
	}

	@Override
	public String getJenkinsJobName() {
		return _jenkinsJobName;
	}

	@Override
	public JenkinsNodeEntity.Type getJenkinsNodeType() {
		String nodeTypeValue = getBuildParameterValue("NODE_TYPE");

		if (StringUtil.isNullOrEmpty(nodeTypeValue)) {
			return JenkinsNodeEntity.Type.SLAVE;
		}

		JenkinsNodeEntity.Type type = JenkinsNodeEntity.Type.getByKey(
			nodeTypeValue);

		if (type == null) {
			return JenkinsNodeEntity.Type.SLAVE;
		}

		return type;
	}

	@Override
	public JobEntity getJobEntity() {
		return _jobEntity;
	}

	@Override
	public long getJobEntityId() {
		return _jobEntityId;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		State state = getState();

		jsonObject.put(
			"initialBuild", isInitialBuild()
		).put(
			"jenkinsJobName", getJenkinsJobName()
		).put(
			"name", getName()
		).put(
			"parameters", String.valueOf(_getBuildParametersJSONArray())
		).put(
			"r_jobToBuilds_c_jobId", getJobEntityId()
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public BuildRunEntity getLatestBuildRunEntity() {
		List<BuildRunEntity> historyBuildRunEntities =
			getHistoryBuildRunEntities();

		if (historyBuildRunEntities.isEmpty()) {
			return null;
		}

		return historyBuildRunEntities.get(historyBuildRunEntities.size() - 1);
	}

	@Override
	public int getMaxNodeCount() {
		String maxNodeCount = getBuildParameterValue("MAX_NODE_COUNT");

		if (StringUtil.isNullOrEmpty(maxNodeCount)) {
			maxNodeCount = getBuildParameterValue("MAXIMUM_SLAVES_PER_HOST");
		}

		if (StringUtil.isNullOrEmpty(maxNodeCount) ||
			!maxNodeCount.matches("\\d+")) {

			return _DEFAULT_MAX_NODE_COUNT;
		}

		return Integer.valueOf(maxNodeCount);
	}

	@Override
	public int getMinNodeRAM() {
		String minNodeRAM = getBuildParameterValue("MIN_NODE_RAM");

		if (StringUtil.isNullOrEmpty(minNodeRAM)) {
			minNodeRAM = getBuildParameterValue("MINIMUM_SLAVE_RAM");
		}

		if (StringUtil.isNullOrEmpty(minNodeRAM) ||
			!minNodeRAM.matches("\\d+")) {

			return _DEFAULT_MIN_NODE_RAM;
		}

		return Integer.valueOf(minNodeRAM);
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Set<BuildEntity> getParentBuildEntities() {
		return _parentBuildEntities;
	}

	@Override
	public State getState() {
		return _state;
	}

	@Override
	public Set<TaskEntity> getTaskEntities() {
		return getRelatedEntities(TaskEntity.class);
	}

	@Override
	public boolean isChildBuildEntity(BuildEntity parentBuildEntity) {
		Set<BuildEntity> parentBuildEntities = _getAllParentBuildEntities();

		return parentBuildEntities.contains(parentBuildEntity);
	}

	@Override
	public boolean isInitialBuild() {
		return _initialBuild;
	}

	@Override
	public boolean isParentBuildEntity(BuildEntity childBuildEntity) {
		Set<BuildEntity> childBuildEntities = _getAllChildBuildEntities();

		return childBuildEntities.contains(childBuildEntity);
	}

	@Override
	public void removeBuildRunEntities(Set<BuildRunEntity> buildRunEntities) {
		removeRelatedEntities(buildRunEntities);
	}

	@Override
	public void removeBuildRunEntity(BuildRunEntity buildRunEntity) {
		removeRelatedEntity(buildRunEntity);
	}

	@Override
	public void removeEnvironmentEntities(
		Set<EnvironmentEntity> environmentEntities) {

		removeRelatedEntities(environmentEntities);
	}

	@Override
	public void removeEnvironmentEntity(EnvironmentEntity environmentEntity) {
		removeRelatedEntity(environmentEntity);
	}

	@Override
	public void removeTaskEntities(Set<TaskEntity> taskEntities) {
		removeRelatedEntities(taskEntities);
	}

	@Override
	public void removeTaskEntity(TaskEntity taskEntity) {
		removeRelatedEntity(taskEntity);
	}

	@Override
	public boolean requiresGoodBattery() {
		String requiresGoodBattery = getBuildParameterValue(
			"REQUIRES_GOOD_BATTERY");

		if (StringUtil.isNullOrEmpty(requiresGoodBattery) ||
			!Objects.equals(
				StringUtil.toLowerCase(requiresGoodBattery), "true")) {

			return false;
		}

		return true;
	}

	@Override
	public void setJenkinsJobName(String jenkinsJobName) {
		_jenkinsJobName = jenkinsJobName;
	}

	@Override
	public void setJobEntity(JobEntity jobEntity) {
		_jobEntity = jobEntity;

		if (_jobEntity != null) {
			_jobEntityId = _jobEntity.getId();
		}
		else {
			_jobEntityId = 0;
		}
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_initialBuild = jsonObject.optBoolean("initialBuild");
		_jenkinsJobName = jsonObject.getString("jenkinsJobName");
		_jobEntityId = jsonObject.optLong("r_jobToBuilds_c_jobId");
		_name = jsonObject.getString("name");
		_state = State.get(jsonObject.get("state"));

		String parameters = jsonObject.getString("parameters");

		if (StringUtil.isNullOrEmpty(parameters)) {
			return;
		}

		_parameters = new HashMap<>();

		try {
			JSONArray parametersJSONArray = new JSONArray(parameters);

			for (int i = 0; i < parametersJSONArray.length(); i++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(i);

				_parameters.put(
					parameterJSONObject.getString("name"),
					parameterJSONObject.getString("value"));
			}
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsonException);
			}
		}
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseBuildEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	private Set<BuildEntity> _getAllChildBuildEntities() {
		Set<BuildEntity> childBuildEntities = new HashSet<>(
			_childBuildEntities);

		for (BuildEntity childBuildEntity : _childBuildEntities) {
			childBuildEntities.addAll(childBuildEntity.getChildBuildEntities());
		}

		return childBuildEntities;
	}

	private Set<BuildEntity> _getAllParentBuildEntities() {
		Set<BuildEntity> parentBuildEntities = new HashSet<>(
			_parentBuildEntities);

		for (BuildEntity parentBuildEntity : _parentBuildEntities) {
			parentBuildEntities.addAll(
				parentBuildEntity.getParentBuildEntities());
		}

		return parentBuildEntities;
	}

	private JSONArray _getBuildParametersJSONArray() {
		JSONArray buildParametersJSONArray = new JSONArray();

		if (_parameters.isEmpty()) {
			return buildParametersJSONArray;
		}

		Set<String> parameterNames = new TreeSet<>(_parameters.keySet());

		for (String parameterName : parameterNames) {
			if (!parameterName.matches("[A-Z0-9_]+")) {
				continue;
			}

			String parameterValue = _parameters.get(parameterName);

			if (StringUtil.isNullOrEmpty(parameterValue)) {
				continue;
			}

			JSONObject buildParameterJSONObject = new JSONObject();

			buildParameterJSONObject.put(
				"name", parameterName
			).put(
				"value", parameterValue
			);

			buildParametersJSONArray.put(buildParameterJSONObject);
		}

		return buildParametersJSONArray;
	}

	private static final int _DEFAULT_MAX_NODE_COUNT = 2;

	private static final int _DEFAULT_MIN_NODE_RAM = 12;

	private static final Log _log = LogFactory.getLog(BaseBuildEntity.class);

	private final Set<BuildEntity> _childBuildEntities = new HashSet<>();
	private boolean _initialBuild;
	private String _jenkinsJobName;
	private JobEntity _jobEntity;
	private long _jobEntityId;
	private String _name;
	private Map<String, String> _parameters;
	private final Set<BuildEntity> _parentBuildEntities = new HashSet<>();
	private State _state;

}