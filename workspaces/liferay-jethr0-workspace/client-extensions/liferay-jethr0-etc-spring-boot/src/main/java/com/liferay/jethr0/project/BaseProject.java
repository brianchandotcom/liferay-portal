/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.project;

import com.liferay.jethr0.bui1d.Build;
import com.liferay.jethr0.entity.BaseEntity;
import com.liferay.jethr0.gitbranch.GitBranch;
import com.liferay.jethr0.jenkins.cohort.JenkinsCohort;
import com.liferay.jethr0.task.Task;
import com.liferay.jethr0.testsuite.TestSuite;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseProject extends BaseEntity implements Project {

	@Override
	public void addBuild(Build build) {
		addRelatedEntity(build);
	}

	@Override
	public void addBuilds(Set<Build> builds) {
		addRelatedEntities(builds);
	}

	@Override
	public void addGitBranch(GitBranch gitBranch) {
		addRelatedEntity(gitBranch);
	}

	@Override
	public void addGitBranches(Set<GitBranch> gitBranches) {
		addRelatedEntities(gitBranches);
	}

	@Override
	public void addJenkinsCohort(JenkinsCohort jenkinsCohort) {
		addRelatedEntity(jenkinsCohort);
	}

	@Override
	public void addJenkinsCohorts(Set<JenkinsCohort> jenkinsCohorts) {
		addRelatedEntities(jenkinsCohorts);
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
	public void addTestSuite(TestSuite testSuite) {
		addRelatedEntity(testSuite);
	}

	@Override
	public void addTestSuites(Set<TestSuite> testSuites) {
		addRelatedEntities(testSuites);
	}

	@Override
	public Set<Build> getBuilds() {
		return getRelatedEntities(Build.class);
	}

	@Override
	public Set<GitBranch> getGitBranches() {
		return getRelatedEntities(GitBranch.class);
	}

	@Override
	public Set<JenkinsCohort> getJenkinsCohorts() {
		return getRelatedEntities(JenkinsCohort.class);
	}

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		Project.State state = getState();
		Project.Type type = getType();

		jsonObject.put(
			"name", getName()
		).put(
			"position", getPosition()
		).put(
			"priority", getPriority()
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
	public int getPosition() {
		return _position;
	}

	@Override
	public int getPriority() {
		return _priority;
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
	public Set<Task> getTasks() {
		return getRelatedEntities(Task.class);
	}

	@Override
	public Set<TestSuite> getTestSuites() {
		return getRelatedEntities(TestSuite.class);
	}

	@Override
	public Type getType() {
		return _type;
	}

	@Override
	public void removeBuild(Build build) {
		removeRelatedEntity(build);
	}

	@Override
	public void removeBuilds(Set<Build> builds) {
		removeRelatedEntities(builds);
	}

	@Override
	public void removeGitBranch(GitBranch gitBranch) {
		removeRelatedEntity(gitBranch);
	}

	@Override
	public void removeGitBranches(Set<GitBranch> gitBranches) {
		removeRelatedEntities(gitBranches);
	}

	@Override
	public void removeJenkinsCohort(JenkinsCohort jenkinsCohort) {
		removeRelatedEntity(jenkinsCohort);
	}

	@Override
	public void removeJenkinsCohorts(Set<JenkinsCohort> jenkinsCohorts) {
		removeRelatedEntities(jenkinsCohorts);
	}

	@Override
	public void removeTask(Task task) {
		removeRelatedEntity(task);
	}

	public void removeTasks(Set<Task> tasks) {
		removeRelatedEntities(tasks);
	}

	@Override
	public void removeTestSuite(TestSuite testSuite) {
		removeRelatedEntity(testSuite);
	}

	@Override
	public void removeTestSuites(Set<TestSuite> testSuites) {
		removeRelatedEntities(testSuites);
	}

	@Override
	public void setName(String name) {
		_name = name;
	}

	@Override
	public void setPosition(int position) {
		if (position <= 0) {
			position = Integer.MAX_VALUE;
		}

		_position = position;
	}

	@Override
	public void setPriority(int priority) {
		_priority = priority;
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public void setState(State state) {
		_state = state;
	}

	protected BaseProject(JSONObject jsonObject) {
		super(jsonObject);

		_name = jsonObject.getString("name");

		int position = jsonObject.optInt("position", Integer.MAX_VALUE);

		if (position <= 0) {
			position = Integer.MAX_VALUE;
		}

		_position = position;

		_priority = jsonObject.optInt("priority");
		_startDate = StringUtil.toDate(jsonObject.optString("startDate"));
		_state = State.get(jsonObject.getJSONObject("state"));
		_type = Type.get(jsonObject.getJSONObject("type"));
	}

	private String _name;
	private int _position;
	private int _priority;
	private Date _startDate;
	private State _state;
	private final Type _type;

}