/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.Entity;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.testsuite.TestSuiteEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.net.URL;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface JobEntity extends Entity {

	public static ParameterDefinition PARAMETER_DEFINITION_JENKINS_GITHUB_URL =
		new ParameterDefinition(
			"jenkinsGitHubURL", "Jenkins GitHub URL",
			ParameterDefinition.Type.URL,
			"https://github.com/liferay/liferay-jenkins-ee/tree/master",
			"e.g. https://github.com/[user]/liferay-jenkins-ee/tree/[branch]",
			"https://github.com/[^/]+/liferay-jenkins-ee/tree/[^/]+");

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

	public URL getJenkinsGitHubURL();

	public String getName();

	public Map<String, String> getParameters();

	public String getParameterValue(String name);

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

	public void setJenkinsGitHubURL(URL jenkinsGitHubURL);

	public void setName(String name);

	public void setParameterValue(String name, String value);

	public void setPriority(int priority);

	public void setStartDate(Date startDate);

	public void setState(State state);

	public class ParameterDefinition {

		public ParameterDefinition(
			String key, String label, Type type, String valueDefault,
			String valueDescription, String valueRegex) {

			_key = key;
			_label = label;
			_type = type;
			_valueDefault = valueDefault;
			_valueDescription = valueDescription;
			_valueRegex = valueRegex;
		}

		public JSONObject getJSONObject() {
			JSONObject jsonObject = new JSONObject();

			jsonObject.put(
				"key", getKey()
			).put(
				"label", getLabel()
			).put(
				"type", getType()
			).put(
				"valueDefault", getValueDefault()
			).put(
				"valueDescription", getValueDescription()
			).put(
				"valueRegex", getValueRegex()
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		public String getLabel() {
			return _label;
		}

		public Type getType() {
			return _type;
		}

		public String getValueDefault() {
			return _valueDefault;
		}

		public String getValueDescription() {
			return _valueDescription;
		}

		public String getValueRegex() {
			return _valueRegex;
		}

		public enum Type {

			STRING, URL

		}

		private final String _key;
		private final String _label;
		private final Type _type;
		private final String _valueDefault;
		private final String _valueDescription;
		private final String _valueRegex;

	}

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

		DEFAULT("default", "Default", DefaultJobEntity.class),
		PORTAL_PULL_REQUEST(
			"portalPullRequest", "Portal Pull Request",
			DefaultPortalPullRequestJobEntity.class),
		PORTAL_PULL_REQUEST_SF(
			"portalPullRequestSF", "Portal Pull Request SF",
			SFPortalPullRequestJobEntity.class),
		PORTAL_UPSTREAM_ACCEPTANCE(
			"portalUpstreamAcceptance", "Portal Upstream Acceptance",
			AcceptancePortalUpstreamJobEntity.class),
		PORTAL_UPSTREAM_TEST_SUITE(
			"portalUpstreamTestSuite", "Portal Upstream Test Suite",
			TestSuitePortalUpstreamJobEntity.class);

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
			).put(
				"parameterDefinitions", _getJobParameterDefinitionsJSONArray()
			);

			return jsonObject;
		}

		public String getKey() {
			return _key;
		}

		public String getName() {
			return _name;
		}

		private Type(String key, String name, Class<?> clazz) {
			_key = key;
			_name = name;
			_clazz = clazz;
		}

		private JSONArray _getJobParameterDefinitionsJSONArray() {
			try {
				Method method = _clazz.getMethod("getParameterDefinitions");

				Object object = method.invoke(null);

				if (!(object instanceof List)) {
					return null;
				}

				JSONArray parameterDefinitionsJSONArray = new JSONArray();

				List<ParameterDefinition> parameterDefinitions =
					(List<ParameterDefinition>)object;

				for (ParameterDefinition parameterDefinition :
						parameterDefinitions) {

					parameterDefinitionsJSONArray.put(
						parameterDefinition.getJSONObject());
				}

				return parameterDefinitionsJSONArray;
			}
			catch (IllegalAccessException | InvocationTargetException |
				   NoSuchMethodException exception) {

				throw new RuntimeException(exception);
			}
		}

		private static final Map<String, Type> _types = new HashMap<>();

		static {
			for (Type type : values()) {
				_types.put(type.getKey(), type);
			}
		}

		private final Class<?> _clazz;
		private final String _key;
		private final String _name;

	}

}