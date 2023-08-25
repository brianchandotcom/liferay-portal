/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d;

import com.liferay.jethr0.bui1d.parameter.BuildParameter;
import com.liferay.jethr0.bui1d.run.BuildRun;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.environment.Environment;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
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

	public JenkinsNodeEntity.Type getJenkinsNodeType();

	public String getJobName();

	public int getMaxNodeCount();

	public int getMinNodeRAM();

	public Set<Build> getParentBuilds();

	public Project getProject();

	public long getProjectId();

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