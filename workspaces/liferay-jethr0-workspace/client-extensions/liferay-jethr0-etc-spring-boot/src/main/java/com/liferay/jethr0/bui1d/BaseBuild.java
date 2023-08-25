/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d;

import com.liferay.jethr0.bui1d.parameter.BuildParameter;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.util.StringUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuild extends BaseEntity implements Build {

	@Override
	public void addBuildParameter(BuildParameter buildParameter) {
		addRelatedEntity(buildParameter);
	}

	@Override
	public void addBuildParameters(Set<BuildParameter> buildParameters) {
		addRelatedEntities(buildParameters);
	}

	@Override
	public void addBuildRun(BuildRun buildRun) {
		addRelatedEntity(buildRun);
	}

	@Override
	public void addBuildRuns(Set<BuildRun> buildRuns) {
		addRelatedEntities(buildRuns);
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
	public void addTask(Task task) {
		addRelatedEntity(task);
	}

	@Override
	public void addTasks(Set<Task> tasks) {
		addRelatedEntities(tasks);
	}

	@Override
	public String getBuildName() {
		return _buildName;
	}

	@Override
	public BuildParameter getBuildParameter(String name) {
		for (BuildParameter buildParameter : getBuildParameters()) {
			if (Objects.equals(name, buildParameter.getName())) {
				return buildParameter;
			}
		}

		return null;
	}

	@Override
	public Set<BuildParameter> getBuildParameters() {
		return getRelatedEntities(BuildParameter.class);
	}

	@Override
	public Set<BuildRun> getBuildRuns() {
		return getRelatedEntities(BuildRun.class);
	}

	@Override
	public Set<Build> getChildBuilds() {
		return _childBuilds;
	}

	@Override
	public Set<Environment> getEnvironments() {
		return getRelatedEntities(Environment.class);
	}

	@Override
	public JenkinsNodeEntity.Type getJenkinsNodeType() {
		BuildParameter buildParameter = getBuildParameter("NODE_TYPE");

		if (buildParameter == null) {
			return null;
		}

		JenkinsNodeEntity.Type type = JenkinsNodeEntity.Type.getByKey(
			buildParameter.getValue());

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
			"r_projectToBuilds_c_projectId", getProjectId()
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public int getMaxNodeCount() {
		BuildParameter buildParameter = getBuildParameter("MAX_NODE_COUNT");

		if (buildParameter == null) {
			return _DEFAULT_MAX_NODE_COUNT;
		}

		String value = buildParameter.getValue();

		if ((value == null) || !value.matches("\\d+")) {
			return _DEFAULT_MAX_NODE_COUNT;
		}

		return Integer.valueOf(value);
	}

	@Override
	public int getMinNodeRAM() {
		BuildParameter buildParameter = getBuildParameter("MIN_NODE_RAM");

		if (buildParameter == null) {
			return _DEFAULT_MIN_NODE_RAM;
		}

		String value = buildParameter.getValue();

		if ((value == null) || !value.matches("\\d+")) {
			return _DEFAULT_MIN_NODE_RAM;
		}

		return Integer.valueOf(value);
	}

	public Set<Build> getParentBuilds() {
		return _parentBuilds;
	}

	@Override
	public Project getProject() {
		return _project;
	}

	@Override
	public long getProjectId() {
		return _projectId;
	}

	@Override
	public State getState() {
		return _state;
	}

	@Override
	public Set<Task> getTasks() {
		return getRelatedEntities(Task.class);
	}

	@Override
	public boolean isChildBuild(Build parentBuild) {
		Set<Build> parentBuilds = _getAllParentBuilds();

		return parentBuilds.contains(parentBuild);
	}

	@Override
	public boolean isParentBuild(Build childBuild) {
		Set<Build> childBuilds = _getAllChildBuilds();

		return childBuilds.contains(childBuild);
	}

	@Override
	public void removeBuildParameter(BuildParameter buildParameter) {
		removeRelatedEntity(buildParameter);
	}

	@Override
	public void removeBuildParameters(Set<BuildParameter> buildParameters) {
		removeRelatedEntities(buildParameters);
	}

	@Override
	public void removeBuildRun(BuildRun buildRun) {
		removeRelatedEntity(buildRun);
	}

	@Override
	public void removeBuildRuns(Set<BuildRun> buildRuns) {
		removeRelatedEntities(buildRuns);
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
	public void removeTask(Task task) {
		removeRelatedEntity(task);
	}

	@Override
	public void removeTasks(Set<Task> tasks) {
		removeRelatedEntities(tasks);
	}

	@Override
	public boolean requiresGoodBattery() {
		BuildParameter buildParameter = getBuildParameter(
			"REQUIRES_GOOD_BATTERY");

		if (buildParameter == null) {
			return false;
		}

		String requiresGoodBattery = buildParameter.getValue();

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
	public void setProject(Project project) {
		_project = project;

		if (_project != null) {
			_projectId = _project.getId();
		}
		else {
			_projectId = 0;
		}
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseBuild(JSONObject jsonObject) {
		super(jsonObject);

		_buildName = jsonObject.getString("buildName");
		_jobName = jsonObject.getString("jobName");
		_projectId = jsonObject.optLong("r_projectToBuilds_c_projectId");
		_state = State.get(jsonObject.getJSONObject("state"));
	}

	private Set<Build> _getAllChildBuilds() {
		Set<Build> childBuilds = new HashSet<>(_childBuilds);

		for (Build childBuild : _childBuilds) {
			childBuilds.addAll(childBuild.getChildBuilds());
		}

		return childBuilds;
	}

	private Set<Build> _getAllParentBuilds() {
		Set<Build> parentBuilds = new HashSet<>(_parentBuilds);

		for (Build parentBuild : _parentBuilds) {
			parentBuilds.addAll(parentBuild.getParentBuilds());
		}

		return parentBuilds;
	}

	private static final int _DEFAULT_MAX_NODE_COUNT = 2;

	private static final int _DEFAULT_MIN_NODE_RAM = 12;

	private final String _buildName;
	private final Set<Build> _childBuilds = new HashSet<>();
	private String _jobName;
	private final Set<Build> _parentBuilds = new HashSet<>();
	private Project _project;
	private long _projectId;
	private State _state;

}