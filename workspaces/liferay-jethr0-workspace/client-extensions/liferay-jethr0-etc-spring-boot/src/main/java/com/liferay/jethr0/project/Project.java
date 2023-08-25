/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.testsuite.TestSuite;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface Project extends Entity {

	public void addBuild(Build build);

	public void addBuilds(Set<Build> builds);

	public void addGitBranch(GitBranch gitBranch);

	public void addGitBranches(Set<GitBranch> gitBranches);

	public void addJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities);

	public void addJenkinsCohortEntity(JenkinsCohortEntity jenkinsCohortEntity);

	public void addTaskEntities(Set<TaskEntity> taskEntities);

	public void addTaskEntity(TaskEntity taskEntity);

	public void addTestSuite(TestSuite testSuite);

	public void addTestSuites(Set<TestSuite> testSuites);

	public Set<Build> getBuilds();

	public Set<GitBranch> getGitBranches();

	public Set<JenkinsCohortEntity> getJenkinsCohortEntities();

	public String getName();

	public int getPosition();

	public int getPriority();

	public Date getStartDate();

	public State getState();

	public Set<TaskEntity> getTaskEntities();

	public Set<TestSuite> getTestSuites();

	public Type getType();

	public void removeBuild(Build build);

	public void removeBuilds(Set<Build> builds);

	public void removeGitBranch(GitBranch gitBranch);

	public void removeGitBranches(Set<GitBranch> gitBranches);

	public void removeJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities);

	public void removeJenkinsCohortEntity(
		JenkinsCohortEntity jenkinsCohortEntity);

	public void removeTaskEntities(Set<TaskEntity> taskEntities);

	public void removeTaskEntity(TaskEntity taskEntity);

	public void removeTestSuite(TestSuite testSuite);

	public void removeTestSuites(Set<TestSuite> testSuites);

	public void setName(String name);

	public void setPosition(int position);

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

		DEFAULT_JOB("defaultJob", "Default Job"),
		MAINTENANCE_JOB("maintenanceJob", "Maintenance Job"),
		PULL_REQUEST_JOB("pullRequestJob", "Pull Request Job"),
		UPSTREAM_JOB("upstreamJob", "Upstream Job"),
		VERIFICATION_JOB("verificationJob", "Verification Job");

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