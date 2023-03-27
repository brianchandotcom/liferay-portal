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

package com.liferay.jethr0.project;

import com.liferay.jethr0.build.Build;
import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.testsuite.TestSuite;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface Project {

	public void addBuild(Build build);

	public void addBuilds(List<Build> builds);

	public void addGitBranch(GitBranch gitBranch);

	public void addGitBranches(List<GitBranch> gitBranches);

	public void addTestSuite(TestSuite testSuite);

	public void addTestSuites(List<TestSuite> testSuites);

	public List<Build> getBuilds();

	public Date getCreatedDate();

	public List<GitBranch> getGitBranches();

	public long getId();

	public JSONObject getJSONObject();

	public String getName();

	public int getPriority();

	public State getState();

	public List<TestSuite> getTestSuites();

	public Type getType();

	public void removeBuild(Build build);

	public void removeBuilds(List<Build> builds);

	public void removeGitBranch(GitBranch gitBranch);

	public void removeGitBranches(List<GitBranch> gitBranches);

	public void removeTestSuite(TestSuite testSuite);

	public void removeTestSuites(List<TestSuite> testSuites);

	public void setName(String name);

	public void setPriority(int priority);

	public void setState(State state);

	public enum State {

		COMPLETED("completed"), EVALUATING("evaluating"), OPENED("opened"),
		PREPARING("preparing"), QUEUED("queued"), RUNNING("running");

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

	public enum Type {

		DEFAULT_JOB("defaultJob"), MAINTENANCE_JOB("maintenanceJob"),
		PULL_REQUEST_JOB("pullRequestJob"), UPSTREAM_JOB("upstreamJob"),
		VERIFICATION_JOB("verificationJob");

		public static Type get(JSONObject jsonObject) {
			return getByKey(jsonObject.getString("key"));
		}

		public static Type getByKey(String key) {
			return _types.get(key);
		}

		public JSONObject getJSONObject() {
			return new JSONObject("{\"key\": \"" + getKey() + "\"}");
		}

		public String getKey() {
			return _key;
		}

		private Type(String key) {
			_key = key;
		}

		private static final Map<String, Type> _types = new HashMap<>();

		static {
			for (Type type : values()) {
				_types.put(type.getKey(), type);
			}
		}

		private final String _key;

	}

}