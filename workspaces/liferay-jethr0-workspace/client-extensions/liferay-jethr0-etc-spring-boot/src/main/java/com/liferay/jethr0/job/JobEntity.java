/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.gitbranch.GitBranchEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.testsuite.TestSuiteEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface JobEntity extends Entity {

	public void addBuildEntities(Set<BuildEntity> buildEntities);

	public void addBuildEntity(BuildEntity buildEntity);

	public void addGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void addGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void addJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities);

	public void addJenkinsCohortEntity(JenkinsCohortEntity jenkinsCohortEntity);

	public void addTaskEntities(Set<TaskEntity> taskEntities);

	public void addTaskEntity(TaskEntity taskEntity);

	public void addTestSuiteEntities(Set<TestSuiteEntity> testSuiteEntities);

	public void addTestSuiteEntity(TestSuiteEntity testSuiteEntity);

	public Set<BuildEntity> getBuildEntities();

	public Set<GitBranchEntity> getGitBranchEntities();

	public Set<BuildEntity> getInitialBuildEntities();

	public List<JSONObject> getInitialBuildJSONObjects();

	public Set<JenkinsCohortEntity> getJenkinsCohortEntities();

	public String getName();

	public int getPriority();

	public Date getStartDate();

	public State getState();

	public Set<TaskEntity> getTaskEntities();

	public Set<TestSuiteEntity> getTestSuiteEntities();

	public Type getType();

	public void removeBuildEntities(Set<BuildEntity> buildEntities);

	public void removeBuildEntity(BuildEntity buildEntity);

	public void removeGitBranchEntities(Set<GitBranchEntity> gitBranchEntities);

	public void removeGitBranchEntity(GitBranchEntity gitBranchEntity);

	public void removeJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities);

	public void removeJenkinsCohortEntity(
		JenkinsCohortEntity jenkinsCohortEntity);

	public void removeTaskEntities(Set<TaskEntity> taskEntities);

	public void removeTaskEntity(TaskEntity taskEntity);

	public void removeTestSuiteEntities(Set<TestSuiteEntity> testSuiteEntities);

	public void removeTestSuiteEntity(TestSuiteEntity testSuiteEntity);

	public void setName(String name);

	public void setPriority(int priority);

	public void setStartDate(Date startDate);

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
				"key", getKey()
			).put(
				"name", getName()
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		public String getName() {
			return _name;
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

	public enum Type {

		DEFAULT("default", "Default"),
		PORTAL_PULL_REQUEST("portalPullRequest", "Portal Pull Request"),
		PORTAL_PULL_REQUEST_SF("portalPullRequestSF", "Portal Pull Request SF");

		public static Type get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static Type getByKey(String key) {
			return _types.get(key);
		}

		public static Set<String> getKeys() {
			return _types.keySet();
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"key", getKey()
			).put(
				"name", getName()
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		public String getName() {
			return _name;
		}

		private Type(String key, String name) {
			_key = key;
			_name = name;
		}

		private static final Map<String, Type> _types = new HashMap<>();

		static {
			for (Type type : values()) {
				_types.put(type.getKey(), type);
			}
		}

		private final String _key;
		private final String _name;

	}

}