/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d;

import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.project.ProjectEntity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.util.StringUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuildEntity
	extends BaseEntity implements BuildEntity {

	@Override
	public void addBuildParameterEntities(
		Set<BuildParameterEntity> buildParameterEntities) {

		addRelatedEntities(buildParameterEntities);
	}

	@Override
	public void addBuildParameterEntity(
		BuildParameterEntity buildParameterEntity) {

		addRelatedEntity(buildParameterEntity);
	}

	@Override
	public void addBuildRunEntities(Set<BuildRunEntity> buildRunEntities) {
		addRelatedEntities(buildRunEntities);
	}

	@Override
	public void addBuildRunEntity(BuildRunEntity buildRunEntity) {
		addRelatedEntity(buildRunEntity);
	}

	@Override
	public void addEnvironment(Environment environment) {
		addRelatedEntity(environment);
	}

	@Override
	public void addEnvironments(Set<Environment> environments) {
		addRelatedEntities(environments);
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
	public String getBuildName() {
		return _buildName;
	}

	@Override
	public Set<BuildParameterEntity> getBuildParameterEntities() {
		return getRelatedEntities(BuildParameterEntity.class);
	}

	@Override
	public BuildParameterEntity getBuildParameterEntity(String name) {
		for (BuildParameterEntity buildParameterEntity :
				getBuildParameterEntities()) {

			if (Objects.equals(name, buildParameterEntity.getName())) {
				return buildParameterEntity;
			}
		}

		return null;
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
	public Set<Environment> getEnvironments() {
		return getRelatedEntities(Environment.class);
	}

	@Override
	public JenkinsNodeEntity.Type getJenkinsNodeType() {
		BuildParameterEntity buildParameterEntity = getBuildParameterEntity(
			"NODE_TYPE");

		if (buildParameterEntity == null) {
			return null;
		}

		JenkinsNodeEntity.Type type = JenkinsNodeEntity.Type.getByKey(
			buildParameterEntity.getValue());

		if (type == null) {
			return null;
		}

		return type;
	}

	@Override
	public String getJobName() {
		return _jobName;
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		State state = getState();

		jsonObject.put(
			"buildName", getBuildName()
		).put(
			"jobName", getJobName()
		).put(
			"r_projectToBuilds_c_projectId", getProjectEntityId()
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public int getMaxNodeCount() {
		BuildParameterEntity buildParameterEntity = getBuildParameterEntity(
			"MAX_NODE_COUNT");

		if (buildParameterEntity == null) {
			return _DEFAULT_MAX_NODE_COUNT;
		}

		String value = buildParameterEntity.getValue();

		if ((value == null) || !value.matches("\\d+")) {
			return _DEFAULT_MAX_NODE_COUNT;
		}

		return Integer.valueOf(value);
	}

	@Override
	public int getMinNodeRAM() {
		BuildParameterEntity buildParameterEntity = getBuildParameterEntity(
			"MIN_NODE_RAM");

		if (buildParameterEntity == null) {
			return _DEFAULT_MIN_NODE_RAM;
		}

		String value = buildParameterEntity.getValue();

		if ((value == null) || !value.matches("\\d+")) {
			return _DEFAULT_MIN_NODE_RAM;
		}

		return Integer.valueOf(value);
	}

	public Set<BuildEntity> getParentBuildEntities() {
		return _parentBuildEntities;
	}

	@Override
	public ProjectEntity getProjectEntity() {
		return _projectEntity;
	}

	@Override
	public long getProjectEntityId() {
		return _projectEntityId;
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
	public boolean isParentBuildEntity(BuildEntity childBuildEntity) {
		Set<BuildEntity> childBuildEntities = _getAllChildBuildEntities();

		return childBuildEntities.contains(childBuildEntity);
	}

	@Override
	public void removeBuildParameterEntities(
		Set<BuildParameterEntity> buildParameterEntities) {

		removeRelatedEntities(buildParameterEntities);
	}

	@Override
	public void removeBuildParameterEntity(
		BuildParameterEntity buildParameterEntity) {

		removeRelatedEntity(buildParameterEntity);
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
	public void removeEnvironment(Environment environment) {
		removeRelatedEntity(environment);
	}

	@Override
	public void removeEnvironments(Set<Environment> environments) {
		removeRelatedEntities(environments);
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
		BuildParameterEntity buildParameterEntity = getBuildParameterEntity(
			"REQUIRES_GOOD_BATTERY");

		if (buildParameterEntity == null) {
			return false;
		}

		String requiresGoodBattery = buildParameterEntity.getValue();

		if ((requiresGoodBattery == null) ||
			!Objects.equals(
				StringUtil.toLowerCase(requiresGoodBattery), "true")) {

			return false;
		}

		return true;
	}

	@Override
	public void setJobName(String jobName) {
		_jobName = jobName;
	}

	@Override
	public void setProjectEntity(ProjectEntity projectEntity) {
		_projectEntity = projectEntity;

		if (_projectEntity != null) {
			_projectEntityId = _projectEntity.getId();
		}
		else {
			_projectEntityId = 0;
		}
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseBuildEntity(JSONObject jsonObject) {
		super(jsonObject);

		_buildName = jsonObject.getString("buildName");
		_jobName = jsonObject.getString("jobName");
		_projectEntityId = jsonObject.optLong("r_projectToBuilds_c_projectId");
		_state = State.get(jsonObject.getJSONObject("state"));
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

	private static final int _DEFAULT_MAX_NODE_COUNT = 2;

	private static final int _DEFAULT_MIN_NODE_RAM = 12;

	private final String _buildName;
	private final Set<BuildEntity> _childBuildEntities = new HashSet<>();
	private String _jobName;
	private final Set<BuildEntity> _parentBuildEntities = new HashSet<>();
	private ProjectEntity _projectEntity;
	private long _projectEntityId;
	private State _state;

}