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
import com.liferay.jethr0.build.run.BuildRun;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.jenkins.node.JenkinsNode;
import com.liferay.jethr0.project.Project;
import com.liferay.jethr0.task.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface Build extends Entity {

	public void addBuildParameter(BuildParameter buildParameter);

	public void addBuildParameters(Set<BuildParameter> buildParameters);

	public void addBuildRun(BuildRun buildRun);

	public void addBuildRuns(Set<BuildRun> buildRuns);

	public void addEnvironment(Environment environment);

	public void addEnvironments(Set<Environment> environments);

	public void addTask(Task task);

	public void addTasks(Set<Task> tasks);

	public String getBuildName();

	public BuildParameter getBuildParameter(String name);

	public Set<BuildParameter> getBuildParameters();

	public Set<BuildRun> getBuildRuns();

	public Set<Build> getChildBuilds();

	public Set<Environment> getEnvironments();

	public String getJobName();

	public int getMaxNodeCount();

	public int getMinNodeRAM();

	public JenkinsNode.Type getNodeType();

	public Set<Build> getParentBuilds();

	public Project getProject();

	public State getState();

	public Set<Task> getTasks();

	public boolean isChildBuild(Build parentBuild);

	public boolean isParentBuild(Build build);

	public void removeBuildParameter(BuildParameter buildParameter);

	public void removeBuildParameters(Set<BuildParameter> buildParameters);

	public void removeBuildRun(BuildRun buildRun);

	public void removeBuildRuns(Set<BuildRun> buildRuns);

	public void removeEnvironment(Environment environment);

	public void removeEnvironments(Set<Environment> environments);

	public void removeTask(Task task);

	public void removeTasks(Set<Task> tasks);

	public boolean requiresGoodBattery();

	public void setJobName(String jobName);

	public void setProject(Project project);

	public void setState(State state);

	public enum State {

		BLOCKED("blocked"), COMPLETED("completed"), OPENED("opened"),
		QUEUED("queued"), RUNNING("running");

		public static State get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static State getByKey(String key) {
			return _states.get(key);
		}

		public JSONObject getJSONObject() {
			return new JSONObject("{\"key\": \"" + getKey() + "\"}");
		}

		public String getKey() {
			return _key;
		}

		private State(String key) {
			_key = key;
		}

		private static final Map<String, State> _states = new HashMap<>();

		static {
			for (State state : values()) {
				_states.put(state.getKey(), state);
			}
		}

		private final String _key;

	}

}