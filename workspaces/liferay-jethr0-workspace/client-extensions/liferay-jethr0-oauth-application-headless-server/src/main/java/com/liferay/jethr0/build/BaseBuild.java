/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.jethr0.build;

import com.liferay.jethr0.build.parameter.BuildParameter;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseBuild implements Build {

	@Override
	public void addBuildParameter(BuildParameter buildParameter) {
		addBuildParameters(Arrays.asList(buildParameter));
	}

	@Override
	public void addBuildParameters(List<BuildParameter> buildParameters) {
		buildParameters.removeAll(Collections.singleton(null));

		for (BuildParameter buildParameter : buildParameters) {
			_buildParameters.put(buildParameter.getName(), buildParameter);
		}
	}

	@Override
	public void addTask(Task task) {
		addTasks(Arrays.asList(task));
	}

	@Override
	public void addTasks(List<Task> tasks) {
		for (Task task : tasks) {
			if (_tasks.contains(task)) {
				continue;
			}

			_tasks.add(task);
		}
	}

	@Override
	public String getBuildName() {
		return _buildName;
	}

	@Override
	public BuildParameter getBuildParameter(String name) {
		return _buildParameters.get(name);
	}

	@Override
	public List<BuildParameter> getBuildParameters() {
		return new ArrayList<>(_buildParameters.values());
	}

	@Override
	public List<Build> getChildBuilds() {
		return _childBuilds;
	}

	@Override
	public long getId() {
		return _id;
	}

	@Override
	public String getJobName() {
		return _jobName;
	}

	@Override
	public JSONObject getJSONObject() {
		State state = getState();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put(
			"buildName", getBuildName()
		).put(
			"id", getId()
		).put(
			"jobName", getJobName()
		).put(
			"state", state.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public int getMaxSlaveCount() {
		BuildParameter buildParameter = getBuildParameter("MAX_SLAVE_COUNT");

		if (buildParameter == null) {
			return _DEFAULT_MAX_SLAVE_COUNT;
		}

		String value = buildParameter.getValue();

		if ((value == null) || !value.matches("\\d+")) {
			return _DEFAULT_MAX_SLAVE_COUNT;
		}

		return Integer.valueOf(value);
	}

	@Override
	public int getMinSlaveRAM() {
		BuildParameter buildParameter = getBuildParameter("MIN_SLAVE_RAM");

		if (buildParameter == null) {
			return _DEFAULT_MIN_SLAVE_RAM;
		}

		String value = buildParameter.getValue();

		if ((value == null) || !value.matches("\\d+")) {
			return _DEFAULT_MIN_SLAVE_RAM;
		}

		return Integer.valueOf(value);
	}

	public List<Build> getParentBuilds() {
		return _parentBuilds;
	}

	@Override
	public Project getProject() {
		return _project;
	}

	@Override
	public State getState() {
		return _state;
	}

	@Override
	public List<Task> getTasks() {
		return _tasks;
	}

	@Override
	public boolean isChildBuild(Build parentBuild) {
		List<Build> parentBuilds = _getAllParentBuilds();

		return parentBuilds.contains(parentBuild);
	}

	@Override
	public boolean isParentBuild(Build childBuild) {
		List<Build> childBuilds = _getAllChildBuilds();

		return childBuilds.contains(childBuild);
	}

	@Override
	public void removeBuildParameter(BuildParameter buildParameter) {
		_buildParameters.remove(buildParameter.getName());
	}

	@Override
	public void removeBuildParameters(List<BuildParameter> buildParameters) {
		for (BuildParameter buildParameter : buildParameters) {
			removeBuildParameter(buildParameter);
		}
	}

	@Override
	public void removeTask(Task task) {
		_tasks.remove(task);
	}

	@Override
	public void removeTasks(List<Task> tasks) {
		_tasks.removeAll(tasks);
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
	public void setState(State state) {
		_state = state;
	}

	protected BaseBuild(Project project, JSONObject jsonObject) {
		_project = project;

		_buildName = jsonObject.getString("buildName");
		_id = jsonObject.getLong("id");
		_jobName = jsonObject.getString("jobName");
		_state = State.get(jsonObject.getJSONObject("state"));
	}

	private List<Build> _getAllChildBuilds() {
		List<Build> childBuilds = new ArrayList<>(_childBuilds);

		for (Build childBuild : _childBuilds) {
			childBuilds.addAll(childBuild.getChildBuilds());
		}

		return childBuilds;
	}

	private List<Build> _getAllParentBuilds() {
		List<Build> parentBuilds = new ArrayList<>(_parentBuilds);

		for (Build parentBuild : _parentBuilds) {
			parentBuilds.addAll(parentBuild.getParentBuilds());
		}

		return parentBuilds;
	}

	private static final int _DEFAULT_MAX_SLAVE_COUNT = 2;

	private static final int _DEFAULT_MIN_SLAVE_RAM = 12;

	private final String _buildName;
	private final Map<String, BuildParameter> _buildParameters =
		new HashMap<>();
	private final List<Build> _childBuilds = new ArrayList<>();
	private final long _id;
	private String _jobName;
	private final List<Build> _parentBuilds = new ArrayList<>();
	private final Project _project;
	private State _state;
	private final List<Task> _tasks = new ArrayList<>();

}