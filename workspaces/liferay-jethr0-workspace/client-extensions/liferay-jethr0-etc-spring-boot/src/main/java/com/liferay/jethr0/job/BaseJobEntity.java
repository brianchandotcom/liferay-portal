/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.git.commit.GitCommitEntity;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohortEntity;
import com.liferay.jethr0.routine.RoutineEntity;
import com.liferay.jethr0.task.TaskEntity;
import com.liferay.jethr0.testsuite.TestSuiteEntity;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseJobEntity extends BaseEntity implements JobEntity {

	@Override
	public void addBuildEntities(Set<BuildEntity> buildEntities) {
		addRelatedEntities(buildEntities);
	}

	@Override
	public void addBuildEntity(BuildEntity buildEntity) {
		addRelatedEntity(buildEntity);
	}

	@Override
	public void addJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities) {

		addRelatedEntities(jenkinsCohortEntities);
	}

	@Override
	public void addJenkinsCohortEntity(
		JenkinsCohortEntity jenkinsCohortEntity) {

		addRelatedEntity(jenkinsCohortEntity);
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
	public void addTestSuiteEntities(Set<TestSuiteEntity> testSuiteEntities) {
		addRelatedEntities(testSuiteEntities);
	}

	@Override
	public void addTestSuiteEntity(TestSuiteEntity testSuiteEntity) {
		addRelatedEntity(testSuiteEntity);
	}

	@Override
	public boolean getBlessed() {
		return _blessed;
	}

	@Override
	public Set<BuildEntity> getBuildEntities() {
		return getRelatedEntities(BuildEntity.class);
	}

	@Override
	public URL getEntityURL() {
		return StringUtil.toURL(
			StringUtil.combine(
				Jethr0ContextUtil.getLiferayPortalURL(), "/#/jobs/", getId()));
	}

	@Override
	public GitCommitEntity getGitCommitEntity() {
		return _gitCommitEntity;
	}

	@Override
	public long getGitCommitEntityId() {
		return _gitCommitEntityId;
	}

	@Override
	public Set<BuildEntity> getInitialBuildEntities() {
		Set<BuildEntity> initialBuildEntities = new HashSet<>();

		for (BuildEntity buildEntity : getBuildEntities()) {
			if (buildEntity.isInitialBuild()) {
				initialBuildEntities.add(buildEntity);
			}
		}

		return initialBuildEntities;
	}

	@Override
	public List<JSONObject> getInitialBuildJSONObjects() {
		return Collections.singletonList(getInitialBuildJSONObject());
	}

	@Override
	public URL getJenkinsBranchURL() {
		String jenkinsBranchURL = getParameterValue("jenkinsBranchURL");

		if (StringUtil.isNullOrEmpty(jenkinsBranchURL)) {
			return null;
		}

		return StringUtil.toURL(jenkinsBranchURL);
	}

	@Override
	public Set<JenkinsCohortEntity> getJenkinsCohortEntities() {
		return getRelatedEntities(JenkinsCohortEntity.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		JobEntity.State state = getState();

		JobEntity.Type type = getType();

		jsonObject.put(
			"blessed", getBlessed()
		).put(
			"name", getName()
		).put(
			"parameters", String.valueOf(_getParametersJSONArray())
		).put(
			"priority", getPriority()
		).put(
			"r_gitCommitToJobs_c_gitCommitId", getGitCommitEntityId()
		).put(
			"r_routineToJobs_c_routineId", getRoutineEntityId()
		).put(
			"startDate", StringUtil.toString(getStartDate())
		).put(
			"state", state.getJSONObject()
		).put(
			"type", type.getJSONObject()
		);

		return jsonObject;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Map<String, String> getParameters() {
		return _parameters;
	}

	@Override
	public String getParameterValue(String name) {
		return _parameters.get(name);
	}

	@Override
	public int getPriority() {
		return _priority;
	}

	@Override
	public RoutineEntity getRoutineEntity() {
		return _routineEntity;
	}

	@Override
	public long getRoutineEntityId() {
		return _routineEntityId;
	}

	@Override
	public Date getStartDate() {
		return _startDate;
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
	public Set<TestSuiteEntity> getTestSuiteEntities() {
		return getRelatedEntities(TestSuiteEntity.class);
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public void removeBuildEntities(Set<BuildEntity> buildEntities) {
		removeRelatedEntities(buildEntities);
	}

	@Override
	public void removeBuildEntity(BuildEntity buildEntity) {
		removeRelatedEntity(buildEntity);
	}

	@Override
	public void removeJenkinsCohortEntities(
		Set<JenkinsCohortEntity> jenkinsCohortEntities) {

		removeRelatedEntities(jenkinsCohortEntities);
	}

	@Override
	public void removeJenkinsCohortEntity(
		JenkinsCohortEntity jenkinsCohortEntity) {

		removeRelatedEntity(jenkinsCohortEntity);
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
	public void removeTestSuiteEntities(
		Set<TestSuiteEntity> testSuiteEntities) {

		removeRelatedEntities(testSuiteEntities);
	}

	@Override
	public void removeTestSuiteEntity(TestSuiteEntity testSuiteEntity) {
		removeRelatedEntity(testSuiteEntity);
	}

	@Override
	public void setBlessed(boolean blessed) {
		_blessed = blessed;
	}

	@Override
	public void setGitCommitEntity(GitCommitEntity gitCommitEntity) {
		_gitCommitEntity = gitCommitEntity;

		if (_gitCommitEntity != null) {
			_gitCommitEntityId = _gitCommitEntity.getId();
		}
		else {
			_gitCommitEntityId = 0;
		}
	}

	@Override
	public void setJenkinsBranchURL(URL jenkinsBranchURL) {
		setParameterValue("jenkinsBranchURL", String.valueOf(jenkinsBranchURL));
	}

	@Override
	public void setJSONObject(JSONObject jsonObject) {
		super.setJSONObject(jsonObject);

		_blessed = jsonObject.optBoolean("blessed");
		_gitCommitEntityId = jsonObject.optLong(
			"r_gitCommitToJobs_c_gitCommitId");
		_name = jsonObject.getString("name");
		_parameters = new HashMap<>();
		_priority = jsonObject.optInt("priority");
		_routineEntityId = jsonObject.optLong("r_routineToJobs_c_routineId");
		_startDate = StringUtil.toDate(jsonObject.optString("startDate"));
		_state = State.get(jsonObject.get("state"));
		_type = Type.get(jsonObject.get("type"));

		String parameters = jsonObject.getString("parameters");

		if (StringUtil.isNullOrEmpty(parameters)) {
			return;
		}

		try {
			JSONArray parametersJSONArray = new JSONArray(parameters);

			for (int i = 0; i < parametersJSONArray.length(); i++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(i);

				_parameters.put(
					parameterJSONObject.getString("key"),
					parameterJSONObject.getString("value"));
			}
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsonException);
			}
		}
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setParameters(Map<String, String> parameters) {
		_parameters.clear();

		_parameters.putAll(parameters);
	}

	@Override
	public void setParameterValue(String name, String value) {
		_parameters.put(name, value);
	}

	@Override
	public void setPriority(int priority) {
		_priority = priority;
	}

	@Override
	public void setRoutineEntity(RoutineEntity routineEntity) {
		_routineEntity = routineEntity;

		if (_routineEntity != null) {
			_routineEntityId = _routineEntity.getId();
		}
		else {
			_routineEntityId = 0;
		}
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	protected String getBranchURLGroupValue(URL branchURL, String groupName) {
		if (branchURL == null) {
			return null;
		}

		Matcher matcher = _branchURLPattern.matcher(String.valueOf(branchURL));

		if (!matcher.find()) {
			return null;
		}

		return matcher.group(groupName);
	}

	protected JSONObject getInitialBuildJSONObject() {
		JSONObject initialBuildJSONObject = new JSONObject();

		initialBuildJSONObject.put(
			"initialBuild", true
		).put(
			"jenkinsJobName", getJenkinsJobName()
		).put(
			"name", "top-level"
		).put(
			"parameters", String.valueOf(_getInitialBuildParametersJSONArray())
		).put(
			"state", BuildEntity.State.OPENED
		);

		return initialBuildJSONObject;
	}

	protected Map<String, String> getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", String.valueOf(getPriority())
		).put(
			"JENKINS_GITHUB_BRANCH_NAME", getJenkinsBranchName()
		).put(
			"JENKINS_GITHUB_BRANCH_USERNAME", getJenkinsBranchUserName()
		).build();
	}

	protected String getJenkinsBranchName() {
		return getBranchURLGroupValue(getJenkinsBranchURL(), "branchName");
	}

	protected String getJenkinsBranchUserName() {
		return getBranchURLGroupValue(getJenkinsBranchURL(), "userName");
	}

	protected abstract String getJenkinsJobName();

	protected Boolean getParameterValueBoolean(String name) {
		String valueBoolean = getParameterValue(name);

		if (StringUtil.isNullOrEmpty(valueBoolean)) {
			return null;
		}

		valueBoolean = StringUtil.toLowerCase(valueBoolean);

		if (!valueBoolean.equals("false") && !valueBoolean.equals("true")) {
			return null;
		}

		return Boolean.valueOf(valueBoolean);
	}

	protected Integer getParameterValueInteger(String name) {
		String valueInteger = getParameterValue(name);

		if (StringUtil.isNullOrEmpty(valueInteger)) {
			return null;
		}

		return Integer.valueOf(valueInteger);
	}

	protected Long getParameterValueLong(String name) {
		String valueLong = getParameterValue(name);

		if (StringUtil.isNullOrEmpty(valueLong)) {
			return null;
		}

		return Long.valueOf(valueLong);
	}

	protected URL getParameterValueURL(String name) {
		String urlString = getParameterValue(name);

		if (StringUtil.isNullOrEmpty(urlString)) {
			return null;
		}

		return StringUtil.toURL(urlString);
	}

	protected void setParameterValueBoolean(String name, Boolean valueBoolean) {
		setParameterValue(name, String.valueOf(valueBoolean));
	}

	protected void setParameterValueInteger(String name, Integer valueInteger) {
		setParameterValue(name, String.valueOf(valueInteger));
	}

	protected void setParameterValueLong(String name, Long valueLong) {
		setParameterValue(name, String.valueOf(valueLong));
	}

	protected void setParameterValueURL(String name, URL valueURL) {
		if (valueURL == null) {
			setParameterValue(name, null);
		}

		setParameterValue(name, String.valueOf(valueURL));
	}

	private JSONArray _getInitialBuildParametersJSONArray() {
		JSONArray initialBuildParametersJSONArray = new JSONArray();

		Map<String, String> initialBuildParameters =
			getInitialBuildParameters();

		for (Map.Entry<String, String> initialBuildParameter :
				initialBuildParameters.entrySet()) {

			String initialBuildParameterValue =
				initialBuildParameter.getValue();

			if (StringUtil.isNullOrEmpty(initialBuildParameterValue) ||
				initialBuildParameterValue.equals("null")) {

				continue;
			}

			JSONObject initialBuildParameterJSONObject = new JSONObject();

			initialBuildParameterJSONObject.put(
				"name", initialBuildParameter.getKey()
			).put(
				"value", initialBuildParameterValue
			);

			initialBuildParametersJSONArray.put(
				initialBuildParameterJSONObject);
		}

		return initialBuildParametersJSONArray;
	}

	private JSONArray _getParametersJSONArray() {
		JSONArray parametersJSONArray = new JSONArray();

		if (_parameters.isEmpty()) {
			return parametersJSONArray;
		}

		Set<String> parameterNames = new TreeSet<>(_parameters.keySet());

		for (String parameterName : parameterNames) {
			JSONObject parameterJSONObject = new JSONObject();

			parameterJSONObject.put(
				"key", parameterName
			).put(
				"value", _parameters.get(parameterName)
			);

			parametersJSONArray.put(parameterJSONObject);
		}

		return parametersJSONArray;
	}

	private static final Log _log = LogFactory.getLog(BaseJobEntity.class);

	private static final Pattern _branchURLPattern = Pattern.compile(
		"https://github.com/(?<userName>[^/]+)/(?<repositoryName>[^/]+)/tree/" +
			"(?<branchName>[^/]+)");

	private boolean _blessed;
	private GitCommitEntity _gitCommitEntity;
	private long _gitCommitEntityId;
	private String _name;
	private Map<String, String> _parameters;
	private int _priority;
	private RoutineEntity _routineEntity;
	private long _routineEntityId;
	private Date _startDate;
	private State _state;
	private Type _type;

}