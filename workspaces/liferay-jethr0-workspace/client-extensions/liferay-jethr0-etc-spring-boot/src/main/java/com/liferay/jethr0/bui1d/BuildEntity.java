/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.bui1d;

import com.liferay.jethr0.bui1d.parameter.BuildParameterEntity;
import com.liferay.jethr0.bui1d.run.BuildRunEntity;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.environment.EnvironmentEntity;
import com.liferay.jethr0.jenkins.node.JenkinsNodeEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.task.TaskEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface BuildEntity extends Entity {

	public void addBuildParameterEntities(
		Set<BuildParameterEntity> buildParameterEntities);

	public void addBuildParameterEntity(
		BuildParameterEntity buildParameterEntity);

	public void addBuildRunEntities(Set<BuildRunEntity> buildRunEntities);

	public void addBuildRunEntity(BuildRunEntity buildRunEntity);

	public void addEnvironmentEntities(
		Set<EnvironmentEntity> environmentEntities);

	public void addEnvironmentEntity(EnvironmentEntity environmentEntity);

	public void addTaskEntities(Set<TaskEntity> taskEntities);

	public void addTaskEntity(TaskEntity taskEntity);

	public Set<BuildParameterEntity> getBuildParameterEntities();

	public BuildParameterEntity getBuildParameterEntity(String name);

	public Set<BuildRunEntity> getBuildRunEntities();

	public Set<BuildEntity> getChildBuildEntities();

	public Set<EnvironmentEntity> getEnvironmentEntities();

	public List<BuildRunEntity> getHistoryBuildRunEntities();

	public String getJenkinsJobName();

	public JenkinsNodeEntity.Type getJenkinsNodeType();

	public JobEntity getJobEntity();

	public long getJobEntityId();

	public int getMaxNodeCount();

	public int getMinNodeRAM();

	public String getName();

	public Set<BuildEntity> getParentBuildEntities();

	public State getState();

	public Set<TaskEntity> getTaskEntities();

	public boolean isChildBuildEntity(BuildEntity parentBuildEntity);

	public boolean isInitialBuild();

	public boolean isParentBuildEntity(BuildEntity buildEntity);

	public void removeBuildParameterEntities(
		Set<BuildParameterEntity> buildParameterEntities);

	public void removeBuildParameterEntity(
		BuildParameterEntity buildParameterEntity);

	public void removeBuildRunEntities(Set<BuildRunEntity> buildRunEntities);

	public void removeBuildRunEntity(BuildRunEntity buildRunEntity);

	public void removeEnvironmentEntities(
		Set<EnvironmentEntity> environmentEntities);

	public void removeEnvironmentEntity(EnvironmentEntity environmentEntity);

	public void removeTaskEntities(Set<TaskEntity> taskEntities);

	public void removeTaskEntity(TaskEntity taskEntity);

	public boolean requiresGoodBattery();

	public void setJenkinsJobName(String jenkinsJobName);

	public void setJobEntity(JobEntity jobEntity);

	public void setState(State state);

	public enum State {

		BLOCKED("blocked", "Blocked"), COMPLETED("completed", "Completed"),
		OPENED("opened", "Opened"), QUEUED("queued", "Queued"),
		RUNNING("running", "Running");

		public static State get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static State getByKey(String key) {
			return _states.get(key);
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"key", _key
			).put(
				"name", _name
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		private State(String key, String name) {
			_key = key;
			_name = name;
		}

		private static final Map<String, State> _states = new HashMap<>();

		static {
			for (State state : values()) {
				_states.put(state.getKey(), state);
			}
		}

		private final String _key;
		private final String _name;

	}

}